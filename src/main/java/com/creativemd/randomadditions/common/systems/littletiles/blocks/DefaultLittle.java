package com.creativemd.randomadditions.common.systems.littletiles.blocks;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.littletiles.SubBlockLittle;

public class DefaultLittle extends SubBlockLittle{

	public DefaultLittle(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}

}
