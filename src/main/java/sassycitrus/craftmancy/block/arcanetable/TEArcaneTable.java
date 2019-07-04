package sassycitrus.craftmancy.block.arcanetable;

import javax.annotation.Nullable;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import sassycitrus.craftmancy.block.TileEntityBase;

public class TEArcaneTable extends TileEntityBase
{
    private ArcaneTableInventory inventory = new ArcaneTableInventory();

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
        super.readFromNBT(nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        NBTTagCompound nbt = packet.getNbtCompound();
        readFromNBT(nbt);
        markDirty();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing)
    {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing)
    {
        @SuppressWarnings("unchecked")
        T inv = (T) inventory;

        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? inv : super.getCapability(capability, facing);
    }

    public void dropInventoryItems(World world, BlockPos pos)
    {
        for (int i = 0; i < 9; i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);

            if (!stack.isEmpty())
            {
                InventoryHelper.spawnItemStack(world, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stack);
            }
        }
    }

    private static class ArcaneTableInventory extends ItemStackHandler
    {
        public ArcaneTableInventory()
        {
            super(9);
        }

        @Override
        public int getSlotLimit(int slot)
        {
            return 1;
        }
    }
}