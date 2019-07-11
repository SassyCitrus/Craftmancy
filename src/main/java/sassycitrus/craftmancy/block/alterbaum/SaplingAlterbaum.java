package sassycitrus.craftmancy.block.alterbaum;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.world.WorldGenTreeAlterbaum;

public class SaplingAlterbaum extends BlockBush implements IGrowable
{
    protected static final String NAME = "alterbaum_sapling";
    protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

    public SaplingAlterbaum()
    {
        setUnlocalizedName(NAME);
        setRegistryName(NAME);
        setCreativeTab(Craftmancy.creativeTab);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return SAPLING_AABB;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (!world.isRemote)
        {
            super.updateTick(world, pos, state, rand);

            if (world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
            {
                grow(world, rand, pos, state);
            }
        }
    }

    public void generateTree(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (TerrainGen.saplingGrowTree(world, rand, pos))
        {
            WorldGenerator worldGenerator = new WorldGenTreeAlterbaum(true);
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            worldGenerator.generate(world, rand, pos);
        }
    }

    @Override
    public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
    {
        return world.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World world, Random rand, BlockPos pos, IBlockState state)
    {
        if (state.getValue(STAGE).intValue() == 0)
        {
            world.setBlockState(pos, state.cycleProperty(STAGE), 4);
        }
        else
        {
            generateTree(world, pos, state, rand);
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(STAGE, Integer.valueOf((meta)));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(STAGE).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, STAGE);
    }
}