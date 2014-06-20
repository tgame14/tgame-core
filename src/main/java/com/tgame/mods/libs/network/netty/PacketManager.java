package com.tgame.mods.libs.network.netty;

import com.tgame.mods.core.Settings;
import com.tgame.mods.interfaces.IDataObject;
import com.tgame.mods.interfaces.IProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.fluids.FluidTank;

import java.util.EnumMap;

/**
 * @since 26/05/14
 * @author tgame14
 */
public class PacketManager implements IProxy
{

	protected EnumMap<Side, FMLEmbeddedChannel> channelEnumMap;

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

	@SideOnly(Side.CLIENT)
	public void sendToServer (AbstractPacket packet)
	{
		this.channelEnumMap.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		this.channelEnumMap.get(Side.CLIENT).writeAndFlush(packet);
	}

	public static void writeData (ByteBuf data, Object... sendData)
	{
		for (Object dataValue : sendData)
		{
			if (dataValue instanceof Integer)
			{
				data.writeInt((Integer) dataValue);
			}
			else if (dataValue instanceof Float)
			{
				data.writeFloat((Float) dataValue);
			}
			else if (dataValue instanceof Double)
			{
				data.writeDouble((Double) dataValue);
			}
			else if (dataValue instanceof Byte)
			{
				data.writeByte((Byte) dataValue);
			}
			else if (dataValue instanceof Boolean)
			{
				data.writeBoolean((Boolean) dataValue);
			}
			else if (dataValue instanceof String)
			{
				ByteBufUtils.writeUTF8String(data, (String) dataValue);
			}
			else if (dataValue instanceof Short)
			{
				data.writeShort((Short) dataValue);
			}
			else if (dataValue instanceof Long)
			{
				data.writeLong((Long) dataValue);
			}
			else if (dataValue instanceof IByteBufObject)
			{
				((IByteBufObject) dataValue).writeBytes(data);
			}
			else if (dataValue instanceof NBTTagCompound)
			{
				ByteBufUtils.writeTag(data, (NBTTagCompound) dataValue);
			}
			else if (dataValue instanceof FluidTank)
			{
				data.writeInt(((FluidTank) dataValue).getCapacity());
				ByteBufUtils.writeTag(data, ((FluidTank) dataValue).writeToNBT(new NBTTagCompound()));
			}
			else if (dataValue instanceof IDataObject)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				((IDataObject) dataValue).writeToNBT(nbt);
				ByteBufUtils.writeTag(data, nbt);
			}
			else
			{
				Settings.LOGGER.fatal("Resonant Engine packet attempt to write an invalid type: " + dataValue.getClass());
			}
		}
	}

	public Packet toMCPacket(AbstractPacket packet)
	{
		return channelEnumMap.get(FMLCommonHandler.instance().getEffectiveSide()).generatePacketFrom(packet);
	}

	@Override
	public void preInit()
	{

	}

	@Override
	public void init()
	{
		this.channelEnumMap = NetworkRegistry.INSTANCE.newChannel(Settings.CHANNEL, new ResonantChannelHandler(), new ResonantPacketHandler());
	}

	@Override
	public void postInit()
	{

	}
}


