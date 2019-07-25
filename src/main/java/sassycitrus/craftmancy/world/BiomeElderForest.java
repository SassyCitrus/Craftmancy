package sassycitrus.craftmancy.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;

public class BiomeElderForest extends Biome
{
    public static final String NAME = "elder_forest";
    private static final WorldGenTreeAlterbaum TREE_ALTERBAUM_FEATURE = new WorldGenTreeAlterbaum(false);

    public BiomeElderForest()
    {
        super(new BiomeProperties(NAME)
                .setTemperature(0.7F)
                .setRainfall(0.8f)
                .setBaseHeight(0.2F)
                .setHeightVariation(0.3F)
                .setWaterColor(0x925CFF)
        );

        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.grassPerChunk = 2;
        this.decorator.treesPerChunk = 6;
        this.decorator.flowersPerChunk = 1;
        this.decorator.generateFalls = false;

        setRegistryName(Craftmancy.modid, NAME);
        setSpawnables();
        addFlowers();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return TREE_ALTERBAUM_FEATURE;
    }

    private void setSpawnables()
    {
        this.spawnableCreatureList.clear();
        this.spawnableMonsterList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
    }

    private void addFlowers()
    {
        this.flowers.clear();
        addDefaultFlowers();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        return getModdedBiomeGrassColor(0x302052);
    }
}