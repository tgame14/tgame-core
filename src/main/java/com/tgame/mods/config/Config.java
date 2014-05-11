package com.tgame.mods.config;

import net.minecraftforge.common.config.Configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @since 11/05/14
 * @author tgame14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Config
{
    public String category () default Configuration.CATEGORY_GENERAL;

    public String key () default "";

    public String comment () default "";
}
