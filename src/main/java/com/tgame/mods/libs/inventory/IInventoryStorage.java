package com.tgame.mods.libs.inventory;

import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author tgame14
 * @since 09/05/14
 */
public interface IInventoryStorage
{
	/**
	 * Add an ItemStack to the inventory of this object. This returns what is remaining of the original stack - a null return means that the entire
	 * stack was accepted!
	 *
	 * @param item     ItemStack to be inserted. The size of this stack corresponds to the maximum amount to insert.
	 * @param simulate If TRUE, the insertion will only be simulated.
	 * @return An ItemStack representing how much is remaining after the item was inserted (or would have been, if simulated) into the container inventory.
	 */
	ItemStack insertItem(ItemStack item, boolean simulate);

	/**
	 * Extract an ItemStack from the inventory of this container item. This returns the resulting stack - a null return means that nothing was extracted!
	 *
	 * @param item     ItemStack to be extracted. The size of this stack corresponds to the maximum amount to extract. If this is null, then a null ItemStack should
	 *                 immediately be returned.
	 * @param simulate If TRUE, the extraction will only be simulated.
	 * @return An ItemStack representing how much was extracted (or would have been, if simulated) from the container inventory.
	 */
	ItemStack extractItem(ItemStack item, boolean simulate);

	/**
	 * Extract an ItemStack from the inventory of this container item. This returns the resulting stack - a null return means that nothing was extracted!
	 *
	 * @param maxExtract Maximum number of items to extract. (The returned ItemStack should have a stackSize no higher than this.)
	 * @param simulate   If TRUE, the extraction will only be simulated.
	 * @return An ItemStack representing how much was extracted (or would have been, if simulated) from the container inventory.
	 */
	ItemStack extractItem(int maxExtract, boolean simulate);

	/**
	 * Get the contents of the container item's inventory. This should only return non-null ItemStacks, and an empty List if the inventory has nothing.
	 */
	List<ItemStack> getInventoryContents();

	/**
	 * Get the size of this inventory of this container item.
	 */
	int getSizeInventory();

	/**
	 * Returns whether or not the container item's inventory is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns whether or not the container item's inventory is full.
	 */
	boolean isFull();
}
