package com.tgame.mods.core;

import com.tgame.mods.config.Config;
import com.tgame.mods.config.ConfigScanner;
import com.tgame.mods.interfaces.IMod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

/**
 * @since 10/03/14
 * @author tgame14
 */
@SuppressWarnings("unused")
@Mod(modid = Settings.MODID, version = Settings.VERSION, name = Settings.NAME)
public class TgameCore implements IMod
{
    @Mod.Instance(Settings.MODID)
    private static TgameCore instance;

    @Config
    private static int testField = 3;

    @SidedProxy(serverSide = "com.tgame.mods.core.CommonProxyBase", clientSide = "com.tgame.mods.core.ClientProxyBase")
    public static CommonProxyBase proxy;

    public TgameCore()
    {

    }

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ConfigScanner.instance().generateSets(event.getAsmData());
    }

    @Override
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @Override
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }




}
