package sassycitrus.craftmancy.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public abstract class TileEntityBase extends TileEntity
{
    public void notifyBlockUpdate()
    {
        IBlockState state = this.world.getBlockState(this.pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    public void sendUpdates()
    {
        notifyBlockUpdate();
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
        notifyBlockUpdate();
    }
}