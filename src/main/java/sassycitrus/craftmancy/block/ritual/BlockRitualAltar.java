package sassycitrus.craftmancy.block.ritual;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sassycitrus.craftmancy.crafting.RitualAltarCraftingManager;
import sassycitrus.craftmancy.crafting.RitualAltarCraftingManager.RitualRecipe;
import sassycitrus.craftmancy.init.CraftmancyBlocks;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.network.MessageRitualParticle;
import sassycitrus.craftmancy.network.Network;
import sassycitrus.craftmancy.util.ManaUtil;
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
            world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D, world.rand.nextGaussian() * 0.02D);
            ItemStack heldItem = player.getHeldItem(hand);

            if (heldItem.getItem() instanceof Wand)
            {
                performRitual(world, pos, player);
                return true;
            }
            else
            {
                return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
            }
        }

        return true;
    }

    public void performRitual(World world, BlockPos pos, EntityPlayer player)
    {            
        if (isValidAltar(world, pos))
        {
            RitualRecipe recipe = getRecipe(world, pos);

            if (recipe != null)
            {
                if (ManaUtil.removeManaLevel(player, recipe.getCost()))
                {
                    removeItems(world, pos);
                    Network.sendTo(new MessageRitualParticle(pos), player);
                    world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    getTileEntity(world, pos).setItem(recipe.getResult().copy());
                }
                else
                {
                    StringUtil.sendMessage(player, "info.craftmancy.ritualAltarManaLow");
                }
            }
        }
        else
        {
            StringUtil.sendMessage(player, "info.craftmancy.ritualAltarInvalid");
        }
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

        ArrayList<ItemStack> pedestalItems = new ArrayList<>();

        ItemStack item;

        item = getTileEntity(world, pos.add(-1, 0, 1)).getItem();
        if (!item.isEmpty())
        {
            pedestalItems.add(item);
        }
        
        item = getTileEntity(world, pos.add(1, 0, 1)).getItem();
        if (!item.isEmpty())
        {
            pedestalItems.add(item);
        }

        item = getTileEntity(world, pos.add(-1, 0, -1)).getItem();
        if (!item.isEmpty())
        {
            pedestalItems.add(item);
        }
        
        item = getTileEntity(world, pos.add(1, 0, -1)).getItem();
        if (!item.isEmpty())
        {
            pedestalItems.add(item);
        }

        NonNullList<ItemStack> pedestalInput = NonNullList.from(ItemStack.EMPTY, pedestalItems.toArray(new ItemStack[pedestalItems.size()]));

        return RitualAltarCraftingManager.getRecipe(altarInput, pedestalInput);
    }

    public void removeItems(World world, BlockPos pos)
    {
        getTileEntity(world, pos).removeItem();
        getTileEntity(world, pos.add(-1, 0, 1)).removeItem();
        getTileEntity(world, pos.add(1, 0, 1)).removeItem();
        getTileEntity(world, pos.add(-1, 0, -1)).removeItem();
        getTileEntity(world, pos.add(1, 0, -1)).removeItem();
    }
}