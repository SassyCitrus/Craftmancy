package sassycitrus.craftmancy.init;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.api.IBurnableFuel;
import sassycitrus.craftmancy.block.BlockArcaneTable;
import sassycitrus.craftmancy.block.BlockBase;
import sassycitrus.craftmancy.block.alterbaum.AlterbaumLeaves;
import sassycitrus.craftmancy.block.alterbaum.AlterbaumLog;
import sassycitrus.craftmancy.block.alterbaum.AlterbaumPlanks;
import sassycitrus.craftmancy.block.alterbaum.AlterbaumSapling;
import sassycitrus.craftmancy.block.alterbaum.AlterbaumWood;
import sassycitrus.craftmancy.block.manafurnace.BlockManaFurnace;
import sassycitrus.craftmancy.block.manafurnace.TileManaFurnace;
import sassycitrus.craftmancy.block.ore.OreEdelila;
import sassycitrus.craftmancy.block.ore.OreFeuermin;
import sassycitrus.craftmancy.block.ritual.BlockRitual;
import sassycitrus.craftmancy.block.ritual.BlockRitualAltar;
import sassycitrus.craftmancy.block.ritual.TileRitualBlock;

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
    public static Block ALTERBAUM_LOG = registerBlock(new AlterbaumLog());
    public static Block ALTERBAUM_WOOD = registerBlock(new AlterbaumWood());
    public static Block ALTERBAUM_PLANKS = registerBlock(new AlterbaumPlanks());
    public static Block ALTERBAUM_SAPLING = registerBlock(new AlterbaumSapling());
    public static Block ALTERBAUM_LEAVES = registerBlock(new AlterbaumLeaves());
    public static Block ARCANE_TABLE = registerBlock(new BlockArcaneTable());
    public static Block MANA_FURNACE = registerBlock(new BlockManaFurnace());
    public static Block RITUAL_ALTAR = registerBlock(new BlockRitualAltar());
    public static Block RITUAL_PEDESTAL = registerBlock(new BlockRitual("ritual_pedestal"));

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

        registerTileEntities();
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
        Item item;

        if (block instanceof IBurnableFuel)
        {
            item = new ItemBlock(block)
            {
                @Override
                public int getItemBurnTime(ItemStack stack)
                {
                    return ((IBurnableFuel) block).getBurnTime();
                }
            };
        }
        else
        {
            item = new ItemBlock(block);
        }
        
        return item.setRegistryName(block.getRegistryName());
    }

    public static void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileManaFurnace.class, new ResourceLocation("craftmancy:mana_furnace"));
        GameRegistry.registerTileEntity(TileRitualBlock.class, new ResourceLocation("craftmancy:ritual_block"));
    }
}