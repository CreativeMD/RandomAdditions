package com.creativemd.randomadditions.common.item.enchantment;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentSpeed extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Efficiency";
	}
	
	@Override
	public float getMiningSpeed(EntityPlayer player, boolean canHarvest, Block block, float speed)
	{
		if(canHarvest && hasNormalEngouhEnergy(player, level))
		{
			if(block == Blocks.obsidian)
				return speed+100/(4-level);
			return speed + level*2;
		}
		return speed;
	}
	
	@Override
	public float getMiningSpeed(float speed)
	{
		return speed + level*2;
	}
	
	@Override
	public void onHarvestBlock(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops)
	{
		if(block == Blocks.obsidian)
			cantakeNormalEnergy(player, level);
	}
	
}
