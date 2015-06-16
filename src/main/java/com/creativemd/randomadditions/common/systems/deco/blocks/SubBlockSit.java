package com.creativemd.randomadditions.common.systems.deco.blocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.entity.EntitySit;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;

public abstract class SubBlockSit extends SubBlockDeco{

	public SubBlockSit(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	
	@Override
	public boolean onBlockActivated(EntityPlayer player, ItemStack stack, TileEntity tileEntity)
	{
		if(!tileEntity.getWorldObj().isRemote)
		{
			EntitySit sit = new EntitySit(tileEntity.getWorldObj(), tileEntity.xCoord+0.5, tileEntity.yCoord+0.5, tileEntity.zCoord+0.5);
			tileEntity.getWorldObj().spawnEntityInWorld(sit);
			player.mountEntity(sit);
		}
		return true;
	}
}
