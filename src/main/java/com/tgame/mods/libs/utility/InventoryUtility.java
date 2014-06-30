package com.tgame.mods.libs.utility;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tgame14
 * @since 29/06/2014
 */
public class InventoryUtility
{
	public static IInventory checkChestInv(IInventory inv)
	{
		if (inv instanceof TileEntityChest)
		{
			TileEntityChest main = (TileEntityChest) inv;
			TileEntityChest adj = null;

			if (main.adjacentChestXNeg != null)
			{
				adj = main.adjacentChestXNeg;
			}
			else if (main.adjacentChestXPos != null)
			{
				adj = main.adjacentChestXPos;
			}
			else if (main.adjacentChestZNeg != null)
			{
				adj = main.adjacentChestZNeg;
			}
			else if (main.adjacentChestZPos != null)
			{
				adj = main.adjacentChestZPos;
			}

			if (adj != null)
			{
				return new InventoryLargeChest("", main, adj);
			}
		}

		return inv;
	}

	public static ItemStack putStackInInventory(IInventory inventory, ItemStack toInsert, boolean force)
	{
		inventory = checkChestInv(inventory);

		for (int slot = 0; slot < inventory.getSizeInventory(); slot++)
		{
			if (!force)
			{
				if (!inventory.isItemValidForSlot(slot, toInsert))
				{
					continue;
				}
			}

			ItemStack slot_stack = inventory.getStackInSlot(slot);

			if (slot_stack == null)
			{
				inventory.setInventorySlotContents(slot, toInsert);
				return null;
			}
			else if (slot_stack.isItemEqual(toInsert) && slot_stack.stackSize < slot_stack.getMaxStackSize())
			{
				if (slot_stack.stackSize + toInsert.stackSize <= slot_stack.getMaxStackSize())
				{
					ItemStack toSet = toInsert.copy();
					toSet.stackSize += slot_stack.stackSize;

					inventory.setInventorySlotContents(slot, toSet);
					return null;
				}
				else
				{
					int rejects = (slot_stack.stackSize + toInsert.stackSize) - slot_stack.getMaxStackSize();

					ItemStack toSet = toInsert.copy();
					toSet.stackSize = slot_stack.getMaxStackSize();

					ItemStack remains = toInsert.copy();
					remains.stackSize = rejects;

					inventory.setInventorySlotContents(slot, toSet);

					toInsert = remains;
				}
			}
		}
		return toInsert;
	}

	public static ItemStack putStackInInventory(IInventory inventory, ItemStack itemStack, int side, boolean force)
	{
		ItemStack toInsert = itemStack != null ? itemStack.copy() : null;
		if (toInsert != null)
		{
			if (!(inventory instanceof ISidedInventory))
			{
				return putStackInInventory(inventory, toInsert, force);
			}
			else
			{
				ISidedInventory sidedInventory = (ISidedInventory) inventory;
				int[] slots = sidedInventory.getAccessibleSlotsFromSide(ForgeDirection.getOrientation(side).getOpposite().ordinal());

				if (slots != null && slots.length != 0)
				{
					for (int get = 0; get <= slots.length - 1; get++)
					{
						int slotID = slots[get];

						if (!force)
						{
							if (!sidedInventory.isItemValidForSlot(slotID, toInsert) && !sidedInventory.canInsertItem(slotID, toInsert, ForgeDirection.getOrientation(side).getOpposite().ordinal()))
							{
								continue;
							}
						}

						ItemStack inSlot = inventory.getStackInSlot(slotID);

						if (inSlot == null)
						{
							inventory.setInventorySlotContents(slotID, toInsert);
							return null;
						}
						else if (inSlot.isItemEqual(toInsert) && inSlot.stackSize < inSlot.getMaxStackSize())
						{
							if (inSlot.stackSize + toInsert.stackSize <= inSlot.getMaxStackSize())
							{
								ItemStack toSet = toInsert.copy();
								toSet.stackSize += inSlot.stackSize;

								inventory.setInventorySlotContents(slotID, toSet);
								return null;
							}
							else
							{
								int rejects = (inSlot.stackSize + toInsert.stackSize) - inSlot.getMaxStackSize();

								ItemStack toSet = toInsert.copy();
								toSet.stackSize = inSlot.getMaxStackSize();

								ItemStack remains = toInsert.copy();
								remains.stackSize = rejects;

								inventory.setInventorySlotContents(slotID, toSet);

								toInsert = remains;
							}
						}
					}
				}
			}
		}
		return toInsert;

	}

	public static ItemStack takeTopItemFromInventory(IInventory inventory, int side)
	{
		if (!(inventory instanceof ISidedInventory))
		{
			for (int i = inventory.getSizeInventory() - 1; i >= 0; i--)
			{
				if (inventory.getStackInSlot(i) != null)
				{
					ItemStack toSend = inventory.getStackInSlot(i).copy();
					toSend.stackSize = 1;

					inventory.decrStackSize(i, 1);

					return toSend;
				}
			}
		}
		else
		{
			ISidedInventory sidedInventory = (ISidedInventory) inventory;
			int[] slots = sidedInventory.getAccessibleSlotsFromSide(side);

			if (slots != null)
			{
				for (int get = slots.length - 1; get >= 0; get--)
				{
					int slotID = slots[get];

					if (sidedInventory.getStackInSlot(slotID) != null)
					{
						ItemStack toSend = sidedInventory.getStackInSlot(slotID);
						toSend.stackSize = 1;

						if (sidedInventory.canExtractItem(slotID, toSend, side))
						{
							sidedInventory.decrStackSize(slotID, 1);

							return toSend;
						}
					}
				}
			}
		}

		return null;
	}

	public static ItemStack takeTopBlockFromInventory(IInventory inventory, int side)
	{
		if (!(inventory instanceof ISidedInventory))
		{
			for (int i = inventory.getSizeInventory() - 1; i >= 0; i--)
			{
				if (inventory.getStackInSlot(i) != null && inventory.getStackInSlot(i).getItem() instanceof ItemBlock)
				{
					ItemStack toSend = inventory.getStackInSlot(i).copy();
					toSend.stackSize = 1;

					inventory.decrStackSize(i, 1);

					return toSend;
				}
			}
		}
		else
		{
			ISidedInventory sidedInventory = (ISidedInventory) inventory;
			int[] slots = sidedInventory.getAccessibleSlotsFromSide(side);

			if (slots != null)
			{
				for (int get = slots.length - 1; get >= 0; get--)
				{
					int slotID = slots[get];

					if (sidedInventory.getStackInSlot(slotID) != null && inventory.getStackInSlot(slotID).getItem() instanceof ItemBlock)
					{
						ItemStack toSend = sidedInventory.getStackInSlot(slotID);
						toSend.stackSize = 1;

						if (sidedInventory.canExtractItem(slotID, toSend, side))
						{
							sidedInventory.decrStackSize(slotID, 1);

							return toSend;
						}
					}
				}
			}
		}

		return null;
	}

	public static void dropBlockAsItem(World world, int x, int y, int z)
	{
		dropBlockAsItem(world, x, y, z, false);
	}

	public static void dropBlockAsItem(World world, int x, int y, int z, boolean destroy)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlock(x, y, z);

			int meta = world.getBlockMetadata(x, y, z);
			if (block != null)
			{
				ArrayList<ItemStack> items = block.getDrops(world, x, y, z, meta, 0);

				for (ItemStack itemStack : items)
				{
					dropItemStack(world, x, y, z, itemStack);
				}
			}
			if (destroy)
			{
				world.setBlockToAir(x, y, z);
			}
		}
	}

	/**
	 * Drops an item stack on the floor.
	 */
	public static void dropItemStack(World world, int x, int y, int z, ItemStack itemStack)
	{
		dropItemStack(world, x, y, z, itemStack, 10);
	}

	public static void dropItemStack(World world, int x, int y, int z, ItemStack itemStack, int delay)
	{
		dropItemStack(world, x, y, z, itemStack, delay, 0f);
	}

	public static void dropItemStack(World world, int x, int y, int z, ItemStack itemStack, int delay, float randomAmount)
	{
		dropItemStack(world, x, y, z, itemStack, delay, randomAmount);
	}

	public static void dropItemStack(World world, double x, double y, double z, ItemStack itemStack, int delay, float randomAmount)
	{
		assert world.isRemote : "Inventory Utility [Can not drop ItemStacks client side @" + x + "x " + y + "y " + z + "z]";
		assert itemStack == null : "Inventory Utility [Can not drop null ItemStacks @" + x + "x " + y + "y " + z + "z]";

		if (!world.isRemote && itemStack != null)
		{
			double randomX = 0;
			double randomY = 0;
			double randomZ = 0;

			if (randomAmount > 0)
			{
				randomX = world.rand.nextFloat() * randomAmount + (1.0F - randomAmount) * 0.5D;
				randomY = world.rand.nextFloat() * randomAmount + (1.0F - randomAmount) * 0.5D;
				randomZ = world.rand.nextFloat() * randomAmount + (1.0F - randomAmount) * 0.5D;
			}

			EntityItem entityitem = new EntityItem(world, x + randomX, y + randomY, z + randomZ, itemStack);

			if (randomAmount <= 0)
			{
				entityitem.motionX = 0;
				entityitem.motionY = 0;
				entityitem.motionZ = 0;
			}

			if (itemStack.hasTagCompound())
			{
				entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
			}

			entityitem.delayBeforeCanPickup = delay;
			world.spawnEntityInWorld(entityitem);
		}
	}

	/**
	 * Decreases the stack by a set amount
	 *
	 * @param stack  - starting stack
	 * @param amount - amount of items
	 * @return the edited stack
	 */
	public static ItemStack decrStackSize(ItemStack stack, int amount)
	{
		if (stack != null)
		{
			ItemStack itemStack = stack.copy();
			if (itemStack.stackSize <= amount)
			{
				return null;
			}
			else
			{
				itemStack.stackSize -= amount;

				if (itemStack.stackSize <= 0)
				{
					return null;
				}
				return itemStack;
			}
		}
		else
		{
			return null;
		}
	}

	/**
	 * Called to consume an ItemStack in a way that is mod supported. This mainly just allows fluid
	 * items to return empty versions. For example a lava bucket will be consumed turned into an
	 * empty bucket. This version of consume will consume the item held in the player's hand.
	 */
	public static void consumeHeldItem(EntityPlayer player)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		if (player != null && stack != null)
		{
			stack = stack.copy();
			if (stack.getItem().hasContainerItem())
			{
				if (stack.stackSize == 1)
				{
					stack = stack.getItem().getContainerItem(stack);
				}
				else
				{
					player.inventory.addItemStackToInventory(stack.getItem().getContainerItem(stack.splitStack(1)));
				}
			}
			else
			{
				if (stack.stackSize == 1)
				{
					stack = null;
				}
				else
				{
					stack.splitStack(1);
				}
			}
			player.inventory.setInventorySlotContents(player.inventory.currentItem, stack);
		}
	}

	/**
	 * Called to consume an ItemStack in a way that is mod supported. This mainly just allows fluid
	 * items to return empty versions. For example a lava bucket will be consumed turned into an
	 * empty bucket.
	 */
	public static ItemStack consumeStack(ItemStack stack)
	{
		if (stack.stackSize == 1)
		{
			if (stack.getItem().hasContainerItem(stack))
			{
				return stack.getItem().getContainerItem(stack);
			}
		}
		else
		{
			return stack.splitStack(1);
		}
		return null;
	}

	public static boolean stacksMatchExact(ItemStack stackA, ItemStack stackB)
	{
		if (stackA == null && stackB == null)
		{
			return true;
		}
		else if (stackA != null && stackB != null)
		{
			return stackA.isItemEqual(stackB) && stackA.stackSize == stackB.stackSize;
		}
		return false;
	}

	/**
	 * Checks to see how many of the item are in the inventory.
	 *
	 * @param stack - stack to check against, ignores stacksize
	 * @param inv   - inventory
	 * @param slots - slots to checks, if null defaults to entire inventory
	 * @return count of items using the stacksize of each itemstack found
	 */
	public static int getStackCount(ItemStack stack, IInventory inv, int[] slots)
	{
		int count = 0;

		if (stack != null)
		{
			List<Integer> slot_list = new ArrayList<Integer>();

			if (slots != null & slots.length > 0)
			{
				for (int i = 0; i < slots.length; i++)
				{
					slot_list.add(slots[i]);
				}
			}

			for (int slot = 0; slot < inv.getSizeInventory(); slot++)
			{
				if (slot_list.isEmpty() || slot_list.contains(slot))
				{
					if (inv.getStackInSlot(slot) != null && inv.getStackInSlot(slot).isItemEqual(stack))
					{
						count += inv.getStackInSlot(slot).stackSize;
					}
				}
			}
		}

		return count;
	}

	public static int getStackCount(Class<?> compare, IInventory inv)
	{
		return getStackCount(compare, inv);
	}

	public static int getStackCount(Class<?> compare, IInventory inv, int[] slots)
	{
		int count = 0;

		if (compare != null)
		{
			List<Integer> slot_list = new ArrayList<Integer>();

			if (slots != null & slots.length > 0)
			{
				for (int i = 0; i < slots.length; i++)
				{
					slot_list.add(slots[i]);
				}
			}

			for (int slot = 0; slot < inv.getSizeInventory(); slot++)
			{
				if (slot_list.isEmpty() || slot_list.contains(slot))
				{
					if (inv.getStackInSlot(slot) != null && compare.isInstance(inv.getStackInSlot(slot).getItem()))
					{
						count += inv.getStackInSlot(slot).stackSize;
					}
				}
			}
		}

		return count;
	}

}
