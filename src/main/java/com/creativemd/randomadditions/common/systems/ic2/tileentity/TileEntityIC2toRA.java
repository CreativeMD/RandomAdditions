package com.creativemd.randomadditions.common.systems.ic2.tileentity;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.ic2.SubSystemIC2;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.tile.IEnergyStorage;
import ic2.core.IC2;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityIC2toRA extends EnergyComponent implements IEnergyStorage, IEnergySink, IEnergySource{

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
	public int getStored() {
		return (int) SubSystemIC2.RAtoEU(getCurrentPower());
	}

	@Override
	public void setStored(int energy) {
		setCurrentPower(SubSystemIC2.EUtoRA(energy));
	}

	@Override
	public int addEnergy(int amount) {
		return (int) receivePower(SubSystemIC2.EUtoRA(amount));
	}

	@Override
	public int getCapacity() {
		return (int) SubSystemIC2.RAtoEU(getInteralStorage());
	}

	@Override
	public int getOutput() {
		return (int) SubSystemIC2.RAtoEU(getOutputPower());
	}

	@Override
	public double getOutputEnergyUnitsPerTick() {
		return SubSystemIC2.RAtoEU(getOutputPower());
	}

	@Override
	public boolean isTeleporterCompatible(ForgeDirection side) {
		return true;
	}

	@Override
	public boolean acceptsEnergyFrom(TileEntity emitter,
			ForgeDirection direction) {
		return true;
	}

	@Override
	public double getDemandedEnergy() {
		return getCapacity()-getStored();
	}

	@Override
	public int getSinkTier() {
		return 1;
	}

	@Override
	public double injectEnergy(ForgeDirection directionFrom, double amount,
			double voltage) {
		if (getStored() >= getCapacity()) {
	      return amount;
	    }
	    setStored((int) (getStored()+amount));
	    setInputPower((int) (getInputPower()+amount));
	    return 0.0D;
	}

	@Override
	public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
		return false;
	}

	@Override
	public double getOfferedEnergy() {
		return 0;
	}

	@Override
	public void drawEnergy(double amount) {
		drainPower(SubSystemIC2.EUtoRA(amount));
	}

	@Override
	public int getSourceTier() {
		return 1;
	}
	
	public boolean addedToEnergyNet = false;
	
	@Override
	public void validate()
    {
		super.validate();
		if (IC2.platform.isSimulating())
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
			this.addedToEnergyNet = true;
		}
    }
	
	@Override
	public void onChunkUnload()
	{
		if ((IC2.platform.isSimulating()) && (this.addedToEnergyNet))
		{
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			this.addedToEnergyNet = false;
		}
		super.onChunkUnload();
	}

}
