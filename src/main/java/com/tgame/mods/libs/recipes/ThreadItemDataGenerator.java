package com.tgame.mods.libs.recipes;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.LinkedList;
import java.util.List;

/**
 * @author tgame14
 * @since 27/05/14
 */
public class ThreadItemDataGenerator extends Thread
{
	@Override
	public void run()
	{
		ItemList.itemMap.clear();
		ItemList.items.clear();
		ItemList.erroredItems.clear();

		for (Object name : Item.itemRegistry.getKeys())
		{
			Item candidate = (Item) GameData.getItemRegistry().getObject(name);
			List<ItemStack> itemsCreativeTab = new LinkedList<ItemStack>();
			candidate.getSubItems(candidate, candidate.getCreativeTab(), itemsCreativeTab);

			ItemList.itemMap.putAll(candidate, (Iterable) itemsCreativeTab);
			ItemList.items.addAll(itemsCreativeTab);
		}
	}
}
