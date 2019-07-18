package sassycitrus.craftmancy.util;

import net.minecraft.entity.player.EntityPlayer;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler.IManaHandler;
import sassycitrus.craftmancy.network.Network;

public class ManaUtil
{
    public static void addMana(EntityPlayer player, int amount)
    {
        IManaHandler handler = ManaCapabilityHandler.getHandler(player);
        handler.addManaTotal(amount);
        handler.addMana(amount);
        updateLevels(handler);
        Network.syncPlayerMana(player);
    }

    public static boolean removeManaLevel(EntityPlayer player, int levels)
    {
        IManaHandler handler = ManaCapabilityHandler.getHandler(player);
        boolean result = handler.removeManaLevel(levels);
        Network.syncPlayerMana(player);
        return result;
    }

    public static void updateLevels(IManaHandler handler)
    {
        while (true)
        {
            int manaNextLevel = ManaUtil.calculateManaForLevel(handler.getManaLevel());

            if (handler.getMana() >= manaNextLevel)
            {
                handler.removeMana(manaNextLevel);
                handler.addManaLevel();
            }
            else
            {
                break;
            }
        }
    }

    public static int calculateManaForLevel(int level)
    {
        if (level <= 15)
        {
            // Levels 0-15
            return 2 * level + 7;
        }
        else if (level <= 30)
        {
            // Levels 16-30
            return 5 * level - 38;
        }
        else
        {
            // Levels 31+
            return 9 * level - 158;
        }
    }
}