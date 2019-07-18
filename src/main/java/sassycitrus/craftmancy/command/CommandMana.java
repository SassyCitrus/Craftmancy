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
import sassycitrus.craftmancy.util.ManaUtil;

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
            EntityPlayer player = (EntityPlayer) sender;
            IManaHandler handler = ManaCapabilityHandler.getHandler(player);

            if (args.length > 0)
            {
                if (args[0].equals("clear"))
                {
                    handler.setManaLevel(0);
                    handler.setManaTotal(0);
                    handler.setMana(0);
                    Network.syncPlayerMana(player);
                }
                else
                {
                    try
                    {
                        int mana = Integer.parseInt(args[0]);
                        ManaUtil.addMana((EntityPlayer) sender, mana);
                    }
                    catch (NumberFormatException e)
                    {
                        throw new NumberInvalidException("commands.generic.num.invalid", args[0]);
                    }
                }
            }

            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Mana Level" + TextFormatting.RESET + ": "  + handler.getManaLevel()));
            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Mana Total" + TextFormatting.RESET + ": "  + handler.getManaTotal()));
            sender.sendMessage(new TextComponentString(TextFormatting.BLUE + "Mana" + TextFormatting.RESET + ": "  + handler.getMana()));

            Network.syncPlayerMana((EntityPlayer) sender);
        }
    }
}