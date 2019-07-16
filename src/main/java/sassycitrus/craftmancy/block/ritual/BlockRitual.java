package sassycitrus.craftmancy.block.ritual;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.world.World;
import sassycitrus.craftmancy.block.BlockBase;

public class BlockRitual extends BlockBase
{
    public BlockRitual(String name)
    {
        super(Material.ROCK, name);
        setHardness(2.5F);
    }

    @Override
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileRitual();
    }
}