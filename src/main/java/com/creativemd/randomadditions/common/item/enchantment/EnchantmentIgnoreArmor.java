package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class EnchantmentIgnoreArmor extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Ignore-Armor";
	}
	
	@Override
	public float getDamageOnEntity(EntityPlayer player, EntityLivingBase entity, float damage)
	{
		float armorDamage = damage;
		int i = 25 - entity.getTotalArmorValue();
        float f1 = armorDamage * (float)i;
        armorDamage = f1 / 25.0F;
        float addedDamage = damage-armorDamage;
		return damage + addedDamage + addedDamage-(addedDamage * i)/25;
	}

}
