package com.creativemd.randomadditions.common.systems.deco.blocks.tables;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityTable;

public abstract class Table {
	
	public abstract void addRecipe(ItemStack output, ItemStack plank);
	
	public abstract ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z, int plank);
	
	public static boolean canConnect(TileEntityTable sofa, ForgeDirection direction)
	{
		ChunkCoordinates chunk = new ChunkCoordinates(sofa.xCoord, sofa.yCoord, sofa.zCoord);
		RotationUtils.applyDirection(direction, chunk);
		TileEntity entity = sofa.getWorldObj().getTileEntity(chunk.posX, chunk.posY, chunk.posZ);
		if (entity instanceof TileEntityTable) {
			return true;
		}
		return false;
	}
	
	public abstract ArrayList<CubeObject> getBoxes(IBlockAccess world, int x, int y, int z);
	
	public abstract CubeObject getSelBoxes(IBlockAccess world, int x, int y, int z);
}
