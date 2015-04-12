package com.creativemd.randomadditions.common.energy.core;

import java.util.ArrayList;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class EnergyUtils {
	
	public static SearchResult getNetwork(IBlockAccess world, int x, int y, int z, ForgeDirection direction)
	{
		ArrayList<MachineEntry> components = new ArrayList<MachineEntry>();
		ArrayList<EnergyCable> cables = new ArrayList<EnergyCable>();
		
		ArrayList<EnergyCore> unchecked = new ArrayList<EnergyCore>();
		
		int transmitPower = 0;
		
		TileEntity origin = world.getTileEntity(x, y, z);
		if(origin instanceof EnergyCore)
			unchecked.add((EnergyCore) origin);
		
		while(unchecked.size() > 0)
		{
			ArrayList connections = new ArrayList();
			if(unchecked.get(0) instanceof EnergyCable || unchecked.get(0) == origin)
				connections = unchecked.get(0).getConnections(direction);
			for (int i = 0; i < connections.size(); i++) {
				if(!components.contains(connections.get(i)) && !cables.contains(connections.get(i)) && !unchecked.contains(connections.get(i)))
				{
					if(connections.get(i) instanceof EnergyCore)
						unchecked.add((EnergyCore) connections.get(i));
					else if(connections.get(i) instanceof MachineEntry)
						components.add((MachineEntry) connections.get(i));
				}
			}
			if(unchecked.get(0) != origin)
			{
				if(unchecked.get(0) instanceof EnergyCable)
				{
					EnergyCable cable = (EnergyCable) unchecked.get(0);
					cables.add(cable);
					int transmitablePower = cable.getTransmitablePower()-cable.transmitedPower;
					if((transmitPower == 0 || transmitPower > transmitablePower) && transmitablePower > 0)
						transmitPower = transmitablePower;
				}
			}
			
			unchecked.remove(0);
		}
		if(transmitPower == 0 && cables.size() == 0)
			transmitPower = Integer.MAX_VALUE;
		return new SearchResult(new ChunkCoordinates(x, y, z), components, cables, transmitPower);
	}
	
	public static class MachineEntry
	{
		
		public EnergyComponent machine;
		
		public boolean recieve;
		
		public MachineEntry(EnergyComponent machine, ForgeDirection direction)
		{
			this.machine = machine;
			this.recieve = machine.canRecieveEnergy(direction);
		}
		
	}
	
	public static class SearchResult
	{
		public int transmitPower;
		
		public ArrayList<MachineEntry> machines;
		
		public ArrayList<EnergyCable> cables;
		
		public SearchResult(ChunkCoordinates origin, ArrayList<MachineEntry> components, ArrayList<EnergyCable> cables, int transmitPower)
		{
			this.transmitPower = transmitPower;
			this.cables = cables;
			
			//Sort Components
			this.machines = new ArrayList<MachineEntry>();
			for (int i = 0; i < components.size(); i++) {
				int j = 0;
				while(j < this.machines.size() && this.machines.get(j).machine.getDistance(origin) > components.get(i).machine.getDistance(origin))
					j++;
				this.machines.add(j, components.get(i));
			}
		}
	}
}
