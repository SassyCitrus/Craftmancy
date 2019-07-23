package sassycitrus.craftmancy.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.entity.EntityFireball;

public class RenderFireball extends Render<EntityFireball>
{
    private static final float SCALE = 0.2f;
    private static final ResourceLocation TEXTURE = new ResourceLocation(Craftmancy.modid, "textures/entity/fireball.png");

    public RenderFireball(RenderManager manager)
    {
        super(manager);
    }

    @Override
    public void doRender(EntityFireball entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        GlStateManager.pushMatrix();  
        GlStateManager.translate((float) x, (float) y, (float) z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.disableAlpha();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        GlStateManager.scale(SCALE, SCALE, SCALE);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.bindTexture(TEXTURE);

        float yaw = Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? this.renderManager.playerViewX : -this.renderManager.playerViewX;
        GlStateManager.rotate(180.0f - this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(yaw, 1.0f, 0.0f, 0.0f);


        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buffer.pos(-0.5, -0.25, 0.0).tex(0.0, 1.0).endVertex();
		buffer.pos(0.5, -0.25, 0.0).tex(1.0, 1.0).endVertex();
		buffer.pos(0.5, 0.75, 0.0).tex(1.0, 0).endVertex();
		buffer.pos(-0.5, 0.75, 0.0).tex(0, 0).endVertex();
        tessellator.draw();
        
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityFireball entity)
    {
        return TEXTURE;
    }
}