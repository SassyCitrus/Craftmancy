package sassycitrus.craftmancy.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import sassycitrus.craftmancy.crafting.ArcaneTableCraftingManager;

public class CraftmancyRecipes
{
    public static void registerSmelting()
    {
        GameRegistry.addSmelting(CraftmancyBlocks.ORE_MITHRIL, new ItemStack(CraftmancyItems.INGOT_MITHRIL), 0.8F);
    }

    public static void registerOreDict()
    {
        OreDictionary.registerOre("oreMithril", new ItemStack(CraftmancyBlocks.ORE_MITHRIL));
        OreDictionary.registerOre("blockMithril", new ItemStack(CraftmancyBlocks.MITHRIL_BLOCK));
        OreDictionary.registerOre("ingotMithril", new ItemStack(CraftmancyItems.INGOT_MITHRIL));
    }

    public static void registerArcaneTableCrafting()
    {
        ArcaneTableCraftingManager.addRecipe
        (
            new ItemStack(CraftmancyItems.WAND_TIER_1), 0,
            ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.DIAMOND),
            ItemStack.EMPTY, new ItemStack(Items.STICK), ItemStack.EMPTY,
            new ItemStack(Items.IRON_INGOT), ItemStack.EMPTY, ItemStack.EMPTY
        );
    }
}