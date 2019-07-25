package sassycitrus.craftmancy.init;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.world.BiomeElderForest;

@ObjectHolder(Craftmancy.modid)
public class CraftmancyBiomes
{
    public final static BiomeElderForest ELDER_FOREST = null;

    public static void register(final RegistryEvent.Register<Biome> event)
    {
        event.getRegistry().register(new BiomeElderForest());
    }

    public static void init()
    {
        BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ELDER_FOREST, 8));
        BiomeManager.addSpawnBiome(ELDER_FOREST);
        BiomeDictionary.addTypes(ELDER_FOREST,
            BiomeDictionary.Type.FOREST,
            BiomeDictionary.Type.MAGICAL
        );
    }
}