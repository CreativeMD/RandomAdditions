package com.creativemd.randomadditions.common.systems.assembly;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.assembly.blocks.DefaultBlockAssembly;
import com.creativemd.randomadditions.common.systems.assembly.blocks.SubBlockAssemblyMachine;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;

public class SubSystemAssembly extends SubBlockSystem<SubBlockAssembly> {
	
	public static SubSystemAssembly instance;
	
	public SubSystemAssembly() {
		super("assembly");
		instance = this;
	}

	@Override
	public SubBlockAssembly getDefault() {
		return new DefaultBlockAssembly(this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}
	
	@SideOnly(Side.CLIENT)
	public void initTextures(IIconRegister registry)
	{
		
	}
	
	public static SubBlockAssemblyMachine aMachine1;
	public static SubBlockAssemblyMachine aMachine2;
	public static SubBlockAssemblyMachine aMachine3;
	public static SubBlockAssemblyMachine aMachine4;
	public static SubBlockAssemblyMachine aMachine5;

	@Override
	public void registerBlocks() {
		aMachine1 = registerBlock(new SubBlockAssemblyMachine("machine1", this, 1, 0.5F, 1, Blocks.stone, Blocks.planks));
		aMachine2 = registerBlock(new SubBlockAssemblyMachine("machine2", this, 2, 0.75F, 2, Blocks.brick_block, Blocks.cobblestone));
		aMachine3 = registerBlock(new SubBlockAssemblyMachine("machine3", this, 4, 1F, 3, Blocks.iron_block, null));
		aMachine4 = registerBlock(new SubBlockAssemblyMachine("machine4", this, 8, 1.5F, 4, Blocks.gold_block, null));
		aMachine5 = registerBlock(new SubBlockAssemblyMachine("machine5", this, 10, 3F, 6, Blocks.emerald_block, Blocks.iron_block));
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
