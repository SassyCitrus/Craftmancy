package sassycitrus.craftmancy.block.manafurnace;

import java.util.Random;
import java.util.UUID;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.BlockWithFacing;
import sassycitrus.craftmancy.gui.GuiHandler;
import sassycitrus.craftmancy.util.PlayerUtil;

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

        return state.withProperty(ACTIVE, te.isBurning());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);

        if (!world.isRemote && placer instanceof EntityPlayer)
        {
            TileManaFurnace manaFurnace = getTileEntity(world, pos);
            UUID player = PlayerUtil.getUUIDFromPlayer((EntityPlayer) placer);

            manaFurnace.setPlayerUUID(player);
        }
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

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand)
    {
        if (getTileEntity(world, pos).isBurning())
        {
            if (rand.nextDouble() < 0.1D)
            {
                world.playSound(pos.getX() + 05D, pos.getY(), pos.getZ() + 0.5D, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
        }
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileManaFurnace tile = getTileEntity(world, pos);
    
        if (tile != null && tile.hasWorld())
        {
            return tile.isBurning() ? 14 : 0;
        }
        else
        {
            return super.getLightValue(state, world, pos);
        }
    }
}