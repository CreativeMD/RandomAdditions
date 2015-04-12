package com.creativemd.randomadditions.common.item.items;

import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;

public class RandomItemWeight extends RandomItem {

	public RandomItemWeight(String name) {
		super(name);
	}
	
	@Override
	public void onRegister()
	{
		if(name.equals("cobblestoneWeight1"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(), new Object[]
					{
					"AXA", "XAX", "AXA", 'A', Blocks.cobblestone, 'X', Blocks.stone
					}));
		}else if(name.equals("cobblestoneWeight2")){
			GameRegistry.addShapelessRecipe(getItemStack(), cobblestoneWeight1.getItemStack(), cobblestoneWeight1.getItemStack());
		}else if(name.equals("cobblestoneWeight3")){
			GameRegistry.addShapelessRecipe(getItemStack(), cobblestoneWeight1.getItemStack(), cobblestoneWeight1.getItemStack(), cobblestoneWeight1.getItemStack());
		}
		
		if(name.equals("bronzeWeight1"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(), new Object[]
					{
					"AAA", "ABA", "AAA", Character.valueOf('A'), "ingotBronze", 'B', cobblestoneWeight3.getItemStack()
					}));
		}else if(name.equals("bronzeWeight2")){
			GameRegistry.addShapelessRecipe(getItemStack(), bronzeWeight1.getItemStack(), bronzeWeight1.getItemStack());
		}else if(name.equals("bronzeWeight3")){
			GameRegistry.addShapelessRecipe(getItemStack(), bronzeWeight1.getItemStack(), bronzeWeight1.getItemStack(), bronzeWeight1.getItemStack());
		}
		
		if(name.equals("obsidianWeight1"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(), new Object[]
					{
					"AAA", "ABA", "AAA", 'A', Blocks.obsidian, 'B', bronzeWeight3.getItemStack()
					}));
		}else if(name.equals("obsidianWeight2")){
			GameRegistry.addShapelessRecipe(getItemStack(), obsidianWeight1.getItemStack(), obsidianWeight1.getItemStack());
		}else if(name.equals("obsidianWeight3")){
			GameRegistry.addShapelessRecipe(getItemStack(), obsidianWeight1.getItemStack(), obsidianWeight1.getItemStack(), obsidianWeight1.getItemStack());
		}
	}
}
