package com.creativemd.randomadditions.common.item.tools;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolAxe extends Tool{

	public ToolAxe(RandomItem plate, String name) {
		super(plate, name, 0.75F);
	}	
	
	@Override
	public boolean isToolEffective(ItemStack stack, Block block)
	{
		return block.getMaterial() == Material.wood || block.getMaterial() == Material.plants || block.getMaterial() == Material.vine;
	}

	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + ItemTool.getMaterial(stack.getItemDamage()).getSpeed(stack) + " Break Speed";
	}

	@Override
	public void addRecipe(Object matieral, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"XX", "XS", "AS", Character.valueOf('X'), matieral, Character.valueOf('S'), Items.stick
				}));
	}

}
