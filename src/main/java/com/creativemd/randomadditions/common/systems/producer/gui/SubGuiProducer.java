package com.creativemd.randomadditions.common.systems.producer.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
import com.creativemd.randomadditions.common.systems.producer.SubBlockProducer;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityProducer;

public class SubGuiProducer extends SubGuiTileEntity{
	
	public TileEntityProducer producer;
	public SubBlockProducer block;
	
	public SubGuiProducer(TileEntityProducer producer, SubBlockProducer block)
	{
		super(producer);
		this.producer = producer;
		this.block = block;
	}
	
	@Override
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		controls.add(new GuiPowerOMeter(block, producer, 87, 20, 170, 10));
		return controls;
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		fontRenderer.drawString("Speed/Max: " + producer.speed + "/" + producer.maxSpeed, 3, 30, 0);
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		
	}

}
