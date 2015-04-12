package com.creativemd.randomadditions.common.item.enchantment;

import java.util.ArrayList;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.core.CraftMaterial;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;

public class EnchantmentWater extends EnchantmentModifier{

	@Override
	public String getName() {
		return "AlltimeWorker";
	}
	
	@Override
	public float getMiningSpeedLate(EntityPlayer player, ItemStack stack, boolean canHarvest, Block block, float speed)
	{
		if(hasNormalEngouhEnergy(player, level) && canHarvest)
		{
			speed = ItemTool.getMaterial(stack).getSpeed(stack);
			ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
	    	for (int i = 0; i < modifiers.size(); i++) {
	    		speed = (int) modifiers.get(i).getMiningSpeed(player, true, block, speed);
			}
	    	return speed;
		}
		return speed;
	}
	
	@Override
	public void onHarvestBlock(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops)
	{
		if(player.isInWater() || !player.onGround)
		{
			cantakeNormalEnergy(player, level);
		}
	}
}
