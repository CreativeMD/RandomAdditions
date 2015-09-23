package com.creativemd.randomadditions.common.systems.producer;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemMillarm;
import com.creativemd.randomadditions.common.item.items.RandomItemWatermillarm;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.producer.blocks.CreativeProducer;
import com.creativemd.randomadditions.common.systems.producer.blocks.HeatGenerator;
import com.creativemd.randomadditions.common.systems.producer.blocks.Mill;
import com.creativemd.randomadditions.common.systems.producer.blocks.Watermill;
import com.creativemd.randomadditions.common.systems.producer.recipe.MillArmRecipe;
import com.creativemd.randomadditions.common.systems.producer.recipe.MillRecipe;
import com.creativemd.randomadditions.common.systems.producer.recipe.WatermillArmRecipe;
import com.creativemd.randomadditions.common.systems.producer.recipe.WatermillRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

public class SubSystemProducer extends SubBlockSystem<SubBlockProducer>{
	
	public static SubSystemProducer instance;
	
	public SubSystemProducer() {
		super("Producer");
		instance = this;
	}

	@Override
	public SubBlockProducer getDefault() {
		return new CreativeProducer(this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.wood;
	}
	
	public HeatGenerator heatGenerator;
	
	@Override
	public void registerBlocks() {
		registerBlock(defaultSubBlock);
		registerBlock(new Mill(this));
		registerBlock(new Watermill(this));
		heatGenerator = (HeatGenerator) registerBlock(new HeatGenerator("HeatGenerator", this));
		
		GameRegistry.addRecipe(heatGenerator.getItemStack(), new Object[]
				{
				"GPG", "CIC", "BBB", 'P', SubSystemBattery.instance.getBattery(1), 'C', Blocks.coal_block, 'I', Blocks.iron_block, 'B', Blocks.brick_block, 'G', Blocks.glass
				});
	}
	
	@Override
	public void onRegister()
	{
		super.onRegister();
		ItemStack output = RandomItem.millarm.getItemStack();
		RandomItemMillarm.setLength(output, 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"A", "A", "W", 'A', Blocks.wool, 'W', Blocks.planks
				}));
		
		output = RandomItem.watermillarm.getItemStack();
		RandomItemWatermillarm.setLength(output, 1);
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"A", "A", "W", 'A', Blocks.planks, 'W', Blocks.log
				}));
		for (int i = 2; i <= 9; i++) {
			ItemStack[] arms = new ItemStack[i];
			Arrays.fill(arms, RandomItem.millarm.getItemStack());
			GameRegistry.addRecipe(new MillArmRecipe(RandomItem.millarm.getItemStack(), (Object[])arms));
			
			ItemStack[] waterarms = new ItemStack[i];
			Arrays.fill(waterarms, RandomItem.watermillarm.getItemStack());
			GameRegistry.addRecipe(new WatermillArmRecipe(RandomItem.watermillarm.getItemStack(), (Object[])waterarms));
			if(i != 9)
			{
				arms = new ItemStack[i+1];
				Arrays.fill(arms, RandomItem.millarm.getItemStack());
				arms[arms.length-1] = SubSystemBattery.instance.getBattery(0);
				GameRegistry.addRecipe(new MillRecipe(getItemStack(1), (Object[])arms));
				
				waterarms = new ItemStack[i+1];
				Arrays.fill(waterarms, RandomItem.watermillarm.getItemStack());
				waterarms[waterarms.length-1] = SubSystemBattery.instance.getBattery(0);
				GameRegistry.addRecipe(new WatermillRecipe(getItemStack(2), (Object[])waterarms));
			}
		}
	}

	@Override
	public String getHarvestTool() {
		return "pickaxe";
	}

}
