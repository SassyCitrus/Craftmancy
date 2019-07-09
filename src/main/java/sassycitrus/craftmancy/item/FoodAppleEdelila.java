package sassycitrus.craftmancy.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler;
import sassycitrus.craftmancy.capability.ManaCapabilityHandler.IManaHandler;
import sassycitrus.craftmancy.network.Network;
import sassycitrus.craftmancy.util.StringUtil;

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
            IManaHandler playerMana = ManaCapabilityHandler.getHandler(player);
            playerMana.addMana(MANA_GIVEN);
            Network.syncPlayerMana(player);
        }
    }
    
    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.RARE;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        if (StringUtil.isShiftKeyDown())
        {
            tooltip.add(StringUtil.getInfoText("info.entropycraft." + NAME));
        }
        else
        {
            tooltip.add(StringUtil.shiftForDetails());
        }
    }
}