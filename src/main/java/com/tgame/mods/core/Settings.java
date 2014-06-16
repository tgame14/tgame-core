package com.tgame.mods.core;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
}
