package com.creativemd.randomadditions.common.item.items;

import java.util.List;

import com.creativemd.randomadditions.common.systems.cable.SubSystemCable;
import com.creativemd.randomadditions.common.upgrade.Upgrade;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RandomItemInputUpgrade extends RandomItemUpgrade{
	
	public RandomItemInputUpgrade(String name) {
		super(name, Upgrade.Input);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add("Increase maximal input by " + getModifier(stack) + " RA/tick");
	}
	
	@Override
	public void onRegister()
	{
		GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(0), new Object[]
				{
				"IRI", "RAR", "IRI", 'R', compressedplastic.getItemStack(), Character.valueOf('I'), "ingotTin", 'A', SubSystemCable.instance.getItemStack(0)
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(1), new Object[]
				{
				"IRI", "RAR", "IRI", 'R', getItemStack(0), Character.valueOf('I'), "ingotTin", 'A', compressedplastic.getItemStack()
				}));
		GameRegistry.addRecipe(new ShapedOreRecipe(getItemStack(2), new Object[]
				{
				"IRI", "AAA", "IRI", 'R', compressedplastic.getItemStack(), 'I', getItemStack(1), Character.valueOf('A'), "ingotTin"
				}));
	}

}
