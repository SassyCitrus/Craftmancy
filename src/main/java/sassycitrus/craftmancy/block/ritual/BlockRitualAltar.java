package sassycitrus.craftmancy.block.ritual;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sassycitrus.craftmancy.crafting.RitualAltarCraftingManager;
import sassycitrus.craftmancy.crafting.RitualAltarCraftingManager.RitualRecipe;
import sassycitrus.craftmancy.init.CraftmancyBlocks;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.util.StringUtil;

public class BlockRitualAltar extends BlockRitual
{
    public BlockRitualAltar()
    {
        super("ritual_altar");
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (!world.isRemote)
        {
            ItemStack heldItem = player.getHeldItem(hand);

            if (heldItem.getItem() instanceof Wand)
            {
                if (isValidAltar(world, pos))
                {
                    RitualRecipe recipe = getRecipe(world, pos);

                    if (recipe != null)
                    {
                        StringUtil.sendMessage(player, "Is a valid recipe");
                    }
                }
                else
                {
                    StringUtil.sendMessage(player, "info.craftmancy.ritualAltarInvalid");
                }

                return true;
            }
            else
            {
                return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
            }
        }

        return true;
    }

    public boolean isValidAltar(World world, BlockPos pos)
    {
        ResourceLocation name = CraftmancyBlocks.RITUAL_PEDESTAL.getRegistryName();
        
        IBlockState corner1 = world.getBlockState(pos.add(-1, 0, 1));
        IBlockState corner2 = world.getBlockState(pos.add(1, 0, 1));
        IBlockState corner3 = world.getBlockState(pos.add(-1, 0, -1));
        IBlockState corner4 = world.getBlockState(pos.add(1, 0, -1));

        if (corner1.getBlock().getRegistryName() == name && corner2.getBlock().getRegistryName() == name &&
            corner3.getBlock().getRegistryName() == name && corner4.getBlock().getRegistryName() == name)
        {
            return true;
        }

        return false;
    }

    @Nullable
    public RitualRecipe getRecipe(World world, BlockPos pos)
    {
        ItemStack altarInput = getTileEntity(world, pos).getItem();

        ItemStack[] pedestalItems = new ItemStack[]
        {
            getTileEntity(world, pos.add(-1, 0, 1)).getItem(),
            getTileEntity(world, pos.add(1, 0, 1)).getItem(),
            getTileEntity(world, pos.add(-1, 0, -1)).getItem(),
            getTileEntity(world, pos.add(1, 0, -1)).getItem()
        };

        NonNullList<ItemStack> pedestalInput = NonNullList.from(ItemStack.EMPTY, pedestalItems);

        return RitualAltarCraftingManager.getRecipe(altarInput, pedestalInput);
    }
}