package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiButtonControl;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubGuiRepair extends SubGuiTileEntity{
	
	public SubGuiRepair(TileEntityRandom tileEntity) {
		super(tileEntity);
	}

	@Override
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		controls.add(new GuiButtonControl("Repair", 110, 60, 60, 20));
		return controls;
	}
	
	public void onControlClicked(GuiControl control)
	{
		sendGuiPacket(0, "Clicked");
	}
	
	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainerSub.background);
		//GL11.glTranslated(mc.displayWidth/8, mc.displayHeight/8, 0);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper2D.drawTexturedModalRect(50, 33, 176, 18, 12, 12);
		RenderHelper2D.drawTexturedModalRect(100, 32, 194, 0, 25, 13);
		GL11.glPopMatrix();
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		
	}

}
