package com.creativemd.randomadditions.common.systems.ore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubBlockOre extends SubBlock{
	
	public int harvestLevel;
	
	public SubBlockOre(String name, int level, SubBlockSystem system) {
		super(name, system);
		this.harvestLevel = level;
	}
	
	@Override
	public int getHarvestLevel()
	{
		return harvestLevel;
	}

	@Override
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return true;
	}

}
