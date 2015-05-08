package com.creativemd.randomadditions.common.item.tools;

import net.minecraft.item.ItemStack;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;

public class ToolArmor extends Tool{
	
	public int armorType;
	
	public ToolArmor(RandomItem plate, String name, int armorType, int cost) {
		super(plate, name, 1);
		this.armorType = armorType;
		external = true;
		this.cost = cost;
	}



	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + ItemTool.getMaterial(stack).protection[armorType] + " Protection";
	}

	@Override
	public void addRecipe(Object matieral, ItemStack output) {
	}
}
