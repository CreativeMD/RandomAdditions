package com.creativemd.randomadditions.common.systems.deco.blocks;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityChair;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockChair extends SubBlockSit{
	
	public SubBlockChair(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.planks.getBlockTextureFromSide(0);
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityChair)
		{
			TileEntityChair sofa = (TileEntityChair) tileEntity;
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			sofa.plank = stack.stackTagCompound.getInteger("plank");
		}
	}
	
	@Override
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = super.getExtraDrop(tileEntity);
		if(tileEntity instanceof TileEntityChair)
		{
			ItemStack stack = new ItemStack(system.block, 1, getID());
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("plank", ((TileEntityChair) tileEntity).plank);
			stacks.add(stack);
		}
		return stacks;
	}
	
	@Override
	public ArrayList<ItemStack> getDrop(IBlockAccess world, int x, int y, int z, int fortune)
	{
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public ArrayList<CubeObject> getBoxes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0.1, 0, 0.1, 0.9, 0.7, 0.9));
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityChair)
			cubes.add(CubeObject.rotateCube(new CubeObject(0.1, 0.6, 0.1, 0.2, 1.3, 0.9), ((TileEntityChair) tileEntity).getDirection()));
		return cubes;
	}
	
	@Override
	public CubeObject getSelBox(IBlockAccess world, int x, int y, int z)
	{
		return new CubeObject(0.1, 0, 0.1, 0.9, 0.7, 0.9);
	}
	
	@Override
	public ArrayList<ItemStack> getItemStacks()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(system.block, 1, getID());
		stack.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < SubBlockSofa.planksAmount; i++) {
			ItemStack showStack = stack.copy();
			showStack.stackTagCompound = new NBTTagCompound();
			showStack.stackTagCompound.setInteger("plank", i);
			stacks.add(showStack);
		}
		return stacks;
	}
	
	@Override
	public int getRotation()
	{
		return 1;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		int plank = 0;
		if(par1ItemStack != null && par1ItemStack.stackTagCompound != null)
		{
			plank = par1ItemStack.stackTagCompound.getInteger("plank");
		}
		
		return "tile.BlockRA" + system.name + "." + name + plank; 
    }
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityChair();
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		stack = renderedItemStack;
		int plank = 0;
		if(world == null)
		{
			if(stack != null && stack.stackTagCompound != null)
			{
				plank = stack.stackTagCompound.getInteger("plank");
			}
		}else{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityChair)
			{
				TileEntityChair sofa = (TileEntityChair) tileEntity;
				plank = sofa.plank;
			}
		}
		
		double size = 0.76;
		double minSize = 0.5-size/2;
		double maxSize = 0.5+size/2;
		
		cubes.add(new CubeObject(minSize, 0.5D, minSize, maxSize, 0.6D, maxSize, Blocks.planks.getIcon(0, plank)));
		
		//Legs
		cubes.add(new CubeObject(minSize, 0.0D, minSize, minSize+0.1, 0.5D, minSize+0.1, Blocks.planks.getIcon(0, plank)));
		cubes.add(new CubeObject(minSize, 0.0D, maxSize-0.1, minSize+0.1, 0.5D, maxSize, Blocks.planks.getIcon(0, plank)));
		cubes.add(new CubeObject(maxSize-0.1, 0.0D, minSize, maxSize, 0.5D, minSize+0.1, Blocks.planks.getIcon(0, plank)));
		cubes.add(new CubeObject(maxSize-0.1, 0.0D, maxSize-0.1, maxSize, 0.5D, maxSize, Blocks.planks.getIcon(0, plank)));
		
		//Lehne
		cubes.add(new CubeObject(minSize, 1.2D, minSize, minSize+0.1, 1.3D, maxSize, Blocks.planks.getIcon(0, plank)));
		
		double length = 0.03;
		
		cubes.addAll(CubeObject.getGrid(new CubeObject(minSize+length, 0.6D, minSize, minSize+0.1-length, 1.2D, maxSize, Blocks.planks.getIcon(0, plank)), 0.04, 5));
		
		//cubes.add(new CubeObject(minSize, 1.2D, minSize, minSize+0.1, 1.3D, maxSize, Blocks.planks.getIcon(0, plank)));
		
		return cubes;
	}
	
	@Override
	public void onRegister()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(system.block, 1, getID());
		stack.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < SubBlockSofa.planksAmount; i++) {
			ItemStack showStack = stack.copy();
			showStack.stackTagCompound = new NBTTagCompound();
			showStack.stackTagCompound.setInteger("plank", i);
			showStack.stackSize = 3;
			GameRegistry.addRecipe(new ShapedOreRecipe(showStack.copy(), new Object[]
					{
					"PPP", "SSS", "PPP", 'S', Items.stick, 'P', new ItemStack(Blocks.planks, 1, i)
					}));
		}
	}
}
