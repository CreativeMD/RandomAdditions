package com.creativemd.randomadditions.common.systems.machine;

import net.minecraft.nbt.NBTTagCompound;

public class CustomOreInput {
	
	public String ore;
	public int stackSize;
	public NBTTagCompound nbt;
	
	public CustomOreInput(String ore, int stackSize)
	{
		this.ore = ore;
		this.stackSize = stackSize;
		this.nbt = null;
	}
	
	public CustomOreInput(String ore, int stackSize, NBTTagCompound nbt)
	{
		this(ore, stackSize);
		this.nbt = nbt;
	}
}
