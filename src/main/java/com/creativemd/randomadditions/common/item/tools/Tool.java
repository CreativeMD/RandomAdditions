package com.creativemd.randomadditions.common.item.tools;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class Tool {
	
	/** 1 for normal tools; 2 for swords */
	public int durabilityFactor = 1;
	public String name;
	public String displayName;
	/** -1 for nothing, 0 for nothing special, 1 for stone/rock (pickaxe), 2 for wood (axe), 3 for dirt/grass (shovel) **/
	public int harvest = 0;
	public int maxduration = 0;
	public EnumAction action;
	public float damageAmount;	
	public RandomItem plate;
	public int cost = 1;
	public boolean external = false;
	
	@SideOnly(Side.CLIENT)
	public ArrayList<IIcon> icons;
	
	public Tool(RandomItem plate, String name, float damageAmount)
	{
		this.plate = plate;
		this.name = name;
		this.displayName = name.substring(0, 1).toUpperCase();
		this.displayName = this.displayName + name.substring(1);
		action = EnumAction.none;
		this.damageAmount = damageAmount;
		ItemTool.tools.add(this);
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			icons = new ArrayList<IIcon>();
	}
	
	public Tool setCost(int cost)
	{
		this.cost = cost;
		return this;
	}
	
	public Tool setAction(EnumAction action)
	{
		this.action = action;
		return this;
	}
	
	public Tool setMaxDuration(int duration)
	{
		this.maxduration = duration;
		return this;
	}
	
	public Tool setDurabilityFactor(int factor)
	{
		this.durabilityFactor = factor;
		return this;
	}
	
	/** -1 for nothing, 0 for nothing special, 1 for stone/rock (pickaxe), 2 for wood (axe), 3 for dirt/grass (shovel) **/
	public Tool setHarvest(int harvest)
	{
		this.harvest = harvest;
		return this;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, ItemStack usingItem, int useRemaining)
	{
		if(ItemTool.getMaterial(stack).id < icons.size())
			return icons.get(ItemTool.getMaterial(stack).id);
		return ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(1))).getAtlasSprite("missingno");
	}
	
	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister par1IconRegister, CraftMaterial material)
	{
		icons.add(par1IconRegister.registerIcon(RandomAdditions.modid + ":" + material.name + displayName));
	}
	
	public abstract String getAttributeModifiers(ItemStack stack);
	
	public abstract void addRecipe(Object matieral, ItemStack output);
	
	public boolean isToolEffective(ItemStack stack, Block block)
	{
		return false;
	}
	
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		return false;
    }

	public boolean canExtend(EntityPlayer par3EntityPlayer) {
		return true;
	}
	
}
