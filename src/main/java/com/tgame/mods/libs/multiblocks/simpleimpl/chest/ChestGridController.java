package com.tgame.mods.libs.multiblocks.simpleimpl.chest;

import com.tgame.mods.libs.inventory.IInventoryHandler;
import com.tgame.mods.libs.inventory.IInventoryStorage;
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

	protected IInventoryStorage input;
	protected IInventoryStorage output;

	public ChestGridController(World world)
	{
		super(world);

		this.input = null;
		this.output = null;
	}

	@Override
	protected void onMachineAssembled()
	{
		this.input = new InventoryStorage(24);
		this.output = new InventoryStorage(24);
	}

	@Override
	protected void onMachineDisassembled()
	{
		super.onMachineDisassembled();
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
		return null;
	}

	@Override
	public ItemStack extractItem(ForgeDirection from, ItemStack item, boolean simulate)
	{
		return null;
	}

	@Override
	public ItemStack extractItem(ForgeDirection from, int maxExtract, boolean simulate)
	{
		return null;
	}

	@Override
	public List<ItemStack> getInventoryContents(ForgeDirection from)
	{
		return null;
	}

	@Override
	public int getSizeInventory(ForgeDirection from)
	{
		return 0;
	}

	@Override
	public boolean isEmpty(ForgeDirection from)
	{
		return false;
	}

	@Override
	public boolean isFull(ForgeDirection from)
	{
		return false;
	}
}
