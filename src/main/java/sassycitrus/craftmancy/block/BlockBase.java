package sassycitrus.craftmancy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;

public class BlockBase extends Block
{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    protected String name;

    public BlockBase(Material material, String name)
    {
        super(material);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Craftmancy.creativeTab);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    public BlockBase setHardness(float hardness)
    {
        this.blockHardness = hardness;
        return this;
    }

    public BlockBase setResistance(float resistance)
    {
        this.blockResistance = resistance;
        return this;
    }

    public BlockBase setHarvestLevelBase(String tool, int level)
    {
        this.setHarvestLevel(tool, level);
        return this;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos, state.withProperty(FACING, getFacingFromEntity(pos, placer)), 2);
    }

    public static EnumFacing getFacingFromEntity(BlockPos pos, EntityLivingBase entity)
    {
        return entity.getHorizontalFacing().getOpposite();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState()
                .withProperty(FACING, EnumFacing.getHorizontal(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }
}