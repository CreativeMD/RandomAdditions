package com.creativemd.randomadditions.common.systems.deco.blocks;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;
import com.creativemd.randomadditions.common.systems.deco.blocks.tables.DefaultTable;
import com.creativemd.randomadditions.common.systems.deco.blocks.tables.GlassTable;
import com.creativemd.randomadditions.common.systems.deco.blocks.tables.LivingRoomTable;
import com.creativemd.randomadditions.common.systems.deco.blocks.tables.Table;
import com.creativemd.randomadditions.common.systems.deco.tileentity.TileEntityTable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockTable extends SubBlockDeco{

	public SubBlockTable(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	public static ArrayList<Table> tables = new ArrayList<Table>();
	
	public static void initTables()
	{
		tables.add(new DefaultTable());
		tables.add(new GlassTable());
		tables.add(new LivingRoomTable());
	}
	
	@Override
	public CubeObject getSelBox(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTable)
		{
			return tables.get(((TileEntityTable) tileEntity).type).getSelBoxes(world, x, y, z);
		}
		return new CubeObject();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.planks.getBlockTextureFromSide(0);
	}
	
	@Override
	public ArrayList<CubeObject> getBoxes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityTable)
		{
			cubes.addAll(tables.get(((TileEntityTable) tileEntity).type).getBoxes(world, x, y, z));
		}
		return cubes;
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityTable)
		{
			TileEntityTable sofa = (TileEntityTable) tileEntity;
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			sofa.plank = stack.stackTagCompound.getInteger("plank");
			sofa.type = stack.stackTagCompound.getInteger("type");
		}
	}
	
	@Override
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = super.getExtraDrop(tileEntity);
		if(tileEntity instanceof TileEntityTable)
		{
			ItemStack stack = new ItemStack(system.block, 1, getID());
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("plank", ((TileEntityTable) tileEntity).plank);
			stack.stackTagCompound.setInteger("type", ((TileEntityTable) tileEntity).type);
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
	public ArrayList<ItemStack> getItemStacks()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(system.block, 1, getID());
		stack.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < SubBlockSofa.planksAmount; i++) {
			for (int j = 0; j < tables.size(); j++) {
				ItemStack showStack = stack.copy();
				showStack.stackTagCompound = new NBTTagCompound();
				showStack.stackTagCompound.setInteger("plank", i);
				showStack.stackTagCompound.setInteger("type", j);
				stacks.add(showStack);
			}
		}
		return stacks;
	}
	
	@Override
	public int getRotation()
	{
		return 0;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		int plank = 0;
		int type = 0;
		if(par1ItemStack != null && par1ItemStack.stackTagCompound != null)
		{
			plank = par1ItemStack.stackTagCompound.getInteger("plank");
			type = par1ItemStack.stackTagCompound.getInteger("type");
		}
		
		return "tile.BlockRA" + system.name + "." + name + plank + type; 
    }
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityTable();
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		int plank = 0;
		int type = 0;
		stack = renderedItemStack;
		if(world == null)
		{
			if(stack != null && stack.stackTagCompound != null)
			{
				plank = stack.stackTagCompound.getInteger("plank");
				type = stack.stackTagCompound.getInteger("type");
			}
		}else{
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if (tileEntity instanceof TileEntityTable)
			{
				TileEntityTable sofa = (TileEntityTable) tileEntity;
				plank = sofa.plank;
				type = sofa.type;
			}
		}
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.addAll(tables.get(type).getCubes(stack, world, x, y, z, plank));
		return cubes;
	}
	
	@Override
	public void onRegister()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		ItemStack stack = new ItemStack(system.block, 1, getID());
		stack.stackTagCompound = new NBTTagCompound();
		for (int i = 0; i < SubBlockSofa.planksAmount; i++) {
			for (int j = 0; j < tables.size(); j++) {
				ItemStack showStack = stack.copy();
				showStack.stackTagCompound = new NBTTagCompound();
				showStack.stackTagCompound.setInteger("plank", i);
				showStack.stackTagCompound.setInteger("type", j);
				showStack.stackSize = 3;
				tables.get(j).addRecipe(showStack.copy(), new ItemStack(Blocks.planks, 1, i));
				/*GameRegistry.addRecipe(new ShapedOreRecipe(showStack.copy(), new Object[]
						{
						"PPP", "SSS", "PPP", 'S', Items.stick, 'P', new ItemStack(Blocks.planks, 1, i)
						}));*/
			}
		}
	}
}
