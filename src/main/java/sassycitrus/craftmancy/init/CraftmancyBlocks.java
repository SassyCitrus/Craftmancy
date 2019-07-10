package sassycitrus.craftmancy.init;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.block.BlockArcaneTable;
import sassycitrus.craftmancy.block.BlockBase;
import sassycitrus.craftmancy.block.alterbaum.LogAlterbaum;
import sassycitrus.craftmancy.block.ore.OreEdelila;
import sassycitrus.craftmancy.block.ore.OreFeuermin;

public class CraftmancyBlocks
{
    public static HashMap<String, Block> BLOCKS = new HashMap<String, Block>();

    public static Block EDELILA_BLOCK = registerBlock(new BlockBase(Material.IRON, "edelila_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2).setSoundType(SoundType.METAL));
    public static Block MITHRIL_BLOCK = registerBlock(new BlockBase(Material.IRON, "mithril_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2).setSoundType(SoundType.METAL));
    public static Block FEUERMIN_BLOCK = registerBlock(new BlockBase(Material.IRON, "feuermin_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2).setSoundType(SoundType.METAL));
    public static Block FEUERSTEEL = registerBlock(new BlockBase(Material.IRON, "feuersteel_block").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2).setSoundType(SoundType.METAL));
    public static Block ORE_EDELILA = registerBlock(new OreEdelila());
    public static Block ORE_MITHRIL = registerBlock(new BlockBase(Material.ROCK, "ore_mithril").setHardness(3.0F).setResistance(5.0F).setHarvestLevelBase("pickaxe", 2).setSoundType(SoundType.STONE));
    public static Block ORE_FEUERMIN = registerBlock(new OreFeuermin());
    public static Block ALTERBAUM_LOG = registerBlock(new LogAlterbaum());
    public static Block ALTERBAUM_WOOD = registerBlock(new BlockBase(Material.WOOD, "alterbaum_wood").setHardness(2.0F).setHarvestLevelBase("axe", 2).setSoundType(SoundType.WOOD));
    public static Block ALTERBAUM_PLANKS = registerBlock(new BlockBase(Material.WOOD, "alterbaum_planks").setHardness(2.0F).setHarvestLevelBase("axe", 2).setSoundType(SoundType.WOOD));
    public static Block ARCANE_TABLE = registerBlock(new BlockArcaneTable());

    private static Block registerBlock(Block block)
    {
        BLOCKS.put(block.getRegistryName().getResourcePath(), block);
        return block;
    }

    public static void register(IForgeRegistry<Block> registry)
    {
        registry.registerAll(
            BLOCKS.values().toArray(new Block[BLOCKS.size()])
        );
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry)
    {
        ArrayList<Item> itemBlocks = new ArrayList<Item>();

        for (Block block : BLOCKS.values())
        {
            itemBlocks.add(createItemBlock(block));
        }

        registry.registerAll(
            itemBlocks.toArray(new Item[BLOCKS.size()])
        );
    }

    public static void registerModels()
    {
        for (String name : BLOCKS.keySet())
        {
            Craftmancy.proxy.registerItemRenderer(Item.getItemFromBlock(BLOCKS.get(name)), 0, name);
        }
    }

    private static Item createItemBlock(Block block)
    {
        return new ItemBlock(block).setRegistryName(block.getRegistryName());
    }
}