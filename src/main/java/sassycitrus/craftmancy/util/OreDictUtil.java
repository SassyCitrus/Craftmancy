package sassycitrus.craftmancy.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictUtil
{

    /**
     * 
     * @param stack Item to get name for.
     * @return Ore name for item; fallbacks to registry name if not oredict'd or Empty if stack is empty.
     */
    public static String getOreName(ItemStack stack)
    {
        if (stack.isEmpty())
        {
            return "Empty";
        }

        int[] ids = OreDictionary.getOreIDs(stack);
        if (ids != null && ids.length >= 1)
        {
            return OreDictionary.getOreName(ids[0]);
        }

        return stack.getItem().getRegistryName().toString();
    }
}