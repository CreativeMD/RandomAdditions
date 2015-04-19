package com.creativemd.randomadditions.common.item;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCore extends Item{
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if(!isHot(stack))
			return super.getColorFromItemStack(stack, pass);
		int result = (int) (((Math.sin((double)System.nanoTime()/(double)100000000)+1)/2) * 255);
		String hex = String.format("%02x%02x%02x", 255, result, result);
		return (int) Long.parseLong(hex, 16);
    }
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		if(isHot(stack) && entity instanceof EntityPlayer)
			entity.attackEntityFrom(DamageSource.inFire, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		if(isHot(stack))
		{
			list.add("Heated");
			list.add("Take care! It damages you!");
		}
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
    {
		ItemStack stack = entityItem.getEntityItem();
		if(isHot(stack) && entityItem.isInsideOfMaterial(Material.water))
		{
			entityItem.playSound("random.fizz", 0.4F, 2.0F + entityItem.worldObj.rand.nextFloat() * 0.4F);
			for(int zahl = 0; zahl < 100; zahl++)
				entityItem.worldObj.spawnParticle("smoke", entityItem.posX, entityItem.posY, entityItem.posZ, (entityItem.worldObj.rand.nextFloat()*0.1)-0.05, 0.2*entityItem.worldObj.rand.nextDouble(), (entityItem.worldObj.rand.nextFloat()*0.1)-0.05);
			if(!entityItem.worldObj.isRemote)
			{
				removeHeat(stack);
			}
		}
        return false;
    }
	
	public static ItemStack removeHeat(ItemStack stack)
	{
		if(isHot(stack))
		{
			stack.stackTagCompound.removeTag("heat");
			if(stack.stackTagCompound.func_150296_c().size() == 0)
				stack.stackTagCompound = null;
		}
		return stack;
	}
	
	public static boolean isHot(ItemStack stack)
	{
		if(stack.stackTagCompound == null)
			return false;
		else
			return stack.stackTagCompound.getBoolean("heat");
	}
	
	public static ItemStack makeHot(ItemStack stack)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setBoolean("heat", true);
		return stack;
	}

	public static NBTTagCompound getHeatNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setBoolean("heat", true);
		return nbt;
	}
	
}
