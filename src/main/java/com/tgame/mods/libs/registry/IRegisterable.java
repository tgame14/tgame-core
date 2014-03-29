package com.tgame.mods.libs.registry;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

/**
 * @since 29/03/14
 * @author tgame14
 */
@SuppressWarnings("unused")
public interface IRegisterable
{
    RegistryType getRegistryType();

    public enum RegistryType
    {
        BLOCK, ITEM, ENTITY, WORLDGEN,
    }

    public static interface IRegisterableBlock extends IRegisterable
    {
        void addRecipes();

        Class<? extends TileEntity> getTileClass();
        Class<? extends ItemBlock> getItemBlock();
    }

    public static interface IRegisterableItem
    {
        void addRecipes();
    }

    public static interface IRegisterableEntity extends IRegisterable
    {

    }
}
