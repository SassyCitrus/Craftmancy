package sassycitrus.craftmancy.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Craftmancy.modid)
public class HUDHandler
{
    private static ResourceLocation MANA_BAR = new ResourceLocation("craftmancy:textures/gui/hud/mana_bar.png");

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onDraw(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() == ElementType.EXPERIENCE)
        {
            ScaledResolution resolution = event.getResolution();

            drawManaBar(resolution);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void drawManaBar(ScaledResolution resolution)
    {
        Minecraft mc = Minecraft.getMinecraft();

        int padding = 16;
        int width = 101;
        int height = 5;

        int x = padding;
        int y = resolution.getScaledHeight() - height - padding;

        mc.renderEngine.bindTexture(MANA_BAR);

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, 128, 32);

        // Get players mana level
        // TODO: pull data from players mana capability
        int playerMana = 64;
        int playerManaCap = 100;

        int manaBarWidth = (int) (playerMana / (float) playerManaCap * width);

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, height, manaBarWidth, height, 128, 32);
    }
}