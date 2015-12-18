package com.creativemd.randomadditions.common.systems.producer.gui;

import java.util.ArrayList;

import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityHeatGenerator;

public class GuiHeatOMeter extends GuiPowerOMeter{
	
	public TileEntityHeatGenerator heat;
	public int index;
	
	public GuiHeatOMeter(String name, SubBlock block, TileEntityHeatGenerator machine, int x, int y,
			int width, int height, int index) {
		super(name, block, machine, x, y, width, height);
		this.heat = machine;
		this.index = index;
	}
	
	@Override
	public float getPower()
	{
		return heat.fuel[index];
	}
	
	@Override
	public int getMaxPower()
	{
		return heat.maxfuel[index];
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		ArrayList<String> tip = new ArrayList<String>();
		double percent = (double)getPower() / (double)getMaxPower();
		tip.add(getPower() + "/" + getMaxPower() + " RA (" + (int)(percent*100) + "%)");
		return tip;
	}

}
