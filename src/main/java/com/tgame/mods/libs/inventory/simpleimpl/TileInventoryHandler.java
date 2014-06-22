package com.tgame.mods.libs.inventory.simpleimpl;

import com.tgame.mods.libs.inventory.IInventoryHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * @author tgame14
 * @since 16/06/14
 */
public class TileInventoryHandler extends TileEntity implements IInventoryHandler
{

	protected InventoryStorage inv;

	public TileInventoryHandler()
	{
		this.inv = new InventoryStorage(1);
	}

	public TileInventoryHandler(int size)
	{
		this.inv = new InventoryStorage(size);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.inv.readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.inv.readFromNBT(nbt);
	}

	@Override
	public boolean canConnectInventory(ForgeDirection from)
	{
		return true;
	}

	@Override
	public ItemStack insertItem(ForgeDirection from, ItemStack item, boolean simulate)
	{
		return this.inv.insertItem(item, simulate);
	}

	@Override
	public ItemStack extractItem(ForgeDirection from, ItemStack item, boolean simulate)
	{
		return this.inv.extractItem(item, simulate);
	}

	@Override
	public ItemStack extractItem(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return this.inv.extractItem(maxExtract, simulate);
	}

	@Override
	public List<ItemStack> getInventoryContents(ForgeDirection from)
	{
		return this.inv.getInventoryContents();
	}

	@Override
	public int getSizeInventory(ForgeDirection from)
	{
		return this.inv.getSizeInventory();
	}

	@Override
	public boolean isEmpty(ForgeDirection from)
	{
		return this.inv.isEmpty();
	}

	@Override
	public boolean isFull(ForgeDirection from)
	{
		return this.inv.isFull();
	}
}
