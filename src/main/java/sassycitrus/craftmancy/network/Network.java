package sassycitrus.craftmancy.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import sassycitrus.craftmancy.Craftmancy;

public class Network
{
    public static final SimpleNetworkWrapper INSTANCE = new SimpleNetworkWrapper(Craftmancy.modid);

    public static void register()
    {
        INSTANCE.registerMessage(MessagePlayerMana.class, MessagePlayerMana.class, 0, Side.CLIENT);
    }

    public static void sendTo(IMessage message, EntityPlayer player)
    {
        INSTANCE.sendTo(message, (EntityPlayerMP) player);
    }

    public static void syncPlayerMana(EntityPlayer player)
    {
        sendTo(new MessagePlayerMana(player), player);
    }
}