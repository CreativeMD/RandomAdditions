package com.creativemd.randomadditions.client;

import com.creativemd.randomadditions.common.systems.ore.SubSystemOre;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RandomTab extends CreativeTabs{

	public RandomTab(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		return new ItemStack(SubSystemOre.instance.block).getItem();
	}

}
