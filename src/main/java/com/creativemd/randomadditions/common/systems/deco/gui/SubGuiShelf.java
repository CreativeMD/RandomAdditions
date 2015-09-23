package com.creativemd.randomadditions.common.systems.deco.gui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SubGuiShelf extends SubGuiTileEntity{

	public SubGuiShelf(TileEntityRandom tileEntity) {
		super(tileEntity);
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		
	}

	@Override
	public void createControls() {
		
	}

}
