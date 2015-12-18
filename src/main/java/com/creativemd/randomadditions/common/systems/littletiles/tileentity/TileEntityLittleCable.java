package com.creativemd.randomadditions.common.systems.littletiles.tileentity;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.randomadditions.common.energy.api.IRAReciever;
import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.energy.core.EnergyUtils.MachineEntry;
import com.creativemd.randomadditions.common.systems.littletiles.blocks.SubBlockLittleCable;
import com.creativemd.randomadditions.common.systems.littletiles.littleblocks.LittleCable;

import cpw.mods.fml.common.Loader;

public class TileEntityLittleCable extends EnergyCable{
	
	public LittleCable cable;
	
	@Override
	public int getTransmitablePower() {
		return SubBlockLittleCable.power[cable.meta];
	}

	@Override
	public ArrayList getConnections(ForgeDirection direction) {
		if(worldObj == null)
			return new ArrayList();
		ArrayList connections = new ArrayList();
		for (int i = 0; i < cable.connections.length; i++) {
			ForgeDirection blockdirection = ForgeDirection.getOrientation(i);
			if(cable.connections[i] != null) //&& (direction == null || blockdirection != direction.getOpposite()))
			{
				ChunkCoordinates coord = cable.connections[i].coord;
				TileEntity te = null;
				if(coord != null)
					te = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
				else
					te = worldObj.getTileEntity(cable.te.xCoord, cable.te.yCoord, cable.te.zCoord);
				if(te != null)
				{
					if(te instanceof EnergyComponent)
						connections.add(new MachineEntry((EnergyComponent) te, blockdirection.getOpposite()));
					else if(te instanceof IRAReciever)
						connections.add(new MachineEntry((IRAReciever) te, ForgeDirection.getOrientation(i).getOpposite()));
					else if(te instanceof TileEntityLittleTiles && cable.connections[i].vec != null){
						LittleTile tile = ((TileEntityLittleTiles) te).getTile((byte)cable.connections[i].vec.x, (byte)cable.connections[i].vec.y, (byte)cable.connections[i].vec.z);
						if(tile instanceof LittleCable)
							connections.add(((LittleCable) tile).tileEntity);
					}
				}				
			}
		}
		return connections;
	}
	
	public static TileEntityLittleCable getConnection(World world, ChunkCoordinates origin, ChunkCoordinates coord, ForgeDirection direction)
	{
		if(!Loader.isModLoaded("littletiles"))
			return null;
		TileEntity te = world.getTileEntity(coord.posX, coord.posY, coord.posZ);
		if(te instanceof TileEntityLittleTiles)
		{
			TileEntityLittleTiles teTiles = (TileEntityLittleTiles) te;
			for (int i = 0; i < teTiles.tiles.size(); i++) {
				if(teTiles.tiles.get(i) instanceof LittleCable)
				{
					LittleCable cable = (LittleCable) teTiles.tiles.get(i);
					int index = RotationUtils.getIndex(direction.getOpposite());
					if(cable.connections[index] != null && cable.connections[index].coord != null && cable.connections[index].coord.equals(origin))
						return (TileEntityLittleCable) cable.tileEntity;
				}
			}
		}
		return null;
	}

}
