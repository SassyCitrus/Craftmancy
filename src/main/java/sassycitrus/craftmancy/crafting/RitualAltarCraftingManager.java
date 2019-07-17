package sassycitrus.craftmancy.crafting;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;

public class RitualAltarCraftingManager
{
    private static ArrayList<RitualRecipe> RECIPES = new ArrayList<>();

    public static void addRecipe(RitualRecipe recipe)
    {
        RECIPES.add(recipe);
    }

    @Nullable
    public static RitualRecipe getRecipe(ItemStack altarInput, NonNullList<ItemStack> pedestalInput)
    {
        for (RitualRecipe recipe : RECIPES)
        {
            if (recipe.matches(altarInput, pedestalInput))
            {
                return recipe;
            }
        }

        return null;
    }

    public static class RitualRecipe
    {
        private ItemStack result;
        private int cost;
        private Ingredient altar;
        private NonNullList<Ingredient> pedestal;

        public RitualRecipe(ItemStack result, int cost, Ingredient altar, NonNullList<Ingredient> pedestal)
        {
            this.result = result;
            this.cost = cost;
            this.altar = altar;
            this.pedestal = pedestal;
        }

        public boolean matches(ItemStack altarInput, NonNullList<ItemStack> pedestalInput)
        {
            if (this.altar.apply(altarInput))
            {
                if (pedestalInput.size() == this.pedestal.size())
                {
                    return RecipeMatcher.findMatches(pedestalInput, this.pedestal) != null;
                }
            }
                
            return false;
        }

        public int getCost()
        {
            return this.cost;
        }

        public ItemStack getResult()
        {
            return this.result.copy();
        }

        public static class Builder
        {
            private ItemStack result = ItemStack.EMPTY;
            private int cost = 0;
            private Ingredient altar = Ingredient.EMPTY;
            private NonNullList<Ingredient> pedestal = NonNullList.create();

            public Builder setResult(ItemStack result)
            {
                this.result = result;
                return this;
            }

            public Builder setResult(Item result)
            {
                this.result = new ItemStack(result);
                return this;
            }

            public Builder setResult(Block result)
            {
                this.result = new ItemStack(result);
                return this;
            }

            public Builder setCost(int cost)
            {
                this.cost = cost;
                return this;
            }

            public Builder setAltarIngredient(Ingredient altar)
            {
                this.altar = altar;
                return this;
            }

            public Builder addPedestalIngredient(Ingredient pedestal)
            {
                this.pedestal.add(pedestal);
                return this;
            }

            public RitualRecipe build()
            {
                return new RitualRecipe(this.result, this.cost, this.altar, this.pedestal);
            }
        }
    }
}