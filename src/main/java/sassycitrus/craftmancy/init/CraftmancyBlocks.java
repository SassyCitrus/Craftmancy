package sassycitrus.craftmancy.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.block.BlockBase;
import sassycitrus.craftmancy.block.ore.OreEdelila;

public class CraftmancyBlocks
{
    public static BlockBase blockEdelila = new BlockBase(Material.IRON, "edelila_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2);
    public static BlockBase oreEdelila = new OreEdelila();

    public static void register(IForgeRegistry<Block> registry)
    {
        registry.registerAll(
            blockEdelila,
            oreEdelila
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            blockEdelila.createItemBlock(),
            oreEdelila.createItemBlock()
        );
    }

    public static void registerModels()
    {
        blockEdelila.registerItemModel(Item.getItemFromBlock(blockEdelila));
        oreEdelila.registerItemModel(Item.getItemFromBlock(oreEdelila));
    }
}