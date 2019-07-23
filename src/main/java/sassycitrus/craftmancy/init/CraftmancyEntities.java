package sassycitrus.craftmancy.init;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.client.renderer.RenderFireball;
import sassycitrus.craftmancy.entity.EntityFireball;

public class CraftmancyEntities
{
    // Smaller numbers are more frequent
    private static final int PROJECTILE_UPDATE_INTERVAL = 10;

    public static void register()
    {
        int id = 0;

        registerEntity(EntityFireball.class, "fireball", id++, 128, PROJECTILE_UPDATE_INTERVAL, true);
    }

    public static void registerRenderers()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityFireball.class, manager -> new RenderFireball(manager));
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int id, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
    {
        ResourceLocation registryName = new ResourceLocation(Craftmancy.modid, name);
        EntityRegistry.registerModEntity(registryName, entityClass, registryName.toString(), id, Craftmancy.instance, trackingRange, updateFrequency, sendsVelocityUpdates);
    }
}