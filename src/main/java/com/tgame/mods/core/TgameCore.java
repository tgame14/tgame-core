package com.tgame.mods.core;

import com.tgame.mods.config.Config;
import com.tgame.mods.config.ConfigHandler;
import com.tgame.mods.config.ConfigScanner;
import com.tgame.mods.interfaces.IMod;
import com.tgame.mods.libs.multiblocks.MultiblockEventHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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
        Settings.CONFIGURATION = new Configuration(event.getSuggestedConfigurationFile());

		FMLCommonHandler.instance().bus().register(new MultiblockEventHandler());
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
        ConfigHandler.configure(Settings.CONFIGURATION, Settings.DOMAIN);
		generateRecipeDump(Loader.instance().getConfigDir());
    }

	private void generateRecipeDump(File file)
	{
		System.out.println(Item.itemRegistry.getKeys());

	}
}
