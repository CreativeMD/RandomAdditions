package com.creativemd.randomadditions.common.systems.assembly;

import java.util.ArrayList;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.handcraft.recipe.HandRecipe;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;

public abstract class SubBlockAssembly extends SubBlock{
	
	public SubBlockAssembly(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}
	
	@Override
	public boolean hasBlockTexture()
	{
		return false;
	}
	
}