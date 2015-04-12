package com.creativemd.randomadditions.common.subsystem;

import com.creativemd.creativecore.common.container.SubContainer;

public abstract class SubContainerTileEntity extends SubContainer {
	
	public TileEntityRandom tileEntity;
	
	public SubContainerTileEntity(TileEntityRandom tileEntity)
	{
		this.tileEntity = tileEntity;
	}
}
