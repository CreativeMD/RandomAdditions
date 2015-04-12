package com.creativemd.randomadditions.common.systems.cable.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.creativecore.lib.Vector3d;
import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.energy.core.EnergyUtils.MachineEntry;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.systems.cable.blocks.SubBlockWire;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityWire extends TileEntityCableBase{
	
	public ArrayList<TileEntityWire> connections = new ArrayList<TileEntityWire>();
	
	public ArrayList<ChunkCoordinates> coordinates;
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 100000.0D;
    }
	
	public SubBlockWire getBlock()
	{
		return (SubBlockWire) super.getBlock();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       coordinates = new ArrayList<ChunkCoordinates>();
       int size = nbt.getInteger("size");
       for (int i = 0; i < size; i++) {
    	   int x = nbt.getInteger("X" + i);
    	   int y = nbt.getInteger("Y" + i);
    	   int z = nbt.getInteger("Z" + i);
    	   coordinates.add(new ChunkCoordinates(x, y, z));
       }
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("size", connections.size());
        for (int i = 0; i < connections.size(); i++) {
			nbt.setInteger("X" + i, connections.get(i).xCoord);
			nbt.setInteger("Y" + i, connections.get(i).yCoord);
			nbt.setInteger("Z" + i, connections.get(i).zCoord);
		}
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		nbt.setInteger("transpower", sendedTransmitPower);
		nbt.setInteger("size", connections.size());
        for (int i = 0; i < connections.size(); i++) {
			nbt.setInteger("X" + i, connections.get(i).xCoord);
			nbt.setInteger("Y" + i, connections.get(i).yCoord);
			nbt.setInteger("Z" + i, connections.get(i).zCoord);
		}
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		connections.clear();
		transmitedPower = pkt.func_148857_g().getInteger("transpower");	
		int size = pkt.func_148857_g().getInteger("size");
	       for (int i = 0; i < size; i++) {
	    	   int x = pkt.func_148857_g().getInteger("X" + i);
	    	   int y = pkt.func_148857_g().getInteger("Y" + i);
	    	   int z = pkt.func_148857_g().getInteger("Z" + i);
	    	   TileEntity tileEntity = worldObj.getTileEntity(x, y, z);
	    	   if(tileEntity instanceof TileEntityWire)
	    		   connections.add((TileEntityWire) tileEntity);
	       }
		worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord);
    }
	
	public boolean checkConnection(TileEntityWire tileEntityWire)
	{
		boolean valid = worldObj.getTileEntity(tileEntityWire.xCoord, tileEntityWire.yCoord, tileEntityWire.zCoord) == tileEntityWire;
		if(!valid)
			return false;
		Vector3d vector = new Vector3d((xCoord+0.5)-(tileEntityWire.xCoord+0.5), (yCoord+0.5)-(tileEntityWire.yCoord+0.5), (zCoord+0.5)-(tileEntityWire.zCoord+0.5));
		int amount = (int) vector.length();
		vector.normalize();
		/*vector.x /= amount;
		vector.y /= amount;
		vector.z /= amount;*/
		Vector3d pos = new Vector3d(xCoord+0.5, yCoord+0.5, zCoord+0.5);
		int i = 0;
		while(i < amount && ((int)pos.x != tileEntityWire.xCoord || (int)pos.y != tileEntityWire.yCoord || (int)pos.z != tileEntityWire.zCoord))
		{
			pos.sub(vector);
			int x = (int) Math.floor(pos.x);
			int y = (int) Math.floor(pos.y);
			int z = (int) Math.floor(pos.z);
			Block block = worldObj.getBlock(x, y, z);
			if(block.isNormalCube(worldObj, x, y, z))
				return false;
			i++;
		}
		return true;
	}
	
	public int getDistance(TileEntity entity)
	{
		double difX = xCoord-entity.xCoord;
		double difY = yCoord-entity.yCoord;
		double difZ = zCoord-entity.zCoord;
		return (int) Math.ceil(Math.sqrt(difX*difX+difY*difY+difZ*difZ));
	}
	
	public boolean removeConnection(int id)
	{
		if(id > -1 && id < connections.size())
		{
			int distance = getDistance(connections.get(id));
			connections.get(id).updateBlock();
			connections.remove(id);
			ItemStack stack = RandomItem.wire.getItemStack();
			stack.stackSize = distance;
			EntityItem item = new EntityItem(worldObj, xCoord, yCoord, zCoord, stack);
			worldObj.spawnEntityInWorld(item);
			updateBlock();
			return true;
		}
		return false;	
	}
	
	public void updateConnections()
	{
		int i = 0;
		while(i < connections.size())
		{
			if(checkConnection(connections.get(i)))
				i++;
			else
				removeConnection(i);
		}
	}
	
	public static final int updateTime = 1000;
	public int nextUpdate = 0;
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			if(coordinates != null)
			{
				for (int i = 0; i < coordinates.size(); i++) {
					TileEntity tileEntity = worldObj.getTileEntity(coordinates.get(i).posX, coordinates.get(i).posY, coordinates.get(i).posZ);
					if(tileEntity instanceof TileEntityWire)
						connections.add((TileEntityWire) tileEntity);
				}
				coordinates.clear();
				coordinates = null;
			}
			nextUpdate++;
			if(nextUpdate >= updateTime)
			{
				nextUpdate = 0;
				updateConnections();
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
		return TileEntity.INFINITE_EXTENT_AABB;
    }
	
	@Override
	public ArrayList getConnections(ForgeDirection direction) {
		ArrayList connections = new ArrayList();
		
		ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
		RotationUtils.applyDirection(getDirection(), coord);
		TileEntity entity = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
		if(entity instanceof EnergyCable)
			connections.add(entity);
		else if(entity instanceof EnergyComponent)
			connections.add(new MachineEntry((EnergyComponent) entity, getDirection().getOpposite()));
		
		connections.addAll(this.connections);
		return connections;
	}
}
