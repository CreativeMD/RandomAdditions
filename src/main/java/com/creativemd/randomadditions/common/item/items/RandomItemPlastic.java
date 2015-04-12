package com.creativemd.randomadditions.common.item.items;

import ic2.api.item.IC2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;

public class RandomItemPlastic extends RandomItem {

	public RandomItemPlastic(String name) {
		super(name);
	}
	
	@Override
	public void onRegister()
	{
		if(name.equals("plastic"))
		{
			ItemStack output = getItemStack();
			output.stackSize = 4;
			FurnaceRecipes.smelting().func_151396_a(Items.slime_ball, output, 1);
		}else if(name.equals("compressedplastic")){
			GameRegistry.addRecipe(getItemStack(), new Object[]
					{
					"AA", "AA", 'A', plastic.getItemStack()
					});
			ItemStack result = plastic.getItemStack();
			result.stackSize = 2;
			if(Loader.isModLoaded("IC2"))
				FurnaceRecipes.smelting().func_151394_a(ic2.core.Ic2Items.rubber, result, 1);
		}else if(name.equals("compressedplastic2")){
			GameRegistry.addRecipe(getItemStack(), new Object[]
					{
					"AA", "AA", 'A', compressedplastic.getItemStack()
					});
		}else{
			GameRegistry.addRecipe(getItemStack(), new Object[]
					{
					"AAA", "BBB", "AAA", 'A', compressedplastic2.getItemStack(), 'B', Blocks.nether_brick
					});
		}
	}
}
