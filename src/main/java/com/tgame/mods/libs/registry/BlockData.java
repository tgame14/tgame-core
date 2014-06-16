package com.tgame.mods.libs.registry;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tgame14
 * @since 11/05/14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BlockData
{
	public Class<? extends TileEntity>[] tileClass();

	public Class<? extends ItemBlock> itemBlockClass() default ItemBlock.class;
}
