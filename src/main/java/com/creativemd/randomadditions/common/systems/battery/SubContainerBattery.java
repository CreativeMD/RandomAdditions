package com.creativemd.randomadditions.common.systems.battery;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubContainerBattery extends SubContainerTileEntity{

	public SubContainerBattery(TileEntityRandom tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}
	
	@Override
	public void createControls() {
		addSlotToContainer(new Slot((IInventory) tileEntity, 0, 75, 10));
		addPlayerSlotsToContainer(player, 8, 84);
	}

	@Override
	public void onGuiPacket(int controlID, NBTTagCompound nbt, EntityPlayer player) {
		
	}

}
