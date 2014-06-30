package com.tgame.mods.prefabs.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author tgame14
 * @since 22/06/2014
 */
public class TGBlockBase extends Block
{
	protected TGBlockBase(Material material)
	{
		super(material);

		this.setHardness(1.5F);
		this.setStepSound(soundTypeStone);
	}

	@Override
	public int damageDropped(int i) {

		return i;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}


}
