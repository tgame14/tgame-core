package com.tgame.mods.libs.network.npackets;

import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * implement on Tiles to receive packets
 *
 * @author tgame14
 * @since 10/05/14
 */
public interface IPacketReceiverWithID
{
	void onReceivePacketId(int id, EntityPlayer player, ByteBuf buffer, Side side);
}
