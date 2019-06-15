package sassycitrus.craftmancy.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.block.BlockBase;

public class CraftmancyBlocks
{
    public static BlockBase blockEdelila = new BlockBase(Material.IRON, "edelila_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2);

    public static void register(IForgeRegistry<Block> registry)
    {
        registry.registerAll(
            blockEdelila
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            blockEdelila.createItemBlock()
        );
    }

    public static void registerModels()
    {
        blockEdelila.registerItemModel(Item.getItemFromBlock(blockEdelila));
    }
}