package sassycitrus.craftmancy.block.alterbaum;

import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;

public class LogAlterbaum extends BlockLog
{
    public static final PropertyEnum<EnumAxis> LOG_AXIS = PropertyEnum.<EnumAxis>create("axis", EnumAxis.class);    

    public LogAlterbaum()
    {
        super();
        setHarvestLevel("axe", 2);
        setUnlocalizedName("alterbaum_log");
        setRegistryName("alterbaum_log");
        setCreativeTab(Craftmancy.creativeTab);
        setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.X));
    }

    @Override
    public Material getMaterial(IBlockState state)
    {
        return Material.ROCK;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
            float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getStateFromMeta(meta).withProperty(LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumAxis axis;

        switch (meta)
        {
            case 0:
                axis = EnumAxis.X;
                break;
            case 1:
                axis = EnumAxis.Y;
                break;
            case 2:
                axis = EnumAxis.Z;
                break;
            default:
                axis = EnumAxis.NONE;
                break;
        }

        return getDefaultState()
                .withProperty(LOG_AXIS, axis);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        switch (state.getValue(LOG_AXIS))
        {
            case X:
                return 0;
            case Y:
                return 1;
            case Z:
                return 2;
            default:
                return 3;
        }
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, LOG_AXIS);
    }
}