package sassycitrus.craftmancy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.gui.GuiHandler;
import sassycitrus.craftmancy.init.CraftmancyBlocks;
import sassycitrus.craftmancy.init.CraftmancyCommands;
import sassycitrus.craftmancy.init.CraftmancyItems;
import sassycitrus.craftmancy.init.CraftmancyRecipes;
import sassycitrus.craftmancy.network.Network;
import sassycitrus.craftmancy.proxy.CommonProxy;
import sassycitrus.craftmancy.world.CraftmancyWorldGen;

@Mod(modid = Craftmancy.modid, name = Craftmancy.name, version = Craftmancy.version)
public class Craftmancy
{
    public static final String modid = "craftmancy";
    public static final String name = "Craftmancy";
    public static final String version = "0.1.0";

    public static final CraftmancyTab creativeTab = new CraftmancyTab();

    @Mod.Instance(modid)
    public static Craftmancy instance;

    @SidedProxy(serverSide = "sassycitrus.craftmancy.proxy.CommonProxy", clientSide = "sassycitrus.craftmancy.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println(name + " is loading!");
        
        GameRegistry.registerWorldGenerator(new CraftmancyWorldGen(), 3);
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        ManaCapabilityHandler.register();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        CraftmancyRecipes.registerSmelting();
        CraftmancyRecipes.registerOreDict();
        Network.register();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        CraftmancyCommands.register(event);
    }

    @Mod.EventBusSubscriber
    public static class Registrationhandler
    {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event)
        {
            CraftmancyBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event)
        {
            CraftmancyItems.register(event.getRegistry());
            CraftmancyBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event)
        {
            CraftmancyItems.registerModels();
            CraftmancyBlocks.registerModels();
        }
    }
}