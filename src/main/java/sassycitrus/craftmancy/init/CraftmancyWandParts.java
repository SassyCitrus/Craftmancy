package sassycitrus.craftmancy.init;

import net.minecraft.init.Items;
import sassycitrus.craftmancy.api.wand.WandPartRegistry;
import sassycitrus.craftmancy.api.wand.WandPartRegistry.PartType;

public class CraftmancyWandParts
{
    public static void register()
    {
        WandPartRegistry registry = WandPartRegistry.getInstance();
        
        registry.registerWandPart("iron", PartType.BINDING, Items.IRON_INGOT);
        registry.registerWandPart("gold", PartType.BINDING, Items.GOLD_INGOT);
        registry.registerWandPart("mithril", PartType.BINDING, CraftmancyItems.INGOT_MITHRIL);

        registry.registerWandPart("wood", PartType.CORE, Items.STICK);

        registry.registerWandPart("diamond", PartType.GEM, Items.DIAMOND);
        registry.registerWandPart("emerald", PartType.GEM, Items.EMERALD);
        registry.registerWandPart("edelila", PartType.GEM, CraftmancyItems.GEM_EDELILA);
    }
}