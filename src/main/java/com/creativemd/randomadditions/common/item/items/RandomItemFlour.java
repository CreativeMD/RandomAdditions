package com.creativemd.randomadditions.common.item.items;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.registry.GameRegistry;

public class RandomItemFlour extends RandomItem {

	public RandomItemFlour(String name) {
		super(name);
	}
	
	@Override
	public void onRegister()
	{
		GameRegistry.addRecipe(new ItemStack(Items.cake), new Object[]
				{
				"MMM", "SES", "FFF", 'S', Items.sugar, 'M', Items.milk_bucket, 'E', Items.egg, 'F', getItemStack()
				});
		FurnaceRecipes.smelting().func_151394_a(getItemStack(), new ItemStack(Items.bread), 0.5F);
	}
}
