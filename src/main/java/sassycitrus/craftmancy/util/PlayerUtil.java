package sassycitrus.craftmancy.util;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class PlayerUtil
{
    public static final UUID DEFAULT_UUID = new UUID(0, 0);

    public static EntityPlayer getPlayerFromUUID(UUID uuid)
    {
        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUUID(uuid);
    }

    public static UUID getUUIDFromPlayer(EntityPlayer player)
    {
        return player.getGameProfile().getId();
    }
}