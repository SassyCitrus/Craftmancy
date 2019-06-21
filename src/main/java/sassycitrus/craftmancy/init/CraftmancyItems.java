package sassycitrus.craftmancy.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.item.FoodAppleEdelila;

public class CraftmancyItems
{
    public static ItemBase GEM_EDELILA = new ItemBase("gem_edelila");
    public static ItemBase INGOT_MITHRIL = new ItemBase("ingot_mithril");
    public static FoodAppleEdelila EDELILA_APPLE = new FoodAppleEdelila();
    public static Wand WAND = new Wand();

    public static void register(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            GEM_EDELILA,
            INGOT_MITHRIL,
            EDELILA_APPLE,
            WAND
        );
    }

    public static void registerModels()
    {
        GEM_EDELILA.registerItemModel();
        INGOT_MITHRIL.registerItemModel();
        EDELILA_APPLE.registerItemModel();
    }
}