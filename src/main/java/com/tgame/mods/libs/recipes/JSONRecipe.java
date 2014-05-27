package com.tgame.mods.libs.recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * @author tgame14
 * @since 27/05/14
 */
public class JSONRecipe implements IRecipe
{
	@Override
	public boolean matches(InventoryCrafting var1, World var2)
	{
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting var1)
	{
		return null;
	}

	@Override
	public int getRecipeSize()
	{
		return 0;
	}

	@Override
	public ItemStack getRecipeOutput()
	{
		return null;
	}
}
