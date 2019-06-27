package sassycitrus.craftmancy.gui.arcanetable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import sassycitrus.craftmancy.block.arcanetable.TEArcaneTable;

public class ContainerArcaneTable extends Container
{
    public ContainerArcaneTable(InventoryPlayer playerInventory, final TEArcaneTable table)
    {
        IItemHandler inventory = table.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new SlotItemHandler(inventory, j + i * 3, 62 + j * 18, 17 + i * 18)
                {
                    @Override
                    public void onSlotChanged()
                    {
                        table.markDirty();
                    }
                });
            }
        }

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
    public boolean canInteractWith(EntityPlayer playerIn)
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