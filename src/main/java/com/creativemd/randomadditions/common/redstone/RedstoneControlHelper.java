package com.creativemd.randomadditions.common.redstone;

import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class RedstoneControlHelper {
	
	public static IIcon ignore;
	public static IIcon impulse;
	public static IIcon nosignal;
	public static IIcon signal;
	
	public static IIcon[] icons;
	public static String[] modes = new String[]{"custom.redstone.ignore", "custom.redstone.signal", "custom.redstone.nosignal", "custom.redstone.impluse"};
	
	public static boolean handleRedstoneInput(TileEntity tileEntity, IRedstoneControl control)
	{
		int mode = control.getMode();
		if(mode == 0)
		{
			control.setActive(true);
			return true;
		}
		World world = tileEntity.getWorldObj();
		int x = tileEntity.xCoord;
		int y = tileEntity.yCoord;
		int z = tileEntity.zCoord;
		boolean hasSignal = false;
		if(world.getBlockPowerInput(x, y, z) > 0 || world.isBlockIndirectlyGettingPowered(x, y, z))
			hasSignal = true;
		switch(mode)
		{
		case 1:
			control.setActive(hasSignal);
			return hasSignal;
		case 2:
			control.setActive(!hasSignal);
			return !hasSignal;
		case 3:
			boolean hadSignal = control.hadSignal();
			boolean active = control.isActive();
			if(hasSignal && !hadSignal)
			{
				active = !active;
			}
			control.setSignal(hasSignal);
			control.setActive(active);
			return active;
		}
		return true;
	}
	
}
