package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.creativemd.creativecore.common.gui.controls.GuiImageButton;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.randomadditions.common.packet.RedstonePacket;
import com.creativemd.randomadditions.common.redstone.IRedstoneControl;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;

public class GuiRedstoneControl extends GuiImageButton{
	
	public IRedstoneControl control;
	
	public GuiRedstoneControl(int x, int y, int width, int height, int id, IRedstoneControl control) {
		super("redstone", x, y, width, height, id);
		this.control = control;
	}

	@Override
	public IIcon getIcon() {
		return RedstoneControlHelper.icons[control.getMode()];
	}
	
	@Override
	public boolean mousePressed(int posX, int posY, int button)
	{
		if(super.mousePressed(posX, posY, button))
		{
			int mode = control.getMode();
			mode++;
			if(mode >= RedstoneControlHelper.modes.length)
				mode = 0;
			control.setMode(mode);
			PacketHandler.sendPacketToServer(new RedstonePacket(((TileEntity) control).xCoord, ((TileEntity) control).yCoord, ((TileEntity) control).zCoord, mode));
			return true;
		}
		return false;
	}
	
	@Override
	public boolean shouldDrawCaption()
	{
		return false;
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		ArrayList<String> tip = new ArrayList<String>();
		tip.add(StatCollector.translateToLocal(RedstoneControlHelper.modes[control.getMode()]));
		return tip;
	}
}
