package sassycitrus.craftmancy.item.tool;

import net.minecraft.item.EnumRarity;
import sassycitrus.craftmancy.item.ItemBase;

public class Wand extends ItemBase
{
    private int tier;
    
    public Wand(int tier)
    {
        super("wand_tier_" + tier);
        this.tier = tier;

        setMaxStackSize(1);
        setRarity(EnumRarity.RARE);
    }

    public int getTier()
    {
        return this.tier;
    }
}