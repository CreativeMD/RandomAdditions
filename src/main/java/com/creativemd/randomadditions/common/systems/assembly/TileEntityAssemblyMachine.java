package com.creativemd.randomadditions.common.systems.assembly;

import com.creativemd.handcraft.recipe.HandRecipe;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.common.upgrade.Upgrade;
import com.creativemd.randomadditions.common.utils.TileEntityMachineBase;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityAssemblyMachine extends TileEntityMachineBase{
	
	private HandRecipe recipe = null;
	
	public void setRecipe(HandRecipe recipe)
	{
		
		this.recipe = recipe;
	}
	
	public boolean hasRecipe()
	{
		return getRecipe() != null;
	}
	
	public HandRecipe getRecipe()
	{
		return recipe;
	}
	
	public SubBlockAssembly getBlock()
	{
		return SubSystemAssembly.instance.getSubBlock(getBlockMetadata());
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("recipe"))
			recipe = HandRecipe.readFromNBT(nbt.getCompoundTag("recipe"));
		else
			recipe = null;
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(recipe != null)
			nbt.setTag("recipe", recipe.writeToNBT());
	}
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		if(recipe != null)
			nbt.setTag("recipe", recipe.writeToNBT());
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		if(pkt.func_148857_g().hasKey("recipe"))
			recipe = HandRecipe.readFromNBT(pkt.func_148857_g().getCompoundTag("recipe"));
		else
			recipe = null;
    }
	
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
	}
	
	@Override
	public void receiveUpdatePacket(NBTTagCompound nbt)
	{
		recipe = HandRecipe.readFromNBT(nbt);
		updateBlock();
	}
}
