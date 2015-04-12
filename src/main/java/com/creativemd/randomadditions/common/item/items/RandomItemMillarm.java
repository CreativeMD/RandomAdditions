package com.creativemd.randomadditions.common.item.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RandomItemMillarm extends RandomItem {

	public RandomItemMillarm(String name) {
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add("Length: " + getLength(stack));
	}
	
	public static int getLength(ItemStack stack)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		int length = stack.stackTagCompound.getInteger("length");
		if(length == 0)
			stack.stackTagCompound.setInteger("length", 1);
		return length;
	}
	
	public static void setLength(ItemStack stack, int length)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setInteger("length", length);
	}
	
}
