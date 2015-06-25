package com.creativemd.randomadditions.common.systems.littletiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlockLittle extends SubBlock{

	public SubBlockLittle(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}
	
	@Override
	public String getTileEntityName()
	{
		return "RA" + system.name + name;
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}

}
