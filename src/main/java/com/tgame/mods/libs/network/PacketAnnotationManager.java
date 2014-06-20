package com.tgame.mods.libs.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.tgame.mods.core.Settings;
import com.tgame.mods.core.TgameCore;
import com.tgame.mods.interfaces.IDataObject;
import com.tgame.mods.libs.network.netty.PacketManager;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author tgame14
 * @since 26/05/14
 */
public class PacketAnnotationManager
{
	public static final PacketAnnotationManager INSTANCE = new PacketAnnotationManager();

	private PacketAnnotationManager()
	{
	}


	private int maxID = 0;

	/** A map of sync classes with their correspoding ID. */
	protected final BiMap<Class, Integer> classPacketIDMap = HashBiMap.create();

	/** Class ID : List of PacketSet sorted by ID */
	protected final HashMap<Integer, HashMap<Integer, PacketSet>> packetSetIDMap = new HashMap<Integer, HashMap<Integer, PacketSet>>();

	public void register(Class<? extends TileEntity> clazz)
	{
		constructPacketSets(clazz);
	}

	/** Constructs the packet sets for this specific class */
	public void constructPacketSets(Class clazz)
	{
		if (classPacketIDMap.containsKey(clazz))
		{
			return;
		}

		int classID = ++maxID;
		classPacketIDMap.put(clazz, classID);

		HashMap<Integer, PacketSet> packetSets = new HashMap<Integer, PacketSet>();

		while (clazz != null)
		{
			for (Field f : clazz.getDeclaredFields())
			{
				if (f.isAnnotationPresent(Synced.class))
				{
					Synced sync = f.getAnnotation(Synced.class);

					for (int packetID : sync.value())
					{
						PacketSet packetSet = packetSets.containsKey(packetID) ? packetSets.get(packetID) : new PacketSet(packetID);
						packetSet.syncFields.add(f);
						packetSets.put(packetID, packetSet);
					}
				}
			}

			for (Method m : clazz.getDeclaredMethods())
			{
				if (m.isAnnotationPresent(Synced.SyncedInput.class))
				{
					Synced.SyncedInput sync = m.getAnnotation(Synced.SyncedInput.class);

					for (int packetID : sync.value())
					{
						PacketSet packetSet = packetSets.containsKey(packetID) ? packetSets.get(packetID) : new PacketSet(packetID);
						packetSet.syncInputs.add(m);
						packetSets.put(packetID, packetSet);
					}
				}

				if (m.isAnnotationPresent(Synced.SyncedOutput.class))
				{
					Synced.SyncedOutput sync = m.getAnnotation(Synced.SyncedOutput.class);

					for (int packetID : sync.value())
					{
						PacketSet packetSet = packetSets.containsKey(packetID) ? packetSets.get(packetID) : new PacketSet(packetID);
						packetSet.syncOutputs.add(m);
						packetSets.put(packetID, packetSet);
					}
				}
			}

			clazz = clazz.getSuperclass();
		}

		packetSetIDMap.put(classID, packetSets);
	}

	public void sync(Object obj, int packetSetID)
	{
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
		{
			TgameCore.INSTANCE.packetManager.sendToAll(new PacketAnnotation(obj, packetSetID));
		}
		else
		{
			TgameCore.INSTANCE.packetManager.sendToServer(new PacketAnnotation(obj, packetSetID));
		}
	}





	public static class PacketSet
	{
		public final int id;

		public final List<Field> syncFields = new ArrayList<Field>();
		public final List<Method> syncInputs = new ArrayList<Method>();
		public final List<Method> syncOutputs = new ArrayList<Method>();

		public PacketSet(int id)
		{
			this.id = id;
		}

		public ByteBuf getPacketArrayData(Object obj)
		{
			ByteBuf data = Unpooled.buffer();

			try
			{
				for (Field f : syncFields)
				{
					f.setAccessible(true);
					Class type = f.getType();
					Object syncObj = f.get(obj);

					if (!type.isPrimitive())
					{
						if (syncObj == null)
						{
							data.writeBoolean(false);

						}
						else
						{
							data.writeBoolean(true);
							PacketManager.writeData(data, syncObj);
						}
					}
					else
					{
						PacketManager.writeData(data, syncObj);
					}
				}

				for (Method m : syncOutputs)
				{
					m.setAccessible(true);
					NBTTagCompound nbt = new NBTTagCompound();
					m.invoke(obj, nbt);
					ByteBufUtils.writeTag(data, nbt);
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			return data;


		}

		public void read(Object obj, ByteBuf data)
		{
			try
			{
				for (Field f : syncFields)
				{
					f.setAccessible(true);
					Class type = f.getType();
					Object result = null;

					if (type.isPrimitive())
					{
						if (type == Integer.class || type == Integer.TYPE)
						{
							result = data.readInt();
						}
						else if (type == Float.class || type == Float.TYPE)
						{
							result = data.readFloat();
						}
						else if (type == Double.class || type == Double.TYPE)
						{
							result = data.readDouble();
						}
						else if (type == Byte.class || type == Byte.TYPE)
						{
							result = data.readByte();
						}
						else if (type == Boolean.class || type == Boolean.TYPE)
						{
							result = data.readBoolean();
						}
						else if (type == Short.class || type == Short.TYPE)
						{
							result = data.readShort();
						}
						else if (type == Long.class || type == Long.TYPE)
						{
							result = data.readLong();
						}

						if (result == null)
						{
							Settings.LOGGER.fatal("packet read a null field for " + obj.getClass().getSimpleName());
						}
					}
					else
					{
						if (data.readBoolean())
						{
							if (type == String.class)
							{
								result = ByteBufUtils.readUTF8String(data);
							}
							else if (type == NBTTagCompound.class)
							{
								result = ByteBufUtils.readTag(data);
							}
							else if (type == FluidTank.class)
							{
								result = new FluidTank(data.readInt()).readFromNBT(ByteBufUtils.readTag(data));
							}
							else if (IDataObject.class.isAssignableFrom(type))
							{
								result = f.get(obj);
								((IDataObject) result).readFromNBT(ByteBufUtils.readTag(data));
							}
						}
						else
						{
							result = null;
						}
					}

					f.set(obj, result);
				}

				for (Method m : syncInputs)
				{
					m.setAccessible(true);
					m.invoke(obj, ByteBufUtils.readTag(data));
				}
			}
			catch (Exception e)
			{
				Settings.LOGGER.fatal("annotation packet failed for " + obj.getClass().getSimpleName());
				e.printStackTrace();
			}
		}
	}
}
