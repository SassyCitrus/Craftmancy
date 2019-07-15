package sassycitrus.craftmancy.gui.manafurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import sassycitrus.craftmancy.block.manafurnace.TileManaFurnace;

public class ContainerManaFurnace extends Container
{
    public ContainerManaFurnace(InventoryPlayer playerInventory, final TileManaFurnace manaFurnace)
    {
        IItemHandler inventory = manaFurnace.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);

        this.addSlotToContainer(new SlotItemHandler(inventory, 0, 83, 27)
        {
            @Override
            public void onSlotChanged()
            {
                manaFurnace.markDirty();
            }
        });

        for (int k = 0; k < 3; k++)
        {
            for (int l = 0; l < 9; l++)
            {
                addSlotToContainer(new Slot(playerInventory, l + k * 9 + 9, 8 + l * 18, 84 + k * 18));
            }
        }

        for (int m = 0; m < 9; m++)
        {
            addSlotToContainer(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack slotStack = slot.getStack();
            stack = slotStack.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots)
            {
                if (!this.mergeItemStack(slotStack, containerSlots, inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(slotStack, 0, containerSlots, false))
            {
                return ItemStack.EMPTY;
            }

            if (slotStack.getCount() == 0)
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (slotStack.getCount() == stack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }

        return stack;
    }
}