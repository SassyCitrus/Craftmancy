package sassycitrus.craftmancy.item.tool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import sassycitrus.craftmancy.entity.EntityFireball;

public class WandEmbers extends Wand
{
    public WandEmbers()
    {
        super("embers");
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        if (!world.isRemote)
        {        
            EntityFireball fireball = new EntityFireball(world, player);
            world.spawnEntity(fireball);
        }

        player.swingArm(hand);
        player.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1, 1);
        return new ActionResult<ItemStack>(EnumActionResult.PASS, player.getHeldItem(hand));
    }
}