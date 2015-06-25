package com.creativemd.randomadditions.common.systems.deco.blocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;
import com.creativemd.randomadditions.common.systems.deco.gui.SubContainerShelf;
import com.creativemd.randomadditions.common.systems.deco.gui.SubGuiShelf;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityShelf;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockShelf extends SubBlockDeco{

	public SubBlockShelf(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.planks.getBlockTextureFromSide(0);
	}
	
	@Override
	public int getRotation()
	{
		return 2;
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityShelf)
		{
			TileEntityShelf sofa = (TileEntityShelf) tileEntity;
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			sofa.plank = stack.stackTagCompound.getInteger("plank");
		}
	}
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z)
	{
		if(entity instanceof TileEntityShelf)
		{
			TileEntityShelf shelf = (TileEntityShelf) entity;
			ForgeDirection direction = shelf.getDirection().getRotation(ForgeDirection.UP);
			
			int rows = 4;
			int amountperRow = (int) Math.ceil((double)shelf.inventory.length/(double)rows);
			for (int i = 0; i < shelf.inventory.length; i++) {
				int row = i/amountperRow;
				int col = i-row*amountperRow;
				if(shelf.inventory[i] != null)
				{
					double rotation = 0;
					double yOffset = -0.02;
					if(!(shelf.inventory[i].getItem() instanceof ItemBlock))
					{
						rotation = 90;
						yOffset = -0.05;
					}
					RenderHelper3D.renderItem(shelf.inventory[i], x, y, z, rotation, 0, 0, 0.3, direction, col*0.8/(double)amountperRow-0.35, row*-0.243+0.33+yOffset, 0);
					//RenderHelper3D.renderItem(shelf.inventory[i], x, y, z, System.nanoTime()/10000000D+row*20+col*20, 0, 0, 0.2+0.1*(Math.sin(System.nanoTime()/1000000000D)+1), direction, col*0.8/(double)amountperRow-0.35, row*-0.25+0.37, 0);
			
				}
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = super.getExtraDrop(tileEntity);
		if(tileEntity instanceof TileEntityShelf)
		{
			ItemStack stack = new ItemStack(system.block, 1, getID());
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("plank", ((TileEntityShelf) tileEntity).plank);
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
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		if(tileentity instanceof TileEntityRandom)
			return CubeObject.rotateCube(new CubeObject(0, 0, 0, 0.6, 1, 1), ((TileEntityRandom)world.getTileEntity(x, y, z)).getDirection());
		return new CubeObject(0, 0, 0, 0.6, 1, 1);
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
			if (tileEntity instanceof TileEntityShelf)
			{
				TileEntityShelf sofa = (TileEntityShelf) tileEntity;
				plank = sofa.plank;
			}
		}
		
		//cubes.add(new CubeObject(0, 0, 0, 0.5, 1, 1, Blocks.planks.getIcon(0, plank)));
		cubes.addAll(CubeObject.getBlock(new CubeObject(0, 0, 0, 0.6, 1, 1, Blocks.planks.getIcon(0, plank)), 0.05, ForgeDirection.NORTH));
		cubes.add(new CubeObject(0, 0.475, 0, 0.6, 0.525, 1, Blocks.planks.getIcon(0, plank)));
		cubes.add(new CubeObject(0, 0.475+0.25, 0, 0.6, 0.525+0.25, 1, Blocks.planks.getIcon(0, plank)));
		cubes.add(new CubeObject(0, 0.475-0.25, 0, 0.6, 0.525-0.25, 1, Blocks.planks.getIcon(0, plank)));
		return cubes;
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
					"PSP", "PSP", "PSP", 'S', Items.stick, 'P', new ItemStack(Blocks.planks, 1, i)
					}));
		}
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityShelf();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return new SubGuiShelf((TileEntityRandom) tileEntity);
	}
	
	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerShelf((TileEntityRandom) tileEntity, player);
	}
}
