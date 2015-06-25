package com.creativemd.randomadditions.common.systems.cable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;

public abstract class SubBlockCableBase extends SubBlock{
	
	public SubBlockCableBase(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	public abstract int getTransmitablePower(TileEntity tileEntity);
	
	@Override
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}
	
	@Override
	public boolean hasBlockTexture()
	{
		return false;
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}
	
}
