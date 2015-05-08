package com.creativemd.randomadditions.common.event;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.randomadditions.common.entity.EntityRandomArrow;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemBattery;
import com.creativemd.randomadditions.common.item.tools.Tool;
import com.creativemd.randomadditions.common.item.tools.ToolBow;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EventHandlerRandom {
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void tick(RenderTickEvent event)
	{
		Minecraft mc = Minecraft.getMinecraft();
		if(event.phase == Phase.END && mc.thePlayer != null && mc.inGameHasFocus)
		{
			boolean hasBattery = false;
			int maxpower = 0;
			int power = 0;
			RandomItemBattery heighest = null;
			for (int i = 0; i < mc.thePlayer.inventory.mainInventory.length; i++) {
				RandomItem item = RandomItem.getRandomItem(mc.thePlayer.inventory.mainInventory[i]);
				if(item instanceof RandomItemBattery)
				{
					maxpower += ((RandomItemBattery) item).maxDamage;
					if(heighest == null || ((RandomItemBattery) item).maxDamage > heighest.maxDamage)
						heighest = (RandomItemBattery) item;
					power += ((RandomItemBattery) item).getPower(mc.thePlayer.inventory.mainInventory[i]);
					hasBattery = true;
				}
			}
			if(hasBattery)
			{
				//RenderHelper2D.renderIcon(Blocks.iron_block.getBlockTextureFromSide(0), 3, 3, 1, true, 0, 100, 20);
				GL11.glDisable(GL11.GL_LIGHTING);
				mc.fontRenderer.drawString(power + "/" + maxpower + " RA", 23, 10, 16777215);
				RenderHelper2D.renderItem(heighest.getItemStack(), 5, 5);
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityDeath(LivingDeathEvent event)
	{
		if(event.source.getSourceOfDamage() instanceof EntityPlayer || event.source.getSourceOfDamage() instanceof EntityRandomArrow)
		{
			ItemStack stack = null;
			EntityPlayer player = null;
			if(event.source.getSourceOfDamage() instanceof EntityRandomArrow)
			{
				stack = ((EntityRandomArrow)event.source.getSourceOfDamage()).bow;
				player = (EntityPlayer) ((EntityRandomArrow)event.source.getSourceOfDamage()).shootingEntity; 
			}
			else
			{
				stack = ((EntityPlayer)event.source.getSourceOfDamage()).getHeldItem();
				player = (EntityPlayer)event.source.getSourceOfDamage();
			}
			if(stack != null && stack.getItem() instanceof ItemTool)
			{
				ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
		    	for (int i = 0; i < modifiers.size(); i++) {
		    		modifiers.get(i).onEntityBeforeDeath(player, event.entityLiving);
				}
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void getFOV(FOVUpdateEvent event)
	{
		if (event.entity.isUsingItem() && event.entity.getItemInUse().getItem() == RandomAdditions.tools)
        {
            int i = event.entity.getItemInUseDuration();
            Tool tool = ItemTool.getTool(event.entity.getItemInUse());
            if(tool == ItemTool.bow)
            {
	            float f1 = (float)i / ToolBow.getSpan(event.entity, event.entity.getItemInUse());
	
	            if (f1 > 1.0F)
	            {
	                f1 = 1.0F;
	            }
	            else
	            {
	                f1 *= f1;
	            }
	
	            event.newfov *= 1.0F - f1 * 0.3F;
            }
        }
	}
	
	@SubscribeEvent
	public void getSpeed(BreakSpeed event)
	{
		ItemStack stack = event.entityPlayer.getHeldItem();
		float newSpeed = 1;
		if(stack != null && stack.getItem() instanceof ItemTool)
		{
			newSpeed = ItemTool.getDigSpeed(event.entityPlayer, stack, event.block, event.metadata);
			if (event.entityPlayer.isPotionActive(Potion.digSpeed))
	        {
				newSpeed *= 1.0F + (float)(event.entityPlayer.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2F;
	        }

	        if (event.entityPlayer.isPotionActive(Potion.digSlowdown))
	        {
	        	newSpeed *= 1.0F - (float)(event.entityPlayer.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2F;
	        }

	        if (event.entityPlayer.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(event.entityPlayer))
	        {
	        	newSpeed /= 5.0F;
	        }

	        if (!event.entityPlayer.onGround)
	        {
	        	newSpeed /= 5.0F;
	        }
			if(ItemTool.getItemDamage(stack) >= ItemTool.getTool(stack).durabilityFactor*ItemTool.getMaterial(stack).durability)
			{
				newSpeed = 1;
			}else{
				ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
		    	for (int i = 0; i < modifiers.size(); i++) {
		    		newSpeed = modifiers.get(i).getMiningSpeedLate(event.entityPlayer, stack, ItemTool.getTool(stack).isToolEffective(stack, event.block), event.block, newSpeed);
				}
	    	}
	        event.newSpeed = newSpeed;
		}
	}
	
	@SubscribeEvent
	public void onEntityDrops(LivingDropsEvent event)
	{
		if(event.source.getSourceOfDamage() instanceof EntityPlayer || event.source.getSourceOfDamage() instanceof EntityRandomArrow)
		{
			ItemStack stack = null;
			EntityPlayer player = null;
			if(event.source.getSourceOfDamage() instanceof EntityRandomArrow)
			{
				stack = ((EntityRandomArrow)event.source.getSourceOfDamage()).bow;
				player = (EntityPlayer) ((EntityRandomArrow)event.source.getSourceOfDamage()).shootingEntity; 
			}
			else
			{
				stack = ((EntityPlayer)event.source.getSourceOfDamage()).getHeldItem();
				player = (EntityPlayer)event.source.getSourceOfDamage();
			}
			if(stack != null && stack.getItem() instanceof ItemTool)
			{
				ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
		    	for (int i = 0; i < modifiers.size(); i++) {
		    		modifiers.get(i).onEntityDeath(player, event.entityLiving, event.recentlyHit);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onEntityAttack(LivingAttackEvent event)
	{
		if(event.source.getSourceOfDamage() instanceof EntityPlayer)
		{
			ItemStack stack = ((EntityPlayer)event.source.getSourceOfDamage()).getHeldItem();
			if(stack != null && stack.getItem() instanceof ItemTool)
			{
				ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
		    	for (int i = 0; i < modifiers.size(); i++) {
		    		modifiers.get(i).onAttackEntity((EntityPlayer)event.source.getSourceOfDamage(), event.entityLiving);
				}
			}
		}
	}
	
	@SubscribeEvent
	public void onDrop(HarvestDropsEvent event)
	{
		if(event.harvester == null)
			return ;
		ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(event.harvester.getHeldItem());
    	for (int i = 0; i < modifiers.size(); i++) {
    		modifiers.get(i).onHarvestBlockPre(event.harvester, event.world, event.x, event.y, event.z, event.blockMetadata, event.block, event.drops);
    	}
    	
    	for (int i = 0; i < modifiers.size(); i++) {
    		modifiers.get(i).onHarvestBlock(event.harvester, event.world, event.x, event.y, event.z, event.blockMetadata, event.block, event.drops);
    	}
    	
    	for (int i = 0; i < modifiers.size(); i++) {
    		modifiers.get(i).onHarvestBlockPost(event.harvester, event.world, event.x, event.y, event.z, event.blockMetadata, event.block, event.drops);
    	}
    	
	}
}
