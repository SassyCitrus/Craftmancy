package sassycitrus.craftmancy.world;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import sassycitrus.craftmancy.init.CraftmancyBlocks;

public class CraftmancyWorldGen implements IWorldGenerator
{
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider)
    {
        if (world.provider.getDimension() == 0)
        {
            generateOverworld(random, chunkX, chunkZ, world, generator, provider);
        }
        else if (world.provider.getDimension() == -1)
        {
            generateNether(random, chunkX, chunkZ, world, generator, provider);
        }
    }

    private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider)
    {
        generateOre(CraftmancyBlocks.ORE_EDELILA.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 4, 32, 1 + random.nextInt(5), 8);
        generateOre(CraftmancyBlocks.ORE_MITHRIL.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 1, 63, 3 + random.nextInt(3), 12);
    }

    private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider)
    {
        generateOre(CraftmancyBlocks.ORE_FEUERMIN.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 32, 95, 2 + random.nextInt(4), 12, BlockMatcher.forBlock(Blocks.NETHERRACK));
    }

    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances, Predicate<IBlockState> predicate)
    {
        int deltaY = maxY - minY;

        for (int i = 0; i < chances; i++)
        {
            BlockPos pos = new BlockPos(
                x + random.nextInt(16),
                minY + random.nextInt(deltaY),
                z + random.nextInt(16)
            );

            WorldGenMinable generator = new WorldGenMinable(ore, size, predicate);
            generator.generate(world, random, pos);
        }
    }

    private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances)
    {
        generateOre(ore, world, random, x, z, minY, maxY, size, chances, BlockMatcher.forBlock(Blocks.STONE));
    }
}