package sassycitrus.craftmancy.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraftforge.items.ItemStackHandler;

public class TEArcaneTable extends TileEntityBase
{

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt = super.writeToNBT(nbt);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        NBTTagCompound nbt = packet.getNbtCompound();
        readFromNBT(nbt);
        markDirty();
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