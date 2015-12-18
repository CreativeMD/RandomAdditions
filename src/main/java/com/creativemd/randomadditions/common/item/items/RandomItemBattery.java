package com.creativemd.randomadditions.common.item.items;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RandomItemBattery extends RandomItemDamage implements RandomItemEnergy{

	public RandomItemBattery(String name, int maxDamage) {
		super(name, maxDamage);
	}
	
	public float getNeededPower(ItemStack stack)
	{
		return maxDamage - getDamage(stack);
	}
	
	public static float getPower(ItemStack stack)
	{
		if(getRandomItem(stack) instanceof RandomItemBattery)
		{
			return getDamage(stack);
		}
		return 0;
	}
	
	@Override
	public void onRegister()
	{
		if(name.contains("1"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(), new Object[]
					{
					"XAX", "ABA", "XAX", 'A', Items.redstone, Character.valueOf('X'), "ingotCopper", 'B', SubSystemBattery.instance.getBattery(1)
					}));
		}else if(name.contains("2"))
		{
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(), new Object[]
					{
					"XAX", "ABA", "XAX", 'A', Items.redstone, Character.valueOf('X'), "gemRuby", 'B', SubSystemBattery.instance.getBattery(4)
					}));
		}else{
			GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(), new Object[]
					{
					"XAX", "ABA", "XAX", 'A', Blocks.redstone_block, Character.valueOf('X'), "gemTourmaline", 'B', SubSystemBattery.instance.getBattery(6)
					}));
		}
	}
	
	@Override
	public ArrayList<ItemStack> getSubItems(Item item)
	{
		ArrayList<ItemStack> items = super.getSubItems(item);
		setDamage(items.get(0), maxDamage);
		
		return items;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add(getDamage(stack) +  "/" + maxDamage + " RA");
	}

	@Override
	public float onRecieveEnergy(ItemStack stack, float amount) {
		float power = getNeededPower(stack);
		if(power > amount)
			power = amount;
		damageItem(stack, -power);
		return power;
	}

	@Override
	public float onProduceEnergy(ItemStack stack, int max) {
		return 0;
	}
}
