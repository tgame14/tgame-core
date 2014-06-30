package com.tgame.mods.prefabs.blocks;

import com.tgame.mods.libs.utility.InventoryUtility;
import com.tgame.mods.prefabs.tiles.TGTileBase;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author tgame14
 * @since 29/06/2014
 */
public abstract class TGBlockTile extends TGBlockBase implements ITileEntityProvider
{
	protected TGBlockTile(Material material)
	{
		super(material);
	}


	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntity tile = world.getTileEntity(x, y, z);

		if (tile instanceof TGTileBase)
		{
			TGTileBase candidate = (TGTileBase) tile;
			candidate.blockBroken();
		}
		if (tile instanceof IInventory)
		{
			IInventory inv = (IInventory) tile;
			for (int i = 0; i < inv.getSizeInventory(); i++)
			{
				InventoryUtility.dropItemStack(world, x, y, z, inv.getStackInSlot(i));
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode) {
			dropBlockAsItem(world, x, y, z, meta, 0);
			world.setBlockToAir(x, y, z);
		}
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {

		TileEntity tile = world.getTileEntity(x, y, z);
		return tile instanceof TGTileBase ? ((TGTileBase) tile).getComparatorInput(side) : 0;
	}
}
