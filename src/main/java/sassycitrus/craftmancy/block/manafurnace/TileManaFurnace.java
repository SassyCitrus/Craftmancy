package sassycitrus.craftmancy.block.manafurnace;

import net.minecraft.nbt.NBTTagCompound;
import sassycitrus.craftmancy.block.TileEntityBase;

public class TileManaFurnace extends TileEntityBase
{
    private boolean isActive = false;

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("active", this.isActive);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        this.isActive = nbt.getBoolean("active");
    }

    public boolean isActive()
    {
        return this.isActive;
    }

    public void rightClick()
    {
        this.isActive = !this.isActive;
        sendUpdates();
    }
}