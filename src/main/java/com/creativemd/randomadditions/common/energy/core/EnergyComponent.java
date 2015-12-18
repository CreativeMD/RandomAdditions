package com.creativemd.randomadditions.common.energy.core;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.energy.api.IRAReciever;
import com.creativemd.randomadditions.common.energy.core.EnergyUtils.MachineEntry;
import com.creativemd.randomadditions.common.energy.core.EnergyUtils.SearchResult;
import com.creativemd.randomadditions.common.redstone.IRedstoneControl;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;
import com.creativemd.randomadditions.common.systems.littletiles.tileentity.TileEntityLittleCable;

public abstract class EnergyComponent extends EnergyCore implements IRedstoneControl, IRAReciever{
	
	public abstract boolean canRecieveEnergy(ForgeDirection direction);
	
	public abstract boolean canProduceEnergy(ForgeDirection direction);
	
	public abstract int getInteralStorage();
	
	public abstract int getMaxOutput();
	
	public abstract int getMaxInput();
	
	private float currentPower;
	
	private float outputPower;
	private float sendedOutputPower;
	
	private float inputPower;
	private float sendedInputPower;
	
	public float getOutputPower()
	{
		return outputPower;
	}
	
	public void setOutputPower(float power)
	{
		outputPower = power;
	}
	
	public float getInputPower()
	{
		return inputPower;
	}
	
	public void setInputPower(float power)
	{
		inputPower = power;
	}
	
	public boolean hasEnoughPower(float amount)
	{
		return amount <= currentPower;
	}
	
	public boolean hasEnoughSpace(float amount)
	{
		return (getInteralStorage() - getCurrentPower()) >= amount;
	}
	
	public boolean drainPower(float amount)
	{
		if(hasEnoughPower(amount))
		{
			currentPower -= amount;
			outputPower += amount;
			return true;
		}
		return false;
	}
	
	public float receivePower(float amount)
	{
		if(!hasEnoughSpace(amount))
			amount = getInteralStorage()-getCurrentPower();
		if(amount > 0)
		{
			currentPower += amount;
			inputPower += amount;
			return amount;
		}
		return 0;
	}
	
	public float getCurrentPower()
	{
		return currentPower;
	}
	
	/**Please use recievePower instead if possible**/
	protected void setCurrentPower(float power)
	{
		currentPower = power;
	}
	
	public float getRecieveablePower()
	{
		if(!active)
			return 0;
		float input = getMaxInput() - inputPower;
		float space = getInteralStorage() - getCurrentPower();
		if(space < input)
			input = space;
		return input;
	}
	
	public float getProvideablePower()
	{
		float output = getMaxOutput() - outputPower;
		float power = getCurrentPower();
		if(power < output)
			output = power;
		return output;
	}
	
	public float providePower(IRAReciever machine, int max)
	{
		float power = this.getProvideablePower();
		float maxrecieve = machine.getRecieveablePower();
		if(power > maxrecieve)
			power = maxrecieve;
		if(power > max)
			power = max;
		power = machine.receivePower(power);
		if(power > 0)
			drainPower(power);
		return power;
	}
	
	public boolean canProvidePower()
	{
		boolean provide = false;
		for (int i = 0; i < 6; i++) {
			if(canProduceEnergy(ForgeDirection.getOrientation(i)))
			{
				provide = true;
				break;
			}
		}
		return getCurrentPower() > 0 || provide; 
	}
	
	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			if(RedstoneControlHelper.handleRedstoneInput(this, this))
			{
				if(canProvidePower())
				{
					ArrayList<SearchResult> results = new ArrayList<EnergyUtils.SearchResult>();
					for (int i = 0; i < 6; i++) {
						ForgeDirection direction = ForgeDirection.getOrientation(i);
						if(canProduceEnergy(direction))
							results.add(EnergyUtils.getNetwork(worldObj, xCoord, yCoord, zCoord, direction));
					}
					for (int j = 0; j < results.size(); j++) {
						int providedpower = 0;
						SearchResult result = results.get(j);
						int i = 0;
						while(i < result.machines.size() && providedpower < result.transmitPower && getCurrentPower() > 0)
						{
							if(this != result.machines.get(i).machine && result.machines.get(i).recieve)
								providedpower += providePower(result.machines.get(i).machine, result.transmitPower);
							i++;
						}
						for (int q = 0; q < result.cables.size(); q++) {
							result.cables.get(q).transmitedPower += providedpower;
						}
					}
					
				}
			}
			if(sendedInputPower != inputPower || sendedOutputPower != outputPower)
			{
				updateBlock();
				sendedInputPower = inputPower;
				sendedOutputPower = outputPower;
			}
			outputPower = 0;
			inputPower = 0;
		}
	}
	
	@Override
	public ArrayList getConnections(ForgeDirection direction)
	{
		ArrayList connections = new ArrayList();
		for (int i = 0; i < 6; i++) {
			ForgeDirection blockdirection = ForgeDirection.getOrientation(i);
			if(canProduceEnergy(blockdirection) && (direction == null || blockdirection == direction))
			{
				ChunkCoordinates coord = new ChunkCoordinates(xCoord, yCoord, zCoord);
				RotationUtils.applyDirection(blockdirection, coord);
				TileEntity entity = worldObj.getTileEntity(coord.posX, coord.posY, coord.posZ);
				if(entity instanceof EnergyCable)
					connections.add(entity);
				else if(entity instanceof EnergyComponent)
					connections.add(new MachineEntry((EnergyComponent) entity, blockdirection.getOpposite()));
				else if(entity instanceof IRAReciever)
					connections.add(new MachineEntry((IRAReciever) entity, blockdirection.getOpposite()));
				else{
					EnergyCable cable = TileEntityLittleCable.getConnection(worldObj, new ChunkCoordinates(xCoord, yCoord, zCoord), coord, blockdirection);
					if(cable != null)
						connections.add(cable);
				}
			}
		}
		return connections;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       currentPower = nbt.getFloat("power");
       mode = nbt.getInteger("mode");
       active = nbt.getBoolean("active");
       hadSignal = nbt.getBoolean("hadSignal");
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setFloat("power", currentPower);
        nbt.setInteger("mode", mode);
        nbt.setBoolean("active", active);
        nbt.setBoolean("hadSignal", hadSignal);
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);;
		nbt.setFloat("output", sendedOutputPower);
		nbt.setFloat("input", sendedInputPower);
		nbt.setFloat("power", currentPower);
		nbt.setInteger("mode", mode);
        nbt.setBoolean("active", active);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		inputPower = pkt.func_148857_g().getFloat("input");
		outputPower = pkt.func_148857_g().getFloat("output");
		currentPower = pkt.func_148857_g().getFloat("power");
		mode = pkt.func_148857_g().getInteger("mode");
	    active = pkt.func_148857_g().getBoolean("active");
		updateRender();
    }
	
	public boolean active = true;
	public boolean hadSignal = false;
	public int mode = 0;

	@Override
	public void setMode(int mode) {
		this.mode = mode;
		updateBlock();
	}

	@Override
	public int getMode() {
		return mode;
	}

	@Override
	public boolean hadSignal() {
		return hadSignal;
	}

	@Override
	public void setSignal(boolean hadSignal) {
		this.hadSignal = hadSignal;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}
	
}
