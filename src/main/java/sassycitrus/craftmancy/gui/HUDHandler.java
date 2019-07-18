package sassycitrus.craftmancy.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler.IManaHandler;
import sassycitrus.craftmancy.item.tool.Wand;
import sassycitrus.craftmancy.util.ManaUtil;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Craftmancy.modid)
@SideOnly(Side.CLIENT)
public class HUDHandler
{
    private static ResourceLocation MANA_BAR = new ResourceLocation("craftmancy:textures/gui/hud/mana_bar.png");

    @SubscribeEvent
    public static void onDraw(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() == ElementType.EXPERIENCE)
        {
            drawManaBar(event);
        }
    }

    private static void drawManaBar(RenderGameOverlayEvent.Pre event)
    {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution resolution = event.getResolution();

        if (!(mc.player.getHeldItemMainhand().getItem() instanceof Wand))
        {
            return;
        }

        event.setCanceled(true);

        IManaHandler player = mc.player.getCapability(ManaCapabilityHandler.CAPABILITY_MANA, EnumFacing.DOWN);

        if (player == null)
        {
            return;
        }

        int width = 182;
        int height = 5;

        int x = resolution.getScaledWidth() / 2 - width / 2;
        int y = resolution.getScaledHeight() - height - 24;
        int manaBarWidth = (int) (player.getMana() / (float) ManaUtil.calculateManaForLevel(player.getManaLevel()) * width);

        mc.renderEngine.bindTexture(MANA_BAR);

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, 192, 16);
        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, height, manaBarWidth, height, 192, 16);

        if (player.getManaLevel() > 0)
        {
            String s = "" + player.getManaLevel();
            int i1 = (resolution.getScaledWidth() - mc.fontRenderer.getStringWidth(s)) / 2;
            int j1 = resolution.getScaledHeight() - 31 - 4;
            mc.fontRenderer.drawString(s, i1 + 1, j1, 0);
            mc.fontRenderer.drawString(s, i1 - 1, j1, 0);
            mc.fontRenderer.drawString(s, i1, j1 + 1, 0);
            mc.fontRenderer.drawString(s, i1, j1 - 1, 0);
            mc.fontRenderer.drawString(s, i1, j1, 0xB79BEF);
        }
    }
}