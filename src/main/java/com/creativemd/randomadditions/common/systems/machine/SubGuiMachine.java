package com.creativemd.randomadditions.common.systems.machine;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.gui.controls.GuiGearInformation;
import com.creativemd.randomadditions.common.gui.controls.GuiMachineProgressControl;
import com.creativemd.randomadditions.common.gui.controls.GuiPowerOMeter;
import com.creativemd.randomadditions.common.gui.controls.GuiRedstoneControl;
import com.creativemd.randomadditions.common.gui.controls.SubGuiMachineRecipeControl;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;

import cpw.mods.fml.common.Loader;

public class SubGuiMachine extends SubGuiTileEntity{
	
	public TileEntityMachine machine;
	
	public SubBlockMachine block;
	
	public SubGuiMachine(SubBlockMachine block, TileEntityMachine machine)
	{
		super(machine);
		this.machine = machine;
		this.block = block;
	}

	@Override
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		controls.add(new GuiMachineProgressControl(block, machine, 93, 33, 96, 51));
		GuiPowerOMeter meter = new GuiPowerOMeter(block, machine, 15, 42, 78, 15);
		meter.rotation = -90;
		controls.add(meter);
		controls.add(new GuiGearInformation(block, machine, 155, 68, 30, 30));
		controls.add(new GuiRedstoneControl(35, 70, 20, 20, 0, machine));
		if(Loader.isModLoaded("NotEnoughItems"))
			controls.add(new SubGuiMachineRecipeControl(block, 155, 13, 16, 16));
		return controls;
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		
	}

}
