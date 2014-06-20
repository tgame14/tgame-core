package com.tgame.mods.libs.network.netty;

import com.tgame.mods.core.TgameCore;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetHandler;

/**
 * @author tgame14
 * @since 31/05/14
 */
public class ResonantPacketHandler extends SimpleChannelInboundHandler<AbstractPacket>
{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, AbstractPacket packet) throws Exception
	{
		INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
		EntityPlayer player = TgameCore.proxy.getPlayerFromNetHandler(netHandler);

		switch (FMLCommonHandler.instance().getEffectiveSide())
		{
			case CLIENT:
				packet.handleClientSide(player);
				break;
			case SERVER:
				packet.handleServerSide(player);
				break;
			default:
				break;
		}

	}
}
