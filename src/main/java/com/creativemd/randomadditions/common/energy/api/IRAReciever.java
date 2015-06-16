package com.creativemd.randomadditions.common.energy.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRAReciever {
	
	public boolean canRecieveEnergy(ForgeDirection direction);
	
	public int getInteralStorage();
	
	public int getMaxInput();
	
	public int receivePower(int amount);
	
	/**Based on your already received power and the maxinput power.**/
	public int getRecieveablePower();
	
}
