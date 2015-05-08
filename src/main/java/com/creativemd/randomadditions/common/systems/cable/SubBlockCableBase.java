package com.creativemd.randomadditions.common.systems.cable;

import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;

public abstract class SubBlockCableBase extends SubBlock{
	
	public SubBlockCableBase(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	public abstract int getTransmitablePower(TileEntity tileEntity);
	
	@Override
	public SubGuiTileEntity getGui(TileEntity tileEntity) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity) {
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
