package com.creativemd.randomadditions.common.systems.enchant;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.item.ItemRandomArmor;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.enchant.blocks.DefaultEnchantTable;
import com.creativemd.randomadditions.common.systems.enchant.blocks.SubBlockCombineTable;
import com.creativemd.randomadditions.common.systems.enchant.blocks.SubBlockRepairTable;
import com.creativemd.randomadditions.common.systems.enchant.blocks.SubBlockUpgradeTable;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubSystemEnchant extends SubBlockSystem<SubBlockEnchant>{
	
	public static SubSystemEnchant instance;
	
	public SubSystemEnchant() {
		super("Upgrade");
		instance = this;
	}

	@Override
	public SubBlockEnchant getDefault() {
		return new DefaultEnchantTable("DefaultTable", this);
	}

	@Override
	public Material getBlockMaterial() {
		return Material.wood;
	}

	@Override
	public void registerBlocks() {
		registerBlock(new SubBlockUpgradeTable("UpgradeTable", this));
		registerBlock(new SubBlockCombineTable("CombineTable", this));
		registerBlock(new SubBlockRepairTable("RepairTable", this));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 1, 0), new Object[]
				{
				"IBI", "TWT", "WGW", 'I', Items.iron_ingot, 'T', RandomAdditions.tourmaline.getIngot(), 'B', Items.book, 'W', Blocks.planks, 'G', Blocks.glass
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 1, 1), new Object[]
				{
				"III", "WTW", "WGW", 'I', Items.iron_ingot, 'T', RandomAdditions.tourmaline.getIngot(), 'W', Blocks.planks, 'G', Blocks.glass
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(block, 1, 2), new Object[]
				{
				"IDI", "WTW", "WGW", 'I', Items.iron_ingot, 'T', RandomAdditions.tourmaline.getIngot(), 'W', Blocks.planks, 'G', Blocks.quartz_block, 'D', Items.diamond
				}));
	}

	@Override
	public String getHarvestTool() {
		return "axe";
	}
	
	public static EnchantmentModifier getModifier(ItemStack stack)
	{
		return ItemTool.getMaterial(stack).getModifier(stack);
	}
	
	public static int getLevel(ItemStack stack)
	{
		return CraftMaterial.getLevel(ItemTool.getMaterial(stack), stack);
	}
	
	public static int getRequiredLevel(ItemStack stack)
	{
		return (getLevel(stack)+1)*10;
	}
	
	public static boolean canEnchantItem(ItemStack stack, EntityPlayer player)
	{
		if(stack == null)
			return false;
		if(getLevel(stack) < 3 && (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemRandomArmor))
		{
			int level = getRequiredLevel(stack);
			return player.experienceLevel >= level || player.capabilities.isCreativeMode;
		}
		return false;
	}

}
