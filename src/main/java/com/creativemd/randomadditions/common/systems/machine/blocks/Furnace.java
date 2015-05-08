package com.creativemd.randomadditions.common.systems.machine.blocks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;

import cpw.mods.fml.common.registry.GameRegistry;

public class Furnace extends SubBlockMachine{

	public Furnace(SubBlockSystem system) {
		super("Furnace", system);
	}

	@Override
	public int getNumberOfInputs() {
		return 1;
	}

	@Override
	public void renderProgressField(double percent) {
		for (int i = 0; i < 6; i++) {
        	RenderHelper2D.renderItem(new ItemStack(Blocks.fire), 2+i*15, 32, percent);
        	RenderHelper2D.renderItem(new ItemStack(Blocks.fire), 2+i*15, 3, percent, 180);
		}
	}

	@Override
	public void addRecipe(ItemStack battery, ItemStack output) {
		GameRegistry.addRecipe(output, new Object[]
				{
				"XXX", "WAW", "WWW", 'A', battery, 'X', Blocks.iron_bars, 'W', Blocks.brick_block
				});
	}
	
	@Override
	public ArrayList<MachineRecipe> getRecipes()
	{
		ArrayList<MachineRecipe> recipes = new ArrayList<MachineRecipe>();
		recipes.addAll(super.getRecipes());
		Iterator iterator = FurnaceRecipes.smelting().getSmeltingList().entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry entry = (Entry) iterator.next();
			recipes.add(new MachineRecipe((ItemStack)entry.getKey(), (ItemStack)entry.getValue(), 250));
		}
		return recipes;
	}
	
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {	
		TileEntityMachine machine = (TileEntityMachine) par1World.getTileEntity(par2, par3, par4);
		if(machine.progress > 0)
		{
		    Random random = par1World.rand;
		    double d = 0.0625D;
		
		    for (int i = 0; i < 10; i++)
		    {
		
		    	double d1 = (float)par2 + random.nextFloat();
		        double d2 = (float)par3 + random.nextFloat();
		        double d3 = (float)par4 + random.nextFloat();	
		        par1World.spawnParticle("smoke", d1, d2, d3, 0.0D, random.nextDouble()*0.05, 0.0D);
		    }
		}
    }
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		Block block = Blocks.brick_block;
		Block block2 = Blocks.stone;
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.addAll(CubeObject.getBlock(new CubeObject(), 0.1, ForgeDirection.NORTH));
		cubes.add(new CubeObject(0.9, 0, 0.1, 1, 1, 0.2));
		cubes.add(new CubeObject(0.9, 0, 0.8, 1, 1, 0.9));
		cubes.add(new CubeObject(0.9, 0.8, 0.2, 1, 1, 0.8));
		cubes.add(new CubeObject(0.9, 0.3, 0.2, 1, 0.6, 0.8));
		cubes.add(new CubeObject(0.9, 0, 0.2, 1, 0.1, 0.8));
		cubes.add(new CubeObject(0.8, 0.1, 0.2, 0.9, 0.9, 0.9, Blocks.coal_block));
		
		cubes.addAll(CubeObject.getGrid(new CubeObject(0.925, 0.6, 0.2, 0.975, 0.8, 0.8, Blocks.iron_block), 0.04, 5));
		
		if(world != null)
		{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityMachine && ((TileEntityMachine)tileEntity).progress > 0 && ((TileEntityMachine) tileEntity).active)
				cubes.add(new CubeObject(0.9, 0.1, 0.2, 0.95, 0.5, 0.9, Blocks.fire));
		}
		return cubes;
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.brick_block.getBlockTextureFromSide(side);
	}
	
	@Override
	public void registerRecipes() {
		FurnaceRecipes.smelting().func_151393_a(Blocks.sandstone, new ItemStack(Blocks.glass, 4), 0.4F);
	}
	
	
}
