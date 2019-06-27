package sassycitrus.craftmancy.block.arcanetable;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sassycitrus.craftmancy.block.BlockTileEntityBase;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.util.StringUtil;

public class BlockArcaneTable extends BlockTileEntityBase<TEArcaneTable>
{
    public static final float GRID_OFFSET = 5/16F;
    public static final float GRID_SLOT_SIZE = 2/16F;

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

        if (item instanceof Wand)
        {
            if (!world.isRemote )
            {
                Wand wand = (Wand) item;
                StringUtil.sendMessage(player, "Activated with tier " + wand.getTier() + " wand.");
            }

            return true;
        }

        return false;
    }
}