package com.tgame.mods.libs.prefabs.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @author tgame14
 * @since 22/04/14
 */
public abstract class ItemBase extends Item
{
	public ItemBase(CreativeTabs tab, String resource, boolean singleTexture)
	{
		super();
		this.setCreativeTab(tab);
		this.setUnlocalizedName(this.getClass().getSimpleName());

		if (singleTexture)
		{
			this.setTextureName(resource + this.getClass().getSimpleName());
		}

	}
}
