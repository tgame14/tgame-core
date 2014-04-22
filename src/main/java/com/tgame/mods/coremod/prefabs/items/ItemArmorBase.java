package com.tgame.mods.coremod.prefabs.items;

import com.tgame.mods.coremod.Settings;
import net.minecraft.item.ItemArmor;

/**
 * @since 22/04/14
 * @author tgame14
 */
public abstract class ItemArmorBase extends ItemArmor
{

    public ItemArmorBase (ArmorMaterial material, int renderIndex, int armorType, String domain)
    {
        super(material, renderIndex, armorType);
        this.setUnlocalizedName(this.getClass().getSimpleName() + "." + armorType);
        this.setTextureName(domain + this.getClass().getSimpleName() + "." + armorType);
    }
}
