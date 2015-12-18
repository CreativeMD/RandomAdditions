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
	public void drawOverlay(FontRenderer fontRenderer) {
		
	}
	
	@Override
	public void createControls() {
		controls.add(new GuiMachineProgressControl("progress", block, machine, 46, 6, 96, 51));
		GuiPowerOMeter meter = new GuiPowerOMeter("power", block, machine, -23, 34, 78, 15);
		meter.rotation = -90;
		controls.add(meter);
		controls.add(new GuiGearInformation("gear", machine, 140, 53, 30, 30));
		controls.add(new GuiRedstoneControl(25, 60, 20, 20, 0, machine));
		if(Loader.isModLoaded("NotEnoughItems"))
			controls.add(new SubGuiMachineRecipeControl("recipe", block, 147, 5, 16, 16));
	}

}
