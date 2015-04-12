package com.creativemd.randomadditions.common.systems.deco.blocks;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;

public class DefaultDeco extends SubBlockDeco{

	public DefaultDeco(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}

}
