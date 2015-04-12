package com.creativemd.randomadditions.common.item.enchantment;

import com.creativemd.randomadditions.common.entity.EntityRandomArrow;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentFlame extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Flame";
	}
	
	@Override
	public void onAttackEntity(EntityPlayer player, EntityLivingBase entity)
	{
		if(cantakeNormalEnergy(player, level))
			entity.setFire(level*2);
	}
	
	@Override
	public void onShotArrow(EntityPlayer player, EntityRandomArrow arrow)
	{
		arrow.flame = 5*level;
	}

}
