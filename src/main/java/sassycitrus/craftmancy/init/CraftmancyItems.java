package sassycitrus.craftmancy.init;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.item.tool.WandEmbers;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.item.FoodAppleEdelila;

public class CraftmancyItems
{
    public static HashMap<String, Item> ITEMS = new HashMap<String, Item>();

    public static Item ALTERBAUM_STICK = registerItem("stick_alterbaum");
    public static Item GEM_EDELILA = registerItem("gem_edelila");
    public static Item GEM_RUBY = registerItem("gem_ruby");
    public static Item INGOT_MITHRIL = registerItem("ingot_mithril");
    public static Item FEUERMIN = registerItem("feuermin");
    public static Item INGOT_FEUERSTEEL = registerItem("ingot_feuersteel");
    public static Item EDELILA_APPLE = registerItem(new FoodAppleEdelila());
    public static Item WAND_CRAFTMANCY = registerItem(new Wand("craftmancy"));
    public static Item WAND_EMBERS = registerItem(new WandEmbers());
    public static Item CORE_BLANK = registerItem("core_blank");
    public static Item CORE_FURNACE = registerItem("core_furnace");
    public static Item CORE_EMBERS = registerItem("core_embers");

    private static Item registerItem(Item item)
    {
        ITEMS.put(item.getRegistryName().getResourcePath(), item);
        return item;
    }

    private static Item registerItem(String name)
    {
        return registerItem(new ItemBase(name));
    }

    public static void register(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            ITEMS.values().toArray(new Item[ITEMS.size()])
        );
    }

    public static void registerModels()
    {
        for (String name : ITEMS.keySet())
        {
            Craftmancy.proxy.registerItemRenderer(ITEMS.get(name), 0, name);
        }
    }
}