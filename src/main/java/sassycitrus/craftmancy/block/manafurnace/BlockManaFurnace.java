package sassycitrus.craftmancy.block.manafurnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import sassycitrus.craftmancy.block.BlockWithFacing;

public class BlockManaFurnace extends BlockWithFacing
{
    public BlockManaFurnace()
    {
        super(Material.IRON, "mana_furnace");
        setHarvestLevel("pickaxe", 0);
        setHardness(3.0F);
        setResistance(5.0F);
        
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