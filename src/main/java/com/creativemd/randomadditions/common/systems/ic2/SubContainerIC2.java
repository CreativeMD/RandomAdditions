package com.creativemd.randomadditions.common.systems.ic2;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class SubContainerIC2 extends SubContainerTileEntity{

	public SubContainerIC2(TileEntityRandom tileEntity, EntityPlayer player) {
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
