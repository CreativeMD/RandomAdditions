package com.creativemd.randomadditions.core;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.tools.Tool;
import com.creativemd.randomadditions.common.systems.ore.SubSystemOre;

public class CraftMaterial {
	
	public String name;
	public String displayName;
	public int generateOre; /**-1; no ore generated; 0: no ore added; 1: generated like diamond; 2: generated like gold; 3: generated like iron **/
	public Object ingot; /** == null create new ingot; != null use itemstack/Ore**/
	public boolean dropItem = false;
	
	public int durability;
	private double breakSpeed;
	private double damage;
	public int[] protection;
	public int harvestLevel = 3;
	public int maxUpgrade;
	
	public final int id;
	
	public HashMap<Tool, EnchantmentModifier> enchantments = new HashMap<Tool, EnchantmentModifier>();
	
	/** maxUpgeade is a number between 0%-100% **/
	public CraftMaterial(String name, int generateOre, int durability, double speed, double damage, int maxUpgrade, int helmet, int chestplate, int leggings, int boots)
	{
		this.name = name;
		this.displayName = name.substring(0, 1).toUpperCase();
		this.displayName = this.displayName + name.substring(1);
		this.generateOre = generateOre;
		this.durability = durability;
		this.breakSpeed = speed;
		this.damage = damage;
		this.protection = new int[]{helmet, chestplate, leggings, boots};
		this.maxUpgrade = maxUpgrade;
		id = RandomAdditions.materials.size();
		RandomAdditions.materials.add(this);
	}
	
	public CraftMaterial setAllInternalModifier(EnchantmentModifier modifier)
	{
		for (int i = 0; i < ItemTool.tools.size(); i++) {
			if(!ItemTool.tools.get(i).external)
				enchantments.put(ItemTool.tools.get(i), modifier);
		}
		return this;
	}
	
	public CraftMaterial setAllModifier(EnchantmentModifier modifier)
	{
		for (int i = 0; i < ItemTool.tools.size(); i++) {
			enchantments.put(ItemTool.tools.get(i), modifier);
		}
		return this;
	}
	
	public CraftMaterial setSwordModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.sword, modifier);
		return this;
	}
	
	public CraftMaterial setToolModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.axe, modifier);
		enchantments.put(ItemTool.pickaxe, modifier);
		enchantments.put(ItemTool.shovel, modifier);
		return this;
	}
	
	public CraftMaterial setBowModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.bow, modifier);
		return this;
	}
	
	public CraftMaterial setArmorModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.helmet, modifier);
		enchantments.put(ItemTool.chestplate, modifier);
		enchantments.put(ItemTool.leggings, modifier);
		enchantments.put(ItemTool.boots, modifier);
		return this;
	}
	
	public CraftMaterial setChestplateModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.chestplate, modifier);
		return this;
	}
	
	public CraftMaterial setBootsModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.boots, modifier);
		return this;
	}
	
	public CraftMaterial setHelmetModifier(EnchantmentModifier modifier)
	{
		enchantments.put(ItemTool.helmet, modifier);
		return this;
	}
	
	public CraftMaterial setDropItem()
	{
		dropItem = true;
		return this;
	}
	
	public CraftMaterial setHarvestLevel(int level)
	{
		this.harvestLevel = level;
		return this;
	}
	
	/** stack must be an itemstack or a oredictionary string **/
	public CraftMaterial setCustomIngot(Object stack)
	{
		this.ingot = stack;
		return this;
	}
	
	public float getSpeed(ItemStack stack)
	{
		float speed = getSpeed(getLevel(this, stack));
		ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
    	for (int i = 0; i < modifiers.size(); i++) {
    		speed = (int) modifiers.get(i).getMiningSpeed(speed);
		}
		return speed;
	}
	
	public float getSpeed(int level)
	{
		return (float) (breakSpeed + getPercent(maxUpgrade, breakSpeed, level));
	}
	
	public int getDamage(ItemStack stack)
	{
		int damage = getDamage(getLevel(this, stack));
		ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
    	for (int i = 0; i < modifiers.size(); i++) {
    		damage = (int) modifiers.get(i).getDamage(damage);
		}
		return damage;
	}
	
	public static double getPercent(double maxUpgrade, double currentUpgrade, double level)
	{
		if(level == 0)
			return 0;
		return (((double)maxUpgrade/100D)*currentUpgrade)/(4-(double)level);
	}
	
	public int getDamage(int level)
	{
		double enchant = getPercent(maxUpgrade, damage, level);
		return (int) (damage+enchant);
	}
	
	public String oreName = "";
	public String itemName = "";
	
	public CraftMaterial setOres(String ore, String item)
	{
		this.oreName = ore;
		this.itemName = item;
		return this;
	}
	
	
	public void registerOre()
	{
		/*if(!oreName.equals("") && generateOre != 0)
			OreDictionary.registerOre(oreName, new ItemStack(SubSystemOre.instance.block, 1, getIndexOfBlock()));
		if(!itemName.equals("") && ingot == null)
			OreDictionary.registerOre(itemName, new ItemStack(RandomAdditions.itemIngot, 1, getIndexOfIngot()));*/
	}
	
	public String getDust()
	{
		return "dust" + displayName;
	}
	
	public ItemStack getIngot()
	{
		if(ingot != null)
		{
			if(ingot instanceof ItemStack)
				return (ItemStack) ingot;
			if(ingot instanceof String)
			{
				ArrayList<ItemStack> stacks = OreDictionary.getOres((String) ingot);
				if(stacks != null && stacks.size() > 0)
				{
					return stacks.get(0);
				}
			}
		}
		return new ItemStack(RandomAdditions.itemIngot, 1, getIndexOfIngot());
	}
	
	public int getIndexOfBlock()
	{
		return getIndexOfBlock(this);
	}
	
	public int getIndexOfIngot()
	{
		return getIndexOfIngot(this);
	}
	
	public static int getIndexOfBlock(CraftMaterial material)
	{
		int index = -1;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
		{
			if(RandomAdditions.materials.get(zahl).generateOre != 0)
				index++;
			if(RandomAdditions.materials.get(zahl) == material)
				return index;
		}
		return -1;
	}
	
	public static int getIndexOfIngot(CraftMaterial material)
	{
		int index = -1;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
		{
			if(RandomAdditions.materials.get(zahl).ingot == null)
				index++;
			if(RandomAdditions.materials.get(zahl) == material)
				return index;
		}
		return -1;
	}
	
	public static void registerOres()
	{
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
			RandomAdditions.materials.get(zahl).registerOre();
	}

	public static void load() {
		registerOres();
	}
	
	public static CraftMaterial getItemMaterialFromMeta(int metadata)
	{
		int index = -1;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
		{
			if(RandomAdditions.materials.get(zahl).ingot == null)
				index++;
			if(index == metadata)
				return RandomAdditions.materials.get(zahl);
		}
		return null;
	}
	
	public static CraftMaterial getBlockMaterialFromMeta(int metadata)
	{
		int index = -1;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
		{
			if(RandomAdditions.materials.get(zahl).generateOre != 0)
				index++;
			if(index == metadata)
				return RandomAdditions.materials.get(zahl);
		}
		return null;
	}
	
	public static int getIngotsCount()
	{
		int count = 0;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
			if(RandomAdditions.materials.get(zahl).ingot == null)
				count++;
		return count;
	}
	
	public static int getBlocksCount()
	{
		int count = 0;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
			if(RandomAdditions.materials.get(zahl).generateOre != 0)
				count++;
		return count;
	}
	
	public static boolean hasEffect(ItemStack par1ItemStack)
	{
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
			if(getLevel(RandomAdditions.materials.get(zahl), par1ItemStack) > 0)
				return true;
		return false;
	}
	
	public EnchantmentModifier getModifier(ItemStack par1ItemStack)
	{
		Tool tool = ItemTool.getTool(par1ItemStack);
		EnchantmentModifier modifier = enchantments.get(tool);
		return modifier;
	}
	
	public static ArrayList<EnchantmentModifier> getModifiers(ItemStack par1ItemStack)
	{
		if(par1ItemStack == null || !(par1ItemStack.getItem() instanceof ItemTool))
			return new ArrayList<EnchantmentModifier>();
		ArrayList<EnchantmentModifier> modifiers = new ArrayList<EnchantmentModifier>();
		for (int i = 0; i < RandomAdditions.materials.size(); i++) {
			Tool tool = ItemTool.getTool(par1ItemStack);
			EnchantmentModifier modifier = RandomAdditions.materials.get(i).enchantments.get(tool);
			int level = getLevel(RandomAdditions.materials.get(i), par1ItemStack);
			if(modifier != null && level > 0)
			{
				modifier.level = level;
				modifiers.add(modifier);
			}
		}
		return modifiers;
	}
	
	public static int getLevel(CraftMaterial material, ItemStack par1ItemStack)
	{
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		int level = par1ItemStack.stackTagCompound.getInteger(material.name + "level");
		if(level < 0)
			return 0;
		return level;
	}
	
	public static void setLevel(CraftMaterial material, ItemStack par1ItemStack, int level)
	{
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		if(level == 0)
			par1ItemStack.stackTagCompound.removeTag(material.name + "level");
		par1ItemStack.stackTagCompound.setInteger(material.name + "level", level);
	}
}
