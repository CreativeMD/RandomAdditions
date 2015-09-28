package com.creativemd.randomadditions.common.systems.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.creativemd.creativecore.common.utils.stack.StackInfoItem;
import com.creativemd.randomadditions.common.item.ItemCore;
import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public class MachineRecipeCool extends MachineRecipe{
	
	public MachineRecipeCool(Object input, int power) {
		super(input, null, power);
	}
	
	@Override
	public ItemStack getItemStack(int index, ArrayList<ItemStack> inputs)
	{
		ItemStack result = super.getItemStack(index, inputs);
		if(inputs != null && inputs.size() == 1)
		{
			return inputs.get(0);
		}
		return result;
	}
	
	@Override
	public ItemStack getOutput(ArrayList<ItemStack> input)
	{
		if(input.size() > 0 && input.get(0) != null)
		{
			ItemStack output = ItemCore.removeHeat(input.get(0).copy());
			output.stackSize = 1;
			return output;
		}
		return super.getOutput(input);
	}
	
	@Override
	public Map<ArrayList<ItemStack>, ItemStack> getNEIRecipes()
	{
		Map<ArrayList<ItemStack>, ItemStack> result = new HashMap();
		if(isRecipeDoable(null))
		{
			ArrayList<ItemStack> stacks = new ArrayList<ItemStack>(); 
			if(input.get(0) instanceof StackInfoItem)
				((StackInfoItem)input.get(0)).item.getSubItems(((StackInfoItem)input.get(0)).item, RandomAdditions.tab, stacks);
			
			for (int i = 0; i < stacks.size(); i++) {
				ArrayList<ItemStack> input = new ArrayList<ItemStack>();
				input.add(ItemCore.makeHot(stacks.get(i).copy()));
				result.put(input, getOutput(input));
			}
		}
		return result;
	}
}
