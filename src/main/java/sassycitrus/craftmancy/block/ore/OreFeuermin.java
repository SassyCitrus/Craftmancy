package sassycitrus.craftmancy.block.ore;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sassycitrus.craftmancy.block.BlockBase;
import sassycitrus.craftmancy.init.CraftmancyItems;

public class OreFeuermin extends BlockBase
{
    public OreFeuermin()
    {
        super(Material.ROCK, "ore_feuermin");
        setHardness(3.0F);
        setResistance(1.0F);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 2);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return CraftmancyItems.FEUERMIN;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return MathHelper.getInt(random, 3, 5);
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if (fortune > 0)
        {
            return this.quantityDropped(random) + random.nextInt(fortune) + 1;
        }
        else
        {
            return this.quantityDropped(random);
        }
    }

    @Override
    public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
    {
        Random random = world instanceof World ? ((World) world).rand : new Random();
        return MathHelper.getInt(random, 1, 5);
    }
}