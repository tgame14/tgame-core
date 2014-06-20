package com.tgame.mods.libs.inventory.simpleimpl;

import com.tgame.mods.libs.inventory.IInventoryStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a default implementation of {@link com.tgame.mods.libs.inventory.IInventoryStorage}
 * it is encouraged to use this implementation or extend it rather than directly implementing it.
 *
 * @author tgame14
 * @since 09/05/14
 */
public class InventoryStorage implements IInventoryStorage
{
	/**
	 * this list acts as the holding object of the itemstacks
	 * the use of an arraylist is done to allow best interaction with {@code maxSlots} limit
	 */
	protected ArrayList<ItemStack> items;
	protected int maxSlots;

	public InventoryStorage(int maxSlots)
	{
		this.items = new ArrayList<ItemStack>(maxSlots);
		this.maxSlots = maxSlots;
	}

	@Override
	public ItemStack insertItem(ItemStack item, boolean simulate)
	{
		ItemStack candidate = item.copy();
		if (!isFull())
		{
			for (int i = 0; i < getSizeInventory(); i++)
			{
				ItemStack stack = items.get(i);
				if (stack != null)
				{
					if (isItemStackIdentical(stack, candidate))
					{
						int prevSize = stack.stackSize;
						int candidateAccess = Math.min(stack.stackSize + candidate.stackSize, stack.getMaxStackSize());
						if (!simulate)
						{
							stack.stackSize = candidateAccess;
						}
						candidate.stackSize -= candidateAccess - prevSize;
					}
				}
				else
				{
					items.set(i, candidate);
					return candidate;
				}
			}
		}
		return candidate;
	}

	@Override
	public ItemStack extractItem(ItemStack item, boolean simulate)
	{
		ItemStack candidate = item.copy();
		candidate.stackSize = 0;
		if (!isEmpty())
		{
			for (int i = 0; i < items.size(); i++)
			{
				ItemStack stack = items.get(i);
				if (isItemStackIdentical(stack, candidate))
				{
					int prevSize = stack.stackSize;
					int candidateAccess = Math.max(stack.stackSize - candidate.stackSize, 0);
					if (!simulate)
					{
						stack.stackSize = candidateAccess;
					}
					candidate.stackSize += candidateAccess - prevSize;
				}
			}
		}
		return candidate;
	}

	@Override
	public ItemStack extractItem(int maxExtract, boolean simulate)
	{
		for (ItemStack stack : items)
		{
			if (stack != null)
			{
				ItemStack candidate = stack.copy();
				candidate.stackSize = maxExtract;
				return extractItem(candidate, simulate);
			}
		}
		return null;
	}

	@Override
	public List<ItemStack> getInventoryContents()
	{
		return this.items;
	}

	@Override
	public int getSizeInventory()
	{
		return this.maxSlots;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : this.items)
		{
			if (stack != null)
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean isFull()
	{
		for (ItemStack stack : this.items)
		{
			if (stack == null)
			{
				return false;
			}

			if (stack.stackSize < stack.getMaxStackSize())
			{
				return false;
			}

		}
		return false;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		onInventoryChanged();
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if (items.get(i) != null)
			{
				NBTTagCompound data = new NBTTagCompound();
				items.get(i).writeToNBT(data);
				tagList.appendTag(data);
			}
		}
		nbt.setTag("inventory", tagList);
		nbt.setInteger("maxSlots", this.maxSlots);
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		this.maxSlots = nbt.getInteger("maxSlots");
		this.items = new ArrayList<ItemStack>(this.maxSlots);

		NBTTagList tagList = (NBTTagList) nbt.getTag("inventory");

		for (int i = 0; i < tagList.tagCount(); i++)
		{
			NBTTagCompound tag = tagList.getCompoundTagAt(i);
			items.set(i, ItemStack.loadItemStackFromNBT(tag));
		}
		onInventoryChanged();

	}

	/**
	 * this method is used to re-organize the inventory and re-sort it
	 * this gets rid of all items that are either null or have a stacksize of 0
	 * Call this only when inventory has changed, not when simulating!
	 */
	public void onInventoryChanged()
	{
		items.ensureCapacity(getSizeInventory());
		for (ItemStack stack : this.items)
		{
			if (stack != null && stack.stackSize <= 0)
			{
				this.items.set(this.items.indexOf(stack), null);
			}

		}
	}

	private static boolean isItemStackIdentical(ItemStack stack1, ItemStack stack2)
	{
		return stack1.isItemEqual(stack2) ? (stack1.hasTagCompound() && stack2.hasTagCompound() ? stack1.getTagCompound().equals(stack2.getTagCompound()) : true) : false;
	}
}
