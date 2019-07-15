package sassycitrus.craftmancy.gui.manafurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.manafurnace.TileManaFurnace;
import sassycitrus.craftmancy.init.CraftmancyBlocks;
import sassycitrus.craftmancy.util.StringUtil;

public class GuiManaFurnace extends GuiContainer
{
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(Craftmancy.modid, "textures/gui/container/mana_furnace.png");

    private TileManaFurnace te;
    private InventoryPlayer playerInventory;

    public GuiManaFurnace(Container container, InventoryPlayer playerInventory, TileManaFurnace te)
    {
        super(container);
        this.playerInventory = playerInventory;
        this.te = te;
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
        String name = StringUtil.localize(CraftmancyBlocks.MANA_FURNACE.getUnlocalizedName() + ".name");
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x333333);
        fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedComponentText(), 8, ySize - 94, 0x333333);
    }
}