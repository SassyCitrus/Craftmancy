package sassycitrus.craftmancy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CraftmancyTab extends CreativeTabs
{
    public CraftmancyTab()
    {
        super(Craftmancy.modid);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.DIAMOND);
    }
}