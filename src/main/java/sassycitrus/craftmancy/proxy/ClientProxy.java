package sassycitrus.craftmancy.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import sassycitrus.craftmancy.Craftmancy;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelResourceLocation model = new ModelResourceLocation(Craftmancy.modid + ":" + id, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }
}