package sassycitrus.craftmancy.init;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.item.ItemFoodAppleEdelila;

public class CraftmancyItems
{
    public static ItemBase gem_edelila = new ItemBase("gem_edelila");
    public static ItemFoodAppleEdelila apple_edelila = new ItemFoodAppleEdelila();

    public static void register(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            gem_edelila,
            apple_edelila
        );
    }

    public static void registerModels()
    {
        gem_edelila.registerItemModel();
        apple_edelila.registerItemModel();
    }
}