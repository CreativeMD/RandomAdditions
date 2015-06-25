package com.creativemd.randomadditions.common.systems.producer.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityHeatGenerator;

public class SubContainerHeatGen extends SubContainerTileEntity{

	public SubContainerHeatGen(TileEntityRandom tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}

	@Override
	public void onGuiPacket(int control, String value, EntityPlayer player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Slot> getSlots(EntityPlayer player) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		if(tileEntity instanceof TileEntityHeatGenerator)
		{
			for (int i = 0; i < ((TileEntityHeatGenerator)tileEntity).inventory.length; i++) {
				slots.add(new Slot((IInventory) tileEntity, i, 50+i*18, 20));
			}
		}
		slots.addAll(getPlayerSlots(player, 8, 84));
		return slots;
	}

	@Override
	public boolean doesGuiNeedUpdate() {
		return true;
	}

}
