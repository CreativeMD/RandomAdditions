package com.creativemd.randomadditions.common.systems.battery;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.gui.controls.GuiRedstoneControl;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
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
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		controls.add(new GuiPowerOMeter(block, battery, 87, 35, 170, 10));
		controls.add(new GuiRedstoneControl(15, 15, 20, 20, 0, battery));
		return controls;
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		fontRenderer.drawString("Input: " + battery.getInputPower() + "/" + block.input, 3, 45, 0);
		fontRenderer.drawString("Output: " + battery.getOutputPower() + "/" + block.output, 3, 55, 0);
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		
	}

}
