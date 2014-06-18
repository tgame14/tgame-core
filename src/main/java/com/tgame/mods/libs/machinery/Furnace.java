package com.tgame.mods.libs.machinery;

import com.tgame.mods.libs.inventory.IInventoryStorage;
import com.tgame.mods.libs.inventory.simpleimpl.InventoryStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

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

	public Furnace(int burnTimePerItem, int currentItemBurnTime, int inputSlots, int outputSlots)
	{
		this.burnTicksPerItem = burnTimePerItem;
		this.currentItemBurnTicks = currentItemBurnTime;

		this.input = new InventoryStorage(inputSlots);
		this.output = new InventoryStorage(outputSlots);
	}

	public void updateFurnace()
	{

	}

	public void smelt(ItemStack smelted)
	{
		ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(smelted);

		this.output.insertItem(result, false);
		this.input.extractItem(smelted, false);

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
}
