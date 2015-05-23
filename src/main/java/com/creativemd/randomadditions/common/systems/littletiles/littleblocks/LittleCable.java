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
import com.creativemd.littletiles.common.utils.LittleTile.LittleTileSize;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.littletiles.SubSystemLittle;
import com.creativemd.randomadditions.common.systems.littletiles.tileentity.TileEntityLittleCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class LittleCable extends LittleTileTileEntity{

	
	public byte centerX;
	public byte centerY;
	public byte centerZ;
	
	public LittleCable()
	{
		super();
	}
	
	
	public LittleCable(int tier, TileEntity tileEntity)
	{
		super(SubSystemLittle.instance.block, tier, new LittleTileSize(1, 1, 1), tileEntity);
	}
	
	@Override
	public ItemStack getItemStack(World world)
	{
		return new ItemStack(SubSystemLittle.instance.block, 1, meta);
	}
	
	@Override
	public void onPlaced(TileEntityLittleTiles tileEntity)
	{
		super.onPlaced(tileEntity);
		centerX = minX;
		centerY = minY;
		centerZ = minZ;
		((TileEntityLittleCable)this.tileEntity).cable = this;
		connections = new Connection[6];
		updateConnections();
	}
	
	@Override
	public LittleTile copy()
	{
		LittleCable tile = new LittleCable(meta, tileEntity);
		tile.centerX = centerX;
		tile.centerY = centerY;
		tile.centerZ = centerZ;
		copyCore(tile);
		return tile;
	}
	
	@Override
	public void load(World world, NBTTagCompound nbt)
	{
		super.load(world, nbt);
		this.centerX = nbt.getByte("centerX");
		this.centerY = nbt.getByte("centerY");
		this.centerZ = nbt.getByte("centerZ");
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
	}
	
	@Override
	public void save(World world, NBTTagCompound nbt)
	{
		super.save(world,nbt);
		nbt.setByte("centerX", centerX);
		nbt.setByte("centerY", centerY);
		nbt.setByte("centerZ", centerZ);
		for (int i = 0; i < connections.length; i++) {
			if(connections[i] != null)
				nbt.setTag("co" + i, connections[i].saveConnection());
		}
	}
	
	@Override
	public void sendToClient(NBTTagCompound nbt)
	{
		super.sendToClient(nbt);
		nbt.setByte("centerX", centerX);
		nbt.setByte("centerY", centerY);
		nbt.setByte("centerZ", centerZ);
		for (int i = 0; i < connections.length; i++) {
			if(connections[i] != null)
				nbt.setTag("co" + i, connections[i].saveConnection());
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void recieveFromServer(NetworkManager net, NBTTagCompound nbt)
	{
		super.recieveFromServer(net, nbt);
		this.centerX = nbt.getByte("centerX");
		this.centerY = nbt.getByte("centerY");
		this.centerZ = nbt.getByte("centerZ");
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
	}
	
	@Override
	public void updateEntity(World world)
	{
		super.updateEntity(world);
		if(!world.isRemote)
		{
			if(connections == null)
			{
				connections = new Connection[6];
				updateConnections();
			}
		}
	}
	
	public CubeObject getCubeOfConnection(Connection connection, ForgeDirection direction)
	{
		LittleTileVec min = new LittleTileVec(centerX, centerY, centerZ);
		LittleTileVec max = new LittleTileVec(centerX+1, centerY+1, centerZ+1);;
		switch(direction)
		{
		case EAST:
			if(connection.coord == null)
				max.posX = (byte) ((connection.vec.posX+minX)/2);
			else
				max.posX = maxPos;
			min.posX = maxX;
			break;
		case WEST:
			if(connection.coord == null)
				min.posX = (byte) ((connection.vec.posX+minX)/2);
			else
				min.posX = minPos;
			max.posX = minX;
			break;
		case UP:
			if(connection.coord == null)
				max.posY = (byte) ((connection.vec.posY+minY)/2);
			else
				max.posY = maxPos;
			min.posY = maxY;
			break;
		case DOWN:
			if(connection.coord == null)
				min.posY = (byte) ((connection.vec.posY+minY)/2);
			else
				min.posY = minPos;
			max.posY = minY;
			break;
		case SOUTH:
			if(connection.coord == null)
				max.posZ = (byte) ((connection.vec.posZ+minZ)/2);
			else
				max.posZ = maxPos;
			min.posZ = maxZ;
			break;
		case NORTH:
			if(connection.coord == null)
				min.posZ = (byte) ((connection.vec.posZ+minZ)/2);
			else
				min.posZ = minPos;
			max.posZ = minZ;
			break;
		default:
			break;
		
		}
		return new CubeObject(min.posX, min.posY, min.posZ, max.posX, max.posY, max.posZ);
	}
	
	@Override
	public ArrayList<CubeObject> getCubes()
	{
		ArrayList<CubeObject> cubes = super.getCubes();
		if(connections != null)
		{
			for (int i = 0; i < connections.length; i++) {
				if(connections[i] != null)
				{
					
					cubes.add(getCubeOfConnection(connections[i], ForgeDirection.getOrientation(i)));
				}
			}
		}
		return cubes;
	}
	
	public Connection[] connections;
	
	public void connectTileEntity(TileEntityLittleTiles te, int posX, int posY, int posZ, ForgeDirection direction)
	{
		ChunkCoordinates coord = null;
		if(posX != te.xCoord || posY != te.yCoord || posZ != te.zCoord)
			coord = new ChunkCoordinates(te.xCoord, te.yCoord, te.zCoord);
		boolean needDirection = direction != null;
		for (int i = 0; i < te.tiles.size(); i++) {
			if(te.tiles.get(i) instanceof LittleCable && te.tiles.get(i) != this)
			{
				LittleCable tile = (LittleCable) te.tiles.get(i);
				ForgeDirection tempdirection = null;
				boolean x = tile.centerX == centerX;
				boolean y = tile.centerY == centerY;
				boolean z = tile.centerZ == centerZ;
				if(x && y && (!needDirection || direction == ForgeDirection.SOUTH || direction == ForgeDirection.NORTH))
					if(tile.centerZ - centerZ < 0)
						tempdirection = ForgeDirection.NORTH;
					else
						tempdirection = ForgeDirection.SOUTH;
				
				if(x && z && (!needDirection || direction == ForgeDirection.DOWN || direction == ForgeDirection.UP))
					if(tile.centerY - centerY < 0)
						tempdirection = ForgeDirection.DOWN;
					else
						tempdirection = ForgeDirection.UP;
				
				if(y && z && (!needDirection || direction == ForgeDirection.WEST || direction == ForgeDirection.EAST))
					if(tile.centerX - centerX < 0)
						tempdirection = ForgeDirection.WEST;
					else
						tempdirection = ForgeDirection.EAST;
				
				if(tempdirection != null) 
				{
					if(direction != null)
						tempdirection = direction.getOpposite();
					int index = RotationUtils.getIndex(tempdirection);
					Connection tempConnection = new Connection(coord, new LittleTileVec(tile.minX, tile.minY, tile.minZ));
					if((connections[index] != null && connections[index].getDistance(posX, posY, posZ, minX, minY, minZ) > tempConnection.getDistance(posX, posY, posZ, minX, minY, minZ)) || connections[index] == null)
					{
						
						//connections[index] = tempConnection;
						ForgeDirection connectDirection = ForgeDirection.getOrientation(index);
						Connection tempConnection2 = new Connection(null, new LittleTileVec(minX, minY, minZ));
						if(coord != null)
							tempConnection2.coord = new ChunkCoordinates(this.te.xCoord, this.te.yCoord, this.te.zCoord);
						if(this.te.isSpaceForLittleTile(getCubeOfConnection(tempConnection, connectDirection)) && te.isSpaceForLittleTile(tile.getCubeOfConnection(tempConnection2, connectDirection.getOpposite())))
							connections[index] = tempConnection;		
						//else
							//System.out.println("Failed to connect because of something! (minX:" + minX + ", minY:" + minY + ", minZ:" + minZ + ")");
					}
				}
			}
		}
	}
	
	public void updateConnections()
	{
		if(tileEntity != null && tileEntity.getWorldObj() != null)
		{
			if(!tileEntity.getWorldObj().isRemote)
			{
				int posX = tileEntity.xCoord;
				int posY = tileEntity.yCoord;
				int posZ = tileEntity.zCoord;
				if(connections == null)
					connections = new Connection[6];
				else
					Arrays.fill(connections, null);
				if(loadTileEntity())
				{
					connectTileEntity(te, posX, posY, posZ, null);
				}
				//Check neighbour blocks
				for (int i = 0; i < 6; i++) {
					ForgeDirection direction = ForgeDirection.getOrientation(i);
					ChunkCoordinates coord = new ChunkCoordinates(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
					RotationUtils.applyDirection(direction, coord);
					TileEntity te = tileEntity.getWorldObj().getTileEntity(coord.posX, coord.posY, coord.posZ);
					if(te instanceof TileEntityLittleTiles)
					{
						connectTileEntity((TileEntityLittleTiles) te, posX, posY, posZ, direction.getOpposite());
					}
					if(te instanceof EnergyComponent)
					{
						connections[i] = new Connection(coord, null);
					}
				}
				int count = 0;
				for (int i = 0; i < connections.length; i++) {
					if(connections[i] != null)
					{
						count++;
						//System.out.println("Connected to " + ForgeDirection.getOrientation(i).toString());
					}
				}
				//System.out.println("Found " + count + " connections! (minX:" + minX + ", minY:" + minY + ", minZ:" + minZ + ")");
				tileEntity.getWorldObj().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
			}
		}
	}
	
	/*public void updateBounds()
	{
		if(loadTileEntity())
		{
			for (int i = 0; i < 6; i++) {
				ForgeDirection direction = ForgeDirection.getOrientation(i);
				if(connections[i] != null)
				{
					
				}
			}
		}
	}*/
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
    	updateConnections();
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		updateConnections();
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
				return Math.sqrt(Math.pow(x/16D-vec.posX/16D, 2) + Math.pow(y/16D-vec.posY/16D, 2) + Math.pow(z/16D-vec.posZ/16D, 2));
			return Math.sqrt(Math.pow((posX+x/16D)-(coord.posX+vec.posX/16D), 2) + Math.pow((posY+y/16D)-(coord.posY+vec.posY/16D), 2) + Math.pow((posZ+z/16D)-(coord.posZ+vec.posZ/16D), 2));
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
				nbt.setByte("vx", vec.posX);
				nbt.setByte("vy", vec.posY);
				nbt.setByte("vz", vec.posZ);
			}
			return nbt;
		}
	}
}
