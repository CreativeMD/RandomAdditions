package com.creativemd.randomadditions.common.item.enchantment;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

public class EnchantmentAutoSmelt extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Auto-Smelt";
	}
	
	@Override
	public void onHarvestBlock(EntityPlayer player, World world, int x, int y, int z, int meta, Block block, ArrayList<ItemStack> drops)
	{
		boolean smelt = false;
		for (int i = 0; i < drops.size(); i++) {
			ItemStack drop = FurnaceRecipes.smelting().getSmeltingResult(drops.get(i));
			if(drop != null && (smelt || cantakeNormalEnergy(player, level)))
			{
				drops.set(i, drop.copy());
				dropXP(world, x, y, z, block, FurnaceRecipes.smelting().func_151398_b(drops.get(i)));
				smelt = true;
			}
		}
		if(smelt)
		{
			world.spawnParticle("flame", x, y, z, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", x, y, z, 0.0D, 0.0D, 0.1D);
			world.spawnParticle("flame", x, y, z, 0.0D, 0.1D, 0.0D);
			world.spawnParticle("flame", x, y, z, 0.1D, 0.0D, 0.0D);
			world.spawnParticle("flame", x, y, z, 0.1D, 0.0D, 0.1D);
		}
	}

}
