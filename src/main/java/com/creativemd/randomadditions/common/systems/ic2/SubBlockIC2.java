package com.creativemd.randomadditions.common.systems.ic2;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlockIC2 extends SubBlock{

	public SubBlockIC2(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return true;
	}
	
	@Override
	public String getTileEntityName()
	{
		return "RA" + name;
	}
	
	public abstract String getTextureName();
	
	public void registerIcon(IIconRegister register)
	{
		if(getTextureName().equals(""))
		{
			super.registerIcon(register);
			return ;
		}
		icons = new IIcon[6];
		for (int i = 0; i < 3; i++) {
			icons[i] = register.registerIcon(RandomAdditions.modid + ":" + getTextureName() + "_" + i);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (icons.length == 1)
			return icons[0];
		switch(ForgeDirection.getOrientation(side))
		{
		case DOWN: return icons[0];
		case UP: return icons[1];
		default: return icons[2];
		}
		
	}
}
