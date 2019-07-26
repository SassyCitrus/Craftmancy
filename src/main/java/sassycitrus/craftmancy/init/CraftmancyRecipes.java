package sassycitrus.craftmancy.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
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
        OreDictionary.registerOre("stickWood", CraftmancyItems.ALTERBAUM_STICK);

        OreDictionary.registerOre("logAlterbaum", CraftmancyBlocks.ALTERBAUM_LOG);
        OreDictionary.registerOre("logAlterbaum", CraftmancyBlocks.ALTERBAUM_WOOD);

        OreDictionary.registerOre("oreMithril", CraftmancyBlocks.ORE_MITHRIL);
        OreDictionary.registerOre("blockMithril", CraftmancyBlocks.MITHRIL_BLOCK);
        OreDictionary.registerOre("ingotMithril", CraftmancyItems.INGOT_MITHRIL);

        OreDictionary.registerOre("gemRuby", CraftmancyItems.GEM_RUBY);

        GameRegistry.addSmelting(CraftmancyBlocks.ORE_MITHRIL, new ItemStack(CraftmancyItems.INGOT_MITHRIL), 0.8F);

        registerRituals();
    }

    public static void registerRituals()
    {
        RitualAltarCraftingManager.addRecipe(
            new RitualRecipe.Builder().setResult(CraftmancyItems.CORE_FURNACE).setCost(3)
            .setAltarIngredient(CraftmancyItems.CORE_BLANK)
            .addPedestalIngredient(CraftmancyItems.GEM_EDELILA)
            .addPedestalIngredient(Items.COAL)
            .addPedestalIngredient(Blocks.FURNACE)
            .addPedestalIngredient("blockCoal")
            .build()
        );


        RitualAltarCraftingManager.addRecipe(
            new RitualRecipe.Builder().setResult(CraftmancyItems.CORE_EMBERS).setCost(3)
            .setAltarIngredient(CraftmancyItems.CORE_FURNACE)
            .addPedestalIngredient(CraftmancyItems.GEM_EDELILA)
            .addPedestalIngredient(CraftmancyItems.FEUERMIN)
            .addPedestalIngredient(Items.BLAZE_POWDER)
            .addPedestalIngredient(Items.FIRE_CHARGE)
            .build()
        );
    }
}