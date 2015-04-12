package com.creativemd.randomadditions.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;

public class ToolShovel extends Tool{

	public ToolShovel(RandomItem plate, String name) {
		super(plate, name, 0.25F);
	}
	
	public boolean isToolEffective(ItemStack stack, Block block)
	{
		return block.getMaterial() == Material.ground || block.getMaterial() == Material.grass || block.getMaterial() == Material.snow || block.getMaterial() == Material.sand  || block.getMaterial() == Material.craftedSnow || block.getMaterial() == Material.snow || block.getMaterial() == Material.clay;
    }

	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + ItemTool.getMaterial(stack.getItemDamage()).getSpeed(stack) + " Break Speed";
	}
	
	@Override
	public void addRecipe(Object matieral, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"X", "S", "S", Character.valueOf('X'), matieral, Character.valueOf('S'), Items.stick
				}));
	}
}
