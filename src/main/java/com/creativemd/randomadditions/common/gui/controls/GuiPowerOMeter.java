package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.subsystem.SubBlock;

public class GuiPowerOMeter extends GuiControl{
	
	public SubBlock block;
	public EnergyComponent machine;
	
	public GuiPowerOMeter(SubBlock block, EnergyComponent machine, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.block = block;
		this.machine = machine;
	}

	@Override
	public void drawControl(FontRenderer renderer) {
		int border = 1;
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderHelper2D.drawRect(0, 0, 0+width, 0+height, Vec3.createVectorHelper(0, 0, 0), 1);
		RenderHelper2D.drawRect(0+border, 0+border, 0+width-border, 0+height-border, Vec3.createVectorHelper(1, 1, 1), 1);
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainerSub.background);
		double scale = (double)(height-border*2)/8D;
		GL11.glScaled(1, scale, 1);
		double percent = (double)getPower() / (double)getMaxPower();
		RenderHelper2D.drawTexturedModalRect(1, 1D-(scale)/5D, 1, 167, percent*(double)(width-border-1), 8);
	}
	
	public int getPower()
	{
		return machine.getCurrentPower();
	}
	
	public int getMaxPower()
	{
		return machine.getInteralStorage();
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		ArrayList<String> tip = new ArrayList<String>();
		double percent = (double)getPower() / (double)getMaxPower();
		tip.add(getPower() + "/" + getMaxPower() + " RA (" + (int)(percent*100) + "%)");
		return tip;
	}

}
