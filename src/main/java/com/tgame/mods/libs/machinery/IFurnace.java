package com.tgame.mods.libs.machinery;

import com.tgame.mods.interfaces.IDataObject;
import com.tgame.mods.libs.inventory.IInventoryStorage;
import net.minecraft.item.ItemStack;

/**
 * @author tgame14
 * @since 18/06/2014
 */
public interface IFurnace extends IDataObject
{
	public void smelt(ItemStack stack);

	public IInventoryStorage getInputInv();

	public IInventoryStorage getOutputInv();
}
