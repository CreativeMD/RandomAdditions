package com.creativemd.randomadditions.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.creativemd.randomadditions.common.gui.controls.GuiGearInformation;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRandom extends ItemCore{
	
	public ItemRandom()
	{
		hasSubtypes = true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		icons = new IIcon[CraftMaterial.getIngotsCount()];
		int index = 0;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
			if(RandomAdditions.materials.get(zahl).ingot == null)
			{
				icons[index] = par1IconRegister.registerIcon(RandomAdditions.modid + ":" + RandomAdditions.materials.get(zahl).name);
				index++;
			}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return icons[par1];
    }
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		int index = 0;
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
			if(RandomAdditions.materials.get(zahl).ingot == null)
			{
				ItemStack stack = new ItemStack(item, 1, index);
				list.add(stack);
				index++;
			}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return this.getUnlocalizedName() + "." + CraftMaterial.getItemMaterialFromMeta(par1ItemStack.getItemDamage()).name;
    }
}
