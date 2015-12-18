package com.creativemd.randomadditions.common.systems.assembly.gui;

import javax.vecmath.Vector4d;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiScrollBox;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.creativecore.common.gui.premade.SubGuiDialog;
import com.creativemd.handcraft.gui.HandRecipeControl;
import com.creativemd.handcraft.gui.SubGuiHandCraft;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;

public class SubGuiSelectHandRecipe extends SubGuiHandCraft{
	
	@Override
	public void drawBackground()
	{
		int k = (this.width - this.width) / 2;
		int l = (this.height - this.height) / 2;
		
		Vector4d color = new Vector4d(0, 0, 0, 255);
		RenderHelper2D.drawGradientRect(k, l, k+this.width, l+this.height, color, color);
		color = new Vector4d(120, 120, 120, 255);
		RenderHelper2D.drawGradientRect(k+2, l+2, k+this.width-2, l+this.height-2, color, color);
	}
	
	@Override
	public void updateRecipes()
	{
		super.updateRecipes();
		GuiScrollBox scroll = (GuiScrollBox)getControl("scroll");
		for (int i = 0; i < scroll.gui.controls.size(); i++) {
			if(scroll.gui.controls.get(i) instanceof HandRecipeControl)
			{
				scroll.gui.controls.set(i, new HandRecipeSelection((HandRecipeControl) scroll.gui.controls.get(i)));
			}
		}
	}
	
	@CustomEventSubscribe
	public void onClicked(ControlClickEvent event)
	{
		super.onClicked(event);
		if(event.source instanceof HandRecipeSelection && ((HandRecipeSelection)event.source).recipe != null)
		{
			NBTTagCompound nbt = ((HandRecipeSelection)event.source).recipe.writeToNBT();
			closeLayer(nbt);
		}
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		
	}

}
