package sassycitrus.craftmancy.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockStorageMetal extends BlockBase
{
    public BlockStorageMetal(String name)
    {
        super(Material.IRON, name + "_block");
        setHardness(3.0F);
        setResistance(5.0F);
        setHarvestLevel("pickaxe", 2);
        setSoundType(SoundType.METAL);
    }
}