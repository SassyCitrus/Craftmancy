package sassycitrus.craftmancy.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
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

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Craftmancy.modid)
@SideOnly(Side.CLIENT)
public class HUDHandler
{
    private static ResourceLocation MANA_BAR = new ResourceLocation("craftmancy:textures/gui/hud/mana_bar.png");

    @SubscribeEvent
    public static void onDraw(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() == ElementType.EXPERIENCE)
        {
            ScaledResolution resolution = event.getResolution();

            drawManaBar(resolution);
        }
    }

    private static void drawManaBar(ScaledResolution resolution)
    {
        Minecraft mc = Minecraft.getMinecraft();

        if (!(mc.player.getHeldItemMainhand().getItem() instanceof Wand))
        {
            return;
        }

        IManaHandler player = mc.player.getCapability(ManaCapabilityHandler.CAPABILITY_MANA, EnumFacing.DOWN);

        if (player == null)
        {
            return;
        }

        int width = 182;
        int height = 5;

        int x = resolution.getScaledWidth() / 2 - width / 2;
        int y = resolution.getScaledHeight() - height - 40;

        mc.renderEngine.bindTexture(MANA_BAR);

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, width, height, 192, 16);

        int manaBarWidth = (int) (player.getMana() / (float) player.getCapacity() * width);

        Gui.drawModalRectWithCustomSizedTexture(x, y, 0, height, manaBarWidth, height, 192, 16);
    }
}