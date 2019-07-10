package sassycitrus.craftmancy.gui.arcanetable;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class InventoryCraftingArcaneTable extends InventoryCrafting
{
    public static final String NAME = "craftmancy.container.arcane_table";

    public InventoryCraftingArcaneTable(Container container)
    {
        super(container, 3, 3);
    }

    @Override
    public boolean hasCustomName()
    {
        return true;
    }

    @Override
    public String getName()
    {
        return NAME;
    }
}