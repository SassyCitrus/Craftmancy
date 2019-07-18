package sassycitrus.craftmancy.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.util.ManaUtil;

public class FoodAppleEdelila extends ItemFood
{
    private static final String NAME = "apple_edelila";
    private static final int MANA_GIVEN = 5;

    public FoodAppleEdelila()
    {
        super(4, 1.2F, false);
        setUnlocalizedName(NAME);
        setRegistryName(NAME);
        setCreativeTab(Craftmancy.creativeTab);
        setAlwaysEdible();
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            ManaUtil.addMana(player, MANA_GIVEN);
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }
}