package com.tgame.mods.libs.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.HashSet;
import java.util.Set;

/**
 * @since 29/03/14
 * @author tgame14
 */
public class RegistryHandler
{
    private static Set<IRegisterable> callBacks = new HashSet<IRegisterable>();

    public static class RegisterTarget implements IItemDefinition
    {
        private Block block;
        private Item item;
        private Class<? extends TileEntity> tileClass;

        public RegisterTarget(IRegisterable object)
        {
            this.block = null;
            this.item = null;
            this.tileClass = null;

            switch (object.getRegistryType())
            {
                case BLOCK:

            }
        }


        @Override
        public Block getBlock ()
        {
            return this.block;
        }

        @Override
        public Item getItem ()
        {
            return this.item;
        }

        @Override
        public Class<? extends TileEntity> getTile ()
        {
            return this.tileClass;
        }

        @Override
        public boolean sameAs (ItemStack comparableItem)
        {
            if (block == null)
            {
                return this.item.equals(comparableItem.getItem());
            }
            return this.block.equals(comparableItem.getItem());
        }
    }
}
