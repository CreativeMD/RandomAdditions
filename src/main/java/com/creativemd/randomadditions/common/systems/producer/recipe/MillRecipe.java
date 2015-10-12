package com.creativemd.randomadditions.common.systems.producer.recipe;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemMillarm;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class MillRecipe extends ShapelessOreRecipe{

	public MillRecipe(ItemStack result, Object... recipe) {
		super(result, recipe);
	}
	
	@Override
    public ItemStack getCraftingResult(InventoryCrafting var1){
		int length = 0;
		int arms = 0;
		boolean error = false;
		for (int i = 0; i < var1.getSizeInventory(); i++) {
			ItemStack stack = var1.getStackInSlot(i);
			if(stack != null && RandomItem.millarm.isItem(stack))
			{
				if(length == 0)
					length = RandomItemMillarm.getLength(stack);
				if(RandomItemMillarm.getLength(stack) != length)
					error = true; 
				arms++;
			}
		}
		ItemStack output = getRecipeOutput().copy();
		output.stackSize = 1;
		output.stackTagCompound = new NBTTagCompound();
		if(length < 1 || length > 10)
			output.stackTagCompound.setInteger("length", 10);
		else
			output.stackTagCompound.setInteger("length", length);
		output.stackTagCompound.setInteger("arms", arms);
		if(error)
		{
			output.stackTagCompound.setInteger("length", 0);
			output.stackTagCompound.setInteger("arms", 0);
		}
		return output;
    }
	
	public boolean denyHandCraftAccess()
	{
		return true;
	}
	
}
