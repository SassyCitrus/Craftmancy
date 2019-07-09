package sassycitrus.craftmancy.command;

import javax.annotation.Nonnull;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler.IManaHandler;
import sassycitrus.craftmancy.network.Network;

public class CommandMana extends CommandBase
{
    @Override
    @Nonnull
    public String getName()
    {
        return "mana";
    }

    @Override
    public String getUsage(ICommandSender sender)
    {
        return "mana [mana] [capacity]";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
    {
        if (sender instanceof EntityPlayer)
        {
            IManaHandler player = ManaCapabilityHandler.getHandler((EntityPlayer) sender);

            if (args.length > 0)
            {
                try
                {
                    int mana = Integer.parseInt(args[0]);
                    player.setMana(mana);
                }
                catch (NumberFormatException e)
                {
                    throw new NumberInvalidException("commands.generic.num.invalid", args[0]);
                }
            }
            
            if (args.length > 1)
            {
                try
                {
                    int capacity = Integer.parseInt(args[1]);
                    player.setCapacity(capacity);
                }
                catch (NumberFormatException e)
                {
                    throw new NumberInvalidException("commands.generic.num.invalid", args[1]);
                }
            }

            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Mana" + TextFormatting.RESET + ": "  + player.getMana() + "/" + player.getCapacity()));

            Network.syncPlayerMana((EntityPlayer) sender);
        }
    }
}