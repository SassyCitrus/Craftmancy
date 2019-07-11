package sassycitrus.craftmancy.world;

import java.util.Random;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import sassycitrus.craftmancy.init.CraftmancyBlocks;

public class WorldGenTreeAlterbaum extends WorldGenAbstractTree
{
    private static IBlockState LOG_X = CraftmancyBlocks.ALTERBAUM_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X);
    private static IBlockState LOG_Y = CraftmancyBlocks.ALTERBAUM_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
    private static IBlockState LOG_Z = CraftmancyBlocks.ALTERBAUM_LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Z);
    private static IBlockState WOOD = CraftmancyBlocks.ALTERBAUM_WOOD.getDefaultState();
    private static IBlockState LEAVES = CraftmancyBlocks.ALTERBAUM_LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    
    public WorldGenTreeAlterbaum(boolean shouldNotify)
    {
        super(shouldNotify);
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos pos)
    {
        int radius = rand.nextInt(2) + 3;
        int height = rand.nextInt(5) + 15;
        int maxRadius = 9;

        if (!world.isAreaLoaded(pos, maxRadius))
        {
            return false;
        }

        for (int xx = -maxRadius; xx <= maxRadius; xx++)
        {
            for (int zz = -maxRadius; zz <= maxRadius; zz++)
            {
                for (int yy = 2; yy < height; yy++)
                {
                    if (!world.isAirBlock(pos.add(xx, yy, zz)) && world.getBlockState(pos.add(xx, yy, zz)).isNormalCube())
                    {
                        return false;
                    }
                }
            }
        }

        for (int yy = 0; yy< height; yy++)
        {
            if (yy % 3 == 0 && radius > 1 && yy > 3)
            {
                radius--;
            }

            for (int i = radius * -1; i <= radius; i++)
            {
                for (int j = radius * -1; j <= radius; j++)
                {
                    double dSq = i * i + j * j;

                    if (Math.round(Math.sqrt(dSq)) < radius && yy <= 1 + height - 2)
                    {
                        world.setBlockState(pos.add(i, yy, j), WOOD, 2);
                    }
                    if (Math.round(Math.sqrt(dSq)) == radius && yy == 0 || Math.round(Math.sqrt(dSq)) == radius && yy <= height - 1)
                    {
                        world.setBlockState(pos.add(i, yy, j), WOOD, 2);
                    }
                }
            }

            if (yy == height / 2 + 2)
            {
                createBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), 0), 1, false, rand.nextInt(2) + 4);
				createBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), 0), 2, false, rand.nextInt(2) + 4);
				createBranch(world, rand, pos.add(0, yy - rand.nextInt(2), radius + 1), 3, false, rand.nextInt(2) + 4);
				createBranch(world, rand, pos.add(0, yy - rand.nextInt(2), - radius - 1), 4, false, rand.nextInt(2) + 4);

				createBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), radius + 1), 5, false, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), - radius - 1), 6, false, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), radius + 1), 7, false, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), - radius - 1), 8, false, rand.nextInt(2) + 3);
            }

            if ( yy == height /2 + 4)
            {
                createSmallBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), 0), 1, 4);
				createSmallBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), 0), 2, 4);
				createSmallBranch(world, rand, pos.add(0, yy - rand.nextInt(2), radius + 1), 3, 4);
				createSmallBranch(world, rand, pos.add(0, yy - rand.nextInt(2), - radius - 1), 4, 4);

				createSmallBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), radius + 1), 5, 3);
				createSmallBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), - radius - 1), 6, 3);
				createSmallBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), radius + 1), 7, 3);
				createSmallBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), - radius - 1), 8, 3);
            }

            if (yy == height/2 + 7)
            {
                createSmallBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), 0), 1, 2);
				createSmallBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), 0), 2, 2);
				createSmallBranch(world, rand, pos.add(0, yy - rand.nextInt(3), radius + 1), 3, 2);
				createSmallBranch(world, rand, pos.add(0, yy - rand.nextInt(3), - radius - 1), 4, 2);

				createSmallBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), radius + 1), 5, 2);
				createSmallBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), - radius - 1), 6, 2);
				createSmallBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), radius + 1), 7, 2);
				createSmallBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), - radius - 1), 8, 2);
            }

            if (yy == 0)
            {
                createBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), 0), 1, true, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), 0), 2, true, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(0, yy - rand.nextInt(2), radius + 1), 3, true, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(0, yy - rand.nextInt(2), - radius - 1), 4, true, rand.nextInt(2) + 3);

				createBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), radius + 1), 5, true, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), - radius - 1), 6, true, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(- radius - 1, yy - rand.nextInt(2), radius + 1), 7, true, rand.nextInt(2) + 3);
				createBranch(world, rand, pos.add(radius + 1, yy - rand.nextInt(2), - radius - 1), 8, true, rand.nextInt(2) + 3);
            }
        }

        createMainCanopy(world, rand, pos.add(0, height / 2 + 4, 0), maxRadius);
        return true;
    }

    private void createSmallBranch(World world, Random rand, BlockPos pos, int dir, int branchLength)
    {
		int y = 0;
		boolean branchBend = false;
        for (int i = 0; i <= branchLength; ++i)
        {

            if (i >= 2)
            {
				y++;
				branchBend = true;
			}

            switch (dir)
            {
			    case 1:
			    	world.setBlockState(pos.east(i).up(y), branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 2:
			    	world.setBlockState(pos.west(i).up(y),  branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 3:
			    	world.setBlockState(pos.south(i).up(y),  branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 4:
			    	world.setBlockState(pos.north(i).up(y),  branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 5:
			    	world.setBlockState(pos.east(i).up(y).south(i), branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 6:
			    	world.setBlockState(pos.west(i).up(y).north(i), branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 7:
			    	world.setBlockState(pos.west(i).up(y).south(i), branchBend ? LOG_Y : LOG_X, 2);
			    	break;

			    case 8:
			    	world.setBlockState(pos.east(i).up(y).north(i), branchBend ? LOG_Y : LOG_X, 2);
			    	break;
			}
		}
    }
    

    private void createMainCanopy(World world, Random rand, BlockPos pos, int maxRadius)
    {
        for (int x1 = - maxRadius; x1 <= maxRadius; x1++)
        {
            for (int z1 = - maxRadius; z1 <= maxRadius; z1++)
            {
                for (int y1 = 0; y1 < maxRadius; y1++)
                {
					double dSq = Math.pow(x1, 2.0D) + Math.pow(z1, 2.0D) + Math.pow(y1, 2.5D);
					if (Math.round(Math.sqrt(dSq)) < maxRadius - 1 && y1 > 0)
						if (!isALog(world.getBlockState(pos.add(x1, y1, z1))))
							world.setBlockState(pos.add(x1, y1, z1), LOG_Y);
				}
            } 
        }

		//Generate leaves after generating logs so that it doesn't trigger breakBlock when replacing blocks, causing BlockLeaves' CHECK_DECAY to become true
        for (int x1 = - maxRadius; x1 <= maxRadius; x1++)
        {
            for (int z1 = - maxRadius; z1 <= maxRadius; z1++)
            {
                for (int y1 = 0; y1 < maxRadius; y1++)
                {
					double dSq = Math.pow(x1, 2.0D) + Math.pow(z1, 2.0D) + Math.pow(y1, 2.5D);
                    if (Math.round(Math.sqrt(dSq)) <= maxRadius)
                    {
                        if (!isALog(world.getBlockState(pos.add(x1, y1, z1))) && rand.nextInt(5) != 0)
                        {
                            world.setBlockState(pos.add(x1, y1, z1), LEAVES, 2);
                            if (Math.round(Math.sqrt(dSq)) <= maxRadius && rand.nextInt(3) == 0 && y1 == 0)
                            {
                                if (world.getBlockState(pos.add(x1, y1, z1)) == LEAVES)
                                {
                                    for (int i = 1; i < 1 + rand.nextInt(3); i++)
                                    {
                                        if (!isALog(world.getBlockState(pos.add(x1, y1 - i, z1))))
                                        {
                                            world.setBlockState(pos.add(x1, y1 - i, z1), LEAVES, 2);
                                        }
                                    }
                                }
                            }
                    
                        }
                    }
				}
            }
        }         
    }
    
    private boolean isALog(IBlockState state)
    {
		return state.getBlock() instanceof BlockLog;
    }
    
    private void createBranch(World world, Random rand, BlockPos pos, int dir, boolean root, int branchLength)
    {
		int y = 0;
		boolean branchBend = false;
        for (int i = 0; i <= branchLength; ++i)
        {

            if (i >= 3)
            {
				y++;
				branchBend = true;
			}

            switch (dir)
            {
			case 1:

                if (!root)
                {
					world.setBlockState(pos.east(i).up(y), branchBend ? LOG_Y : LOG_X, 2);
				}
                else
                {
					world.setBlockState(pos.east(i).down(y), WOOD, 2);
					world.setBlockState(pos.east(i).down(y - 1), WOOD, 2);
				}
				break;

			case 2:
                if (!root)
                {
					world.setBlockState(pos.west(i).up(y), branchBend ? LOG_Y : LOG_X, 2);
				}
				else {
					world.setBlockState(pos.west(i).down(y), WOOD, 2);
					world.setBlockState(pos.west(i).down(y - 1), WOOD, 2);
				}
				break;

			case 3:
                if (!root)
                {
					world.setBlockState(pos.south(i).up(y), branchBend ? LOG_Y : LOG_Z, 2);

				}
				else {
					world.setBlockState(pos.south(i).down(y), WOOD, 2);
					world.setBlockState(pos.south(i).down(y - 1), WOOD, 2);
				}
				break;

			case 4:
                if (!root)
                {
					world.setBlockState(pos.north(i).up(y), branchBend ? LOG_Y : LOG_Z, 2);
				}
				else {
					world.setBlockState(pos.north(i).down(y), WOOD, 2);
					world.setBlockState(pos.north(i).down(y - 1), WOOD, 2);
				}
				break;

			case 5:
                if (!root)
                {
					world.setBlockState(pos.east(i - 1).up(y).south(i - 1), branchBend ? LOG_Y : LOG_X, 2);
				}
                else
                {
					world.setBlockState(pos.east(i - 1).down(y).south(i - 1), WOOD, 2);
					world.setBlockState(pos.east(i - 1).down(y - 1).south(i - 1), WOOD, 2);
				}
				break;

			case 6:
                if (!root)
                {
					world.setBlockState(pos.west(i - 1).up(y).north(i - 1), branchBend ? LOG_Y : LOG_X, 2);
				}
                else
                {
					world.setBlockState(pos.west(i - 1).down(y).north(i - 1), WOOD, 2);
					world.setBlockState(pos.west(i - 1).down(y - 1).north(i - 1), WOOD, 2);
				}
				break;

			case 7:
                if (!root)
                {
					world.setBlockState(pos.west(i - 1).up(y).south(i - 1), branchBend ? LOG_Y : LOG_Z, 2);
				}
				else {
					world.setBlockState(pos.west(i - 1).down(y).south(i - 1), WOOD, 2);
					world.setBlockState(pos.west(i - 1).down(y - 1).south(i - 1), WOOD, 2);
				}
				break;

			case 8:
                if (!root)
                {
					world.setBlockState(pos.east(i - 1).up(y).north(i - 1), branchBend ? LOG_Y : LOG_Z, 2);
				}
                else
                {
					world.setBlockState(pos.east(i - 1).down(y).north(i - 1), WOOD, 2);
					world.setBlockState(pos.east(i - 1).down(y - 1).north(i - 1), WOOD, 2);
				}
				break;
			}
		}
	}
}