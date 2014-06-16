package com.tgame.mods.libs.multiblocks.simpleimpl.chest;

import com.tgame.mods.libs.inventory.IInventoryHandler;
import com.tgame.mods.libs.inventory.simpleimpl.InventoryStorage;
import com.tgame.mods.libs.multiblocks.simpleimpl.SimpleGridController;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * @author tgame14
 * @since 16/06/14
 */
public class ChestGridController extends SimpleGridController implements IInventoryHandler
{

	protected InventoryStorage inv;

	public ChestGridController(World world)
	{
		super(world);

		this.inv = new InventoryStorage(1);
	}

	@Override
	protected boolean updateServer()
	{
		return false;
	}

	@Override
	public boolean canConnectInventory(ForgeDirection from)
	{
		return isAssembled();
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
