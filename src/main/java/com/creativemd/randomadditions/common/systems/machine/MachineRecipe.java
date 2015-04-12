package com.creativemd.randomadditions.common.systems.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MachineRecipe{
	
	/**Can be String(Ore), Block, Item, ItemStack**/
	public ArrayList input;
	public ItemStack output;
	public int neededPower;
	
	public MachineRecipe(ArrayList input, ItemStack output, int power)
	{
		this.input = input;
		this.output = output;
		this.neededPower = power;
	}
	
	public MachineRecipe(ArrayList input, ItemStack output)
	{
		this(input, output, 250);
	}
	
	public MachineRecipe(Object input, ItemStack output, int power)
	{
		this(new ArrayList(), output, power);
		this.input.add(input);
	}
	
	public MachineRecipe(Object input, ItemStack output)
	{
		this(new ArrayList(), output);
		this.input.add(input);
	}
	
	public boolean isRecipeDoable(ArrayList<ItemStack> inputs)
	{
		for (int i = 0; i < input.size(); i++) {
			if(getItemStack(i, null) == null)
				return false;
		}
		return true;
	}
	
	public ItemStack getItemStack(int index, ArrayList<ItemStack> inputs)
	{
		if(input.get(index) instanceof ItemStack)
			return ((ItemStack) input.get(index)).copy();
		
		if(input.get(index) instanceof Block)
			return new ItemStack((Block) input.get(index));
		
		if(input.get(index) instanceof Item)
			return new ItemStack((Item) input.get(index));
		
		if(input.get(index) instanceof String)
		{
			ArrayList<ItemStack> stacks = OreDictionary.getOres((String) input.get(index));
			if(stacks.size() > 0)
				return stacks.get(0).copy();
		}
		
		if(input.get(index) instanceof CustomOreInput)
		{
			ArrayList<ItemStack> stacks = OreDictionary.getOres(((CustomOreInput)input.get(index)).ore);
			if(stacks.size() > 0)
			{
				ItemStack stack =  stacks.get(0).copy();
				stack.stackSize = ((CustomOreInput)input.get(index)).stackSize;
				stack.stackTagCompound = ((CustomOreInput)input.get(index)).nbt;
				return stack;
			}
		}
		return null;
	}
	
	public ArrayList<ItemStack> getItemStacks(ArrayList<ItemStack> inputs)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for (int i = 0; i < input.size(); i++) {
			stacks.add(getItemStack(i, inputs));
		}
		return stacks;
	}
	
	public Map<ArrayList<ItemStack>, ItemStack> getNEIRecipes()
	{
		Map<ArrayList<ItemStack>, ItemStack> result = new HashMap();
		if(isRecipeDoable(null))
		{
			result.put(getItemStacks(null), getOutput(getItemStacks(null)));
		}
		return result;
	}
	
	/**inputs may be null**/
	public boolean canDo(ArrayList<ItemStack> stacks)
	{
		for (int i = 0; i < input.size(); i++) {
			boolean found = false;
			for (int j = 0; j < stacks.size(); j++) {
				if(isObjectEqual(stacks.get(j), input.get(i)))
					found = true;
			}
			if(!found)
				return false;
		}
		return true;
	} 
	
	public boolean isObjectEqual(ItemStack stack, Object input)
	{
		return isObjectEqual(stack, input, false);
	}
	
	public boolean isObjectEqual(ItemStack stack, Object input, boolean ignoreStackSize)
	{
		if(stack == null)
			return false;
		if(input instanceof ItemStack)
		{
			ItemStack inputStack = (ItemStack) input;
			if(stack.getItem() == inputStack.getItem() && (stack.getItemDamage() == inputStack.getItemDamage() || inputStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) && (!inputStack.hasTagCompound() || inputStack.stackTagCompound.equals(stack.stackTagCompound)))
				if(inputStack.stackSize <= stack.stackSize || ignoreStackSize)
					return true;
		}
		
		if(input instanceof Block)
		{
			if(input == Block.getBlockFromItem(stack.getItem()))
				return true;
		}
		
		if(input instanceof Item)
		{
			if(input == stack.getItem())
				return true;
		}
		
		if(input instanceof String)
		{
			String ore = (String) input;
			int[] ores = OreDictionary.getOreIDs(stack);
			for (int h = 0; h < ores.length; h++) {
				if(OreDictionary.getOreName(ores[h]).equals(ore))
					return true;
			}
		}
		
		if(input instanceof CustomOreInput)
		{
			String ore = ((CustomOreInput) input).ore;
			if(((CustomOreInput) input).stackSize <= stack.stackSize || ignoreStackSize)
			{
				int[] ores = OreDictionary.getOreIDs(stack);
				for (int h = 0; h < ores.length; h++) {
					if(OreDictionary.getOreName(ores[h]).equals(ore))
					{
						if(((CustomOreInput) input).nbt != null)
							return stack.stackTagCompound != null && ((CustomOreInput) input).nbt.equals(stack.stackTagCompound);
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public int getStackSize(int index)
	{
		
		if(input.get(index) instanceof ItemStack)
			return ((ItemStack) input.get(index)).stackSize;
		
		if(input.get(index) instanceof Block)
			return 1;
		
		if(input.get(index) instanceof Item)
			return 1;
		
		if(input.get(index) instanceof String)
			return 1;
		
		if(input.get(index) instanceof CustomOreInput)
			return ((CustomOreInput)input.get(index)).stackSize;
		
		return 0;
	}
	
	public boolean doesContainItem(ItemStack stack)
	{
		for (int i = 0; i < input.size(); i++) {
			if(isObjectEqual(stack, input.get(i), true))
				return true;
		}
		return false;
	}
	
	public ItemStack getOutput(ArrayList<ItemStack> input)
	{
		return output;
	}
	
}
