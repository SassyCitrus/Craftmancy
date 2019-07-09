package sassycitrus.craftmancy.block.arcanetable;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.BlockTileEntityBase;
import sassycitrus.craftmancy.crafting.ArcaneTableCraftingManager;
import sassycitrus.craftmancy.crafting.ArcaneTableCraftingManager.ArcaneTableRecipe;
import sassycitrus.craftmancy.gui.GuiHandler;
import sassycitrus.craftmancy.item.tool.Wand;

public class BlockArcaneTable extends BlockTileEntityBase<TEArcaneTable>
{
    public BlockArcaneTable()
    {
        super(Material.WOOD, "arcane_table");
        setHardness(2.5F);
    }

    @Override
    public Class<TEArcaneTable> getTileEntityClass()
    {
        return TEArcaneTable.class;
    }

    @Nullable
	@Override
    public TEArcaneTable createTileEntity(World world, IBlockState state)
    {
        return new TEArcaneTable();
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
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        Item item = player.getHeldItem(hand).getItem();

        if (!world.isRemote)
        {
            if (item instanceof Wand)
            {
                TileEntity te = world.getTileEntity(pos);

                if (te instanceof TEArcaneTable)
                {
                    Wand wand = (Wand) item;
                    ItemStack[] items = ((TEArcaneTable) te).getInventoryItems();
                    ArcaneTableRecipe recipe = ArcaneTableCraftingManager.getRecipe(items);

                    if (recipe != null && wand.getTier() >= recipe.getTier())
                    {
                        ((TEArcaneTable) te).clearInventory();
                        EntityItem entityitem = new EntityItem(world, (double)pos.getX() + 0.5, (double)pos.getY() + 1, (double)pos.getZ() + 0.5, recipe.getOutput());
                        entityitem.setDefaultPickupDelay();
                        world.spawnEntity(entityitem);
                    }
                }
            }
            else
            {
                player.openGui(Craftmancy.modid, GuiHandler.ARCANE_TABLE, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }

        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TEArcaneTable)
        {
            ((TEArcaneTable) te).dropInventoryItems(world, pos);
        }

        super.breakBlock(world, pos, state);
    }
}