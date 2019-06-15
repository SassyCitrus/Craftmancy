package sassycitrus.craftmancy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sassycitrus.craftmancy.proxy.CommonProxy;

@Mod(modid = Craftmancy.modid, name = Craftmancy.name, version = Craftmancy.version)
public class Craftmancy
{
    public static final String modid = "craftmancy";
    public static final String name = "Craftmancy";
    public static final String version = "1.0.0";

    @Mod.Instance(modid)
    public static Craftmancy instance;

    @SidedProxy(serverSide = "sassycitrus.craftmancy.proxy.CommonProxy", clientSide = "sassycitrus.craftmancy.proxy.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println(name + " is loading!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}