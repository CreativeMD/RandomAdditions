package com.creativemd.randomadditions.common.systems.deco.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.subsystem.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SubGuiShelf extends SubGuiTileEntity{

	public SubGuiShelf(TileEntityRandom tileEntity) {
		super(tileEntity);
	}

	@Override
	public ArrayList<GuiControl> getControls() {
		ArrayList<GuiControl> controls = new ArrayList<GuiControl>();
		
		return controls;
	}

	@Override
	public void drawForeground(FontRenderer fontRenderer) {
		
	}

	@Override
	public void drawBackground(FontRenderer fontRenderer) {
		
	}

}
