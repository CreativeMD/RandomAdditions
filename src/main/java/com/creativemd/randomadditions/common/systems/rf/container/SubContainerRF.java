package com.creativemd.randomadditions.common.systems.rf.container;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubContainerRF extends SubContainerTileEntity{

	public SubContainerRF(TileEntityRandom tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}
	
	@Override
	public void createControls() {
		
	}

	@Override
	public void onGuiPacket(int controlID, NBTTagCompound nbt,
			EntityPlayer player) {
		
	}

}
