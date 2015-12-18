package com.creativemd.randomadditions.common.systems.battery.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.items.RandomItemEnergy;
import com.creativemd.randomadditions.common.systems.battery.SubBlockBattery;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.producer.SubSystemProducer;

public class TileEntityBattery extends EnergyComponent implements ISidedInventory{
	
	public SubBlockBattery getBlock()
	{
		return SubSystemBattery.instance.getSubBlock(getBlockMetadata());
	}
	
	@Override
	public int getInteralStorage() {
		return getBlock().storage;
	}
	

	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return this.getDirection() == direction;
	}

	@Override
	public boolean canProduceEnergy(ForgeDirection direction) {
		return this.getDirection() != direction;
	}
	
	@Override
	public int getMaxInput() {
		return getBlock().input;
	}
	
	@Override
	public int getMaxOutput() {
		return getBlock().output;
	}

	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       loadInventory(nbt);
    }
	
	public void saveInventory(NBTTagCompound nbt)
	{
		NBTTagCompound nbtInv = nbt.getCompoundTag("inventory");
		for (int i = 0; i < inventory.length; i++) {
			NBTTagCompound newNBT = new NBTTagCompound();
			if(inventory[i] != null)
				inventory[i].writeToNBT(newNBT);
			nbtInv.setTag("slot" + i, newNBT);
		}
		nbtInv.setInteger("size", inventory.length);
		nbt.setTag("inventory", nbtInv);
	}
	
	public void loadInventory(NBTTagCompound nbt)
	{
		inventory = new ItemStack[nbt.getCompoundTag("inventory").getInteger("size")];
		if(inventory.length == 0)
			inventory = new ItemStack[5];
		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory").getCompoundTag("slot" + i));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        saveInventory(nbt);
    }
	
	@Override
	public void updateEntity() 
	{
		if(!worldObj.isRemote)
		{
			ItemStack stack = inventory[0];
			if(RandomItem.getRandomItem(stack) instanceof RandomItemEnergy)
			{
				RandomItemEnergy item = (RandomItemEnergy) RandomItem.getRandomItem(stack);
				float power = getProvideablePower();
				if(power > 0)
				{
					power = item.onRecieveEnergy(stack, power);
					drainPower(power);
				}
			}
		}
		super.updateEntity();
	}
	
	public ItemStack[] inventory = new ItemStack[1];
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int stack) {
		if (inventory[slot] != null)
		{
			ItemStack var3;
			
			if (inventory[slot].stackSize <= stack)
			{
				var3 = inventory[slot];
				inventory[slot] = null;
				return var3;
			}
			else
			{
				var3 = inventory[slot].splitStack(stack);
				
				if (inventory[slot].stackSize == 0)
				{
					inventory[slot] = null;
	            }
				return var3;
	            }
	        }
	        else
	        {
	            return null;
	        }
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return inventory[slot];
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
	}

	@Override
	public String getInventoryName() {
		return "Battery";
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
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
		
	}

	@Override
	public void closeInventory() {
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}
	
}
