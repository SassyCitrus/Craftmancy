package sassycitrus.craftmancy.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import sassycitrus.craftmancy.block.arcanetable.BlockArcaneTable;
import sassycitrus.craftmancy.block.arcanetable.TEArcaneTable;

public class TESRArcaneTable extends TileEntitySpecialRenderer<TEArcaneTable>
{
    private static double[] OFFSET_X =
    {
        36.75/16f, 26.75/16f, 16.75/16f,
        36.75/16f, 26.75/16f, 16.75/16f,
        36.75/16f, 26.75/16f, 16.75/16f
    };

    private static double[] OFFSET_Z =
    {
        36.75/16f, 36.75/16f, 36.75/16f,
        26.75/16f, 26.75/16f, 26.75/16f,
        16.75/16f, 16.75/16f, 16.75/16f
    };

    @Override
    public void render(TEArcaneTable te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GlStateManager.pushAttrib();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.enableLighting();
        GlStateManager.pushMatrix();

        GlStateManager.translate(x, y + 15.7/16f, z);
        GlStateManager.scale(0.3f, 0.3f, 0.3f);

        EnumFacing facing = getWorld().getBlockState(te.getPos()).getValue(BlockArcaneTable.FACING);
        switch (facing)
        {
            case SOUTH:
                GlStateManager.rotate(180, 0, 1, 0);
                GlStateManager.translate(-3.35, 0, -3.35);
                break;
            case EAST:
                GlStateManager.rotate(-90, 0, 1, 0);
                GlStateManager.translate(0, 0, -3.35);
                break;
            case WEST:
                GlStateManager.rotate(90, 0, 1, 0);
                GlStateManager.translate(-3.35, 0, 0);
                break;
            default:
                break;
        }

        IItemHandler inventory = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        for (int i = 0; i < 9; i++)
        {
            ItemStack stack = inventory.getStackInSlot(i);

            if(!stack.isEmpty())
            {
                GlStateManager.pushMatrix();

                GlStateManager.translate(OFFSET_X[i], 0, OFFSET_Z[i]);

                if (!(stack.getItem() instanceof ItemBlock))
                {
                    GlStateManager.translate(0, 1.25/16f, -2/16f);
                    GlStateManager.rotate(90, 1, 0, 0);
                    GlStateManager.rotate(180, 0, 1, 0);
                }

                Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.GROUND);

                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }
}