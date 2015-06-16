package com.creativemd.randomadditions.common.systems.ic2;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubContainerIC2 extends SubContainerTileEntity{

	public SubContainerIC2(TileEntityRandom tileEntity) {
		super(tileEntity);
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
