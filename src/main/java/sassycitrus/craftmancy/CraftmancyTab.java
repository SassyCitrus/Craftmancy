package sassycitrus.craftmancy;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import sassycitrus.craftmancy.init.CraftmancyItems;

public class CraftmancyTab extends CreativeTabs
{
    public CraftmancyTab()
    {
        super(Craftmancy.modid);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(CraftmancyItems.GEM_EDELILA);
    }
}