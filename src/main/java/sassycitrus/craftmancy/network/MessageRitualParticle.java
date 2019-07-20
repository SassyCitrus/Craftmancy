package sassycitrus.craftmancy.network;

import java.util.stream.IntStream;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageRitualParticle implements IMessage, IMessageHandler<MessageRitualParticle, IMessage>
{
    // BlockPos serialized as long
    private long serverData;
    private long clientData;

    public MessageRitualParticle() {}

    public MessageRitualParticle(BlockPos pos)
    {
        this.serverData = pos.toLong();
    }
    
    // Client deserialization
    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.clientData = buf.readLong();
    }

    // Server serialization
    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.serverData);
    }

    @Override
    public IMessage onMessage(MessageRitualParticle message, MessageContext ctx)
    {
        FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() ->
        {
            World world = Minecraft.getMinecraft().world;
            BlockPos pos = BlockPos.fromLong(message.clientData);

            double xPos = pos.getX() + 0.5D;
            double yPos = pos.getY() + 1D;
            double zPos = pos.getZ() + 0.5D;

            IntStream.range(0, 10).forEach(n -> 
            {
                double xSpeed = world.rand.nextGaussian() * 0.02D;
                double ySpeed = world.rand.nextGaussian() * 0.02D;
                double zSpeed = world.rand.nextGaussian() * 0.02D;

                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xPos, yPos, zPos, xSpeed, ySpeed, zSpeed);
            });
        });

        return null;
    }
}