package sassycitrus.craftmancy.network;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import sassycitrus.craftmancy.Craftmancy;

public class Network
{
    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Craftmancy.modid);

    public static void register()
    {
        
    }

    public static void sendTo(IMessage message, EntityPlayerMP player)
    {
        INSTANCE.sendTo(message, player);
    }
}