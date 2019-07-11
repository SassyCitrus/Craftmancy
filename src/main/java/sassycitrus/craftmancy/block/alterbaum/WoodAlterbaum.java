package sassycitrus.craftmancy.block.alterbaum;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import sassycitrus.craftmancy.api.IBurnableFuel;
import sassycitrus.craftmancy.block.BlockBase;

public class WoodAlterbaum extends BlockBase implements IBurnableFuel
{
    public WoodAlterbaum()
    {
        super(Material.ROCK, "alterbaum_wood");
        setHarvestLevel("axe", 2);
        setHardness(3.0F);
        setResistance(5.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public int getBurnTime()
    {
        return 300;
    }
}