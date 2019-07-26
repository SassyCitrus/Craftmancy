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

public class OreGem extends BlockBase
{
    Item gem;

    public OreGem(String name, Item gem)
    {
        super(Material.ROCK, "ore_" + name);
        setHardness(3.0F);
        setResistance(5.0F);
        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 2);

        this.gem = gem;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return this.gem;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random)
    {
        return MathHelper.getInt(random, 1, 3);
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
        return MathHelper.getInt(random, 3, 7);
    }
}