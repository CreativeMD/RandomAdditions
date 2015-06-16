package com.creativemd.randomadditions.common.systems.battery;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubContainerBattery extends SubContainerTileEntity{

	public SubContainerBattery(TileEntityRandom tileEntity) {
		super(tileEntity);
	}

	@Override
	public void onGuiPacket(int control, String value, EntityPlayer player) {
		
	}

	@Override
	public boolean doesGuiNeedUpdate() {
		return true;
	}

	@Override
	public ArrayList<Slot> getSlots(EntityPlayer player) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(new Slot((IInventory) tileEntity, 0, 75, 10));
		slots.addAll(getPlayerSlots(player, 8, 84));
		return slots;
	}

}
