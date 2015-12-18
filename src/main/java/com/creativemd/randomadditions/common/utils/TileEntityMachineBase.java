package com.creativemd.randomadditions.common.utils;

import com.creativemd.creativecore.common.utils.InventoryUtils;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.item.items.RandomItemUpgrade;
import com.creativemd.randomadditions.common.subsystem.BlockSub;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.common.upgrade.Upgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMachineBase extends EnergyComponent implements ISidedInventory{
	
	public InventoryBasic input;
	public InventoryBasic output;
	public InventoryBasic upgrade;
	
	public NBTTagCompound loadNBT;
	public IMachineBase base;
	
	public IMachineBase getMachineBase()
	{
		if(base == null)
		{
			try{
				base = getBlockType() instanceof BlockSub ? ((BlockSub)getBlockType()).system.getSubBlock(getBlockMetadata()) instanceof IMachineBase ? (IMachineBase)((BlockSub)getBlockType()).system.getSubBlock(getBlockMetadata()) : null : null;
			}catch(Exception e){
				
			}
		}
		if(loadNBT != null && base != null)
		{
			NBTTagCompound tempNBT = loadNBT;
			loadNBT = null;
			loadTileEntity(tempNBT);
		}
		return base;
	}
	
	public int getInputSize()
	{
		return getMachineBase().getInputWidth()*getMachineBase().getInputHeight();
	}

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
		return Upgrade.getInteralStorage(upgrade, 5000);
	}

	@Override
	public int getMaxOutput() {
		return 0;
	}

	@Override
	public int getMaxInput() {
		return Upgrade.getMaxInput(upgrade, 60);
	}
	
	public float getCraftingSpeed()
	{
		return Upgrade.getProgressSpeed(upgrade, 1F);
	}
	
	public float getNeededPower(float speed)
	{
		float power = speed*speed;
		float percent = 0;
		for (int i = 1; i < upgrade.getSizeInventory(); i++) {
			if(upgrade.getStackInSlot(i) != null)
				percent += RandomItemUpgrade.getUpgradeModifier(Upgrade.Power, upgrade.getStackInSlot(i))*upgrade.getStackInSlot(i).stackSize;
		}
		power -= (int)(percent*(float)power);
		if(power < 1)
			power = 1;
		return power;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(worldObj != null)
			loadTileEntity(nbt);	
		else
			loadNBT = nbt;
	}
	
	public void loadTileEntity(NBTTagCompound nbt)
	{
		input = InventoryUtils.loadInventoryBasic(nbt.getCompoundTag("input"), getInputSize());
		output = InventoryUtils.loadInventoryBasic(nbt.getCompoundTag("output"), getMachineBase().getNumberOfOutputs());
		upgrade = InventoryUtils.loadInventoryBasic(nbt.getCompoundTag("upgrade"), getMachineBase().getNumberOfUpgradeSlots());
	}
	
	public void createInventory()
	{
		input = InventoryUtils.loadInventoryBasic(null, getInputSize());
		output = InventoryUtils.loadInventoryBasic(null, getMachineBase().getNumberOfOutputs());
		upgrade = InventoryUtils.loadInventoryBasic(null, getMachineBase().getNumberOfUpgradeSlots());
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		if(input == null)
			createInventory();
		nbt.setTag("input", InventoryUtils.saveInventoryBasic(input));
		nbt.setTag("output", InventoryUtils.saveInventoryBasic(output));
		nbt.setTag("upgrade", InventoryUtils.saveInventoryBasic(upgrade));
	}

	@Override
	public int getSizeInventory() {
		return input.getSizeInventory()+output.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if(input.getSizeInventory() <= index)
			return output.getStackInSlot(index-input.getSizeInventory());
		return input.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int size) {
		if(input.getSizeInventory() <= index)
			return output.decrStackSize(index-input.getSizeInventory(), size);
		return input.decrStackSize(index, size);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		if(input.getSizeInventory() <= index)
			return output.getStackInSlotOnClosing(index-input.getSizeInventory());
		return input.getStackInSlotOnClosing(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if(input.getSizeInventory() <= index)
			output.setInventorySlotContents(index-input.getSizeInventory(), stack);
		input.setInventorySlotContents(index, stack);
	}

	@Override
	public String getInventoryName() {
		return "MachineBase";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(input.getSizeInventory() <= index)
			return false;
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		ForgeDirection sideDirection = ForgeDirection.getOrientation(side);
		if(sideDirection == ForgeDirection.UP || getDirection().getRotation(ForgeDirection.UP) == sideDirection || getDirection() == sideDirection)
		{
			int[] slots = new int[input.getSizeInventory()];
			for (int i = 0; i < slots.length; i++) {
				slots[i] = i;
			}
			return slots;
		}
		if(sideDirection == ForgeDirection.DOWN || getDirection().getRotation(ForgeDirection.DOWN) == sideDirection || getDirection().getOpposite() == sideDirection)
		{
			int[] slots = new int[output.getSizeInventory()];
			for (int i = 0; i < slots.length; i++) {
				slots[i] = i+input.getSizeInventory();
			}
			return slots;
		}
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, int side) {
		if(input.getSizeInventory() <= index)
			return false;
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, int side) {
		return true;
	}
	
	@Override
	public void updateEntity()
	{
		super.updateEntity();
		if(worldObj != null && loadNBT != null && base == null)
			getMachineBase();
	}
	
}
