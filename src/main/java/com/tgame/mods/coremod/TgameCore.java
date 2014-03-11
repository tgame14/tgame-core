package com.tgame.mods.coremod;

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
@Mod(modid = TgameCore.ID, version = TgameCore.VERSION, name = TgameCore.NAME)
public class TgameCore implements IMod
{
    public static final String ID = "tgame-core";
    public static final String VERSION = "@VERSION@";
    public static final String NAME = "Tgame Core";

    @Mod.Instance(TgameCore.ID)
    private static TgameCore instance;

    @SidedProxy(serverSide = "com.tgame.mods.coremod.CommonProxyBase", clientSide = "com.tgame.mods.coremod.ClientProxyBase")
    public static CommonProxyBase proxy;

    @Override
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {

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
