package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class EnchantmentPosion extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Posion";
	}
	
	@Override
	public void onAttackEntity(EntityPlayer player, EntityLivingBase entity)
	{
		if(cantakeNormalEnergy(player, level))
		{
			if(entity instanceof EntityZombie || entity instanceof EntitySkeleton)
				entity.addPotionEffect(new PotionEffect(Potion.regeneration.id, 50*level, 1));
			else
				entity.addPotionEffect(new PotionEffect(Potion.poison.id, 50*level, 1));
		}
	}
}
