package sassycitrus.craftmancy.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sassycitrus.craftmancy.gui.arcanetable.ContainerArcaneTable;
import sassycitrus.craftmancy.gui.arcanetable.GuiArcaneTable;

public class GuiHandler implements IGuiHandler
{
    public static final int ARCANE_TABLE = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case ARCANE_TABLE:
                return new ContainerArcaneTable(player.inventory, world);
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case ARCANE_TABLE:
                return new GuiArcaneTable((ContainerArcaneTable) getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }
}