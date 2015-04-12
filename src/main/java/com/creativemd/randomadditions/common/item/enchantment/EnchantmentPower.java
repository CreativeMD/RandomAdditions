package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentPower extends EnchantmentModifier{
	
	@Override
	public double getRange(EntityPlayer player, double range)
	{
		if(cantakeNormalEnergy(player, level))
		{
			return range+level;
		}
		return range;
	}
	
	@Override
	public float getDamage(float damage)
	{
		return damage+level*5;
	}

	@Override
	public String getName() {
		return "Power";
	}

}
