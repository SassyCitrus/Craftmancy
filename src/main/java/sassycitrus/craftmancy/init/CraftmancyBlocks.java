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
    public static BlockBase ORE_EDELILA = new OreEdelila();

    public static void register(IForgeRegistry<Block> registry)
    {
        registry.registerAll(
            EDELILA_BLOCK,
            ORE_EDELILA
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {
        registry.registerAll(
            createItemBlock(EDELILA_BLOCK),
            createItemBlock(ORE_EDELILA)
        );
    }

    public static void registerModels()
    {
        EDELILA_BLOCK.registerItemModel(Item.getItemFromBlock(EDELILA_BLOCK));
        ORE_EDELILA.registerItemModel(Item.getItemFromBlock(ORE_EDELILA));
    }

    private static Item createItemBlock(Block block)
    {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }
}