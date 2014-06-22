package com.tgame.mods.core;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

/**
 * @author tgame14
 * @since 22/04/14
 */
public class Settings
{
	public static final String MODID = "tgamecore";
	public static final String RESOURCE_LOCATION = MODID + ":";
	public static final String VERSION = "@VERSION@";
	public static final String NAME = "Tgame - Core";
	public static final String CHANNEL = "tgame-core";
	public static final String DOMAIN = "com.tgame.mods";

	public static final Logger LOGGER = LogManager.getLogger(MODID);
	public static Configuration CONFIGURATION;

	public static final String PATH_TEXTURES = RESOURCE_LOCATION + "textures" + File.separator;
	public static final String PATH_ARMOR = PATH_TEXTURES + "armor" + File.separator;
	public static final String PATH_GUI = PATH_TEXTURES + "gui" + File.separator;
	public static final String PATH_RENDER = PATH_TEXTURES + "blocks" + File.separator;
	public static final String PATH_COMPONENT = PATH_GUI + "components" + File.separator;
	public static final String PATH_ICON = PATH_GUI + "icons" + File.separator;
}
