package com.creativemd.randomadditions.common.systems.cmachine;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.cmachine.blocks.DefaultCMachine;
import com.creativemd.randomadditions.common.systems.cmachine.blocks.SubBlockLightning;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;

import cpw.mods.fml.common.registry.GameRegistry;

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
		SubBlock block = registerBlock(new SubBlockLightning(this));
		GameRegistry.addRecipe(new ShapedOreRecipe(RandomItem.coil.getItemStack(3), new Object[]
				{
				"CCC", "III", "CCC", Character.valueOf('C'), "ingotCopper", Character.valueOf('I'), "ingotIron"
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(block.getItemStack(), new Object[]
				{
				"AIA", "CBC", "AIA", Character.valueOf('C'), "ingotCopper", 'B', SubSystemBattery.instance.getBattery(1), Character.valueOf('I'), "ingotIron", 'A', RandomItem.coil.getItemStack()
				}));
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
