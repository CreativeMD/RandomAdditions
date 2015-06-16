package com.creativemd.randomadditions.common.systems.producer.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.gui.controls.GuiRedstoneControl;
import com.creativemd.randomadditions.common.systems.producer.blocks.HeatGenerator;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityHeatGenerator;

public class SubGuiHeatGen extends SubGuiTileEntity{
	
	public TileEntityHeatGenerator producer;
	public HeatGenerator block;
	
	public SubGuiHeatGen(TileEntityHeatGenerator producer, HeatGenerator block)
	{
		super(producer);
		this.producer = producer;
		this.block = block;
	}
	
	
	@Override
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		controls.add(new GuiPowerOMeter(block, producer, 87, 10, 170, 10));
		controls.add(new GuiRedstoneControl(35, 70, 20, 20, 0, producer));
		for (int i = 0; i < producer.fuel.length; i++) {
			controls.add(new GuiHeatOMeter(block, producer, 58+i*18, 60, 40, 16, i));
			controls.get(controls.size()-1).rotation = -90;
		}
		return controls;
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		// TODO Auto-generated method stub
		
	}

}
