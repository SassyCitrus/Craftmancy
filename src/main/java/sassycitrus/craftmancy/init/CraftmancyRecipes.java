package sassycitrus.craftmancy.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.crafting.RitualAltarCraftingManager;
import sassycitrus.craftmancy.crafting.RitualAltarCraftingManager.RitualRecipe;

@Mod.EventBusSubscriber(modid = Craftmancy.modid)
public class CraftmancyRecipes
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        OreDictionary.registerOre("logWood", CraftmancyBlocks.ALTERBAUM_LOG);
        OreDictionary.registerOre("logWood", CraftmancyBlocks.ALTERBAUM_WOOD);
        OreDictionary.registerOre("plankWood", CraftmancyBlocks.ALTERBAUM_PLANKS);
        OreDictionary.registerOre("treeSapling", CraftmancyBlocks.ALTERBAUM_SAPLING);
        OreDictionary.registerOre("treeLeaves", CraftmancyBlocks.ALTERBAUM_LEAVES);

        OreDictionary.registerOre("logAlterbaum", CraftmancyBlocks.ALTERBAUM_LOG);
        OreDictionary.registerOre("logAlterbaum", CraftmancyBlocks.ALTERBAUM_WOOD);

        OreDictionary.registerOre("oreMithril", CraftmancyBlocks.ORE_MITHRIL);
        OreDictionary.registerOre("blockMithril", CraftmancyBlocks.MITHRIL_BLOCK);
        OreDictionary.registerOre("ingotMithril", CraftmancyItems.INGOT_MITHRIL);

        GameRegistry.addSmelting(CraftmancyBlocks.ORE_MITHRIL, new ItemStack(CraftmancyItems.INGOT_MITHRIL), 0.8F);

        registerRituals();
    }

    public static void registerRituals()
    {
        RitualAltarCraftingManager.addRecipe(
            new RitualRecipe.Builder().setResult(CraftmancyItems.CORE_FURNACE).setCost(3)
            .setAltarIngredient(Ingredient.fromItem(CraftmancyItems.CORE_BLANK))
            .addPedestalIngredient(Ingredient.fromItem(CraftmancyItems.GEM_EDELILA))
            .addPedestalIngredient(Ingredient.fromItem(Items.COAL))
            .addPedestalIngredient(Ingredient.fromItem(Item.getItemFromBlock(Blocks.FURNACE)))
            .addPedestalIngredient(new OreIngredient("blockCoal"))
            .build()
        );
    }
}