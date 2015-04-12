package com.creativemd.randomadditions.common.subsystem.client.block;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class TileEntitySpecialRendererSubBlock extends TileEntitySpecialRenderer{
	
	public SubBlockSystem system;
	
	public TileEntitySpecialRendererSubBlock(SubBlockSystem system)
	{
		this.system = system;
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float p_147500_8_) {
		if(tileEntity instanceof TileEntityRandom)
			system.getSubBlock(tileEntity.getBlockMetadata()).drawRender(tileEntity, x, y, z);		
	}

}
