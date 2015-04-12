package com.creativemd.randomadditions.common.systems.ore;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.core.RandomAdditions;

public class SubSystemOre extends SubBlockSystem<SubBlockOre> {
	
	public static SubSystemOre instance;
	
	public SubSystemOre() {
		super("Ore");
		instance = this;
	}

	@Override
	public SubBlockOre getDefault() {
		return new DefaultOre(this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.rock;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockOreCustomDrop("rubyOre", 2, RandomAdditions.ruby.getIngot(), this));
		registerBlock(new SubBlockOre("copperOre", 1, this));
		registerBlock(new SubBlockOre("tinOre", 1, this));
		registerBlock(new SubBlockOreCustomDrop("tourmalineOre", 2, RandomAdditions.tourmaline.getIngot(), this));
		
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(block, 1, 0), RandomAdditions.ruby.getIngot(), 1);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(block, 1, 1), RandomAdditions.copper.getIngot(), 1);
		FurnaceRecipes.smelting().func_151394_a(new ItemStack(block, 1, 2), RandomAdditions.tin.getIngot(), 1);
	}
	
	public int getMetaByName(String contains)
	{
		for (int i = 0; i < blocks.size(); i++) {
			if(blocks.get(i).name.toLowerCase().contains(contains.toLowerCase()))
				return i;
		}
		return -1;
	}

	@Override
	public String getHarvestTool(){
		return "pickaxe";
	}
	
	@Override
	public boolean areBlocksSolid()
	{
		return true;
	}
	
}
