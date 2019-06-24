package sassycitrus.craftmancy.block;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockTileEntityBase<TE extends TileEntity> extends BlockBase
{
    private Class<TileEntity> te;

    public BlockTileEntityBase(Material material, String name)
    {
        super(material, name);
    }

    public TileEntity getTileEntity(IBlockAccess world, BlockPos pos)
    {
        return te.cast(world.getTileEntity(pos));
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);
}