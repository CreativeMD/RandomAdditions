package com.creativemd.randomadditions.common.item.enchantment;

import com.creativemd.randomadditions.common.entity.EntityRandomArrow;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class EnchantmentKnockback extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Knockback";
	}
	
	@Override
	public void onShotArrow(EntityPlayer player, EntityRandomArrow arrow)
	{
		arrow.knockbackStrength = level;
	}
	
	@Override
	public void onAttackEntity(EntityPlayer player, EntityLivingBase entity)
	{
		if (cantakeNormalEnergy(player, level))
	    {
			entity.addVelocity((double)(-MathHelper.sin(player.rotationYaw * (float)Math.PI / 180.0F) * (float)level * 0.5F), 0.1D, (double)(MathHelper.cos(player.rotationYaw * (float)Math.PI / 180.0F) * (float)level * 0.5F));
			player.motionX *= 0.6D;
			//entity.motionY += 0.1;
			player.motionZ *= 0.6D;
			entity.setSprinting(false);
	    }
	}
	
}
