package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;

public class EnchantmentLooting extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Looting";
	}
	
	@Override
	public void onEntityDeath(EntityPlayer player, EntityLivingBase entity, boolean recentlyHit)
	{
		ItemStack stack = player.getHeldItem();
		if(stack != null && stack.stackTagCompound != null && stack.stackTagCompound.hasKey("ench"))
			stack.stackTagCompound.removeTag("ench");
	}
	
	@Override
	public void onEntityBeforeDeath(EntityPlayer player, EntityLivingBase entity)
	{
		ItemStack stack = player.getHeldItem();
		if(cantakeNormalEnergy(player, level))
		{
			stack.addEnchantment(Enchantment.looting, level);
		}
	}
}
