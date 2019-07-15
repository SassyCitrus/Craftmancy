package sassycitrus.craftmancy.block.manafurnace;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import sassycitrus.craftmancy.block.TileEntityBase;

public class TileManaFurnace extends TileEntityBase implements ITickable
{
    private ItemStackHandler inventory = new ItemStackHandler(1);

    private int currentItemBurnTime = 0;
    private int furnaceBurnTime = 0;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setTag("inventory", inventory.serializeNBT());
        nbt.setInteger("currentItemBurnTime", this.currentItemBurnTime);
        nbt.setInteger("furnaceBurnTime", this.furnaceBurnTime);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
        this.currentItemBurnTime = nbt.getInteger("currentItemBurnTime");
        this.furnaceBurnTime = nbt.getInteger("furnaceBurnTime");
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
        }

        if (!this.world.isRemote)
        {
            int burnTime = getItemBurnTime();

            if (!isBurning() && burnTime > 0)
            {
                this.furnaceBurnTime = burnTime;
                this.currentItemBurnTime = burnTime;
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
}