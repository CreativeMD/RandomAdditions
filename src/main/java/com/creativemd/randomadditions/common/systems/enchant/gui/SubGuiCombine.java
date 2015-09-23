package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;

public class SubGuiCombine extends SubGuiTileEntity{

	public SubGuiCombine(TileEntityRandom tileEntity) {
		super(tileEntity);
	}
	
	@CustomEventSubscribe
	public void onClicked(ControlClickEvent event)
	{
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("type", 0);
		sendPacketToServer(0, nbt);
	}
	
	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
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
		controls.add(new GuiButton("Combine", 80, 50, 60, 20));
	}

}
