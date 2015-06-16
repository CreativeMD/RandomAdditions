package com.creativemd.randomadditions.common.systems.cmachine;

import net.minecraft.tileentity.TileEntity;

import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;

public abstract class SubBlockCMachine extends SubBlock{

	public SubBlockCMachine(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	public String getTileEntityName()
	{
		return "RA" + system.name + name;
	}
	
	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}

}
