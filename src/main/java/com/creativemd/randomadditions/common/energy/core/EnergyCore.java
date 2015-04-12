package com.creativemd.randomadditions.common.energy.core;

import java.util.ArrayList;

import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.energy.core.EnergyUtils.MachineEntry;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public abstract class EnergyCore extends TileEntityRandom{
	
	public abstract ArrayList getConnections(ForgeDirection direction);
}
