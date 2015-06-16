package com.creativemd.randomadditions.common.systems.rf.tileentity;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyReceiver;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.rf.SubSystemRF;

public class TileEntityRFtoRA extends EnergyComponent implements IEnergyReceiver{

	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return false;
	}

	@Override
	public boolean canProduceEnergy(ForgeDirection direction) {
		return true;
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
	public boolean canConnectEnergy(ForgeDirection from) {
		return true;
	}

	@Override
	public int receiveEnergy(ForgeDirection from, int maxReceive,
			boolean simulate) {
		int power = (int) Math.min(SubSystemRF.RFtoRA(maxReceive), getInteralStorage()-getCurrentPower());
		if(!simulate)
			receivePower(power);
		return power;
	}

	@Override
	public int getEnergyStored(ForgeDirection from) {
		return (int) SubSystemRF.RAtoRF(getCurrentPower());
	}

	@Override
	public int getMaxEnergyStored(ForgeDirection from) {
		return (int) SubSystemRF.RAtoRF(getInteralStorage());
	}

}
