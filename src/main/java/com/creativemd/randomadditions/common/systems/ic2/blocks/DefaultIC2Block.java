package com.creativemd.randomadditions.common.systems.ic2.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.ic2.SubBlockIC2;

public class DefaultIC2Block extends SubBlockIC2{

	public DefaultIC2Block(String name, SubBlockSystem system) {
		super(name, system);
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
	public String getTextureName() {
		return "";
	}

}
