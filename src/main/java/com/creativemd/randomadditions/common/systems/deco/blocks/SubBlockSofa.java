package com.creativemd.randomadditions.common.systems.deco.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntitySofa;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockSofa extends SubBlockSit{

	public SubBlockSofa(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	public static final Block planks = Blocks.planks;
	public static final int planksAmount = 6;
	public static final Block wool = Blocks.wool;
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.planks.getBlockTextureFromSide(0);
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntitySofa)
		{
			TileEntitySofa sofa = (TileEntitySofa) tileEntity;
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			sofa.plank = stack.stackTagCompound.getInteger("plank");
			sofa.wool = stack.stackTagCompound.getInteger("wool");
		}
	}
	
	@Override
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = super.getExtraDrop(tileEntity);
		if(tileEntity instanceof TileEntitySofa)
		{
			ItemStack stack = new ItemStack(system.block, 1, getID());
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("plank", ((TileEntitySofa) tileEntity).plank);
			stack.stackTagCompound.setInteger("wool", ((TileEntitySofa) tileEntity).wool);
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
		cubes.add(new CubeObject(0, 0, 0, 1, 0.6, 1));
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntitySofa)
		{
			boolean right = false;
			boolean left = false;
			boolean backright = false;
			boolean backleft = false;
			boolean frontright = false;
			boolean frontleft = false;
			boolean frontC = false;
			boolean backC = false;
			TileEntitySofa sofa = (TileEntitySofa) tileEntity;
			
			ForgeDirection direction = sofa.getDirection();
			
			right = canConnect(sofa, direction.getRotation(ForgeDirection.DOWN));
			left = canConnect(sofa, direction.getRotation(ForgeDirection.UP));
			
			frontright = canConnect(sofa, direction, direction.getRotation(ForgeDirection.DOWN));
			frontleft = canConnect(sofa, direction, direction.getRotation(ForgeDirection.UP));
			
			frontC = frontright || frontleft;
			
			if(!frontC)
			{
				backright = canConnect(sofa, direction.getOpposite(), direction.getOpposite().getRotation(ForgeDirection.DOWN));
				backleft = canConnect(sofa, direction.getOpposite(), direction.getOpposite().getRotation(ForgeDirection.UP));
				backC = backleft || backright;
			}
			
			double minX = 0.0D;
	    	double maxX = 1D;
	    	if(frontC)
	    		minX = 0.1;
	    	if(frontright)
	    	{
	    		minX = 0;
	    		maxX = 0.9;
	    	}
	    	
	    	/*cubes.add(new CubeObject(0.75, 0D, 0.07D, 0.95, 0.3D, 0.27D, Blocks.planks.getIcon(0, plank)));
	    	cubes.add(new CubeObject(0.05, 0D, 0.07D, 0.25, 0.3D, 0.27D, Blocks.planks.getIcon(0, plank)));
	    	
	    	cubes.add(new CubeObject(0.75, 0D, 0.7D, 0.95, 0.3D, 0.9D, Blocks.planks.getIcon(0, plank)));
	    	cubes.add(new CubeObject(0.05, 0D, 0.7D, 0.25, 0.3D, 0.9D, Blocks.planks.getIcon(0, plank)));*/
			
	    	if(backleft)
	    	{
	    		minX = 0.9;
	    		maxX = 1;
	    	}
	    	if(backright)
	    	{
	    		minX = 0;
	    		maxX = 0.1;
	    	}
	    	
	    	cubes.add(new CubeObject(minX, 0.3D, 0.9D, maxX, 1.2D, 1D));
	    	
	    	if(!backC)
	    	{
	    		cubes.add(new CubeObject(minX, 0.3D, 0.07D, maxX, 0.5D, 0.9D));
	    	}
	    	
			if(frontC)
			{
				CubeObject cubeplank = new CubeObject(0, 0.3D, 0.0D, 0.1, 1.2D, 0.8D);
				
	    		if(frontright)
	    		{
	    			cubeplank = new CubeObject(0.9, 0.3D, 0.0D, 1, 1.2D, 0.8D);
	    		}
	    			
	    		cubes.add(cubeplank);
			}
			
	    	for (int i = 1; i < cubes.size(); i++) {
				cubes.set(i, CubeObject.rotateCube(cubes.get(i), sofa.getDirection().getRotation(ForgeDirection.UP)));
			}
			
		}
		return cubes;
	}
	
	@Override
	public CubeObject getSelBox(IBlockAccess world, int x, int y, int z)
	{
		return new CubeObject(0, 0, 0, 1, 0.6, 1);
	}
	
	@Override
	public ArrayList<ItemStack> getItemStacks()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(system.block, 1, getID());
		stack.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < planksAmount; i++) {
			for (int j = 0; j < 16; j++) {
				ItemStack showStack = stack.copy();
				showStack.stackTagCompound = new NBTTagCompound();
				showStack.stackTagCompound.setInteger("plank", i);
				showStack.stackTagCompound.setInteger("wool", j);
				stacks.add(showStack);
			}
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
		int wool = 0;
		if(par1ItemStack != null && par1ItemStack.stackTagCompound != null)
		{
			plank = par1ItemStack.stackTagCompound.getInteger("plank");
			wool = par1ItemStack.stackTagCompound.getInteger("wool");
		}
		
		return "tile.BlockRA" + system.name + "." + name + plank + wool; 
    }
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntitySofa();
	}
	
	public static boolean canConnect(TileEntitySofa sofa, ForgeDirection direction, ForgeDirection forcedDirection)
	{
		ChunkCoordinates chunk = new ChunkCoordinates(sofa.xCoord, sofa.yCoord, sofa.zCoord);
		RotationUtils.applyDirection(direction, chunk);
		TileEntity entity = sofa.getWorldObj().getTileEntity(chunk.posX, chunk.posY, chunk.posZ);
		if (entity instanceof TileEntitySofa) {
			return forcedDirection == ((TileEntitySofa) entity).getDirection();
		}
		return false;
	}
	
	public static boolean canConnect(TileEntitySofa sofa, ForgeDirection direction)
	{
		ChunkCoordinates chunk = new ChunkCoordinates(sofa.xCoord, sofa.yCoord, sofa.zCoord);
		RotationUtils.applyDirection(direction, chunk);
		TileEntity entity = sofa.getWorldObj().getTileEntity(chunk.posX, chunk.posY, chunk.posZ);
		if (entity instanceof TileEntitySofa) {
			return sofa.getDirection().getOpposite() != ((TileEntitySofa) entity).getDirection();
		}
		return false;
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		stack = renderedItemStack;
		int plank = 0;
		int wool = 0;
		boolean right = false;
		boolean left = false;
		boolean backright = false;
		boolean backleft = false;
		boolean frontright = false;
		boolean frontleft = false;
		boolean frontC = false;
		boolean backC = false;
		if(world == null)
		{
			if(stack != null && stack.stackTagCompound != null)
			{
				plank = stack.stackTagCompound.getInteger("plank");
				wool = stack.stackTagCompound.getInteger("wool");
			}
		}else{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntitySofa)
			{
				TileEntitySofa sofa = (TileEntitySofa) tileEntity;
				plank = sofa.plank;
				wool = sofa.wool;
				ForgeDirection direction = sofa.getDirection();
				
				right = canConnect(sofa, direction.getRotation(ForgeDirection.DOWN));
				left = canConnect(sofa, direction.getRotation(ForgeDirection.UP));
				
				frontright = canConnect(sofa, direction, direction.getRotation(ForgeDirection.DOWN));
				frontleft = canConnect(sofa, direction, direction.getRotation(ForgeDirection.UP));
				
				frontC = frontright || frontleft;
				
				if(!frontC)
				{
					backright = canConnect(sofa, direction.getOpposite(), direction.getOpposite().getRotation(ForgeDirection.DOWN));
					backleft = canConnect(sofa, direction.getOpposite(), direction.getOpposite().getRotation(ForgeDirection.UP));
					backC = backleft || backright;
				}
			}
		}	
		
		double minX = 0.0D;
    	double maxX = 1D;
    	if(frontC)
    		minX = 0.1;
    	if(frontright)
    	{
    		minX = 0;
    		maxX = 0.9;
    	}
    	
    	cubes.add(new CubeObject(0.75, 0D, 0.07D, 0.95, 0.3D, 0.27D, Blocks.planks.getIcon(0, plank)));
    	cubes.add(new CubeObject(0.05, 0D, 0.07D, 0.25, 0.3D, 0.27D, Blocks.planks.getIcon(0, plank)));
    	
    	cubes.add(new CubeObject(0.75, 0D, 0.7D, 0.95, 0.3D, 0.9D, Blocks.planks.getIcon(0, plank)));
    	cubes.add(new CubeObject(0.05, 0D, 0.7D, 0.25, 0.3D, 0.9D, Blocks.planks.getIcon(0, plank)));
		
    	if(backleft)
    	{
    		minX = 0.9;
    		maxX = 1;
    	}
    	if(backright)
    	{
    		minX = 0;
    		maxX = 0.1;
    	}
    	
    	cubes.add(new CubeObject(minX, 0.3D, 0.9D, maxX, 1.2D, 1D, Blocks.planks.getIcon(0, plank)));
    	
    	if(!backC)
    	{
    		cubes.add(new CubeObject(minX, 0.3D, 0.07D, maxX, 0.5D, 0.9D, Blocks.planks.getIcon(0, plank)));
    	}
    	
    	if(!frontC && !backC)
    	{
	    	for (int i = 0; i < 2; i++) {
	    		double posX = 0;
	    		boolean show = false;
	    		switch (i) {
				case 0:
					show = right;
					break;
				case 1:
					show = left;
					break;
				}
	    		if(i == 1)
	    			posX = 0.9;
	    		if(!show)
	    		{
		    		minX = 0.025;
		        	cubes.add(new CubeObject(posX+minX, 0.5D, 0.125D, posX+0.075D, 0.7D, 0.2D, Blocks.planks.getIcon(0, plank)));
		        	cubes.add(new CubeObject(posX+minX, 0.5D, 0.425D, posX+0.075D, 0.7D, 0.5D, Blocks.planks.getIcon(0, plank)));
		        	cubes.add(new CubeObject(posX+minX, 0.5D, 0.725D, posX+0.075D, 0.7D, 0.8D, Blocks.planks.getIcon(0, plank)));
		        	
		        	minX = 0;
		        	cubes.add(new CubeObject(posX+minX, 0.7D, 0.1D, posX+0.1D, 0.8D, 0.9D, Blocks.planks.getIcon(0, plank)));
	    		}
	        	
			}
    	}
    	
    	minX = 0.1D;
    	maxX = 0.9D;
    	if(right)
    		minX = 0;
    	if(left)
    		maxX = 1;
    	if(backleft)
    	{
    		minX = 0.8;
    		maxX = 1;
    	}
    	if(backright)
    	{
    		minX = 0;
    		maxX = 0.2;
    	}
    	
    	if(backleft)
    		cubes.add(new CubeObject(0.8, 0.55D, 0.9D, 0.9, 1.1D, 1D, Blocks.wool.getIcon(0, wool)));
    	if(backright)
    		cubes.add(new CubeObject(0.1, 0.55D, 0.9D, 0.2, 1.1D, 1D, Blocks.wool.getIcon(0, wool)));
    	
    	cubes.add(new CubeObject(minX, 0.55D, 0.8D, maxX, 1.1D, 0.9D, Blocks.wool.getIcon(0, wool)));
    	
    	if(!backC)
    		cubes.add(new CubeObject(minX, 0.2D, 0.0D, maxX, 0.55D, 0.89D, Blocks.wool.getIcon(0, wool)));
    	else
    		cubes.add(new CubeObject(0, 0.2D, 0.0D, 1, 0.55D, 1D, Blocks.wool.getIcon(0, wool)));
		
		if(frontC)
		{
			CubeObject cubeplank = new CubeObject(0.1, 0.55D, 0.0D, 0.2, 1.1D, 0.8D, Blocks.wool.getIcon(0, wool));
			CubeObject cubewool = new CubeObject(0, 0.3D, 0D, 0.1, 1.2D, 1D, Blocks.planks.getIcon(0, plank));
			
    		if(frontright)
    		{
    			cubeplank = new CubeObject(0.8, 0.55D, 0.0D, 0.9, 1.1D, 0.8D, Blocks.wool.getIcon(0, wool));
    			cubewool = new CubeObject(0.9, 0.3D, 0D, 1, 1.2D, 1D, Blocks.planks.getIcon(0, plank));
    		}
    			
    		cubes.add(cubeplank);
    		cubes.add(cubewool);
		}
		
		for (int i = 0; i < cubes.size(); i++) {
			cubes.set(i, CubeObject.rotateCube(cubes.get(i), ForgeDirection.SOUTH));
		}
    	
		
		//cubes.add(new CubeObject(0, 0, 0, 1, 0.5, 1, Blocks.planks.getIcon(0, plank)));
		//cubes.add(new CubeObject(0, 0.5, 0, 1, 1, 1, Blocks.wool.getIcon(0, wool)));
		return cubes;
	}
	
	@Override
	public void onRegister()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(system.block, 1, getID());
		stack.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < planksAmount; i++) {
			for (int j = 0; j < 16; j++) {
				ItemStack showStack = stack.copy();
				showStack.stackTagCompound = new NBTTagCompound();
				showStack.stackTagCompound.setInteger("plank", i);
				showStack.stackTagCompound.setInteger("wool", j);
				GameRegistry.addRecipe(new ShapedOreRecipe(showStack.copy(), new Object[]
						{
						"WWW", "WPW", "PPP", 'W', new ItemStack(Blocks.wool, 1, j), 'P', new ItemStack(Blocks.planks, 1, i)
						}));
			}
		}
	}

}
