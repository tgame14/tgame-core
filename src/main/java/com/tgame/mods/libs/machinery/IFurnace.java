package com.tgame.mods.libs.machinery;

import com.tgame.mods.interfaces.IDataObject;
import com.tgame.mods.libs.inventory.simpleimpl.InventoryStorage;
import net.minecraft.item.ItemStack;

/**
 * @author tgame14
 * @since 18/06/2014
 */
public interface IFurnace extends IDataObject
{
	public void smelt(ItemStack stack);

	public InventoryStorage getInputInv();

	public InventoryStorage getOutputInv();
}
