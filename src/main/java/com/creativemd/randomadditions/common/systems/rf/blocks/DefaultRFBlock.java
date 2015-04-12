package com.creativemd.randomadditions.common.systems.rf.blocks;

import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.rf.SubBlockRF;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class DefaultRFBlock extends SubBlockRF{

	public DefaultRFBlock(SubBlockSystem system) {
		super("Default", system, false);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity) {
		return null;
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}
}
