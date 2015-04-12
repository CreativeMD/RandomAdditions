package com.creativemd.randomadditions.common.subsystem;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.controls.GuiControl;

public abstract class SubGuiTileEntity extends SubGui{
	
	public TileEntityRandom tileEntity;
	
	public SubGuiTileEntity(TileEntityRandom tileEntity)
	{
		this.tileEntity = tileEntity;
	}

}
