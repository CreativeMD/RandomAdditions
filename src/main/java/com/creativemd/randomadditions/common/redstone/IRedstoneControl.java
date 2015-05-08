package com.creativemd.randomadditions.common.redstone;

import net.minecraft.tileentity.TileEntity;

public interface IRedstoneControl {
	
	/**Modes: Ignore Signal, Signal, No Signal, Impulse Signal**/	
	public void setMode(int mode);
	
	public int getMode();
	
	/**Only for mode 4**/
	public boolean hadSignal();
	
	/**Only for mode 4**/
	public void setSignal(boolean hadSignal);
	
	/**Only for mode 4**/
	public boolean isActive();
	
	/**Only for mode 4**/
	public void setActive(boolean active);
}
