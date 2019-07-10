package sassycitrus.craftmancy.item.tool;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sassycitrus.craftmancy.item.ItemBase;
import sassycitrus.craftmancy.util.StringUtil;

public class Wand extends ItemBase
{
    public Wand(String name)
    {
        super("wand_" + name);

        setMaxStackSize(1);
        setRarity(EnumRarity.RARE);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if (StringUtil.isShiftKeyDown())
        {
            tooltip.add(StringUtil.getInfoText("info.craftmancy." + name));
        }
        else
        {
            tooltip.add(StringUtil.shiftForDetails());
        }
    }
}