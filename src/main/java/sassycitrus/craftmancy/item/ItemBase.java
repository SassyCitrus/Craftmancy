package sassycitrus.craftmancy.item;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sassycitrus.craftmancy.Craftmancy;

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
}