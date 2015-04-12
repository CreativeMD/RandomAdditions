package com.creativemd.randomadditions.common.systems.cable.tileentity;

import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.systems.cable.SubBlockCableBase;
import com.creativemd.randomadditions.common.systems.cable.SubSystemCable;

public abstract class TileEntityCableBase extends EnergyCable{
	
	public SubBlockCableBase getBlock()
	{
		return SubSystemCable.instance.getSubBlock(getBlockMetadata());
	}
	
	@Override
	public int getTransmitablePower() {
		return getBlock().getTransmitablePower(this);
	}

}
