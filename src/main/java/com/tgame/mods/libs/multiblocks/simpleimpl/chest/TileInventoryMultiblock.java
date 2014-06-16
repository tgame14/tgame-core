package com.tgame.mods.libs.multiblocks.simpleimpl.chest;

import com.tgame.mods.libs.inventory.IInventoryHandler;
import com.tgame.mods.libs.multiblocks.MultiblockValidationException;
import com.tgame.mods.libs.multiblocks.grid.GridController;
import com.tgame.mods.libs.multiblocks.simpleimpl.TileSimpleNode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * @author tgame14
 * @since 16/06/14
 */
public class TileInventoryMultiblock extends TileSimpleNode implements IInventory, IInventoryHandler
{

	@Override
	public GridController createNewMultiblock()
	{
		return new ChestGridController(this.getWorldObj());
	}

	@Override
	public Class<? extends GridController> getMultiblockControllerType()
	{
		return ChestGridController.class;
	}

	public IInventoryHandler getInventoryHandler()
	{
		return (IInventoryHandler) this.getMultiblockController();
	}

	@Override
	public boolean canConnectInventory(ForgeDirection from)
	{
		return this.getInventoryHandler().canConnectInventory(from);
	}

	@Override
	public ItemStack insertItem(ForgeDirection from, ItemStack item, boolean simulate)
	{
		return this.getInventoryHandler().insertItem(from, item, simulate);
	}

	@Override
	public ItemStack extractItem(ForgeDirection from, ItemStack item, boolean simulate)
	{
		return this.getInventoryHandler().extractItem(from, item, simulate);
	}

	@Override
	public ItemStack extractItem(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return this.getInventoryHandler().extractItem(from, maxExtract, simulate);
	}

	@Override
	public List<ItemStack> getInventoryContents(ForgeDirection from)
	{
		return this.getInventoryHandler().getInventoryContents(from);
	}

	@Override
	public int getSizeInventory(ForgeDirection from)
	{
		return this.getInventoryHandler().getSizeInventory(from);
	}

	@Override
	public boolean isEmpty(ForgeDirection from)
	{
		return this.getInventoryHandler().isEmpty(from);
	}

	@Override
	public boolean isFull(ForgeDirection from)
	{
		return this.getInventoryHandler().isFull(from);
	}


	@Override
	public void isGoodForFrame() throws MultiblockValidationException
	{

	}

	@Override
	public void isGoodForSides() throws MultiblockValidationException
	{

	}

	@Override
	public void isGoodForTop() throws MultiblockValidationException
	{

	}

	@Override
	public void isGoodForBottom() throws MultiblockValidationException
	{

	}

	@Override
	public void isGoodForInterior() throws MultiblockValidationException
	{

	}

	@Override
	public void onMachineActivated()
	{

	}

	@Override
	public void onMachineDeactivated()
	{

	}

	@Override
	public int getSizeInventory()
	{
		return this.getInventoryHandler().getSizeInventory(ForgeDirection.UNKNOWN);
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		// a bandage on a bigger problem.. placeholder until 1.8
		List<ItemStack> list = this.getInventoryHandler().getInventoryContents(ForgeDirection.UNKNOWN);
		return list.get(index);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		List<ItemStack> list = this.getInventoryHandler().getInventoryContents(ForgeDirection.UNKNOWN);
		ItemStack stack = list.get(var1).copy();
		stack.stackSize = var2;
		return this.getInventoryHandler().extractItem(ForgeDirection.UNKNOWN, stack, false);

	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return getStackInSlot(var1);
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		List<ItemStack> list = this.getInventoryHandler().getInventoryContents(ForgeDirection.UNKNOWN);
		this.getInventoryHandler().extractItem(ForgeDirection.UNKNOWN, list.get(var1), false);
		this.getInventoryHandler().insertItem(ForgeDirection.UNKNOWN, var2, false);


	}

	@Override
	public String getInventoryName()
	{
		return this.getClass().getSimpleName();
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1)
	{
		return true;
	}

	@Override
	public void openInventory()
	{

	}

	@Override
	public void closeInventory()
	{

	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2)
	{
		return this.getInventoryHandler().isFull(ForgeDirection.UNKNOWN);
	}
}
