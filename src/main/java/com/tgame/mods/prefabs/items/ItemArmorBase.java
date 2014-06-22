package com.tgame.mods.prefabs.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemArmor;

/**
 * @author tgame14
 * @since 22/04/14
 */
public abstract class ItemArmorBase extends ItemArmor
{

	public ItemArmorBase(ArmorMaterial material, int renderIndex, int armorType, String domain, CreativeTabs tab)
	{
		super(material, renderIndex, armorType);
		this.setUnlocalizedName(this.getClass().getSimpleName() + "." + armorType);
		this.setTextureName(domain + this.getClass().getSimpleName() + "." + armorType);
		this.setCreativeTab(tab);

	}
}
