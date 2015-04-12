package com.creativemd.randomadditions.common.item;

import java.util.List;

import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCoreRandom extends ItemCore{
	
	public ItemCoreRandom()
	{
		hasSubtypes = true;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack)
    {
        return RandomItem.getRandomItem(stack).getItemStackLimit(stack);
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return RandomItem.getRandomItem(stack).onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        return RandomItem.getRandomItem(stack).getIcon(stack);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        return getIconIndex(stack);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		for (int i = 0; i < RandomItem.items.size(); i++) {
			RandomItem.items.get(i).registerIcon(par1IconRegister);
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		int index = 0;
		for(int zahl = 0; zahl < RandomItem.items.size(); zahl++)
			list.addAll(RandomItem.items.get(zahl).getSubItems(item));
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return RandomItem.getRandomItem(par1ItemStack).getUnlocalizedName(getUnlocalizedName(), par1ItemStack);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		super.addInformation(stack, player, list, p_77624_4_);
		RandomItem.getRandomItem(stack).addInformation(stack, player, list, p_77624_4_);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
    {
        return RandomItem.getRandomItem(stack).getMaxDamage(stack);
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return RandomItem.getRandomItem(stack).showDurabilityBar(stack);
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
    {
        return RandomItem.getRandomItem(stack).getDurabilityForDisplay(stack);
    }
}
