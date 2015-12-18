package com.creativemd.randomadditions.common.energy.api;

import net.minecraftforge.common.util.ForgeDirection;

public interface IRAReciever {
	
	public boolean canRecieveEnergy(ForgeDirection direction);
	
	public int getInteralStorage();
	
	public int getMaxInput();
	
	public float receivePower(float amount);
	
	/**Based on your already received power and the maxinput power.**/
	public float getRecieveablePower();
	
}
