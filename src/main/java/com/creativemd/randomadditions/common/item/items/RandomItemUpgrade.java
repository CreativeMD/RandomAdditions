package com.creativemd.randomadditions.common.item.items;

import java.util.ArrayList;

import com.creativemd.randomadditions.common.upgrade.Upgrade;
import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

public class RandomItemUpgrade extends RandomItem{
	
	public Upgrade type;
	
	public RandomItemUpgrade(String name, Upgrade type) {
		super(name);
		this.type = type;
	}
	
	public ItemStack getItemStack(int level)
	{
		ItemStack stack = getItemStack();
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger("level", level);
		return stack;
	}
	
	public int getLevel(ItemStack stack)
	{
		if(stack == null)
			return -1;
		if(!stack.hasTagCompound())
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound.getInteger("level");
	}
	
	public float getModifier(ItemStack stack)
	{
		return type.getModifier(getLevel(stack));
		
	}
	
	public static boolean isUpgrade(ItemStack stack)
	{
		return RandomItem.isRandomItem(stack) && RandomItem.getRandomItem(stack) instanceof RandomItemUpgrade;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack)
	{
		try{
			return icons[getLevel(stack)];
		}catch(Exception e){
			return icons[0];
		}
	}
	
	@Override
	public ArrayList<ItemStack> getSubItems(Item item)
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		for (int i = 0; i < type.step.length; i++) {
			items.add(getItemStack(i));
		}
		return items;
	}
	
	@Override
	public void registerIcon(IIconRegister iconRegister)
	{
		icons = new IIcon[type.step.length];
		for (int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon(RandomAdditions.modid + ":" + name + (i+1));
		}
	}
	
	public String getUnlocalizedName(String name, ItemStack par1ItemStack)
	{
		return name + "." + this.name + (getLevel(par1ItemStack)+1);
	}
	
	public static float getUpgradeModifier(Upgrade type, ItemStack stack)
	{
		if(isUpgrade(stack) && ((RandomItemUpgrade) RandomItem.getRandomItem(stack)).type == type)
			return ((RandomItemUpgrade)RandomItem.getRandomItem(stack)).getModifier(stack);
		return 0;
	}

}
