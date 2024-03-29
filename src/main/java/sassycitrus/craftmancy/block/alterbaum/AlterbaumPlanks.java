package sassycitrus.craftmancy.block.alterbaum;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import sassycitrus.craftmancy.api.IBurnableFuel;
import sassycitrus.craftmancy.block.BlockBase;

public class AlterbaumPlanks extends BlockBase implements IBurnableFuel
{
    public AlterbaumPlanks()
    {
        super(Material.WOOD, "alterbaum_planks");
        setHardness(2.0F);
        setSoundType(SoundType.WOOD);
    }

    @Override
    public int getBurnTime()
    {
        return 300;
    }
}