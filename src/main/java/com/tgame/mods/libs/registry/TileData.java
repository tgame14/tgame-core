package com.tgame.mods.libs.registry;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

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
public @interface TileData
{
	public Class<? extends TileEntitySpecialRenderer> tesrClass();
}
