package sassycitrus.craftmancy.item.tool;

import net.minecraft.item.EnumRarity;
import sassycitrus.craftmancy.item.ItemBase;

public class Wand extends ItemBase
{
    protected int level;

    public Wand(int level)
    {
        super("wand_" + level);

        this.level = level;

        setMaxStackSize(1);
        setRarity(EnumRarity.RARE);
    }
}