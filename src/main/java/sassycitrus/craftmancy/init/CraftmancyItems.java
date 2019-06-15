package sassycitrus.craftmancy.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.item.FoodAppleEdelila;

public class CraftmancyItems
{
    public static ItemBase gemEdelila = new ItemBase("gem_edelila");
    public static FoodAppleEdelila appleEdelila = new FoodAppleEdelila();

    public static void register(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            gemEdelila,
            appleEdelila
        );
    }

    public static void registerModels()
    {
        gemEdelila.registerItemModel();
        appleEdelila.registerItemModel();
    }
}