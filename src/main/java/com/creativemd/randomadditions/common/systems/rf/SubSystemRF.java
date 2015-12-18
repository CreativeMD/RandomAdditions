package com.creativemd.randomadditions.common.systems.rf;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.rf.blocks.DefaultRFBlock;

import cpw.mods.fml.common.registry.GameRegistry;

public class SubSystemRF extends SubBlockSystem<SubBlockRF>{

	public SubSystemRF() {
		super("RFConverters");
	} 

	@Override
	public SubBlockRF getDefault() {
		return new DefaultRFBlock(this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}

	@Override
	public void registerBlocks() {
		SubBlockRF ra = registerBlock(new SubBlockRF("RAtoRF", this, true));
		SubBlockRF rf = registerBlock(new SubBlockRF("RFtoRA", this, false));
		Block block = (Block) Block.blockRegistry.getObject("ThermalExpansion:Cell");
		if(block == null && block instanceof BlockAir)
			block = Blocks.redstone_block;
		GameRegistry.addRecipe(new ShapedOreRecipe(rf.getItemStack(), new Object[]
				{
				"IBI", "IRI", "IAI", Character.valueOf('I'), "ingotIron", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('A'), SubSystemBattery.instance.getBattery(2), Character.valueOf('B'), new ItemStack(block, 1, 1)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(ra.getItemStack(), new Object[]
				{
				"IAI", "IRI", "IBI", Character.valueOf('I'), "ingotIron", Character.valueOf('R'), Blocks.redstone_block, Character.valueOf('A'), SubSystemBattery.instance.getBattery(2), Character.valueOf('B'), new ItemStack(block, 1, 1)
				}));
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}
	
	public static double converter = 1;
	
	public static float RFtoRA(double rf)
	{
		return (int) (rf/converter);
	}
	
	public static double RAtoRF(float ra)
	{
		return (double)ra*converter;
	}
}
