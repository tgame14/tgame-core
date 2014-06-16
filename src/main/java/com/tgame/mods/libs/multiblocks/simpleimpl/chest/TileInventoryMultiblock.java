package com.tgame.mods.libs.multiblocks.simpleimpl.chest;

import com.tgame.mods.libs.inventory.IInventoryHandler;
import com.tgame.mods.libs.multiblocks.MultiblockValidationException;
import com.tgame.mods.libs.multiblocks.grid.GridController;
import com.tgame.mods.libs.multiblocks.simpleimpl.TileSimpleNode;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * @author tgame14
 * @since 16/06/14
 */
public class TileInventoryMultiblock extends TileSimpleNode implements IInventoryHandler
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

}
