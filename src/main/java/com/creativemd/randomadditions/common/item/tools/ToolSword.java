package com.creativemd.randomadditions.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;

public class ToolSword extends Tool{

	public ToolSword(RandomItem plate, String name) {
		super(plate, name, 1);
		setDurabilityFactor(2);
		setAction(EnumAction.block);
	}
	
	@Override
	public boolean isToolEffective(ItemStack stack, Block block)
	{
		return block == Blocks.web;
    }

	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + ItemTool.getMaterial(stack.getItemDamage()).getDamage(stack) + " Attack Damage";
	}
	
	@Override
	public void addRecipe(Object matieral, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"X", "X", "S", Character.valueOf('X'), matieral, Character.valueOf('S'), Items.stick
				}));
	}
}
