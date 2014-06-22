package com.tgame.mods.libs.guis.slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public class SlotFalseCopy extends Slot
{
	protected int slotIndex;

	public SlotFalseCopy(IInventory inv, int index, int x, int y)
	{
		super(inv, index, x, y);
		this.slotIndex = index;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return true;
	}

	@Override
	public void putStack(ItemStack stack)
	{

		if (stack != null)
		{
			stack.stackSize = 1;
		}
		this.inventory.setInventorySlotContents(this.slotIndex, stack);
		this.onSlotChanged();
	}
}
