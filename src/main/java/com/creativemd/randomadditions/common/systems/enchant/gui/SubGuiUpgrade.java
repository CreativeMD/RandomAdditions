package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.randomadditions.common.gui.controls.GuiBookControl;
import com.creativemd.randomadditions.common.item.ItemRandomArmor;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.systems.enchant.SubSystemEnchant;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;

public class SubGuiUpgrade extends SubGuiTileEntity{
	
	public TileEntityUpgrade upgrade;
	
	public GuiBookControl book;
	
	public SubGuiUpgrade(TileEntityUpgrade upgrade)
	{
		super(upgrade);
		this.upgrade = upgrade;
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		ItemStack stack = upgrade.inventory[0];
		if(stack != null && (stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemRandomArmor)  && SubSystemEnchant.getLevel(stack) < 3)
		{
			EnchantmentModifier modifier = SubSystemEnchant.getModifier(stack);
			String name = "Level";
			if(modifier != null)
			{
				name = modifier.getName();
			}
			
			int level = SubSystemEnchant.getLevel(stack)+1;
			book.text = new String[2];
			book.text[0] = name + " " + ItemTool.getStringFromLevel(level);
			book.text[1] = "Cost: " + ((level)*10) + " Level";
			
		}else
			book.text = new String[0];
	}
	
	@CustomEventSubscribe
	public void onClicked(ControlClickEvent event)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("type", 0);
		sendPacketToServer(0, nbt);
	}
	
	@Override
	public void createControls() {
		book = new GuiBookControl("book", new String[0], 65, 5, 105, 70);
		controls.add(book);
		controls.add(new GuiButton("Upgrade", 10, 50, 50, 20));
	}

}
