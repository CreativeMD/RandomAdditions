package com.creativemd.randomadditions.common.systems.cable.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.energy.api.IRAReciever;
import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.energy.core.EnergyCore;
import com.creativemd.randomadditions.common.energy.core.EnergyUtils.MachineEntry;
import com.creativemd.randomadditions.common.systems.cable.blocks.SubBlockCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityCable extends TileEntityCableBase{
	
	public Block block;
	public int meta;
	
	public boolean isCoverd()
	{
		return block != null;
	}
	
	public boolean[] connection = new boolean[6];
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 100000.0D;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       for (int i = 0; i < connection.length; i++) {
			connection[i] = nbt.getBoolean("connect" + i);
       }
       String name = nbt.getString("block");
       if(!name.equals(""))
       {
    	   block = Block.getBlockFromName(name);
    	   if(block instanceof BlockAir)
    		   block = null;
    	   meta = nbt.getInteger("meta");
       }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        for (int i = 0; i < connection.length; i++) {
			nbt.setBoolean("connect" + i, connection[i]);
		}
        if(isCoverd())
        {
	        nbt.setString("block", Block.blockRegistry.getNameForObject(block));
	        nbt.setInteger("meta", meta);
        }
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
	{
		super.getDescriptionNBT(nbt);
		nbt.setInteger("transpower", sendedTransmitPower);
		for (int i = 0; i < connection.length; i++) {
			nbt.setBoolean("connect" + i, connection[i]);
		}
		if(isCoverd())
        {
	        nbt.setString("block", Block.blockRegistry.getNameForObject(block));
	        nbt.setInteger("meta", meta);
        }
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		transmitedPower = pkt.func_148857_g().getInteger("transpower");	
		for (int i = 0; i < connection.length; i++) {
			connection[i] = pkt.func_148857_g().getBoolean("connect" + i);
		}
		String name = pkt.func_148857_g().getString("block");
		if(!name.equals(""))
		{
			block = Block.getBlockFromName(name);
			if(block instanceof BlockAir)
				block = null;
			meta = pkt.func_148857_g().getInteger("meta");
		}
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
	
	public SubBlockCable getBlock()
	{
		return (SubBlockCable) super.getBlock();
	}
	
	@SideOnly(Side.CLIENT)
	public double getRotation(double nanoTime)
	{
		return (nanoTime/30000000D)*((float)transmitedPower/10)/(getBlock().speedFactor);
	}
	
	public void updateConnections()
	{
		updateConnections(null);
	}
	
	public boolean canConnect(ForgeDirection direction)
	{
		ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
		RotationUtils.applyDirection(direction, coord);
		TileEntity entity = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
		if(entity instanceof EnergyCore)
		{
			updateConnection(direction, true);
			return true;
		}
		else
		{
			updateConnection(direction, false);
			return false;
		}
	}
	
	public void updateConnection(ForgeDirection direction, boolean value)
	{
		if(connection[RotationUtils.getIndex(direction)] != value)
		{
			connection[RotationUtils.getIndex(direction)] = value;
			updateBlock();
		}
		/*ChunkCoordinates coord = applyDirection(direction);
		TileEntity tileEntity = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
		if(tileEntity instanceof TileEntityCable && ((TileEntityCable) tileEntity).connection[RotationUtils.getIndex(direction.getOpposite())] != connection[RotationUtils.getIndex(direction)])
		{
			if(connection[RotationUtils.getIndex(direction)])
			{
				Arrays.fill(((EnergyCable) tileEntity).connection, false);
				((EnergyCable) tileEntity).connection[getConnectIndex(direction.getOpposite())] = connection[getConnectIndex(direction)];
				((EnergyCable) tileEntity).updateConnections(direction.getOpposite());
			}else
				((EnergyCable) tileEntity).connection[getConnectIndex(direction.getOpposite())] = false;
		}*/
	}
	
	public void updateConnections(ForgeDirection direction)
	{
		for (int i = 0; i < 6; i++) {
			if(direction == null || direction != ForgeDirection.getOrientation(i))
				canConnect(ForgeDirection.getOrientation(i));
		}
	}
	
	public boolean needRotationBlock()
	{
		boolean connectX = connection[0] | connection[1];
		boolean connectY = connection[2] | connection[3];
		boolean connectZ = connection[4] | connection[5];
		boolean bothX = connection[0] && connection[1];
		boolean bothY = connection[2] && connection[3];
		boolean bothZ = connection[4] && connection[5];
		return (connectX && connectY) | (connectX && connectZ) | (connectY && connectZ) | !(bothX | bothY | bothZ);
	}

	@Override
	public ArrayList getConnections(ForgeDirection direction) {
		ArrayList connections = new ArrayList();
		for (int i = 0; i < connection.length; i++) {
			if(connection[i])
			{
				ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
				RotationUtils.applyDirection(ForgeDirection.getOrientation(i), coord);
				TileEntity entity = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
				if(entity instanceof EnergyCable)
					connections.add(entity);
				else if(entity instanceof EnergyComponent)
					connections.add(new MachineEntry((EnergyComponent) entity, ForgeDirection.getOrientation(i).getOpposite()));
				else if(entity instanceof IRAReciever)
					connections.add(new MachineEntry((IRAReciever) entity, ForgeDirection.getOrientation(i).getOpposite()));
			}
		}
		return connections;
	}
}
