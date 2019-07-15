package sassycitrus.craftmancy.block.manafurnace;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.BlockWithFacing;
import sassycitrus.craftmancy.gui.GuiHandler;

public class BlockManaFurnace extends BlockWithFacing
{
    public static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockManaFurnace()
    {
        super(Material.IRON, "mana_furnace");
        setHarvestLevel("pickaxe", 0);
        setHardness(3.0F);
        setResistance(5.0F);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING, ACTIVE});
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileManaFurnace();
    }

    public static TileManaFurnace getTileEntity(IBlockAccess world, BlockPos pos)
    {
        return world instanceof ChunkCache ? 
                (TileManaFurnace) ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) :
                (TileManaFurnace) world.getTileEntity(pos);
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

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileManaFurnace te = getTileEntity(world, pos);

        return state.withProperty(ACTIVE, te.isActive());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            player.openGui(Craftmancy.modid, GuiHandler.MANA_FURNACE, world, pos.getX(), pos.getY(), pos.getZ());
        }

        return true;
    }
}