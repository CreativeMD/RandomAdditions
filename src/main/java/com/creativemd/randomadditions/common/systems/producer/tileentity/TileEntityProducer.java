package com.creativemd.randomadditions.common.systems.producer.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.common.systems.producer.SubBlockProducer;
import com.creativemd.randomadditions.common.systems.producer.SubSystemProducer;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityProducer extends EnergyComponent{
	
	private int[] modifier;
	
	public TileEntityProducer()
	{
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
			rotationOffset = (int) (Math.random()*360);
	}
	
	@SideOnly(Side.CLIENT)
	public double rotationOffset;
	
	public int speed;
	
	public int maxSpeed;
	public int nextSpeed = 100;
	public int canStay;
	
	public SubBlockProducer getBlock()
	{
		return SubSystemProducer.instance.getSubBlock(getBlockMetadata());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 100000.0D;
    }
	
	public int playTimeLeft = 0;
	
	@Override
	public void updateEntity() 
	{
		getBlock().updateTileEntity(this);
		if(!worldObj.isRemote)
		{
			if(getCurrentPower() + speed > getInteralStorage())
				receivePower(getInteralStorage() - getCurrentPower());
			else
				receivePower(speed);
			if(canStay == 0)
			{
				maxSpeed = getBlock().getMaxSpeed(this, null);
				if(maxSpeed < 1)
					maxSpeed = 1;
				canStay = 10000;
				if(!getBlock().canBlockStay(this, worldObj, xCoord, yCoord, zCoord))
					worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
			}
			canStay--;
			
			if(speed < maxSpeed)
			{
				nextSpeed--;
				if(nextSpeed == 0)
				{
					speed++;
					nextSpeed = 100;
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
			if(speed > maxSpeed)
				speed = maxSpeed;
			
			if(speed > 0)
			{
				if(playTimeLeft == 0 && getBlock().getPlayTime() > 0)
				{
					playTimeLeft = getBlock().getPlayTime();
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, RandomAdditions.modid + ":" + getBlock().name.toLowerCase(), getBlock().getPlayVolume(this), 1);
				}
				playTimeLeft--;
			}
		}
		super.updateEntity();
	}
	
	@Override
	public int getInteralStorage() {
		return 5000;
	}

	@Override
	public int getMaxInput() {
		return 0;
	}

	@Override
	public int getMaxOutput() {
		return (int) getCurrentPower();
	}
	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return false;
	}

	@Override
	public boolean canProduceEnergy(ForgeDirection direction) {
		return getBlock().canProvidePower(this, direction);
	}
	
	public int[] getModifiers()
	{
		if(modifier == null || modifier.length != getBlock().getModifiers())
			modifier = new int[getBlock().getModifiers()];
		return modifier;
	}
	
	public void setModifiers(int[] modi)
	{
		this.modifier = modi;
	}
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		nbt.setIntArray("modi", getModifiers());
		nbt.setInteger("speed", speed);
		nbt.setInteger("max", maxSpeed);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		modifier = pkt.func_148857_g().getIntArray("modi");
		maxSpeed = pkt.func_148857_g().getInteger("max");
		double nanoTime = System.nanoTime();
		double rotationA =  getBlock().getRotation(this, nanoTime);
		speed = pkt.func_148857_g().getInteger("speed");
		double rotationB = getBlock().getRotation(this, nanoTime);
		double offset = rotationB - rotationA;
		if(offset < 0)
			offset = -offset;
		rotationOffset -= offset;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       modifier = nbt.getIntArray("modi");
       speed = nbt.getInteger("speed");
    }
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setIntArray("modi", getModifiers());
		nbt.setInteger("speed", speed);
    }
}
