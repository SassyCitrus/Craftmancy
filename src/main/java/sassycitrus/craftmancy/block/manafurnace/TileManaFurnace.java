package sassycitrus.craftmancy.block.manafurnace;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import sassycitrus.craftmancy.block.TileEntityBase;
import sassycitrus.craftmancy.util.ManaUtil;
import sassycitrus.craftmancy.util.PlayerUtil;

public class TileManaFurnace extends TileEntityBase implements ITickable
{
    public static final int TICKS_TILL_MANA = 50;
    public static final int MANA_RATE = 1;

    private ItemStackHandler inventory = new ItemStackHandler(1);
    private UUID playerUUID = PlayerUtil.DEFAULT_UUID;
    public int currentItemBurnTime = 0;
    public int furnaceBurnTime = 0;
    public int ticksTillMana = 0;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setTag("inventory", inventory.serializeNBT());
        nbt.setUniqueId("playerUUID", playerUUID);
        nbt.setInteger("currentItemBurnTime", this.currentItemBurnTime);
        nbt.setInteger("furnaceBurnTime", this.furnaceBurnTime);
        nbt.setInteger("ticksTillMana", this.ticksTillMana);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
        this.playerUUID = nbt.getUniqueId("playerUUID");
        this.currentItemBurnTime = nbt.getInteger("currentItemBurnTime");
        this.furnaceBurnTime = nbt.getInteger("furnaceBurnTime");
        this.ticksTillMana = nbt.getInteger("ticksTillMana");
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        super.onDataPacket(net, packet);
        this.world.checkLightFor(EnumSkyBlock.BLOCK, pos);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? (T) inventory : super.getCapability(capability, facing);
    }

    @Override
    public void update()
    {
        if (isBurning())
        {
            this.furnaceBurnTime--;
            
            if (this.furnaceBurnTime == 0)
            {
                notifyBlockUpdate();
            }

            if (!this.world.isRemote)
            {

                ticksTillMana++;

                if (this.ticksTillMana == TICKS_TILL_MANA)
                {
                    this.ticksTillMana = 0;
                    generateMana();
                }
            }
        }

        if (!this.world.isRemote)
        {
            int burnTime = getItemBurnTime();

            if (!isBurning() && burnTime > 0)
            {
                this.furnaceBurnTime = burnTime;
                this.currentItemBurnTime = burnTime;
                this.ticksTillMana = 0;
                consumeFuel();
                sendUpdates();
            }
        }
    }

    public boolean isBurning()
    {
        return this.furnaceBurnTime > 0;
    }

    private int getItemBurnTime()
    {
        return TileEntityFurnace.getItemBurnTime(this.inventory.getStackInSlot(0));
    }

    private void consumeFuel()
    {
        this.inventory.extractItem(0, 1, false);
    }

    private void generateMana()
    {
        AxisAlignedBB aoe = getRenderBoundingBox().grow(2, 2, 2);
        List<EntityPlayer> players = this.world.getEntitiesWithinAABB(EntityPlayer.class, aoe);

        for (EntityPlayer player : players)
        {
            if (PlayerUtil.getUUIDFromPlayer(player) == this.playerUUID)
            {
                ManaUtil.addMana(player, MANA_RATE);
            }
        }
    }

    public void setPlayerUUID(UUID player)
    {
        this.playerUUID = player;
    }
}