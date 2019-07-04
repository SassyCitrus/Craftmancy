package sassycitrus.craftmancy.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.arcanetable.TEArcaneTable;
import sassycitrus.craftmancy.client.TESRArcaneTable;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit(FMLPreInitializationEvent event)
    {
        super.preInit(event);

        ClientRegistry.bindTileEntitySpecialRenderer(TEArcaneTable.class, new TESRArcaneTable());
    }

    @Override
    public void registerItemRenderer(Item item, int meta, String id)
    {
        ModelResourceLocation model = new ModelResourceLocation(Craftmancy.modid + ":" + id, "inventory");
        ModelLoader.setCustomModelResourceLocation(item, meta, model);
    }
}