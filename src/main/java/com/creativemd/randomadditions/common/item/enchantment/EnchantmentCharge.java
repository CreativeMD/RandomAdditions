package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentCharge extends EnchantmentModifier{

	@Override
	public int getSpanTime(EntityPlayer player, int time)
	{
		switch(level)
		{
		case 1:
			return 15;
		case 2:
			return 10;
		case 3:
			return 5;
		default:
			return time;
		}
	}
	
	@Override
	public String getName() {
		return "Fast Charge";
	}

}
