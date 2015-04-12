package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IIcon;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiGearInformation extends GuiControl{
	
	public SubBlockMachine block;
	public TileEntityMachine machine;
	
	public GuiGearInformation(SubBlockMachine block, TileEntityMachine machine, int x, int y, int width, int height) {
		super(x, y, width, height);
		this.block = block;
		this.machine = machine;
	}

	@SideOnly(Side.CLIENT)
	public static IIcon icon;

	@Override
	public void drawControl(FontRenderer renderer) {
		RenderHelper2D.renderIcon(icon, 0, 0, 1, false, 0, width, height);
		int speed = machine.getProgressSpeed();
		if(speed > 10)
			speed = 10;
		if(machine.getCurrentPower() == 0)
			speed = 0;
		rotation = (int) ((double)System.nanoTime()/100000000D)*speed;
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		ArrayList<String> strings = new ArrayList<String>();
		int speed = machine.getProgressSpeed();
		int power = machine.getNeededPower(speed);
		strings.add("Power: " + power + " RA/tick");
		strings.add("Speed: " + speed);
		double efficiency = (double)20D/power;
		strings.add("Efficiency: " + (int)(efficiency*100D) + "%");
		strings.add("Input: " + machine.getInputPower() + "/" + machine.getMaxInput());
		return strings;
	}
}
