package sassycitrus.craftmancy.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import sassycitrus.craftmancy.block.alterbaum.SaplingAlterbaum;
import sassycitrus.craftmancy.init.CraftmancyBlocks;

public class WorldGenTreeAlterbaum extends WorldGenAbstractTree
{
    private static final IBlockState SAPLING = CraftmancyBlocks.ALTERBAUM_SAPLING.getDefaultState().withProperty(SaplingAlterbaum.STAGE, Integer.valueOf(1));
    private static final ResourceLocation STRUCTURE = new ResourceLocation("craftmancy:natural/alterbaum_tree");
    private static final int HEIGHT = 13;
    private static final int RADIUS = 6;

    public WorldGenTreeAlterbaum(boolean shouldNotify)
    {
        super(shouldNotify);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        if (!world.isAreaLoaded(pos, RADIUS))
        {
            return false;
        }

        for (int xx = -RADIUS; xx <= RADIUS; xx++)
        {
            for (int zz = -RADIUS; zz <= RADIUS; zz++)
            {
                for (int yy = 2; yy < HEIGHT; yy++)
                {
                    if (!world.isAirBlock(pos.add(xx, yy, zz)) && world.getBlockState(pos.add(xx, yy, zz)).isNormalCube())
                    {
                        return false;
                    }
                }
            }
        }

        if (world.getBlockState(pos) == SAPLING)
        {
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
        }

        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        Template template = templateManager.getTemplate(server, STRUCTURE);
        ChunkPos chunkpos = new ChunkPos(pos);
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkpos.getXStart(), 0, chunkpos.getZStart(), chunkpos.getXEnd(), 256, chunkpos.getZEnd());
        PlacementSettings placementSettings = new PlacementSettings().setRandom(rand).setBoundingBox(structureboundingbox);

        template.addBlocksToWorldChunk(world, pos.add(-4, 0, -4), placementSettings);

        return true;
    }
}