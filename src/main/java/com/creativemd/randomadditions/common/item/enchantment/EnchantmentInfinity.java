package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.player.EntityPlayer;

import com.creativemd.randomadditions.common.entity.EntityRandomArrow;

public class EnchantmentInfinity extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Infinity";
	}
	
	@Override
	public void onShotArrow(EntityPlayer player, EntityRandomArrow arrow)
	{
		arrow.canBePickedUp = 2;
	}

}
