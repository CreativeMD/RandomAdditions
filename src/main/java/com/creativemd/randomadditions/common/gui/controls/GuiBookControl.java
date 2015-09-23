package com.creativemd.randomadditions.common.gui.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.controls.GuiControl;

public class GuiBookControl extends GuiControl{
	
	public String[] text;
	
	public static final int pageHeight = 64;
	public static final int pageWidth = 104;
	
	public GuiBookControl(String name, String[] text, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		this.text = text;
	}

	@Override
	public void drawControl(FontRenderer renderer) {
		//RenderHelper2D.drawRect(0, 0, 0, 0, Vec3.createVectorHelper(0, 0, 0), 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainerSub.background);
		//GL11.glTranslated(mc.displayWidth/8, mc.displayHeight/8, 0);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glScaled((double)width/(double)pageWidth, (double)height/(double)pageHeight, 1);
		RenderHelper2D.drawTexturedModalRect(0, 0, 0, 186, pageWidth, pageHeight);
		GL11.glPopMatrix();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		for (int i = 0; i < text.length; i++) {
			renderer.drawString(text[i], 10, 8+i*10, 0);
		}
	}

}
