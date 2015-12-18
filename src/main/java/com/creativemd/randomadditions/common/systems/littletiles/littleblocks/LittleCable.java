package com.creativemd.randomadditions.common.systems.littletiles.littleblocks;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.client.LittleTilesClient;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.common.utils.LittleTileTileEntity;
import com.creativemd.littletiles.common.utils.small.LittleTileBox;
import com.creativemd.littletiles.common.utils.small.LittleTileVec;
import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.littletiles.SubSystemLittle;
import com.creativemd.randomadditions.common.systems.littletiles.tileentity.TileEntityLittleCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LittleCable extends LittleTileTileEntity{
	
	public LittleCable()
	{
		super();
	}
	
	public LittleCable(TileEntityLittleCable te, Block block, int meta)
	{
		super(block, meta, te);
	}
	
	public Connection[] connections = new Connection[6];
	
	public void updateConnections()
	{
		boundingBoxes.clear();
		boundingBoxes.add(new LittleTileBox(cornerVec.x, cornerVec.y, cornerVec.z, cornerVec.x+1, cornerVec.y+1, cornerVec.z+1));
		
		for (int i = 0; i < connections.length; i++) {
			connections[i] = null;
		}
		
		//Connect to internal Tiles
		connectToTE(te, null, boundingBoxes.get(0));
		
		//Connect to neighbors
		for (int i = 0; i < connections.length; i++) {
			ForgeDirection direction = ForgeDirection.getOrientation(i);
			ChunkCoordinates coord = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord);
			RotationUtils.applyDirection(direction, coord);
			
			TileEntity teNeigh = te.getWorldObj().getTileEntity(coord.posX, coord.posY, coord.posZ);
			if(teNeigh instanceof TileEntityLittleTiles)
				connectToTE((TileEntityLittleTiles) teNeigh, direction, boundingBoxes.get(0));
			else if(teNeigh instanceof EnergyComponent)
				overrrideConnection(direction, new Connection(coord, null));
		}
		
		for (int i = 0; i < connections.length; i++) {
			if(connections[i] != null)
			{
				LittleTileBox box = connections[i].getConnectionBox(cornerVec, ForgeDirection.getOrientation(i));
				if(te.isSpaceForLittleTile(box))
					boundingBoxes.add(box);
				else
					connections[i] = null;
			}
		}
		
		markForUpdate();
	}
	
	public void updateBoxes()
	{
		boundingBoxes.clear();
		boundingBoxes.add(new LittleTileBox(cornerVec.x, cornerVec.y, cornerVec.z, cornerVec.x+1, cornerVec.y+1, cornerVec.z+1));
		
		for (int i = 0; i < connections.length; i++) {
			if(connections[i] != null)
			{
				LittleTileBox box = connections[i].getConnectionBox(cornerVec, ForgeDirection.getOrientation(i));
				if(te.isSpaceForLittleTile(box))
					boundingBoxes.add(box);
				else
					connections[i] = null;
			}
		}
		if(te.getWorldObj() != null && te.getWorldObj().isRemote)
			markForUpdate();
	}
	
	@Override
	public ItemStack getDrop()
	{
		return new ItemStack(SubSystemLittle.instance.block, 1, meta);
	}
	
	@Override
	public void loadTileExtra(NBTTagCompound nbt)
	{
		super.loadTileExtra(nbt);
		((TileEntityLittleCable)tileEntity).cable = this;
		if(connections == null)
			connections = new Connection[6];
		for (int i = 0; i < 6; i++) {
			if(nbt.hasKey("co" + i))
			{
				connections[i] = new Connection(nbt.getCompoundTag("co"+ i));
				if(connections[i].isInvalid())
					connections[i] = null;
			}
		}
		updateBoxes();
	}
	
	@Override
	public void saveTileExtra(NBTTagCompound nbt)
	{
		super.saveTileExtra(nbt);
		for (int i = 0; i < connections.length; i++) {
			if(connections[i] != null)
				nbt.setTag("co" + i, connections[i].saveConnection());
		}
	}
	
	@Override
	public void updatePacket(NBTTagCompound nbt)
	{
		super.updatePacket(nbt);
		for (int i = 0; i < connections.length; i++) {
			if(connections[i] != null)
				nbt.setTag("co" + i, connections[i].saveConnection());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void receivePacket(NBTTagCompound nbt, NetworkManager net)
	{
		super.receivePacket(nbt, net);
		((TileEntityLittleCable)tileEntity).cable = this;
		if(connections == null)
			connections = new Connection[6];
		else
			Arrays.fill(connections, null);
		for (int i = 0; i < 6; i++) {
			if(nbt.hasKey("co" + i))
			{
				connections[i] = new Connection(nbt.getCompoundTag("co"+ i));
				if(connections[i].isInvalid())
					connections[i] = null;
			}
		}
		updateBoxes();
	}
	
	@Override
	public void onNeighborChange()
	{
		updateConnections();
	}
	
	public void overrrideConnection(ForgeDirection direction, Connection newConnect)
	{
		int index = RotationUtils.getIndex(direction);
		double distance = Double.MAX_VALUE;
		if(connections[index] != null)
			distance = connections[index].getDistance(te.xCoord, te.yCoord, te.zCoord, (byte)cornerVec.x, (byte)cornerVec.y, (byte)cornerVec.z);
		
		if(distance > newConnect.getDistance(te.xCoord, te.yCoord, te.zCoord, (byte)cornerVec.x, (byte)cornerVec.y, (byte)cornerVec.z))
			connections[index] = newConnect;
	}
	
	public void connectToTE(TileEntityLittleTiles te, ForgeDirection direction, LittleTileBox cableBox)
	{
		for (int i = 0; i < te.tiles.size(); i++) {
			if(te.tiles.get(i) instanceof LittleTileTileEntity && te.tiles.get(i) != this && te.tiles.get(i).boundingBoxes.size() > 0 && (((LittleTileTileEntity) te.tiles.get(i)).tileEntity instanceof EnergyCable || ((LittleTileTileEntity) te.tiles.get(i)).tileEntity instanceof EnergyComponent))
			{
				LittleTileBox box = te.tiles.get(i).boundingBoxes.get(0).copy();
				if(direction != null)
				{
					ChunkCoordinates coord = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord);
					//RotationUtils.applyDirection(direction, coord);
					
					box.applyDirection(direction);
					
					ForgeDirection side = cableBox.faceTo(box);
					if(side != ForgeDirection.UNKNOWN)
					{
						Connection otherSide = new Connection(new ChunkCoordinates(this.te.xCoord, this.te.yCoord, this.te.zCoord), null);
						LittleTileBox otherBox = otherSide.getConnectionBox(te.tiles.get(i).cornerVec.copy(), direction.getOpposite());
						if(te.isSpaceForLittleTile(otherBox.getBox(), te.tiles.get(i)))
							overrrideConnection(direction, new Connection(coord, te.tiles.get(i).cornerVec.copy()));
					}
				}else{
					ForgeDirection side = cableBox.faceTo(box);
					if(side != ForgeDirection.UNKNOWN)
						overrrideConnection(side, new Connection(null, te.tiles.get(i).cornerVec.copy()));
				}
			}
		}
	}
	
	public static class Connection {
		
		public ChunkCoordinates coord;
		public LittleTileVec vec;
		
		public Connection(NBTTagCompound nbt)
		{
			if(nbt.hasKey("x"))
			{
				coord = new ChunkCoordinates(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
			}
			if(nbt.hasKey("vx"))
			{
				vec = new LittleTileVec(nbt.getByte("vx"), nbt.getByte("vy"), nbt.getByte("vz"));
			}
		}
		
		public boolean isInvalid()
		{
			return coord == null && vec == null;
		}
		
		public Connection(ChunkCoordinates coord, LittleTileVec vec)
		{
			this.coord = coord;
			this.vec = vec;
		}
		
		public double getDistance(int posX, int posY, int posZ, byte x, byte y, byte z)
		{
			if(vec == null)
				return Math.sqrt(Math.pow((posX+x/16D)-coord.posX, 2) + Math.pow((posY+y/16D)-coord.posY, 2) + Math.pow((posZ+z/16D)-coord.posZ, 2));
			if(coord == null)
				return Math.sqrt(Math.pow(x/16D-vec.x/16D, 2) + Math.pow(y/16D-vec.y/16D, 2) + Math.pow(z/16D-vec.z/16D, 2));
			return Math.sqrt(Math.pow((posX+x/16D)-(coord.posX+vec.x/16D), 2) + Math.pow((posY+y/16D)-(coord.posY+vec.y/16D), 2) + Math.pow((posZ+z/16D)-(coord.posZ+vec.z/16D), 2));
		}
		
		public LittleTileBox getConnectionBox(LittleTileVec corner, ForgeDirection direction)
		{
			LittleTileVec min = corner.copy();
			LittleTileVec max = corner.copy();
			max.addVec(new LittleTileVec(1, 1, 1));
			switch(direction)
			{
			case EAST:
				min.x += 1;
				if(coord == null)
					max.x = (byte) (Math.ceil((min.x + vec.x)/2D));
				else
					max.x = LittleTile.maxPos;
				break;
			case WEST:
				if(coord == null)
					min.x = (byte) (Math.ceil((corner.x + vec.x + 1)/2D));
				else
					min.x = LittleTile.minPos;
				max.x = corner.x;
				break;
			case UP:
				min.y += 1;
				if(coord == null)
					max.y = (byte) (Math.ceil((min.y + vec.y)/2D));
				else
					max.y = LittleTile.maxPos;
				break;
			case DOWN:
				if(coord == null)
					min.y = (byte) (Math.ceil((corner.y + vec.y + 1)/2D));
				else
					min.y = LittleTile.minPos;
				max.y = corner.y;
				break;
			case SOUTH:
				min.z += 1;
				if(coord == null)
					max.z = (byte) (Math.ceil((min.z + vec.z)/2D));
				else
					max.z = LittleTile.maxPos;
				break;
			case NORTH:
				if(coord == null)
					min.z = (byte) (Math.ceil((corner.z + vec.z + 1)/2D));
				else
					min.z = LittleTile.minPos;
				max.z = corner.z;
				break;
			default:
				break;
			}
			return new LittleTileBox(min, max);
		}
		
		public NBTTagCompound saveConnection()
		{
			NBTTagCompound nbt = new NBTTagCompound();
			if(coord != null)
			{
				nbt.setInteger("x", coord.posX);
				nbt.setInteger("y", coord.posY);
				nbt.setInteger("z", coord.posZ);
			}
			if(vec != null)
			{
				nbt.setByte("vx", (byte) vec.x);
				nbt.setByte("vy", (byte) vec.y);
				nbt.setByte("vz", (byte) vec.z);
			}
			return nbt;
		}
	}
}
