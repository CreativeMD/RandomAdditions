package com.creativemd.randomadditions.common.systems.deco.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityShelf;

public class SubContainerShelf extends SubContainerTileEntity{

	public SubContainerShelf(TileEntityRandom tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}

	@Override
	public void onGuiPacket(int control, NBTTagCompound value, EntityPlayer player) {
		
	}

	@Override
	public void createControls() {
		TileEntityShelf shelf = (TileEntityShelf) tileEntity;
		int size = 9;
		for (int i = 0; i < shelf.inventory.length; i++) {
			addSlotToContainer(new Slot(shelf, i, 8+(i-i/size*size)*18, 7+i/size*18));
		}
		addPlayerSlotsToContainer(player, 8, 84);
	}
}
