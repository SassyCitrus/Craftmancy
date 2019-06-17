package sassycitrus.craftmancy.item.tool;

import net.minecraft.item.EnumRarity;
import sassycitrus.craftmancy.item.ItemBase;

public class Wand extends ItemBase
{
    public Wand()
    {
        super("wand");
        setMaxStackSize(1);
        setRarity(EnumRarity.RARE);
    }
}