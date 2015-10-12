package com.creativemd.randomadditions.common.systems.producer.recipe;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemMillarm;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class MillArmRecipe extends ShapelessOreRecipe{

	public MillArmRecipe(ItemStack result, Object... recipe) {
		super(result, recipe);
	}
	
	@Override
    public ItemStack getCraftingResult(InventoryCrafting var1){
		int length = 0;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(stack != null && RandomItem.millarm.isItem(stack))
				length += RandomItemMillarm.getLength(stack);
		}
		ItemStack output = getRecipeOutput().copy();
		output.stackTagCompound = new NBTTagCompound();
		if(length < 1 || length > 10)
			output.stackTagCompound.setInteger("length", 10);
		else
			output.stackTagCompound.setInteger("length", length);
		return output;
    }
	
	public boolean denyHandCraftAccess()
	{
		return true;
	}

}
