package com.creativemd.randomadditions.common.systems.rf.container;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.systems.rf.SubBlockRF;
import com.creativemd.randomadditions.common.systems.rf.SubSystemRF;

public class SubGuiRF extends SubGuiTileEntity {
	
	public EnergyComponent battery;
	public SubBlockRF block;
	
	public SubGuiRF(EnergyComponent battery, SubBlockRF block)
	{
		super(battery);
		this.battery = battery;
		this.block = block;
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		String in = "Input: " + battery.getInputPower() + " RA/t";
		String out = "Output: " + SubSystemRF.RAtoRF(battery.getOutputPower()) + " RF/t";
		if(!block.isRA)
		{
			in = "Input: " + SubSystemRF.RAtoRF(battery.getInputPower()) + " RF/t";
			out = "Output: " + battery.getOutputPower() + " RA/t";
		}
		fontRenderer.drawString(in, 3, 45, 0);
		fontRenderer.drawString(out, 3, 55, 0);
	}

	@Override
	public void createControls() {
		controls.add(new GuiPowerOMeter("power", block, battery, 2, 30, 170, 10));
	}

}
