package sassycitrus.craftmancy.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.item.FoodAppleEdelila;

public class CraftmancyItems
{
    public static ItemBase GEM_EDELILA = new ItemBase("gem_edelila");
    public static FoodAppleEdelila EDELILA_APPLE = new FoodAppleEdelila();

    public static Wand WAND_0 = new Wand(0);
    public static Wand WAND_1 = new Wand(1);
    public static Wand WAND_2 = new Wand(2);
    public static Wand WAND_3 = new Wand(3);

    public static void register(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            GEM_EDELILA,
            EDELILA_APPLE,
            WAND_0,
            WAND_1,
            WAND_2,
            WAND_3
        );
    }

    public static void registerModels()
    {
        GEM_EDELILA.registerItemModel();
        EDELILA_APPLE.registerItemModel();
        WAND_0.registerItemModel();
        WAND_1.registerItemModel();
        WAND_2.registerItemModel();
        WAND_3.registerItemModel();
    }
}