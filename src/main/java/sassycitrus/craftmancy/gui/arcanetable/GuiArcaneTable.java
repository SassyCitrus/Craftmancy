package sassycitrus.craftmancy.gui.arcanetable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.init.CraftmancyBlocks;
import sassycitrus.craftmancy.util.StringUtil;

public class GuiArcaneTable extends GuiContainer
{
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Craftmancy.modid, "textures/gui/container/arcane_table.png");

    private InventoryPlayer playerInventory;

    public GuiArcaneTable(Container container, InventoryPlayer playerInventory)
    {
        super(container);
        this.playerInventory = playerInventory;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1, 1, 1, 1);
        mc.getTextureManager().bindTexture(BG_TEXTURE);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        String name = StringUtil.localize(CraftmancyBlocks.ARCANE_TABLE.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0xEEEEEE);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 94, 0xEEEEEE);
    }
}