package com.creativemd.randomadditions.common.systems.deco.blocks.tables;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityTable;

import cpw.mods.fml.common.registry.GameRegistry;

public class DefaultTable extends Table{

	@Override
	public void addRecipe(ItemStack output, ItemStack plank) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"PPP", "SAS", 'S', Items.stick, 'P', plank
				}));
	}
	
	@Override
	public CubeObject getSelBoxes(IBlockAccess world, int x, int y, int z) {
		return new CubeObject(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public ArrayList<CubeObject> getBoxes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0.8, 0, 1, 0.9, 1));
		boolean pX = false;
		boolean nX = false;
		boolean pZ = false;
		boolean nZ = false;
		
		if(world != null)
		{
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if(tileentity instanceof TileEntityTable)
			{
				TileEntityTable table = (TileEntityTable) tileentity;
				pX = canConnect(table, ForgeDirection.EAST);
				nX = canConnect(table, ForgeDirection.WEST);
				pZ = canConnect(table, ForgeDirection.SOUTH);
				nZ = canConnect(table, ForgeDirection.NORTH);
			}
		}
		
		double offset = 0.05;
		
		//Legs
		if(!nX && !nZ)
			cubes.add(new CubeObject(offset, 0, offset, offset+0.1, 1, offset+0.1));
		
		if(!pX && !nZ)
			cubes.add(new CubeObject(0.9-offset, 0, offset, 1-offset, 1, 0.1+offset));
		
		if(!nX && !pZ)
			cubes.add(new CubeObject(offset, 0, 0.9-offset, 0.1+offset, 1, 1-offset));
		if(!pX && !pZ)
			cubes.add(new CubeObject(0.9-offset, 0, 0.9-offset, 1-offset, 1, 1-offset));
		return cubes;
	}

	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world,
			int x, int y, int z, int plank) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0.8, 0, 1, 0.9, 1, Blocks.planks.getIcon(0, plank)));
		boolean pX = false;
		boolean nX = false;
		boolean pZ = false;
		boolean nZ = false;
		
		if(world != null)
		{
			TileEntity tileentity = world.getTileEntity(x, y, z);
			if(tileentity instanceof TileEntityTable)
			{
				TileEntityTable table = (TileEntityTable) tileentity;
				pX = canConnect(table, ForgeDirection.EAST);
				nX = canConnect(table, ForgeDirection.WEST);
				pZ = canConnect(table, ForgeDirection.SOUTH);
				nZ = canConnect(table, ForgeDirection.NORTH);
			}
		}
		
		double offset = 0.05;
		
		//Legs
		if(!nX && !nZ)
			cubes.add(new CubeObject(offset, 0, offset, offset+0.1, 1, offset+0.1, Blocks.planks.getIcon(0, plank)));
		
		if(!pX && !nZ)
			cubes.add(new CubeObject(0.9-offset, 0, offset, 1-offset, 1, 0.1+offset, Blocks.planks.getIcon(0, plank)));
		
		if(!nX && !pZ)
			cubes.add(new CubeObject(offset, 0, 0.9-offset, 0.1+offset, 1, 1-offset, Blocks.planks.getIcon(0, plank)));
		if(!pX && !pZ)
			cubes.add(new CubeObject(0.9-offset, 0, 0.9-offset, 1-offset, 1, 1-offset, Blocks.planks.getIcon(0, plank)));
		return cubes;
	}

}
