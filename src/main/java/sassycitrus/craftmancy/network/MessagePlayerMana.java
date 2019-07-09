package sassycitrus.craftmancy.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler.IManaHandler;

public class MessagePlayerMana implements IMessage, IMessageHandler<MessagePlayerMana, IMessage>
{

    private IManaHandler serverPlayer;

    // [player::mana, player::capacity]
    private int[] clientData;

    public MessagePlayerMana() {}

    public MessagePlayerMana(EntityPlayer player)
    {
        if (player != null && player.hasCapability(ManaCapabilityHandler.CAPABILITY_MANA, EnumFacing.DOWN))
        {
            this.serverPlayer = player.getCapability(ManaCapabilityHandler.CAPABILITY_MANA, EnumFacing.DOWN);
        }
    }

    
    // Client deserialization
    @Override
    public void fromBytes(ByteBuf buf)
    {
        clientData = new int[]{buf.readInt(), buf.readInt()};
    }

    // Server serialization
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(serverPlayer.getMana());
        buf.writeInt(serverPlayer.getCapacity());
    }

    // Client handling
    @Override
    public IMessage onMessage(MessagePlayerMana message, MessageContext ctx)
    {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
        {
            IManaHandler player = Minecraft.getMinecraft().player.getCapability(ManaCapabilityHandler.CAPABILITY_MANA, EnumFacing.DOWN);

            if (player != null)
            {
                player.setMana(message.clientData[0]);
                player.setCapacity(message.clientData[1]);
            }
        });

        return null;
    }
}