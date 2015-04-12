package com.creativemd.randomadditions.common.systems.ore;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;

public class SubBlockOreCustomDrop extends SubBlockOre{
	
	public ItemStack drop;
	
	public SubBlockOreCustomDrop(String name, int level, ItemStack drop, SubBlockSystem system) {
		super(name, level, system);
		this.drop = drop;
	}
	
	@Override
	public ArrayList<ItemStack> getDrop(IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		int j = 1;
		if(fortune > 0)
			j = rand.nextInt(fortune) + 1;
		for (int i = 0; i < j; i++) {
			stacks.add(drop.copy());
		}
		return stacks;
	}
	
	public static Random rand = new Random();
	
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
    {
		return MathHelper.getRandomIntegerInRange(rand, 3, 7+fortune);
    }
	
	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
		return true;
    }

}
