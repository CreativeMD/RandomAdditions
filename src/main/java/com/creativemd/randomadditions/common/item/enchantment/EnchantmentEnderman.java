package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentEnderman extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Enderman";
	}
	
	@Override
	public float getDamageOnEntity(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		if(entity instanceof EntityEnderman && cantakeNormalEnergy(player, level))
			return damage + 6 + level*4; //TODO check if this too much
		return damage;
	}
}
