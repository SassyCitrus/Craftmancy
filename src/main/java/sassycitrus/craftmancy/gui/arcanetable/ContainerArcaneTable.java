package sassycitrus.craftmancy.gui.arcanetable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.world.World;

public class ContainerArcaneTable extends Container
{
    public InventoryCraftingArcaneTable craftMatrix = new InventoryCraftingArcaneTable(this);
    public InventoryCraftResult craftResult = new InventoryCraftResult();
    private final World world;
    private final EntityPlayer player;

    public ContainerArcaneTable(InventoryPlayer playerInventory, World world)
    {
        this.world = world;
        this.player = playerInventory.player;

        this.addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
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
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);

        if (!this.world.isRemote)
        {
            this.clearContainer(player, this.world, this.craftMatrix);
        }
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory)
    {
        this.slotChangedCraftingGrid(this.world, this.player, this.craftMatrix, this.craftResult);
    }

    @Override
    protected void slotChangedCraftingGrid(World world, EntityPlayer player, InventoryCrafting inventoryCrafting, InventoryCraftResult craftResult)
    {
        if (!world.isRemote)
        {
            EntityPlayerMP playerMP = (EntityPlayerMP) player;
            ItemStack stack = ItemStack.EMPTY;
            IRecipe irecipe = CraftingManager.findMatchingRecipe(inventoryCrafting, world);

            if (irecipe != null && (irecipe.isDynamic() || !world.getGameRules().getBoolean("doLimitedCrafting") || playerMP.getRecipeBook().isUnlocked(irecipe)))
            {
                craftResult.setRecipeUsed(irecipe);
                stack = irecipe.getCraftingResult(inventoryCrafting);
            }

            craftResult.setInventorySlotContents(0, stack);
            playerMP.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, stack));
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