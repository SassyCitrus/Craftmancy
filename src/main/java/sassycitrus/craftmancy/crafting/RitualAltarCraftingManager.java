package sassycitrus.craftmancy.crafting;

import java.util.ArrayList;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.oredict.OreIngredient;

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

            public Builder setAltarIngredient(Block block)
            {
                this.altar = getIngredient(block);
                return this;
            }

            public Builder setAltarIngredient(Item item)
            {
                this.altar = getIngredient(item);
                return this;
            }

            public Builder setAltarIngredient(String ore)
            {
                this.altar = getIngredient(ore);
                return this;
            }

            public Builder addPedestalIngredient(Block block)
            {
                this.pedestal.add(getIngredient(block));
                return this;
            }

            public Builder addPedestalIngredient(Item item)
            {
                this.pedestal.add(getIngredient(item));
                return this;
            }

            public Builder addPedestalIngredient(String ore)
            {
                this.pedestal.add(getIngredient(ore));
                return this;
            }

            public RitualRecipe build()
            {
                return new RitualRecipe(this.result, this.cost, this.altar, this.pedestal);
            }

            private static Ingredient getIngredient(Block block)
            {
                return Ingredient.fromItem(Item.getItemFromBlock(block));
            }

            private static Ingredient getIngredient(Item item)
            {
                return Ingredient.fromItem(item);
            }

            private static OreIngredient getIngredient(String ore)
            {
                return new OreIngredient(ore);
            }
        }
    }
}