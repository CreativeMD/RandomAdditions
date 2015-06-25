package com.creativemd.randomadditions.common.systems.producer.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubContainerProducer extends SubContainerTileEntity{

	public SubContainerProducer(TileEntityRandom tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}

	@Override
	public void onGuiPacket(int control, String value, EntityPlayer player) {
		
	}

	@Override
	public ArrayList<Slot> getSlots(EntityPlayer player) {
		return new ArrayList<Slot>();
	}

	@Override
	public boolean doesGuiNeedUpdate() {
		return true;
	}

}
