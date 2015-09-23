package com.creativemd.randomadditions.common.systems.littletiles;

import java.util.ArrayList;

import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.common.utils.small.LittleTileBox;
import com.creativemd.littletiles.utils.BoxShiftHandler;
import com.creativemd.randomadditions.common.systems.littletiles.littleblocks.LittleCable;
import com.creativemd.randomadditions.common.systems.littletiles.littleblocks.LittleCable.Connection;
import com.creativemd.randomadditions.common.systems.littletiles.tileentity.TileEntityLittleCable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class LittleShiftHandler extends BoxShiftHandler{

	@Override
	public ArrayList<LittleTileBox> getBoxes(World world, int x, int y, int z) {
		ArrayList<LittleTileBox> boxes = new ArrayList<LittleTileBox>();
		
		TileEntity mainTile = world.getTileEntity(x, y, z);
		if(mainTile instanceof TileEntityLittleTiles)
		{
			for (int i = 0; i < ((TileEntityLittleTiles) mainTile).tiles.size(); i++) {
				if(((TileEntityLittleTiles) mainTile).tiles.get(i) instanceof LittleCable)
				{
					for (int j = 0; j < 6; j++) {
						if(((LittleCable) ((TileEntityLittleTiles) mainTile).tiles.get(i)).connections[j] != null)
							continue;
						ForgeDirection direction = ForgeDirection.getOrientation(j);
						ChunkCoordinates coord = new ChunkCoordinates(x, y, z);
						RotationUtils.applyDirection(direction, coord);
						Connection tempConnection = new Connection(coord, null);
						boxes.add(tempConnection.getConnectionBox(((TileEntityLittleTiles) mainTile).tiles.get(i).cornerVec.copy(), direction));
					}
				}
			}
		}
		
		for (int j = 0; j < 6; j++) {
			ForgeDirection direction = ForgeDirection.getOrientation(j);
			ChunkCoordinates coord = new ChunkCoordinates(x, y, z);
			RotationUtils.applyDirection(direction, coord);
			
			TileEntity te = world.getTileEntity(coord.posX, coord.posY, coord.posZ);
			if(te instanceof TileEntityLittleTiles)
			{
				for (int i = 0; i < ((TileEntityLittleTiles) te).tiles.size(); i++) {
					if(((TileEntityLittleTiles) te).tiles.get(i) instanceof LittleCable)
					{
						LittleCable cable = (LittleCable) ((TileEntityLittleTiles) te).tiles.get(i);
						if(cable.connections[RotationUtils.getIndex(direction.getOpposite())] == null)
						{
							LittleTileBox box = cable.boundingBoxes.get(0).copy();
							switch(direction)
							{
							case EAST:
							case WEST:
								box.minX = LittleTile.minPos;
								box.maxX = LittleTile.maxPos;
								break;
							case UP:
							case DOWN:
								box.minY = LittleTile.minPos;
								box.maxY = LittleTile.maxPos;
								break;
							case SOUTH:
							case NORTH:
								box.minZ = LittleTile.minPos;
								box.maxZ = LittleTile.maxPos;
								break;
							default:
								break;
							}
							boxes.add(box);
						}
					}
				}
			}
			
		}
		
		return boxes;
	}

}
