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
        nbt.setTag("inventory", this.inventory.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        this.inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
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
        T inv = (T) this.inventory;

        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? inv : super.getCapability(capability, facing);
    }

    public ItemStack[] getInventoryItems()
    {
        return this.inventory.getItemStacks();
    }

    public void clearInventory()
    {
        this.inventory.clear();
        sendUpdates();
    }

    public void dropInventoryItems(World world, BlockPos pos)
    {
        for (int i = 0; i < 9; i++)
        {
            ItemStack stack = this.inventory.getStackInSlot(i);

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

        public ItemStack[] getItemStacks()
        {
            ItemStack[] items = new ItemStack[9];

            for (int i = 0; i < 9; i++)
            {
                items[i] = getStackInSlot(i).copy();
            }

            return items;
        }

        public void clear()
        {
            for (int i = 0; i < 9; i++)
            {
                this.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }
}