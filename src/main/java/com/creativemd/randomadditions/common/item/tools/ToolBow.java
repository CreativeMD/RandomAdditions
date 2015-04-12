package com.creativemd.randomadditions.common.item.tools;

import java.util.ArrayList;
import java.util.Random;

import com.creativemd.randomadditions.common.entity.EntityRandomArrow;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ToolBow extends Tool {

	public ToolBow(RandomItem plate, String name) {
		super(plate, name, 0);
		action = EnumAction.bow;
	}
	
	public static final double percentDamage = 0.7;

	@Override
	public String getAttributeModifiers(ItemStack stack) {
		return "+" + Math.round(ItemTool.getMaterial(stack.getItemDamage()).getDamage(stack)*percentDamage) + " Shot Damage";
	}
	
	public static int getSpan(EntityPlayer player, ItemStack stack)
	{
		ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
		int maxduration = 20;
    	for (int i = 0; i < modifiers.size(); i++) {
    		maxduration = modifiers.get(i).getSpanTime(player, maxduration);
		}
    	return maxduration;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, ItemStack usingItem, int useRemaining)
	{
		int index = ItemTool.getMaterial(stack).id*4;
		if(usingItem != null)
		{
			int j = maxduration - useRemaining;
			
			int spantime = getSpan(Minecraft.getMinecraft().thePlayer, usingItem);
			int second = spantime-5;
			if(second < spantime-2)
				second = 1;
		    if (j >= spantime)
			    index += 3;
		    else if (j > second)
			    index += 2;
		    else if (j > 0)
			    index += 1;
		}
		return icons.get(index);
	}
	
	public boolean canExtend(EntityPlayer par3EntityPlayer) {
		return par3EntityPlayer.capabilities.isCreativeMode || par3EntityPlayer.inventory.hasItem(Items.arrow);
	}
	
	@Override
	public void registerIcon(IIconRegister par1IconRegister, CraftMaterial material)
	{
		icons.add(par1IconRegister.registerIcon(RandomAdditions.modid + ":" + material.name + displayName));
		icons.add(par1IconRegister.registerIcon(RandomAdditions.modid + ":" + material.name + displayName + "0"));
		icons.add(par1IconRegister.registerIcon(RandomAdditions.modid + ":" + material.name + displayName + "1"));
		icons.add(par1IconRegister.registerIcon(RandomAdditions.modid + ":" + material.name + displayName + "2"));
	}
	
	public static Random itemRandom = new Random();
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int j = maxduration - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode;

        if (flag || par3EntityPlayer.inventory.hasItem(Items.arrow))
        {
            float percent = (float)j / (float)getSpan(par3EntityPlayer, par1ItemStack);
            //f = (f * f + f * 2.0F) / 3.0F;

            if ((double)percent < 0.1D)
            {
                return;
            }

            if (percent > 1.0F)
            {
            	percent = 1.0F;
            }
            
            ItemTool.damageItem(par1ItemStack, 1, par3EntityPlayer);
            
            double range = 3;
            ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(par1ItemStack);
        	for (int i = 0; i < modifiers.size(); i++) {
        		range = modifiers.get(i).getRange(par3EntityPlayer, range);
    		}
            
            EntityRandomArrow arrow = new EntityRandomArrow(par2World, par3EntityPlayer, percent, range, Math.round(ItemTool.getMaterial(par1ItemStack.getItemDamage()).getDamage(par1ItemStack)*percentDamage));
            arrow.bow = par1ItemStack;
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRandom.nextFloat() * 0.4F + 1.2F) + percent * 0.5F);
            
            modifiers = CraftMaterial.getModifiers(par1ItemStack);
        	for (int i = 0; i < modifiers.size(); i++) {
        		modifiers.get(i).onShotArrow(par3EntityPlayer, arrow);
    		}
            
            if (arrow.canBePickedUp != 2 && !flag)
            	par3EntityPlayer.inventory.consumeInventoryItem(Items.arrow);

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(arrow);
            }
        }
	}
	
	@Override
	public void addRecipe(Object matieral, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"AXS", "XAS", "AXS", Character.valueOf('X'), matieral, Character.valueOf('S'), Items.stick
				}));
	}
}
