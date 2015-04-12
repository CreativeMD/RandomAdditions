package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentSharpness extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Sharpness";
	}
	
	@Override
	public float getDamageOnEntity(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		if(cantakeNormalEnergy(player, level))
			return damage;
		return damage;
	}
	
	@Override
	public float getDamage(float damage)
	{
		return damage+level*2;
	}
	
}
