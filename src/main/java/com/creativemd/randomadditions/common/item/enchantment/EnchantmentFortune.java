package com.creativemd.randomadditions.common.item.enchantment;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class EnchantmentFortune extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Fortune";
	}
	
	@Override
	public void onHarvestBlockPre(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops)
	{
		if(cantakeNormalEnergy(player, level))
		{
			drops.clear();
			drops.addAll(block.getDrops(world, x, y, z, meta, level));
		}
	}
	
	@Override
	public void onHarvestBlockPost(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops)
	{
		int size = drops.size();
		for(int i = 0; i < size; i++)
		{
			if(drops.get(i) != null)
			{
				int[] ores = OreDictionary.getOreIDs(drops.get(i));
				boolean ingot = false;
				for (int j = 0; j < ores.length; j++) {
					if(OreDictionary.getOreName(ores[j]).contains("ingot"))
						ingot = true;
				}
				
				if(ingot && cantakeNormalEnergy(player, level))
				{
					int amount = rand.nextInt(level)-1;
					if(amount < 0)
						amount = 0;
					for (int j = 0; j < amount; j++) {
						
						dropXP(world, x, y, z, block, FurnaceRecipes.smelting().func_151398_b(drops.get(i)));
						
						drops.add(drops.get(i));
					}
				}
			}
		}
	}
	
	@Override
	public ArrayList<EnchantmentModifier> getIncompatibleModifiers()
	{
		ArrayList<EnchantmentModifier> modifiers = new ArrayList<EnchantmentModifier>();
		modifiers.add(new EnchantmentSilkTouch());
		return modifiers;
	}

}
