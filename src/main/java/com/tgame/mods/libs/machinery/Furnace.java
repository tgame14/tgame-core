package com.tgame.mods.libs.machinery;

import com.tgame.mods.libs.inventory.IInventoryStorage;
import com.tgame.mods.libs.inventory.simpleimpl.InventoryStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author tgame14
 * @since 18/06/2014
 */
public class Furnace implements IFurnace
{
	protected int burnTicksPerItem;
	protected int currentItemBurnTicks;

	protected InventoryStorage input;
	protected InventoryStorage output;

	private boolean hasFuel;
	private boolean isActive;
	protected Pair<ItemStack, ItemStack> currentJob;

	public Furnace(int burnTimePerItem, int inputSlots, int outputSlots)
	{
		this.burnTicksPerItem = burnTimePerItem;
		this.currentItemBurnTicks = this.burnTicksPerItem;

		this.input = new InventoryStorage(inputSlots);
		this.output = new InventoryStorage(outputSlots);

		this.hasFuel = false;
		this.currentJob = null;
	}

	public boolean updateFurnace()
	{
		if (canSmelt())
		{
			if (this.currentJob == null)
			{
				ItemStack candidate = this.getInputInv().extractItem(1, true);
				ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(candidate);

				if (result != null)
				{
					this.currentJob = new ImmutablePair<ItemStack, ItemStack>(candidate, result);
					this.currentItemBurnTicks = burnTicksPerItem;
					return true;
				}
			}
			else
			{
				if (isActive())
				{
					if (this.currentItemBurnTicks <= 0)
					{
						this.smelt(this.currentJob.getLeft());
						return true;
					}
				}
			}
		}
		return false;
	}

	public void smelt(ItemStack smelted)
	{
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(smelted);

		this.output.insertItem(result, false);
		this.input.extractItem(smelted, false);

	}

	public boolean canSmelt()
	{
		return this.hasFuel() && !this.getInputInv().isEmpty() && !this.getOutputInv().isFull();
	}

	@Override
	public IInventoryStorage getInputInv()
	{
		return this.input;
	}

	@Override
	public IInventoryStorage getOutputInv()
	{
		return this.output;
	}

	public boolean hasFuel()
	{
		return hasFuel;
	}

	public void setHasFuel(boolean hasFuel)
	{
		this.hasFuel = hasFuel;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		this.burnTicksPerItem = nbt.getInteger("burnTime");
		this.currentItemBurnTicks = nbt.getInteger("currentItemBurn");

		this.input.readFromNBT(nbt.getCompoundTag("inputInv"));
		this.output.readFromNBT(nbt.getCompoundTag("outputInv"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("burnTime", this.burnTicksPerItem);
		nbt.setInteger("currentItemBurn", this.currentItemBurnTicks);

		NBTTagCompound inputTag = new NBTTagCompound();
		this.input.writeToNBT(inputTag);

		NBTTagCompound outputTag = new NBTTagCompound();
		this.output.writeToNBT(outputTag);

		nbt.setTag("inputInv", inputTag);
		nbt.setTag("outputInv", outputTag);

	}

	public int getBurnTicksPerItem()
	{
		return burnTicksPerItem;
	}

	public void setBurnTicksPerItem(int burnTicksPerItem)
	{
		this.burnTicksPerItem = burnTicksPerItem;
	}

	public int getCurrentItemBurnTicks()
	{
		return currentItemBurnTicks;
	}

	public void setCurrentItemBurnTicks(int currentItemBurnTicks)
	{
		this.currentItemBurnTicks = currentItemBurnTicks;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean isActive)
	{
		this.isActive = isActive;
	}
}
