package com.creativemd.randomadditions.common.item.items;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;

public class RandomItemDust extends RandomItem{
	
	public String OreName;
	
	public RandomItemDust(String OreName) {
		super(OreName + "Dust");
		OreName = OreName.substring(0, 1).toUpperCase() + OreName.substring(1);
		this.OreName = OreName;
	}
	
	@Override
	public void onRegister()
	{
		OreDictionary.registerOre("dust" + OreName, getItemStack());
		ArrayList<ItemStack> stacks = OreDictionary.getOres("ingot" + OreName);
		if(stacks.size() == 0)
			stacks = OreDictionary.getOres("gem" + OreName);
		if(stacks.size() > 0)
		{
			stacks.get(0).stackSize = 1;
			FurnaceRecipes.smelting().func_151394_a(getItemStack(), stacks.get(0), 1);
		}
		else
			System.out.println("Found no ingot for " + OreName);
	}
}
