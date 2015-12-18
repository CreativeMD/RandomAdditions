package com.creativemd.randomadditions.common.subsystem;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.subsystem.client.block.TileEntitySpecialRendererSubBlock;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlockSystem<T extends SubBlock> {
	
	public static ArrayList<SubBlockSystem> systems = new ArrayList<SubBlockSystem>();
	
	public static void registerSystem(SubBlockSystem system)
	{
		systems.add(system);
	}
	
	public static ArrayList<TileEntity> tileEntities = new ArrayList<TileEntity>();
	
	public static SubBlock getSubBlock(IBlockAccess world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		if(block instanceof BlockSub)
		{
			SubBlockSystem system = null;
			for (int i = 0; i < systems.size(); i++) {
				if(systems.get(i).block == block)
					system = systems.get(i);
			}
			if(system != null)
			{
				return system.getSubBlock(world.getBlockMetadata(x, y, z));
			}
		}
		return null;
	}
	
	public static void raiseOnRegisterEvent()
	{
		for (int i = 0; i < systems.size(); i++) {
			systems.get(i).onRegister();
		}
	}
	
	public void onRegister()
	{
		for (int j = 0; j < blocks.size(); j++) {
			((SubBlock)blocks.get(j)).onRegister();
		}
	}
	
	public ArrayList<T> blocks = new ArrayList<T>();
	
	public BlockSub block;
	
	public T defaultSubBlock;
	
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRendererSubBlock renderer;
	
	public String name;
	
	public SubBlockSystem(String name)
	{
		this.name = name;
		defaultSubBlock = getDefault();
		block = new BlockSub(this);
		GameRegistry.registerBlock(block, ItemBlockSub.class, "BlockRA" + name);
		registerBlocks();
	}
	
	public <K extends T> K registerBlock(K block)
	{
		TileEntityRandom tileEntityRandom = block.getTileEntity();
		if(tileEntityRandom != null)
		{
			boolean contains = false;
			for (int i = 0; i < tileEntities.size(); i++) {
				if(tileEntities.get(i).getClass() == tileEntityRandom.getClass())
					contains = true;
			}
			if(!contains)
			{
				GameRegistry.registerTileEntity(tileEntityRandom.getClass(), block.getTileEntityName());
				if(FMLCommonHandler.instance().getEffectiveSide().isClient())
				{
					registerClientStuff(tileEntityRandom);
				}
				tileEntities.add(tileEntityRandom);
			}
		}
		block.setID(blocks.size());
		blocks.add(block);
		return block;
	}
	
	@SideOnly(Side.CLIENT)
	public void registerClientStuff(TileEntityRandom tileEntityRandom)
	{
		if(renderer == null)
			renderer = new TileEntitySpecialRendererSubBlock(this);
		ClientRegistry.bindTileEntitySpecialRenderer(tileEntityRandom.getClass(), renderer);
	}
	
	public T getSubBlock(int meta)
	{
		if(meta >= 0 && meta < blocks.size())
			return blocks.get(meta);
		return defaultSubBlock;
	}
	
	public ItemStack getItemStack(SubBlock block)
	{
		return getItemStack(block.getID());
	}
	
	public ItemStack getItemStack(int meta)
	{
		return new ItemStack(block, 1, meta);
	}
	
	public abstract T getDefault();
	
	public abstract Material getBlockMaterial();
	
	public abstract void registerBlocks();
	
	public abstract String getHarvestTool();
	
	public boolean areBlocksSolid()
	{
		return false;
	}
	
	@SideOnly(Side.CLIENT)
	public ArrayList<GuiControl> getGuiControls()
	{
		return new ArrayList<GuiControl>();
	}
		
}
