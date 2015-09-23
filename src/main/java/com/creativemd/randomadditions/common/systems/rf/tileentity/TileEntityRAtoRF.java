package com.creativemd.randomadditions.common.systems.rf.tileentity;

import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.ServerHelper;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.rf.SubSystemRF;

public class TileEntityRAtoRF extends EnergyComponent implements IEnergyHandler{

	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return true;
	}

	@Override
	public boolean canProduceEnergy(ForgeDirection direction) {
		return false;
	}

	@Override
	public int getInteralStorage() {
		return 10000;
	}

	@Override
	public int getMaxOutput() {
		return 10000;
	}

	@Override
	public int getMaxInput() {
		return 10000;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		for (int i = 0; i < 6; i++) {
	        transferEnergy(i);
	      }
	}
	
	IEnergyReceiver[] adjacentHandlers = new IEnergyReceiver[6];
	
	/*public void onNeighborBlockChange()
	{
		super.onNeighborBlockChange();
		updateAdjacentHandlers();
	}*/
	  
	  /*public void onNeighborTileChange(int paramInt1, int paramInt2, int paramInt3)
	  {
	    super.onNeighborTileChange(paramInt1, paramInt2, paramInt3);
	    updateAdjacentHandler(paramInt1, paramInt2, paramInt3);
	  }*/
	
  protected void transferEnergy(int paramInt)
  {
    if (this.adjacentHandlers[paramInt] == null) {
      return;
    }
    drainPower(SubSystemRF.RFtoRA(this.adjacentHandlers[paramInt].receiveEnergy(ForgeDirection.VALID_DIRECTIONS[(paramInt ^ 0x1)], (int) SubSystemRF.RAtoRF(getCurrentPower()), false)));
  }
	
  public void updateAdjacentHandlers()
  {
	  if (ServerHelper.isClientWorld(worldObj)) {
		  return;
	  }
	  for (int i = 0; i < 6; i++)
	  {
		  TileEntity localTileEntity = BlockHelper.getAdjacentTileEntity(this, i);
	      if (EnergyHelper.isEnergyReceiverFromSide(localTileEntity, ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)])) {
	    	  this.adjacentHandlers[i] = ((IEnergyReceiver)localTileEntity);
	      } else {
	    	  this.adjacentHandlers[i] = null;
	      }
	  }
	    //this.cached = true;
  }
	  
  protected void updateAdjacentHandler(int paramInt1, int paramInt2, int paramInt3)
  {
	  if (ServerHelper.isClientWorld(worldObj)) {
		  return;
	  }
	  int i = BlockHelper.determineAdjacentSide(this, paramInt1, paramInt2, paramInt3);
	  
	  TileEntity localTileEntity = worldObj.getTileEntity(paramInt1, paramInt2, paramInt3);
	  if (EnergyHelper.isEnergyReceiverFromSide(localTileEntity, ForgeDirection.VALID_DIRECTIONS[(i ^ 0x1)])) {
		  this.adjacentHandlers[i] = ((IEnergyReceiver)localTileEntity);
	  } else {
		  this.adjacentHandlers[i] = null;
	  }
  }
	
	@Override
	public boolean canConnectEnergy(ForgeDirection from) {
		//System.out.println("Connect");
		return true;
	}
	
	@Override
	public int extractEnergy(ForgeDirection from, int maxExtract,
			boolean simulate) {
		int power = (int) Math.min(maxExtract, SubSystemRF.RAtoRF(getCurrentPower()));
		//System.out.println("Power:" + power);
		if(!simulate)
			drainPower(SubSystemRF.RFtoRA(power));
		return power;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		//System.out.println("Stored Power:" + SubSystemRF.RAtoRF(getCurrentPower()));
		return (int) SubSystemRF.RAtoRF(getCurrentPower());
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		//System.out.println("Max Power:" + SubSystemRF.RAtoRF(getInteralStorage()));
		return (int) SubSystemRF.RAtoRF(getInteralStorage());
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		return 0;
	}

}
