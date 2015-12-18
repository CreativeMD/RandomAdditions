package com.creativemd.randomadditions.common.systems.ic2;

import ic2.api.item.IC2Items;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.ic2.blocks.DefaultIC2Block;
import com.creativemd.randomadditions.common.systems.ic2.blocks.IC2toRAConverter;
import com.creativemd.randomadditions.common.systems.ic2.blocks.RAtoIC2Converter;

import cpw.mods.fml.common.registry.GameRegistry;

public class SubSystemIC2 extends SubBlockSystem<SubBlockIC2>{
	
	public static SubSystemIC2 instance;
	
	public SubSystemIC2() {
		super("RAIC2Converter");
		instance = this;
	}

	@Override
	public SubBlockIC2 getDefault() {
		return new DefaultIC2Block("DefaultBlock", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}

	@Override
	public void registerBlocks() {
		IC2toRAConverter ic2converter = (IC2toRAConverter) registerBlock(new IC2toRAConverter(this));
		RAtoIC2Converter raconverter = (RAtoIC2Converter) registerBlock(new RAtoIC2Converter(this));
		GameRegistry.addRecipe(new ShapedOreRecipe(ic2converter.getItemStack(), new Object[]
				{
				"IBI", "IRI", "IAI", Character.valueOf('I'), "ingotIron", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('A'), SubSystemBattery.instance.getBattery(2), Character.valueOf('B'), ic2.core.Ic2Items.batBox
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(raconverter.getItemStack(), new Object[]
				{
				"IAI", "IRI", "IBI", Character.valueOf('I'), "ingotIron", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('A'), SubSystemBattery.instance.getBattery(2), Character.valueOf('B'), ic2.core.Ic2Items.batBox
				}));
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}
	
	public static double converter = 1;
	
	public static float EUtoRA(double eu)
	{
		return (int) (eu/converter);
	}
	
	public static double RAtoEU(float ra)
	{
		return (double)ra*converter;
	}
	
}
