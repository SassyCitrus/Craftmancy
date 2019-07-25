package sassycitrus.craftmancy.world;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.gen.feature.WorldGenTrees;
import sassycitrus.craftmancy.block.alterbaum.LeavesAlterbaum;
import sassycitrus.craftmancy.init.CraftmancyBlocks;

public class WorldGenTreeAlterbaum extends WorldGenTrees
{

    private static final IBlockState LOG = CraftmancyBlocks.ALTERBAUM_LOG.getDefaultState()
            .withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);

    private static final IBlockState LEAVES = CraftmancyBlocks.ALTERBAUM_LEAVES.getDefaultState()
            .withProperty(LeavesAlterbaum.CHECK_DECAY, false);

    public WorldGenTreeAlterbaum(boolean notify)
    {
        super(notify, 6, LOG, LEAVES, false);
    }
}