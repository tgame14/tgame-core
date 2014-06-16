package com.tgame.mods.config;

import net.minecraftforge.common.config.Configuration;

import java.lang.reflect.Field;

/**
 * @author tgame14
 * @since 11/05/14
 */
public class ConfigHandler
{
	public static void configure(Configuration config, String namespace)
	{
		config.load();
		for (Class clazz : ConfigScanner.instance().classes)
		{
			if (clazz.getName().startsWith(namespace))
			{
				for (Field field : clazz.getDeclaredFields())
				{
					Config cfg = field.getAnnotation(Config.class);
					if (cfg != null)
					{
						handleField(field, cfg, config);
					}
				}
			}
		}
		config.save();
	}

	private static void handleField(Field field, Config cfg, Configuration config)
	{
		try
		{
			// Set field and annotation data Handled before handing the write of field to config
			field.setAccessible(true);
			String key;

			if (cfg.key().isEmpty())
			{
				key = field.getName();
			}

			else
			{
				key = cfg.key();
			}

			String comment = !cfg.comment().isEmpty() ? cfg.comment() : null;

			// if field is Array, use Config lists, otherwise use default config read and writes
			if (!field.getType().isArray())
			{
				if (field.getType() == Integer.TYPE)
				{
					int value = config.get(cfg.category(), key, field.getInt(null), comment).getInt(field.getInt(null));
					field.setInt(null, value);
				}
				else if (field.getType() == Double.TYPE)
				{
					double value = config.get(cfg.category(), key, field.getDouble(null), comment).getDouble(field.getDouble(null));
					field.setDouble(null, value);
				}

				else if (field.getType() == Float.TYPE)
				{
					float value = (float) config.get(cfg.category(), key, field.getFloat(null), comment).getDouble(field.getDouble(null));
					field.setFloat(null, value);
				}
				else if (field.getType() == String.class)
				{
					String value = config.get(cfg.category(), key, (String) field.get(null), comment).getString();
					field.set(null, value);
				}
				else if (field.getType() == Boolean.TYPE)
				{
					boolean value = config.get(cfg.category(), key, field.getBoolean(null), comment).getBoolean(field.getBoolean(null));
					field.setBoolean(null, value);
				}
				else if (field.getType() == Long.TYPE)
				{
					// TODO: Add support for reading long values, marked for 1.7
					long value = config.get(cfg.category(), key, field.getLong(null), comment).getInt();
					field.setLong(null, value);
				}
			}

			else
			{
				if (field.getType().getComponentType() == String.class)
				{
					String[] values = config.get(cfg.category(), key, (String[]) field.get(null), comment).getStringList();
					field.set(null, values);
				}
				else if (field.getType().getComponentType() == int.class)
				{
					int[] values = config.get(cfg.category(), key, (int[]) field.get(null), comment).getIntList();
					field.set(null, values);
				}
				else if (field.getType().getComponentType() == boolean.class)
				{
					boolean[] values = config.get(cfg.category(), key, (boolean[]) field.get(null), comment).getBooleanList();
					field.set(null, values);
				}
				// TODO Add support for reading Long[] lists from config
			}
		}
		catch (Exception e)
		{
			System.out.println("Failed to configure: " + field.getName());
			e.printStackTrace();
		}
	}
}
