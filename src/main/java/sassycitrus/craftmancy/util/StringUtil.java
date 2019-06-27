package sassycitrus.craftmancy.util;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class StringUtil
{
    public static boolean isShiftKeyDown()
    {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }

    public static String localize(String key)
    {
        return I18n.format(key);
    }

    public static String getInfoText(String key)
    {
        return TextFormatting.GREEN + localize(key) + TextFormatting.RESET;
    }

    public static String shiftForDetails()
    {
        return localize("info.craftmancy.holdShiftForDetails");
    }

    public static void sendMessage(EntityPlayer player, String translationKey, Object... args)
    {
        TextComponentTranslation text = new TextComponentTranslation(translationKey, args);
        player.sendMessage(text);
    }

    public static void sendMessage(EntityPlayer player, String msg)
    {
        TextComponentString text = new TextComponentString(msg);
        player.sendMessage(text);
    }
}