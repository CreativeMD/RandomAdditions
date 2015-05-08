package com.creativemd.randomadditions.common.item.enchantment.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;

public class EnchantmentFireResistance extends EnchantmentModifier{

	@Override
	public String getName() {
		return "FireResistance";
	}
	
	@Override
	public void onArmorDamage(EntityPlayer player, DamageSource source, float amount)
	{
		if(source.isFireDamage() && takeEnergy(player, getNormalDamage(level)*10))
		{
			player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, level*10));
		}
	}

}
