package com.creativemd.randomadditions.common.systems.battery;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.cable.SubSystemCable;
import com.creativemd.randomadditions.common.systems.producer.SubSystemProducer;

import cpw.mods.fml.common.registry.GameRegistry;

public class SubSystemBattery extends SubBlockSystem<SubBlockBattery>{
	
	public static SubSystemBattery instance;
	
	public SubSystemBattery() {
		super("Battery");
		instance = this;
	}

	@Override
	public SubBlockBattery getDefault() {
		return new SubBlockBattery(0, 0, 0, Blocks.air, "DefaultBattery", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.iron;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockBattery(5000, 60, 40, Blocks.planks, "Battery0", this));
		registerBlock(new SubBlockBattery(10000, 120, 100, Blocks.cobblestone, "Battery1", this));
		registerBlock(new SubBlockBattery(25000, 150, 140, Blocks.stonebrick, "Battery2", this));
		registerBlock(new SubBlockBattery(50000, 200, 200, Blocks.brick_block, "Battery3", this));
		registerBlock(new SubBlockBattery(100000, 450, 400, Blocks.iron_block, "Battery4", this));
		registerBlock(new SubBlockBattery(500000, 1000, 900, Blocks.gold_block, "Battery5", this));
		registerBlock(new SubBlockBattery(1000000, 5000, 5000, Blocks.diamond_block, "Battery6", this));
		registerBlock(new SubBlockBattery(10000000, 10000, 10000, Blocks.emerald_block, "Battery7", this));
	}
	
	@Override
	public void onRegister()
	{
		super.onRegister();
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(0), new Object[]
				{
				"CRC", "ABA", "CRC", 'A', SubSystemCable.instance.getItemStack(0), 'B', RandomItem.cobblestoneWeight1.getItemStack(), 'C', Blocks.planks, 'R', Items.redstone
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(1), new Object[]
				{
				"CAC", "RBR", "CRC", 'A', getBattery(0), 'B', RandomItem.cobblestoneWeight2.getItemStack(), 'C', Blocks.cobblestone, 'R', Items.redstone
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(2), new Object[]
				{
				"CAC", "OBO", "CRC", 'A', getBattery(1), 'B', RandomItem.cobblestoneWeight3.getItemStack(), 'C', Blocks.stonebrick, 'R', Items.redstone, 'O', SubSystemCable.instance.getItemStack(1)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(3), new Object[]
				{
				"CAC", "OBO", "CRC", 'A', getBattery(2), 'B', RandomItem.bronzeWeight1.getItemStack(), 'C', Blocks.brick_block, 'R', Items.redstone, 'O', SubSystemCable.instance.getItemStack(1)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(4), new Object[]
				{
				"CAC", "OBO", "CRC", 'A', getBattery(3), 'B', RandomItem.bronzeWeight2.getItemStack(), Character.valueOf('C'), "ingotIron", 'R', Items.redstone, 'O', SubSystemCable.instance.getItemStack(2)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(5), new Object[]
				{
				"CAC", "OBO", "CRC", 'A', getBattery(4), 'B', RandomItem.bronzeWeight3.getItemStack(), Character.valueOf('C'), "ingotGold", 'R', Items.redstone, 'O', SubSystemCable.instance.getItemStack(2)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(6), new Object[]
				{
				"CAC", "OBO", "CRC", 'A', getBattery(5), 'B', RandomItem.obsidianWeight1.getItemStack(), Character.valueOf('C'), "blockDiamond", 'R', Blocks.redstone_block, 'O', SubSystemCable.instance.getItemStack(3)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getBattery(7), new Object[]
				{
				"CAC", "OBO", "CRC", 'A', getBattery(6), 'B', RandomItem.obsidianWeight3.getItemStack(), Character.valueOf('C'), "blockEmerald", 'R', Blocks.redstone_block, 'O', SubSystemCable.instance.getItemStack(3)
				}));
	}
	
	@Override
	public boolean areBlocksSolid()
	{
		return true;
	}
	
	public ItemStack getBattery(int level)
	{
		return new ItemStack(block, 1, level);
	}
	
	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
