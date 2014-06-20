package com.tgame.mods.core;

import com.tgame.mods.config.Config;
import com.tgame.mods.config.ConfigHandler;
import com.tgame.mods.config.ConfigScanner;
import com.tgame.mods.interfaces.IMod;
import com.tgame.mods.libs.multiblocks.MultiblockEventHandler;
import com.tgame.mods.libs.network.netty.PacketManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

/**
 * @author tgame14
 * @since 10/03/14
 */
@SuppressWarnings("unused")
@Mod(modid = Settings.MODID, version = Settings.VERSION, name = Settings.NAME)
public class TgameCore implements IMod
{
	@Mod.Instance(Settings.MODID)
	private static TgameCore INSTANCE;

	@Config
	private static int testField = 3;

	@SidedProxy(serverSide = "com.tgame.mods.core.CommonProxyBase", clientSide = "com.tgame.mods.core.ClientProxyBase")
	public static CommonProxyBase proxy;

	public PacketManager packetManager;

	public TgameCore()
	{
		this.packetManager = new PacketManager();
	}

	@Override
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{


		ConfigScanner.instance().generateSets(event.getAsmData());
		Settings.CONFIGURATION = new Configuration(event.getSuggestedConfigurationFile());

		MultiblockEventHandler handler = new MultiblockEventHandler();
		FMLCommonHandler.instance().bus().register(handler);
		MinecraftForge.EVENT_BUS.register(handler);

		proxy.preInit();
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

		proxy.postInit();
	}
}
