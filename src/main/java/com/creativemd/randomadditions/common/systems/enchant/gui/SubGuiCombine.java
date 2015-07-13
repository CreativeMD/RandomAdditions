package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.SubGui.ControlEvent;
import com.creativemd.creativecore.common.gui.controls.GuiButtonControl;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubGuiCombine extends SubGuiTileEntity{

	public SubGuiCombine(TileEntityRandom tileEntity) {
		super(tileEntity);
	}
	
	@Override
	public void onControlEvent(GuiControl control, ControlEvent event)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("type", 0);
		sendPacketToServer(0, nbt);
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
	public void createControls() {
		controls.add(new GuiButtonControl("Combine", 110, 60, 60, 20));
	}

}
