package sassycitrus.craftmancy.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.item.FoodAppleEdelila;

public class CraftmancyItems
{
    public static ItemBase GEM_EDELILA = new ItemBase("gem_edelila");
    public static FoodAppleEdelila EDELILA_APPLE = new FoodAppleEdelila();

    public static void register(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            GEM_EDELILA,
            EDELILA_APPLE
        );
    }

    public static void registerModels()
    {
        GEM_EDELILA.registerItemModel();
        EDELILA_APPLE.registerItemModel();
    }
}