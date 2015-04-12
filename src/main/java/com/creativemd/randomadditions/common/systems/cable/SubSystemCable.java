package com.creativemd.randomadditions.common.systems.cable;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.cable.blocks.DefaultCable;
import com.creativemd.randomadditions.common.systems.cable.blocks.SubBlockCable;
import com.creativemd.randomadditions.common.systems.cable.blocks.SubBlockWire;

import cpw.mods.fml.common.registry.GameRegistry;

public class SubSystemCable extends SubBlockSystem<SubBlockCableBase>{
	
	public static SubSystemCable instance;
	
	public SubSystemCable() {
		super("Cable");
		instance = this;
	}

	@Override
	public SubBlockCableBase getDefault() {
		return new DefaultCable(this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockCable(60, Blocks.planks, 1, Blocks.cobblestone, "Cable0", this));
		registerBlock(new SubBlockCable(500, Blocks.cobblestone, 5, Blocks.coal_block, "Cable1", this));
		registerBlock(new SubBlockCable(5000, Blocks.iron_block, 50, Blocks.lapis_block, "Cable2", this));
		registerBlock(new SubBlockCable(100000, Blocks.obsidian, 250, Blocks.nether_brick, "Cable3", this));
		registerBlock(new SubBlockWire("Wire", this));
	}
	
	@Override
	public void onRegister()
	{
		super.onRegister();
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 16, 0), new Object[]
				{
				"XAX", "BBB", "XAX", 'A', Items.redstone, 'X', Blocks.cobblestone, 'B', Blocks.planks
				}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 8, 1), new Object[]
				{
				"XAX", "BBB", "XAX", 'A', Items.redstone, 'X', RandomItem.plastic.getItemStack(), 'B', Blocks.cobblestone
				}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 8, 2), new Object[]
				{
				"XAX", "BBB", "XAX", 'A', Items.redstone, 'X', RandomItem.compressedplastic.getItemStack(), Character.valueOf('B'), "ingotIron"
				}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 8, 3), new Object[]
				{
				"XAX", "BBB", "XAX", 'A', Items.redstone, 'X', RandomItem.compressedplastic2.getItemStack(), 'B', Blocks.obsidian
				}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 8, 4), new Object[]
				{
				"XAX", "ABA", "XAX", 'A', Items.iron_ingot, 'X', Items.redstone, 'B', getItemStack(0)
				}));
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
