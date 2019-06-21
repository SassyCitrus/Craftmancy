package sassycitrus.craftmancy.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.util.StringUtil;

public class ItemBase extends Item
{
    protected EnumRarity rarity = EnumRarity.COMMON;
    protected String name;

    public ItemBase(String name)
    {
        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Craftmancy.creativeTab);
    }

    public ItemBase setRarity(EnumRarity rarity)
    {
        this.rarity = rarity;
        return this;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return this.rarity;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (StringUtil.isShiftKeyDown())
        {
            tooltip.add(StringUtil.getInfoText("info.craftmancy." + this.name));
        }
        else
        {
            tooltip.add(StringUtil.shiftForDetails());
        }   
    }
}