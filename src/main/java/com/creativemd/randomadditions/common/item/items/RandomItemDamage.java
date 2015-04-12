package com.creativemd.randomadditions.common.item.items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RandomItemDamage extends RandomItem {
	
	public int maxDamage;
	
	public RandomItemDamage(String name, int maxDamage) {
		super(name);
		this.maxDamage = maxDamage;
	}
	
	public int getItemStackLimit(ItemStack stack)
    {
        return 1;
    }
	
	public static void checkNBT(ItemStack stack)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
	}
	
	public boolean damageItem(ItemStack stack, int amount)
	{
		int damage = getDamage(stack);
		if(amount <= damage && damage+amount <= maxDamage)
		{
			setDamage(stack, getDamage(stack)-amount);
			return true;
		}
		else if(damage+amount > maxDamage){
			setDamage(stack, maxDamage);
			return true;
		}else
			return false;
	}
	
	public static int getDamage(ItemStack stack)
	{
		checkNBT(stack);
		return stack.stackTagCompound.getInteger("damage");
	}
	
	public static void setDamage(ItemStack stack, int damage)
	{
		checkNBT(stack);
		stack.stackTagCompound.setInteger("damage", damage);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
    {
        return maxDamage;
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return getDamage(stack) < maxDamage;
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)(maxDamage - getDamage(stack)) / (double)maxDamage;
    }
}
