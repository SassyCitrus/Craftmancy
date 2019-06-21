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
        registry.registerWandPart("wood", PartType.CORE, Items.STICK);
        registry.registerWandPart("diamond", PartType.GEM, Items.DIAMOND);
    }
}