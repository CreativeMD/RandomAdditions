package com.creativemd.randomadditions.common.systems.machine;

import net.minecraft.block.material.Material;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.machine.blocks.Anvil;
import com.creativemd.randomadditions.common.systems.machine.blocks.CoolingPool;
import com.creativemd.randomadditions.common.systems.machine.blocks.Crusher;
import com.creativemd.randomadditions.common.systems.machine.blocks.DefaultMachine;
import com.creativemd.randomadditions.common.systems.machine.blocks.Furnace;
import com.creativemd.randomadditions.common.systems.machine.blocks.Sawing;
import com.creativemd.randomadditions.common.systems.ore.SubSystemOre;
import com.creativemd.randomadditions.support.nei.NEILoading;

import cpw.mods.fml.common.Loader;

public class SubSystemMachine extends SubBlockSystem<SubBlockMachine>{
	
	public static SubSystemMachine instance;
	
	public SubSystemMachine() {
		super("Machine");
		SubSystemMachine.instance = this;
	}

	@Override
	public SubBlockMachine getDefault() {
		return new DefaultMachine("Default", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}
	
	public Crusher crusher;
	public Furnace furnace;
	public Anvil anvil;
	public CoolingPool pool;
	public Sawing saw;

	@Override
	public void registerBlocks() {
		crusher = (Crusher) registerBlock(new Crusher(this));
		furnace = (Furnace) registerBlock(new Furnace(this));
		anvil = (Anvil) registerBlock(new Anvil(this));
		pool = (CoolingPool) registerBlock(new CoolingPool(this));
		saw = (Sawing) registerBlock(new Sawing(this));
		
		SubSystemMachine.instance = this;
		
		if(Loader.isModLoaded("NotEnoughItems"))
			NEILoading.loadNEI();
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
