package sassycitrus.craftmancy.proxy;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.client.model.ModelWand;
import sassycitrus.craftmancy.init.CraftmancyItems;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelResourceLocation model = new ModelResourceLocation(Craftmancy.modid + ":" + id, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

    @Override
    public void registerModelLoader()
    {
        ModelLoaderRegistry.registerLoader(ModelWand.CustomModelLoader.INSTANCE);
        ModelLoader.setCustomMeshDefinition(CraftmancyItems.WAND, stack -> ModelWand.LOCATION);
        ModelBakery.registerItemVariants(CraftmancyItems.WAND, ModelWand.LOCATION);
    }
}