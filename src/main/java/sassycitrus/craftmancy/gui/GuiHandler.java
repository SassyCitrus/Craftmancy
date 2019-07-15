package sassycitrus.craftmancy.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import sassycitrus.craftmancy.block.manafurnace.BlockManaFurnace;
import sassycitrus.craftmancy.gui.arcanetable.ContainerArcaneTable;
import sassycitrus.craftmancy.gui.arcanetable.GuiArcaneTable;
import sassycitrus.craftmancy.gui.manafurnace.ContainerManaFurnace;
import sassycitrus.craftmancy.gui.manafurnace.GuiManaFurnace;

public class GuiHandler implements IGuiHandler
{
    public static final int ARCANE_TABLE = 0;
    public static final int MANA_FURNACE = 1;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        switch (ID)
        {
            case ARCANE_TABLE:
                return new ContainerArcaneTable(player.inventory, world);
            case MANA_FURNACE:
                return new ContainerManaFurnace(player.inventory, BlockManaFurnace.getTileEntity(world, new BlockPos(x, y, z)));
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
            case MANA_FURNACE:
                return new GuiManaFurnace((Container) getServerGuiElement(ID, player, world, x, y, z), player.inventory, BlockManaFurnace.getTileEntity(world, new BlockPos(x, y, z)));
            default:
                return null;
        }
    }
}