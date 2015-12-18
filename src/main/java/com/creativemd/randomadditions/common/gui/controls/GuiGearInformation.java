package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.systems.assembly.SubSystemAssembly;
import com.creativemd.randomadditions.common.systems.assembly.blocks.SubBlockAssemblyMachine;
import com.creativemd.randomadditions.common.systems.assembly.gui.SubGuiAssemblyMachine;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;
import com.creativemd.randomadditions.common.utils.TileEntityMachineBase;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GuiGearInformation extends GuiControl{
	
	public TileEntityMachineBase machine;
	public int level;
	
	public GuiGearInformation(String name, TileEntityMachineBase machine, int x, int y, int width, int height) {
		this(name, machine, x, y, width, height, 0);
	}
	
	public GuiGearInformation(String name, TileEntityMachineBase machine, int x, int y, int width, int height, int level) {
		super(name, x, y, width, height);
		this.machine = machine;
		this.level = level;
	}

	@Override
	public void drawControl(FontRenderer renderer) {
		RenderHelper2D.renderIcon(RandomAdditions.gears[level], 0, 0, 1, true, 0, width, height);
		//RenderHelper2D.renderIcon(icon, 0, 0, 1, false, 30, width, height);
		float speed = machine.getCraftingSpeed();
		//if(speed > 10)
			//speed = 10;
		if(machine.getCurrentPower() == 0)
			speed = 0;
		rotation = (int) ((double)System.nanoTime()/10000000D*speed);
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		ArrayList<String> strings = new ArrayList<String>();
		float speed = machine.getCraftingSpeed()*5;
		float power = machine.getNeededPower(speed);
		strings.add("Power: " + EnumChatFormatting.YELLOW + power + " RA/tick");
		strings.add("Speed: " + EnumChatFormatting.BLUE + speed);
		double efficiency = (double)25D/power;
		strings.add("Efficiency: " + EnumChatFormatting.GREEN + (int)(efficiency*100D) + "%");
		strings.add("Input: " + EnumChatFormatting.LIGHT_PURPLE + machine.getInputPower() + EnumChatFormatting.WHITE + "/" + EnumChatFormatting.RED + machine.getMaxInput());
		return strings;
	}
}
