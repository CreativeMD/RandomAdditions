package com.creativemd.randomadditions.common.systems.battery;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.gui.controls.GuiRedstoneControl;
import com.creativemd.randomadditions.common.systems.battery.tileentity.TileEntityBattery;

public class SubGuiBattery extends SubGuiTileEntity {
	
	public TileEntityBattery battery;
	public SubBlockBattery block;
	
	public SubGuiBattery(TileEntityBattery battery, SubBlockBattery block)
	{
		super(battery);
		this.battery = battery;
		this.block = block;
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		fontRenderer.drawString("Input: " + battery.getInputPower() + "/" + block.input, 3, 45, 0);
		fontRenderer.drawString("Output: " + battery.getOutputPower() + "/" + block.output, 3, 55, 0);
	}

	@Override
	public void createControls() {
		controls.add(new GuiPowerOMeter("power", block, battery, 2, 30, 170, 10));
		controls.add(new GuiRedstoneControl(5, 5, 20, 20, 0, battery));
		
	}

}
