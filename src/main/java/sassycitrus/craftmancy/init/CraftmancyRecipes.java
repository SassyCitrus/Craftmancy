package sassycitrus.craftmancy.init;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import sassycitrus.craftmancy.Craftmancy;

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

        OreDictionary.registerOre("oreMithril", CraftmancyBlocks.ORE_MITHRIL);
        OreDictionary.registerOre("blockMithril", CraftmancyBlocks.MITHRIL_BLOCK);
        OreDictionary.registerOre("ingotMithril", CraftmancyItems.INGOT_MITHRIL);

        GameRegistry.addSmelting(CraftmancyBlocks.ORE_MITHRIL, new ItemStack(CraftmancyItems.INGOT_MITHRIL), 0.8F);
    }
}