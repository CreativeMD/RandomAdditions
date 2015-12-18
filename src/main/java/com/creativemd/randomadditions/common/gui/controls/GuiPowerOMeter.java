package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiProgressBar;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.subsystem.SubBlock;

public class GuiPowerOMeter extends GuiProgressBar{
	
	public SubBlock block;
	public EnergyComponent machine;
	
	public GuiPowerOMeter(String name, SubBlock block, EnergyComponent machine, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		this.block = block;
		this.machine = machine;
	}
	
	public double getPercent()
	{
		return (double)getPower()/(double)getMaxPower();
	}
	
	public float getPower()
	{
		return machine.getCurrentPower();
	}
	
	public int getMaxPower()
	{
		return machine.getInteralStorage();
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
