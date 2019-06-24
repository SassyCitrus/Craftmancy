package sassycitrus.craftmancy.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;

public class ArcaneTable extends BlockTileEntityBase<TileEntity>
{
    public ArcaneTable()
    {
        super(Material.WOOD, "arcane_table");
        setHardness(2.5F);
    }

    @Override
    public Class<TileEntity> getTileEntityClass()
    {
        return null;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return null;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}