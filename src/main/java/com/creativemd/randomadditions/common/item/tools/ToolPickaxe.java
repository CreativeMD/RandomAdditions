package com.creativemd.randomadditions.common.item.tools;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.registry.GameRegistry;

public class ToolPickaxe extends Tool{

	public ToolPickaxe(RandomItem plate, String name) {
		super(plate, name, 0.5F);
	}
	
	@Override
	public boolean isToolEffective(ItemStack stack, Block block)
	{
		int level = ItemTool.getMaterial(stack.getItemDamage()).harvestLevel;
		return block == Blocks.obsidian ? level == 3 : (block != Blocks.diamond_block && block != Blocks.diamond_ore ? (block != Blocks.emerald_ore && block != Blocks.emerald_block ? (block != Blocks.gold_block && block != Blocks.gold_ore ? (block != Blocks.iron_block && block != Blocks.iron_ore ? (block != Blocks.lapis_block && block != Blocks.lapis_ore ? (block != Blocks.redstone_ore && block != Blocks.lit_redstone_ore ? (block.getMaterial() == Material.rock ? true : (block.getMaterial() == Material.iron ? true : block.getMaterial() == Material.anvil)) : level >= 2) : level >= 1) : level >= 1) : level >= 2) : level >= 2) : level >= 2);
    }

	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + ItemTool.getMaterial(stack.getItemDamage()).getSpeed(stack) + " Break Speed";
	}
	
	@Override
	public void addRecipe(Object matieral, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"XXX", "ASA", "ASA", Character.valueOf('X'), matieral, Character.valueOf('S'), Items.stick
				}));
	}
}
