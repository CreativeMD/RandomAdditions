package com.creativemd.randomadditions.common.energy.core;

import java.util.ArrayList;

import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import com.creativemd.randomadditions.common.energy.core.EnergyUtils.MachineEntry;

public abstract class EnergyCable extends EnergyCore{
	
	public abstract int getTransmitablePower();
	
	public int transmitedPower;
	public int sendedTransmitPower;
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(sendedTransmitPower != transmitedPower)
			{
				sendedTransmitPower = transmitedPower;
				updateBlock();
			}
			transmitedPower = 0;
		}
	}
	
}
