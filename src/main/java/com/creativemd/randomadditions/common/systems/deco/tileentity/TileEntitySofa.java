package com.creativemd.randomadditions.common.systems.deco.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

public class TileEntitySofa extends TileEntityRandom{
	
	public int plank;
	public int wool;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       plank = nbt.getInteger("plank");
       wool = nbt.getInteger("wool");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("plank", plank);
		nbt.setInteger("wool", wool);
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
	{
		super.getDescriptionNBT(nbt);
		nbt.setInteger("plank", plank);
		nbt.setInteger("wool", wool);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		plank = pkt.func_148857_g().getInteger("plank");
		wool = pkt.func_148857_g().getInteger("wool");
    }
	
}
