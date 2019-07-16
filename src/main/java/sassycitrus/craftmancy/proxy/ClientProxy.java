package sassycitrus.craftmancy.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.ritual.TESRRitualBlock;
import sassycitrus.craftmancy.block.ritual.TileRitualBlock;

public class ClientProxy extends CommonProxy
{
    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelResourceLocation model = new ModelResourceLocation(Craftmancy.modid + ":" + id, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }

    @Override
    public void registerRenderers()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileRitualBlock.class, new TESRRitualBlock());
    }
}