package com.creativemd.randomadditions.common.item.items;

import net.minecraft.item.ItemStack;

public interface RandomItemEnergy{

	public abstract float onRecieveEnergy(ItemStack stack, float amount);
	
	public abstract float onProduceEnergy(ItemStack stack, int max);

}
