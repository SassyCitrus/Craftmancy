package sassycitrus.craftmancy.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import sassycitrus.craftmancy.tileentity.TEArcaneTable;

public class ArcaneTable extends BlockTileEntityBase<TEArcaneTable>
{
    public ArcaneTable()
    {
        super(Material.WOOD, "arcane_table");
        setHardness(2.5F);
    }

    @Override
    public Class<TEArcaneTable> getTileEntityClass()
    {
        return TEArcaneTable.class;
    }

    @Override
    public TEArcaneTable createTileEntity(World world, IBlockState state)
    {
        return new TEArcaneTable();
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