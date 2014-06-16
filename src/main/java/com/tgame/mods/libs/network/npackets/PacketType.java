package com.tgame.mods.libs.network.npackets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * this is influenced by Calclavia's packet system, yet is my own implementation
 *
 * @author tgame14
 * @since 10/05/14
 */
public abstract class PacketType
{
	/**
	 * Encode the packet data into the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
	 *
	 * @param ctx    channel context
	 * @param buffer the buffer to encode into
	 */
	public abstract void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer);

	/**
	 * Decode the packet data from the ByteBuf stream. Complex data sets may need specific data handlers (See @link{cpw.mods.fml.common.network.ByteBuffUtils})
	 *
	 * @param ctx    channel context
	 * @param buffer the buffer to decode from
	 */
	public abstract void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer);

	/**
	 * Handle a packet on the client side. Note this occurs after decoding has completed.
	 *
	 * @param player the player reference
	 */
	public abstract void handleClientSide(EntityPlayer player);

	/**
	 * Handle a packet on the server side. Note this occurs after decoding has completed.
	 *
	 * @param player the player reference
	 */
	public abstract void handleServerSide(EntityPlayer player);
}
