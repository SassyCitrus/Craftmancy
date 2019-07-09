package sassycitrus.craftmancy.init;

import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import sassycitrus.craftmancy.command.CommandMana;

public class CraftmancyCommands
{
    public static void register(FMLServerStartingEvent event)
    {
        event.registerServerCommand(new CommandMana());
    }
}