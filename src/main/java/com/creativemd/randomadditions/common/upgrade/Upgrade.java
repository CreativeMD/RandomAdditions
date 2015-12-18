package com.creativemd.randomadditions.common.upgrade;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemUpgrade;

import net.minecraft.inventory.IInventory;


public enum Upgrade {
	
	Speed("Speed", RandomItem.SpeedUpgrade, 0.2F, 0.5F, 1F),
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
	
	public static float getProgressSpeed(IInventory inventory, float defaultSpeed)
	{
		float speed = defaultSpeed;
		if(inventory != null)
		{
			for (int i = 1; i < inventory.getSizeInventory(); i++) {
				if(inventory.getStackInSlot(i) != null)
					speed += RandomItemUpgrade.getUpgradeModifier(Upgrade.Speed, inventory.getStackInSlot(i))*defaultSpeed*inventory.getStackInSlot(i).stackSize;
			}
		}
		return speed;
	}

	public static int getInteralStorage(IInventory inventory, int defaultStorage) {
		int storage = defaultStorage;
		if(inventory != null)
		{
			for (int i = 1; i < inventory.getSizeInventory(); i++) {
				if(inventory.getStackInSlot(i) != null)
					storage += RandomItemUpgrade.getUpgradeModifier(Upgrade.Storage, inventory.getStackInSlot(i))*inventory.getStackInSlot(i).stackSize;
			}
		}
		return storage;
	}
	
	public static int getMaxInput(IInventory inventory, int defaultInput) {
		int input = defaultInput;
		if(inventory != null)
		{
			for (int i = 1; i < inventory.getSizeInventory(); i++) {
				if(inventory.getStackInSlot(i) != null)
					input += RandomItemUpgrade.getUpgradeModifier(Upgrade.Input, inventory.getStackInSlot(i))*inventory.getStackInSlot(i).stackSize;
			}
		}
		return input;
	}
	
}
