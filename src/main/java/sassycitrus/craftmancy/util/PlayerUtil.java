package sassycitrus.craftmancy.util;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerUtil
{
    public static final UUID DEFAULT_UUID = new UUID(0, 0);

    public static UUID getUUIDFromPlayer(EntityPlayer player)
    {
        return player.getGameProfile().getId();
    }
}