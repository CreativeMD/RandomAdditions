package com.creativemd.randomadditions.common.systems.deco.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;

public abstract class SubBlockSit extends SubBlockDeco{

	public SubBlockSit(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	
	@Override
	public boolean onBlockActivated(EntityPlayer player, ItemStack stack, TileEntity tileEntity)
	{
		//TODO Add the ability to sit
		return false;
	}
}
