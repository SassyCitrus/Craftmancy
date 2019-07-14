package sassycitrus.craftmancy.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity
{
    public void sendUpdates()
    {
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
        markDirty();
    }
    
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(getPos(), 1, this.getUpdateTag());
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
    {
        readFromNBT(packet.getNbtCompound());
        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
    }
}