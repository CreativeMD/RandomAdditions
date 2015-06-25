package com.creativemd.randomadditions.common.systems.deco;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlockDeco extends SubBlock{

	public SubBlockDeco(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	public static ItemStack renderedItemStack;

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}
	
	@Override
	public ArrayList<ItemStack> getDrop(IBlockAccess world, int x, int y, int z, int fortune)
	{
		return new ArrayList<ItemStack>();
	}
	
	@Override
	public boolean hasBlockTexture()
	{
		return false;
	}
	
	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
    {
        return 0;
    }

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}
	
	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}
	
	public String getTileEntityName()
	{
		return "RA" + system.name + name;
	}

}
