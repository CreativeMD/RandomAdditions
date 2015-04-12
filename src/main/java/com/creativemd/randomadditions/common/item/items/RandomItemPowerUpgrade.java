package com.creativemd.randomadditions.common.item.items;

import java.util.List;

import com.creativemd.randomadditions.common.upgrade.Upgrade;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RandomItemPowerUpgrade extends RandomItemUpgrade {
	
	public RandomItemPowerUpgrade(String name) {
		super(name, Upgrade.Power);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add("Decrease used power by " + (getModifier(stack)*100F) + " %");
	}
	
	
	@Override
	public void onRegister()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(0), new Object[]
				{
				"IRI", "RAR", "IRI", Character.valueOf('R'), "dustRuby", Character.valueOf('I'), "ingotTin", 'A', compressedplastic2.getItemStack()
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(1), new Object[]
				{
				"IRI", "RAR", "IRI", 'R', getItemStack(0), Character.valueOf('I'), "ingotTin", Character.valueOf('A'), "dustRuby"
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(2), new Object[]
				{
				"IRI", "AAA", "IRI", Character.valueOf('R'), "dustRuby", 'I', getItemStack(1), Character.valueOf('A'), "ingotTin"
				}));
	}
}
