package sassycitrus.craftmancy.crafting;

import java.util.HashMap;

import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;
import sassycitrus.craftmancy.util.OreDictUtil;

public class ArcaneTableCraftingManager
{
    private static HashMap<String,ArcaneTableRecipe> recipes = new HashMap<String,ArcaneTableRecipe>();

    public static void addRecipe(ItemStack output, int tier, ItemStack... ingredients)
    {
        ArcaneTableRecipe recipe = new ArcaneTableRecipe(output, tier, ingredients);
        recipes.put(recipe.toString(), recipe);
    }

    @Nullable
    public static ArcaneTableRecipe getRecipe(ItemStack... ingredients)
    {
        return recipes.get(ArcaneTableRecipe.createKey(ingredients));
    }

    public static class ArcaneTableRecipe
    {
        private ItemStack[] ingredients;
        private ItemStack output;
        private int tier;

        public ArcaneTableRecipe(ItemStack output, int tier, ItemStack... ingredients)
        {
            this.ingredients = ingredients;
            this.output = output;
            this.tier = tier;
        }

        public ItemStack getOutput()
        {
            return this.output.copy();
        }

        public int getTier()
        {
            return this.tier;
        }

        public static String createKey(ItemStack[] ingredients)
        {
            StringBuilder key = new StringBuilder();
            
            for (ItemStack ingredient : ingredients)
            {
                key.append(OreDictUtil.getOreName(ingredient));
                key.append(",");
            }

            return key.toString();
        }

        @Override
        public String toString()
        {
            return createKey(this.ingredients);
        }
    }
}