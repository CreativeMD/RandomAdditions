package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Items;
import codechicken.nei.recipe.GuiCraftingRecipe;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;

public class SubGuiMachineRecipeControl extends GuiControl{
	
	public SubBlockMachine machine;
	
	public SubGuiMachineRecipeControl(String name, SubBlockMachine machine, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		this.machine = machine;
	}

	@Override
	public void drawControl(FontRenderer renderer) {
		RenderHelper2D.renderIcon(Items.paper.getIconFromDamage(0), 0, 0, 1, false, rotation, width, height);
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		ArrayList<String> tooltip = new ArrayList<String>();
		tooltip.add("Recipes");
		return tooltip;
	}
	
	@Override
	public boolean mousePressed(int posX, int posY, int button){
		GuiCraftingRecipe.openRecipeGui(machine.name);
		return true;
	}

}
