package com.creativemd.randomadditions.common.item.tools;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.randomadditions.common.entity.EntityThrow;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.core.CraftMaterial;

import cpw.mods.fml.common.registry.GameRegistry;

public class ToolStar extends Tool {

	public ToolStar(RandomItem plate, String name) {
		super(plate, name, 0);
		action = EnumAction.bow;
		maxduration = 72000;
	}
	
	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + ItemTool.getMaterial(stack.getItemDamage()).getDamage(stack) + " Throw Damage";
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int j = maxduration - par4;		
		
		if(!par2World.isRemote)
		{		
			float f = (float)j / 20.0F;
			f = (f * f + f * 2.0F) / 3.0F;
			
			if ((double)f < 0.1D)
			{
				return;
			}
			
			if (f > 1.0F)
			{
				f = 1.0F;
			}
			
			CraftMaterial material = ItemTool.getMaterial(par1ItemStack);
			int level = CraftMaterial.getLevel(material, par1ItemStack);
			EntityThrow Throw = new EntityThrow(par2World, par3EntityPlayer, material.getSpeed(level)/4*f, par1ItemStack);
			par2World.spawnEntityInWorld(Throw);
			
			Random itemRand = new Random();
			par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand .nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);
		}
		if(!par3EntityPlayer.capabilities.isCreativeMode)
			par1ItemStack.stackSize--;
		if(par1ItemStack.stackSize == 0)
			par3EntityPlayer.inventory.mainInventory[par3EntityPlayer.inventory.currentItem] = null;
	}
	
	@Override
	public void addRecipe(Object matieral, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"X", "S", Character.valueOf('X'), matieral, Character.valueOf('S'), Items.stick
				}));
	}
}
