package com.creativemd.randomadditions.common.systems.enchant;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;

public abstract class SubBlockEnchant extends SubBlock{

	public SubBlockEnchant(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	public abstract int getInventorySize();

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityUpgrade();
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityUpgrade)
			((TileEntityUpgrade) tileEntity).inventory = new ItemStack[getInventorySize()];
	}
	
	@Override
	public boolean hasBlockTexture()
	{
		return false;
	}
	
	@Override
	public int getRotation()
	{
		return 1;
	}

}
