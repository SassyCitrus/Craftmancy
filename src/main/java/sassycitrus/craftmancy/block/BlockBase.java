package sassycitrus.craftmancy.block;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.util.StringUtil;

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

    public void registerItemModel(Item itemBlock)
    {
        Craftmancy.proxy.registerItemRenderer(itemBlock, 0, name);
    }

    public Item createItemBlock()
    {
        return new ItemBlock(this).setRegistryName(getRegistryName());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (StringUtil.isShiftKeyDown())
        {
            tooltip.add(StringUtil.getInfoText("info.entropycraft." + this.name));
        }
        else
        {
            tooltip.add(StringUtil.shiftForDetails());
        }   
    }
}