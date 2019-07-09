package sassycitrus.craftmancy.capability;

import java.util.concurrent.Callable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.network.Network;

public class ManaCapabilityHandler
{
    private static final int DEFAULT_CAPACITY = 10;

    @CapabilityInject(IManaHandler.class)
    public static final Capability<IManaHandler> CAPABILITY_MANA = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IManaHandler.class, new Storage(), new ManaFactory());
        MinecraftForge.EVENT_BUS.register(new ManaCapabilityHandler());
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof EntityPlayer)
        {
            event.addCapability(new ResourceLocation(Craftmancy.modid, "CAPABILITY_MANA"), new Provider());
        }
    }

    @SubscribeEvent
    public void entityJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.getEntity() instanceof EntityPlayer && !event.getWorld().isRemote)
        {
            EntityPlayer player = (EntityPlayer) event.getEntity();
            Network.syncPlayerMana(player);
        }
    }

    @SubscribeEvent
    public void clonePlayer(PlayerEvent.Clone event)
    {
        final IManaHandler original = getHandler(event.getOriginal());
        final IManaHandler clone = getHandler(event.getEntity());

        clone.setMana(original.getMana());
    }

    public static IManaHandler getHandler(Entity entity)
    {
        if (entity.hasCapability(CAPABILITY_MANA, EnumFacing.DOWN))
        {
            return entity.getCapability(CAPABILITY_MANA, EnumFacing.DOWN);
        }

        return null;
    }

    public interface IManaHandler
    {
        int getCapacity();
        void setCapacity(int capacity);
        int getMana();
        void setMana(int mana);
        void addMana(int mana);
        boolean removeMana(int mana);
    }

    public static class ManaFactory implements Callable<IManaHandler>
    {
        @Override
        public IManaHandler call() throws Exception
        {
            IManaHandler handler  = new IManaHandler()
            {
                private int capacity;
                private int mana;

                @Override
                public int getCapacity()
                {
                    return this.capacity;
                }

                @Override
                public void setCapacity(int capacity)
                {
                    this.capacity = capacity;
                }

                @Override
                public int getMana()
                {
                    return this.mana;
                }

                @Override
                public void setMana(int mana)
                {
                    this.mana = mana;

                    if (this.mana > this.capacity)
                    {
                        this.mana = this.capacity;
                    }
                }

                @Override
                public void addMana(int mana)
                {
                    this.mana += mana;

                    if (this.mana > this.capacity)
                    {
                        this.mana = this.capacity;
                    }
                }

                @Override
                public boolean removeMana(int mana)
                {
                    if (this.mana < mana)
                    {
                        return false;
                    }

                    this.mana -= mana;

                    if (this.mana < 0)
                    {
                        this.mana = 0;
                    }

                    return true;
                }
            };

            handler.setCapacity(DEFAULT_CAPACITY);
            return handler;
        }
    }

    public static class Storage implements Capability.IStorage<IManaHandler>
    {
        @Override
        public NBTBase writeNBT(Capability<IManaHandler> capability, IManaHandler instance, EnumFacing side)
        {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("capacity", instance.getCapacity());
            nbt.setInteger("mana", instance.getMana());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IManaHandler> capability, IManaHandler instance, EnumFacing side, NBTBase baseNBT)
        {
            final NBTTagCompound nbt = (NBTTagCompound) baseNBT;
            instance.setCapacity(nbt.getInteger("capacity"));
            instance.setMana(nbt.getInteger("mana"));
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        IManaHandler instance = CAPABILITY_MANA.getDefaultInstance();

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing)
        {
            return capability == CAPABILITY_MANA;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing)
        {
            return hasCapability(capability, facing) ? CAPABILITY_MANA.<T>cast(instance) : null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return (NBTTagCompound) CAPABILITY_MANA.getStorage().writeNBT(CAPABILITY_MANA, instance, null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            CAPABILITY_MANA.getStorage().readNBT(CAPABILITY_MANA, instance, null, nbt);
        }
    }
}