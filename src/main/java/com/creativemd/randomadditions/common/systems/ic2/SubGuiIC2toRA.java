package com.creativemd.randomadditions.common.systems.ic2;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.systems.ic2.tileentity.TileEntityIC2toRA;

public class SubGuiIC2toRA extends SubGuiTileEntity {
	
	public TileEntityIC2toRA battery;
	public SubBlockIC2 block;
	
	public SubGuiIC2toRA(TileEntityIC2toRA battery, SubBlockIC2 block)
	{
		super(battery);
		this.battery = battery;
		this.block = block;
	}
	
	@Override
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		controls.add(new GuiPowerOMeter(block, battery, 87, 35, 170, 10));
		return controls;
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		fontRenderer.drawString("Input: " + SubSystemIC2.RAtoEU(battery.getInputPower()) + " EU/t", 3, 45, 0);
		fontRenderer.drawString("Output: " + battery.getOutputPower() + " RA/t", 3, 55, 0);
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		
	}

}
