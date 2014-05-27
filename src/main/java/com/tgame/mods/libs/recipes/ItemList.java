package com.tgame.mods.libs.recipes;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.ListMultimap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * This code is based a bit on Chickenbones' NEI ItemList
 *
 * @author ChickenBones, tgame14
 * @since 27/05/14
 */
public class ItemList
{
	public static List<ItemStack> items = new LinkedList<ItemStack>();
	public static ListMultimap<Item, ItemStack> itemMap = LinkedListMultimap.create();
	protected static HashSet<Item> erroredItems = new HashSet<Item>();

	public static void init()
	{

	}
}
