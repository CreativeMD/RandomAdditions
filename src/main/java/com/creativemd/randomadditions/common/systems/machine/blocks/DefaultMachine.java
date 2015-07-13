package com.creativemd.randomadditions.common.systems.machine.blocks;

import net.minecraft.item.ItemStack;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;

public class DefaultMachine extends SubBlockMachine{

	public DefaultMachine(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public int getNumberOfInputs() {
		return 0;
	}

	@Override
	public void registerRecipes() {
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return null;
	}

	@Override
	public void addRecipe(ItemStack battery, ItemStack output) {
		
	}

	@Override
	public void renderProgressField(double percent) {
		
	}

	@Override
	public int getPlayTime() {
		return 0;
	}

}
