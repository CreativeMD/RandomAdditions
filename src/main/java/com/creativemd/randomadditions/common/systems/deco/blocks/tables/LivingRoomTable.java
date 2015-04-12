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

public class LivingRoomTable extends Table{

	@Override
	public void addRecipe(ItemStack output, ItemStack plank) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"GGG", "ASA", "APA", 'S', Items.stick, 'P', plank, 'G', Blocks.glass
				}));
	}
	
	@Override
	public CubeObject getSelBoxes(IBlockAccess world, int x, int y, int z) {
		return new CubeObject(0, 0, 0, 1, 0.6, 1);
	}

	@Override
	public ArrayList<CubeObject> getBoxes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0, 0, 1, 0.6, 1));
		return cubes;
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world,
			int x, int y, int z, int plank) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		
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
		
		double size = 0.9;
		double minX = 0.5-size/2;
		double minZ = 0.5-size/2;
		double maxX = 0.5+size/2;
		double maxZ = 0.5+size/2;
		
		if(nX)
			minX = 0;
		if(nZ)
			minZ = 0;
		if(pX)
			maxX = 1;
		if(pZ)
			maxZ = 1;
		
		cubes.add(new CubeObject(minX, 0.5, minZ, maxX, 0.6, maxZ, Blocks.glass));
		cubes.add(new CubeObject(0.45, 0, 0.45, 0.55, 0.5, 0.55, Blocks.planks.getIcon(0, plank)));
		return cubes;
	}

}
