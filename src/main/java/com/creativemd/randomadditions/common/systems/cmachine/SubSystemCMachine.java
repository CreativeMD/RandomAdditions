package com.creativemd.randomadditions.common.systems.cmachine;

import net.minecraft.block.material.Material;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.cmachine.blocks.DefaultCMachine;
import com.creativemd.randomadditions.common.systems.cmachine.blocks.SubBlockLightning;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;

public class SubSystemCMachine extends SubBlockSystem<SubBlockCMachine>{
	
	public static SubSystemCMachine instance;
	
	public SubSystemCMachine() {
		super("cmachine");
		instance = this;
	}

	@Override
	public SubBlockCMachine getDefault() {
		return new DefaultCMachine("Default", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.rock;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockLightning(this));
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
