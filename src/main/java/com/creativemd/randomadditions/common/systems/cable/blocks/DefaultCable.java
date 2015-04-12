package com.creativemd.randomadditions.common.systems.cable.blocks;

import net.minecraft.tileentity.TileEntity;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.cable.SubBlockCableBase;

public class DefaultCable extends SubBlockCableBase{

	public DefaultCable(SubBlockSystem system) {
		super("Default", system);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}

	@Override
	public int getTransmitablePower(TileEntity tileEntity) {
		return 0;
	}

}
