package sassycitrus.craftmancy.item.tool;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import sassycitrus.craftmancy.item.ItemBase;

public class Wand extends ItemBase
{
    public Wand()
    {
        super("wand");

        setMaxStackSize(1);
        setRarity(EnumRarity.RARE);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            ItemStack wand = new ItemStack(this);
            NBTTagCompound nbt = new NBTTagCompound();

            nbt.setString("core", "core:wood");
            nbt.setString("binding", "binding:iron");
            nbt.setString("gem", "gem:diamond");

            wand.setTagCompound(nbt);
            items.add(wand);
        }
    }
}