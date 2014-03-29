package com.tgame.mods.libs.network;

import com.tgame.mods.coremod.TgameCore;
import com.tgame.mods.interfaces.IProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;

import java.util.*;

/**
 * @since 12/03/14
 * @author tgame14
 */
public class PacketPipelineHandler extends MessageToMessageCodec<FMLProxyPacket, AbstractPacket> implements IProxy
{
    private EnumMap<Side, FMLEmbeddedChannel> channelEnumMap;
    private List<Class<? extends AbstractPacket>> packets;
    private boolean postInitialized;

    public PacketPipelineHandler ()
    {
        this.packets = new LinkedList<Class<? extends AbstractPacket>>();
        this.postInitialized = false;
    }

    /**
     *
     * @param clazz the class of the packet to add to the packets list
     * @return whether it was successfully registered or not.
     */
    public boolean registerPacket (Class<? extends AbstractPacket> clazz)
    {
        if (this.packets.size() > 256)
        {
            TgameCore.LOGGER.error("Reached Max limit of packets for AT, Report to @AUTHOR@!");
            return false;
        }
        if (this.packets.contains(clazz))
        {
            TgameCore.LOGGER.warn("Packet class is attempted to be registered twice. this is a programming mistake");
            return false;
        }

        if (!this.postInitialized)
        {
            TgameCore.LOGGER.error("Error, Mod is not post initialized yet");
            return false;
        }

        this.packets.add(clazz);
        return true;
    }

    /**
     * Encodes the packet into the FML Channel and ships it over
     *
     * @param ctx the channel handler context
     * @param msg the Abstract Packet being sent
     * @param out whats shipped out
     * @throws Exception
     */
    @Override
    protected void encode (ChannelHandlerContext ctx, AbstractPacket msg, List<Object> out) throws Exception
    {
        ByteBuf buffer = Unpooled.buffer();
        Class<? extends AbstractPacket> clazz = msg.getClass();
        if (!this.packets.contains(msg.getClass()))
            throw new Exception("No packets registered for " + msg.getClass().getSimpleName());

        int packetId = this.packets.indexOf(clazz);
        buffer.writeByte(packetId);
        msg.encodeInto(ctx, buffer);
        FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), ctx.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);

    }

    /**
     * Decodes the packet and handles it within the packet itself and its handling
     *
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception
     */
    @Override
    protected void decode (ChannelHandlerContext ctx, FMLProxyPacket msg, List<Object> out) throws Exception
    {
        ByteBuf payload = msg.payload();
        int packetId = payload.readByte();
        Class<? extends AbstractPacket> clazz = this.packets.get(packetId);

        if (clazz == null)
            throw new Exception("No packets Registered for packet ID: " + packetId);

        AbstractPacket packet = clazz.newInstance();
        packet.decodeInto(ctx, payload.slice());

        EntityPlayer entityPlayer;
        switch (FMLCommonHandler.instance().getEffectiveSide())
        {
        case CLIENT:
            entityPlayer = Minecraft.getMinecraft().thePlayer;
            packet.handleClientSide(entityPlayer);
            break;

        case SERVER:
            INetHandler netHandler = ctx.channel().attr(NetworkRegistry.NET_HANDLER).get();
            entityPlayer = ((NetHandlerPlayServer) netHandler).playerEntity;
            packet.handleServerSide(entityPlayer);
            break;
        default:
            break;
        }

        out.add(packet);

    }

    /**
     * Does nothing
     */
    @Override
    public void preInit ()
    {

    }

    /**
     * call during initialization
     */
    @Override
    public void init ()
    {
        this.channelEnumMap = NetworkRegistry.INSTANCE.newChannel(TgameCore.CHANNEL, this);
    }

    /**
     * call in postinit
     */
    @Override
    public void postInit ()
    {
        if (this.postInitialized)
            return;

        this.postInitialized = true;
        Collections.sort(this.packets, new Comparator<Class<? extends AbstractPacket>>()
        {
            @Override
            public int compare (Class<? extends AbstractPacket> o1, Class<? extends AbstractPacket> o2)
            {
                int com = String.CASE_INSENSITIVE_ORDER.compare(o1.getCanonicalName(), o2.getCanonicalName());
                if (com == 0)
                    com = o1.getCanonicalName().compareTo(o2.getCanonicalName());

                return com;
            }
        });
    }

    @Override
    public String getModId ()
    {
        return TgameCore.ID;
    }

    /// *** THIS IS SPECIAL PACKET SENDING METHODS SIMILAR TO 1.6.4 IMPLEMENTATIONS *** ///

    /**
     * @param packet the packet to send to the player
     * @param player the player MP object
     */
    @SideOnly(Side.SERVER)
    public void sendToPlayer (AbstractPacket packet, EntityPlayerMP player)
    {
        this.channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        this.channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        this.channelEnumMap.get(Side.SERVER).writeAndFlush(packet);
    }

    /**
     *
     * @param packet the packet to send to the players in the dimension
     * @param dimId the dimension ID to send to.
     */
    public void sendToAllInDimension (AbstractPacket packet, int dimId)
    {
        this.channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        this.channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimId);
        this.channelEnumMap.get(Side.SERVER).writeAndFlush(packet);
    }

    /** sends to all clients connected to the server
     *
     * @param packet the packet to send.
     */
    public void sendToAll (AbstractPacket packet)
    {
        this.channelEnumMap.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channelEnumMap.get(Side.CLIENT).writeAndFlush(packet);
    }

    public void sendToAllAround (AbstractPacket message, NetworkRegistry.TargetPoint point)
    {
        this.channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        this.channelEnumMap.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        this.channelEnumMap.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToServer (AbstractPacket packet)
    {
        this.channelEnumMap.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        this.channelEnumMap.get(Side.CLIENT).writeAndFlush(packet);
    }
}