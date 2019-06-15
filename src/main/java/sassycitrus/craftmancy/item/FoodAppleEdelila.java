package sassycitrus.craftmancy.item;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import sassycitrus.craftmancy.Craftmancy;
import sassycitrus.craftmancy.util.StringUtil;

public class FoodAppleEdelila extends ItemFood
{
    private static final String NAME = "apple_edelila";

    public FoodAppleEdelila()
    {
        super(4, 1.2F, false);
        setUnlocalizedName(NAME);
        setRegistryName(NAME);
        setCreativeTab(Craftmancy.creativeTab);
        setAlwaysEdible();
    }

    public void registerItemModel()
    {
        Craftmancy.proxy.registerItemRenderer(this, 0, NAME);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        if (!world.isRemote)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 600, 1));
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