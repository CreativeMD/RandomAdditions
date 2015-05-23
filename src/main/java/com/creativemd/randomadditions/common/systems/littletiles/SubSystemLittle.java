package com.creativemd.randomadditions.common.systems.littletiles;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.cable.SubSystemCable;
import com.creativemd.randomadditions.common.systems.littletiles.blocks.DefaultLittle;
import com.creativemd.randomadditions.common.systems.littletiles.blocks.SubBlockLittleCable;
import com.creativemd.randomadditions.common.systems.littletiles.littleblocks.LittleCable;

import cpw.mods.fml.common.registry.GameRegistry;

public class SubSystemLittle extends SubBlockSystem<SubBlockLittle> {
	
	public static SubSystemLittle instance;
	
	public SubSystemLittle() {
		super("Little");
		instance = this;
	}

	@Override
	public SubBlockLittle getDefault() {
		return new DefaultLittle("Default", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockLittleCable("LittleCable_0", this));
		registerBlock(new SubBlockLittleCable("LittleCable_1", this));
		registerBlock(new SubBlockLittleCable("LittleCable_2", this));
		registerBlock(new SubBlockLittleCable("LittleCable_3", this));
		
		for (int i = 0; i < 4; i++) {
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 4, i), new Object[]
					{
					"ICI", "CMC", "ICI", Character.valueOf('C'), "ingotCopper", Character.valueOf('I'), Items.redstone, Character.valueOf('M'), new ItemStack(SubSystemCable.instance.block, 1, i)
					}));
		}
		
		LittleTile.registerLittleTile(LittleCable.class, "LittleCable");
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
