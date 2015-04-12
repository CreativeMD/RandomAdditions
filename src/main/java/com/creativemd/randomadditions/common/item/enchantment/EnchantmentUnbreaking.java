package com.creativemd.randomadditions.common.item.enchantment;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnchantmentUnbreaking extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Unbreaking";
	}
	
	@Override
	public int onToolTakenDamage(EntityPlayer player, ItemStack stack, int amount)
	{
		if(cantakeNormalEnergy(player, level))
		{
			rand.nextInt(4-level);
			if(level == 0)
				return amount;
		}
		return amount;
	}
	
}
