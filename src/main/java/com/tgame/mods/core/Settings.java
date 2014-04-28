package com.tgame.mods.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @since 22/04/14
 * @author tgame14
 */
public class Settings
{
    public static final String MODID = "tgamecore";
    public static final String DOMAIN = MODID + ":";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "Tgame - Core";

    public static final Logger LOGGER = LogManager.getLogger(MODID);
}
