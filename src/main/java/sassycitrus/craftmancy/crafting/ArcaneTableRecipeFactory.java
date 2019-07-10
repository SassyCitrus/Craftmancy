package sassycitrus.craftmancy.crafting;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Sets;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.common.crafting.CraftingHelper.ShapedPrimer;
import sassycitrus.craftmancy.gui.arcanetable.InventoryCraftingArcaneTable;

public class ArcaneTableRecipeFactory implements IRecipeFactory
{
    @Override
    public IRecipe parse(JsonContext context, JsonObject json)
    {
        return ArcaneTableRecipe.deserialze(context, json);
    }

    public static class ArcaneTableRecipe extends ShapedRecipes
    {
        public ArcaneTableRecipe(int width, int height, NonNullList<Ingredient> ingredients, ItemStack result)
        {
            super("craftmancy:arcane_table", width, height, ingredients, result);
        }

        @Override
        public boolean matches(InventoryCrafting inv, World worldIn)
        {
            if (!inv.getName().equals(InventoryCraftingArcaneTable.NAME))
            {
                return false;
            }

            return super.matches(inv, worldIn);
        }

        public static ArcaneTableRecipe deserialze(JsonContext context, JsonObject json)
        {
            Map<Character, Ingredient> ingMap = Maps.newHashMap();
            for (Entry<String, JsonElement> entry : JsonUtils.getJsonObject(json, "key").entrySet())
            {
                if (entry.getKey().length() != 1)
                    throw new JsonSyntaxException("Invalid key entry: '" + entry.getKey() + "' is an invalid symbol (must be 1 character only).");
                if (" ".equals(entry.getKey()))
                    throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
    
                ingMap.put(entry.getKey().toCharArray()[0], CraftingHelper.getIngredient(entry.getValue(), context));
            }

            ingMap.put(' ', Ingredient.EMPTY);

            JsonArray patternJ = JsonUtils.getJsonArray(json, "pattern");

            if (patternJ.size() == 0)
            {
                throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
            }
            
            String[] pattern = new String[patternJ.size()];
            for (int x = 0; x < pattern.length; ++x)
            {
                String line = JsonUtils.getString(patternJ.get(x), "pattern[" + x + "]");
                if (x > 0 && pattern[0].length() != line.length())
                {
                   throw new JsonSyntaxException("Invalid pattern: each row must  be the same width");
                }
                pattern[x] = line;
            }

            ShapedPrimer primer = new ShapedPrimer();
            primer.width = pattern[0].length();
            primer.height = pattern.length;
            primer.mirrored = JsonUtils.getBoolean(json, "mirrored", true);
            primer.input = NonNullList.withSize(primer.width * primer.height, Ingredient.EMPTY);

            Set<Character> keys = Sets.newHashSet(ingMap.keySet());
            keys.remove(' ');

            int x = 0;
            for (String line : pattern)
            {
                for (char chr : line.toCharArray())
                {
                    Ingredient ing = ingMap.get(chr);
                    if (ing == null)
                        throw new JsonSyntaxException("Pattern references symbol '" + chr + "' but it's not defined in the key");
                    primer.input.set(x++, ing);
                    keys.remove(chr);
                }
            }

            if (!keys.isEmpty())
            {
                throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + keys);
            }

            ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

            return new ArcaneTableRecipe(primer.width, primer.height, primer.input, result);
        }
    }
}