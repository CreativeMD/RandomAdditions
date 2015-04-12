package com.creativemd.randomadditions.common.item.items;

import net.minecraft.item.ItemStack;

public interface RandomItemEnergy{

	public abstract int onRecieveEnergy(ItemStack stack, int amount);
	
	public abstract int onProduceEnergy(ItemStack stack, int max);

}
