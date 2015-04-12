package com.creativemd.randomadditions.common.systems.deco.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.randomadditions.common.subsystem.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityShelf;

public class SubContainerShelf extends SubContainerTileEntity{

	public SubContainerShelf(TileEntityRandom tileEntity) {
		super(tileEntity);
	}

	@Override
	public void onGuiPacket(int control, String value, EntityPlayer player) {
		
	}

	@Override
	public ArrayList<Slot> getSlots(EntityPlayer player) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		TileEntityShelf shelf = (TileEntityShelf) tileEntity;
		int size = 9;
		for (int i = 0; i < shelf.inventory.length; i++) {
			slots.add(new Slot(shelf, i, 8+(i-i/size*size)*18, 7+i/size*18));
		}
		slots.addAll(getPlayerSlots(player, 8, 84));
		return slots;
	}

	@Override
	public boolean doesGuiNeedUpdate() {
		return false;
	}

}
