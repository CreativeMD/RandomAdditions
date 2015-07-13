package com.creativemd.randomadditions.common.systems.producer.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityHeatGenerator;

public class SubContainerHeatGen extends SubContainerTileEntity{

	public SubContainerHeatGen(TileEntityRandom tileEntity, EntityPlayer player) {
		super(tileEntity, player);
	}

	@Override
	public void createControls() {
		if(tileEntity instanceof TileEntityHeatGenerator)
		{
			for (int i = 0; i < ((TileEntityHeatGenerator)tileEntity).inventory.length; i++) {
				addSlotToContainer(new Slot((IInventory) tileEntity, i, 50+i*18, 20));
			}
		}
		addPlayerSlotsToContainer(player);
	}

	@Override
	public void onGuiPacket(int controlID, NBTTagCompound nbt,
			EntityPlayer player) {
		
		
	}

}
