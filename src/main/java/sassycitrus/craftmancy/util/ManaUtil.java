package sassycitrus.craftmancy.util;

import net.minecraft.entity.player.EntityPlayer;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler.IManaHandler;
import sassycitrus.craftmancy.network.Network;

public class ManaUtil
{
    public static int getMana(EntityPlayer player)
    {
        IManaHandler handler = ManaCapabilityHandler.getHandler(player);
        return handler.getMana();
    }

    public static void addMana(EntityPlayer player, int amount)
    {
        IManaHandler handler = ManaCapabilityHandler.getHandler(player);
        handler.addMana(amount);
        Network.syncPlayerMana(player);
    }

    public static void removeMana(EntityPlayer player, int amount)
    {
        addMana(player, -amount);
    }
}