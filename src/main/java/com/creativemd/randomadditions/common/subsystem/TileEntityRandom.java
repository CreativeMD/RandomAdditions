package com.creativemd.randomadditions.common.subsystem;

import com.creativemd.creativecore.common.tileentity.TileEntityCreative;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityRandom extends TileEntityCreative{
	
	public byte direction;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       direction = nbt.getByte("direction");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setByte("direction", direction);
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
	{
		nbt.setByte("direction", direction);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		direction = pkt.func_148857_g().getByte("direction");
		
    }
	
	public ForgeDirection getDirection()
	{
		return ForgeDirection.getOrientation(direction);
	}
}
