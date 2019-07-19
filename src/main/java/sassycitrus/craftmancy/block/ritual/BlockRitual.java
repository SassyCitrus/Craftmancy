package sassycitrus.craftmancy.block.ritual;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
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
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TileRitualBlock();
    }

    public static TileRitualBlock getTileEntity(IBlockAccess world, BlockPos pos)
    {
        return (TileRitualBlock) world.getTileEntity(pos);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            TileRitualBlock te = getTileEntity(world, pos);
            ItemStack heldItem = player.getHeldItem(hand);
            
            if (!te.getItem().isEmpty())
            {
                if (heldItem.isEmpty())
                {
                    player.setHeldItem(hand, te.inventory.extractItem(0, 1, false));
                }
                else if (heldItem.getCount() < heldItem.getMaxStackSize() && heldItem.isItemEqual(te.getItem()))
                {
                    te.inventory.extractItem(0, 1, false);
                    heldItem.grow(1);
                }
            }
            else
            {
                player.setHeldItem(hand, te.inventory.insertItem(0, heldItem, false));
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileRitualBlock te = getTileEntity(world, pos);
        ItemStack stack = te.inventory.getStackInSlot(0);

        if (!stack.isEmpty())
        {
            InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
        }

        super.breakBlock(world, pos, state);
    }
}