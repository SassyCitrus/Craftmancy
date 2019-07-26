package sassycitrus.craftmancy.world;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBirchTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import sassycitrus.craftmancy.Craftmancy;

public class BiomeElderForest extends Biome
{
    public static final String NAME = "elder_forest";
    private static final WorldGenTreeAlterbaum FEATURE_TREE_ALTERBAUM = new WorldGenTreeAlterbaum(false);
    private static final WorldGenBirchTree FEATURE_TREE_BIRCH = new WorldGenBirchTree(false, false);
    private static final WorldGenTrees FEATURE_TREE_OAK = new WorldGenTrees(false);

    public BiomeElderForest()
    {
        super(new BiomeProperties(NAME)
                .setTemperature(0.7F)
                .setRainfall(0.8f)
                .setBaseHeight(0.1F)
                .setHeightVariation(0.05F)
                .setWaterColor(0x925CFF)
        );

        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
        this.decorator.grassPerChunk = 0;
        this.decorator.treesPerChunk = 10;
        this.decorator.flowersPerChunk = 1;
        this.decorator.mushroomsPerChunk = 0;
        this.decorator.generateFalls = false;

        setRegistryName(Craftmancy.modid, NAME);
        setSpawnables();
        addFlowers();
    }

    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        if (rand.nextInt(10) == 0)
        {
            return FEATURE_TREE_ALTERBAUM;
        }
        else if (rand.nextInt(5) == 0)
        {
            return FEATURE_TREE_BIRCH;
        }
        else
        {
            return FEATURE_TREE_OAK;
        }
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