package com.creativemd.randomadditions.common.upgrade;

import com.creativemd.randomadditions.common.item.items.RandomItem;


public enum Upgrade {
	
	Speed("Speed", RandomItem.SpeedUpgrade, 1, 5, 10),
	Input("Input", RandomItem.InputUpgrade, 100, 500, 1000),
	Storage("Storage", RandomItem.StorageUpgrade, 500, 1000, 5000),
	Power("Power", RandomItem.PowerUpgrade, 0.01F, 0.05F, 0.1F);
	
	
	public String name;
	
	public RandomItem item;
	
	public float[] step;
	
	Upgrade(String name, RandomItem item, float... step)
	{
		this.name = name;
		this.item = item;
		this.step = step;
	}
	
	public float getModifier(int level)
	{
		if(level >= 0 && level < step.length)
			return step[level];
		return 0;
	}
	
}
