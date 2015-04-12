package com.creativemd.randomadditions.common.item.enchantment;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantmentSilkTouch extends EnchantmentModifier{

	@Override
	public String getName() {
		return "SilkTouch";
	}
	
	@Override
	public ArrayList<EnchantmentModifier> getIncompatibleModifiers()
	{
		ArrayList<EnchantmentModifier> modifiers = new ArrayList<EnchantmentModifier>();
		modifiers.add(new EnchantmentFortune());
		return modifiers;
	}
	
	@Override
	public void onHarvestBlockPre(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops)
	{
		if(cantakeNormalEnergy(player, level))
		{
			if(block.canSilkHarvest(world, player, x, y, z, meta))
			{
				drops.clear();
				drops.add(new ItemStack(block, 1, meta));
			}
		}
	}
}
