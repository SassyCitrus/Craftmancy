package sassycitrus.craftmancy.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import sassycitrus.craftmancy.Craftmancy;

public class BlockBase extends Block
{
    protected String name;

    public BlockBase(Material material, String name)
    {
        super(material);

        this.name = name;

        setUnlocalizedName(name);
        setRegistryName(name);
        setCreativeTab(Craftmancy.creativeTab);
    }

    public BlockBase setHardness(float hardness)
    {
        this.blockHardness = hardness;
        return this;
    }

    public BlockBase setResistance(float resistance)
    {
        this.blockResistance = resistance;
        return this;
    }

    public BlockBase setHarvestLevelBase(String tool, int level)
    {
        this.setHarvestLevel(tool, level);
        return this;
    }
}