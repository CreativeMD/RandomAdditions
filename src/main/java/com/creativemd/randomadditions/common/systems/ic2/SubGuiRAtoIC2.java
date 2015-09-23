package com.creativemd.randomadditions.common.systems.ic2;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.systems.ic2.tileentity.TileEntityRAtoIC2;

public class SubGuiRAtoIC2 extends SubGuiTileEntity {
	
	public TileEntityRAtoIC2 battery;
	public SubBlockIC2 block;
	
	public SubGuiRAtoIC2(TileEntityRAtoIC2 battery, SubBlockIC2 block)
	{
		super(battery);
		this.battery = battery;
		this.block = block;
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		fontRenderer.drawString("Input: " + battery.getInputPower() + " RA/t", 3, 45, 0);
		fontRenderer.drawString("Output: " + SubSystemIC2.RAtoEU(battery.getOutputPower()) + " EU/t", 3, 55, 0);
	}
	
	@Override
	public void createControls() {
		controls.add(new GuiPowerOMeter("power", block, battery, 2, 30, 170, 10));
	}

}
