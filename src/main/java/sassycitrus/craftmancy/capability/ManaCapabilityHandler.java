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
import sassycitrus.craftmancy.util.ManaUtil;

public class ManaCapabilityHandler
{
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
        clone.setManaLevel(original.getManaLevel());
        Network.syncPlayerMana((EntityPlayer) event.getEntity());
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
        int getManaLevel();
        void setManaLevel(int level);
        void addManaLevel(int level);
        void addManaLevel();
        boolean removeManaLevel(int level);

        int getManaTotal();
        void setManaTotal(int mana);
        void addManaTotal(int mana);
        void removeManaTotal(int mana);

        int getMana();
        void setMana(int mana);
        void addMana(int mana);
        void removeMana(int mana);
    }

    public static class ManaFactory implements Callable<IManaHandler>
    {
        @Override
        public IManaHandler call() throws Exception
        {
            IManaHandler handler  = new IManaHandler()
            {
                private int manaLevel;
                private int manaTotal;
                private int mana;
                
                @Override
                public int getMana()
                {
                    return this.mana;
                }

                @Override
                public void setMana(int mana)
                {
                    this.mana = mana;
                }

                @Override
                public void addMana(int mana)
                {
                    this.mana += mana;
                }

                @Override
                public void removeMana(int mana)
                {
                    this.mana -= mana;
                }

                @Override
                public int getManaLevel()
                {
                    return this.manaLevel;
                }

                @Override
                public void setManaLevel(int level)
                {
                    this.manaLevel = level;
                }

                @Override
                public void addManaLevel(int level)
                {
                    this.manaLevel += level;
                }

                @Override
                public void addManaLevel()
                {
                    addManaLevel(1);
                }

                @Override
                public boolean removeManaLevel(int level)
                {
                    if (this.manaLevel >= level)
                    {
                        for (int i = 0; i < level; i++)
                        {
                            int manaForLevel = ManaUtil.calculateManaForLevel(this.manaLevel - 1);
                            this.manaTotal -= manaForLevel;
                            this.manaLevel--;
                        }

                        ManaUtil.updateLevels(this);
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }

                @Override
                public int getManaTotal()
                {
                    return this.manaTotal;
                }

                @Override
                public void setManaTotal(int mana)
                {
                    this.manaTotal = mana;
                }

                @Override
                public void addManaTotal(int mana)
                {
                    this.manaTotal += mana;
                }

                @Override
                public void removeManaTotal(int mana)
                {
                    this.manaTotal -= mana;
                }
            };

            return handler;
        }
    }

    public static class Storage implements Capability.IStorage<IManaHandler>
    {
        @Override
        public NBTBase writeNBT(Capability<IManaHandler> capability, IManaHandler instance, EnumFacing side)
        {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("manaLevel", instance.getManaLevel());
            nbt.setInteger("manaTotal", instance.getManaTotal());
            nbt.setInteger("mana", instance.getMana());
            return nbt;
        }

        @Override
        public void readNBT(Capability<IManaHandler> capability, IManaHandler instance, EnumFacing side, NBTBase baseNBT)
        {
            final NBTTagCompound nbt = (NBTTagCompound) baseNBT;
            instance.setManaLevel(nbt.getInteger("manaLevel"));
            instance.setManaTotal(nbt.getInteger("manaTotal"));
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