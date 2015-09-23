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
	public void drawOverlay(FontRenderer fontRenderer) {
		
	}

	@Override
	public void createControls() {
		controls.add(new GuiPowerOMeter("power", block, producer, 2, 5, 170, 10));
		controls.add(new GuiRedstoneControl(15, 60, 20, 20, 0, producer));
		for (int i = 0; i < producer.fuel.length; i++) {
			controls.add(new GuiHeatOMeter("heat", block, producer, 39+i*18, 50, 40, 16, i));
			controls.get(controls.size()-1).rotation = -90;
		}
	}

}
