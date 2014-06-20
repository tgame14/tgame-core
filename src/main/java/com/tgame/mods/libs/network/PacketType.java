package com.tgame.mods.libs.network;

import com.tgame.mods.libs.network.netty.AbstractPacket;
import com.tgame.mods.libs.network.netty.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @since 26/05/14
 * @author tgame14
 */
public abstract class PacketType extends AbstractPacket
{
    protected ByteBuf data;

    public PacketType (Object... args)
    {
        this.data = Unpooled.buffer();
        PacketManager.writeData(this.data, args);
    }
}
