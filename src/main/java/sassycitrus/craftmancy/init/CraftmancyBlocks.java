package sassycitrus.craftmancy.init;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.block.BlockBase;
import sassycitrus.craftmancy.block.ore.OreEdelila;

public class CraftmancyBlocks
{
    public static BlockBase EDELILA_BLOCK = new BlockBase(Material.IRON, "edelila_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2);
    public static BlockBase MITHRIL_BLOCK = new BlockBase(Material.IRON, "mithril_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2);
    public static BlockBase ORE_EDELILA = new OreEdelila();
    public static BlockBase ORE_MITHRIL = new BlockBase(Material.ROCK, "ore_mithril").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2);

    public static void register(IForgeRegistry<Block> registry)
    {
        registry.registerAll(
            EDELILA_BLOCK,
            MITHRIL_BLOCK,
            ORE_EDELILA,
            ORE_MITHRIL
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            createItemBlock(EDELILA_BLOCK),
            createItemBlock(MITHRIL_BLOCK),
            createItemBlock(ORE_EDELILA),
            createItemBlock(ORE_MITHRIL)
        );
    }

    public static void registerModels()
    {
        EDELILA_BLOCK.registerItemModel(Item.getItemFromBlock(EDELILA_BLOCK));
        MITHRIL_BLOCK.registerItemModel(Item.getItemFromBlock(MITHRIL_BLOCK));
        ORE_EDELILA.registerItemModel(Item.getItemFromBlock(ORE_EDELILA));
        ORE_MITHRIL.registerItemModel(Item.getItemFromBlock(ORE_MITHRIL));
    }

    private static Item createItemBlock(Block block)
    {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }
}