package com.creativemd.randomadditions.common.systems.machine.tileentity;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.item.items.RandomItemUpgrade;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.common.upgrade.Upgrade;
import com.creativemd.randomadditions.core.RandomAdditions;

public class TileEntityMachine extends EnergyComponent implements ISidedInventory{

	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return true;
	}

	@Override
	public boolean canProduceEnergy(ForgeDirection direction) {
		return false;
	}
	
	public int getProgressSpeed()
	{
		int speed = 1;
		for (int i = 1; i < 4; i++) {
			if(inventory[i] != null)
				speed += RandomItemUpgrade.getUpgradeModifier(Upgrade.Speed, inventory[i])*inventory[i].stackSize;
		}
		return speed;
	}

	@Override
	public int getInteralStorage() {
		int storage = 5000;
		for (int i = 1; i < 4; i++) {
			if(inventory[i] != null)
				storage += RandomItemUpgrade.getUpgradeModifier(Upgrade.Storage, inventory[i])*inventory[i].stackSize;
		}
		return storage;
	}

	@Override
	public int getMaxOutput() {
		return 0;
	}

	@Override
	public int getMaxInput() {
		int input = 60;
		for (int i = 1; i < 4; i++) {
			if(inventory[i] != null)
				input += RandomItemUpgrade.getUpgradeModifier(Upgrade.Input, inventory[i])*inventory[i].stackSize;
		}
		return input;
	}
	
	public SubBlockMachine getBlock()
	{
		return SubSystemMachine.instance.getSubBlock(getBlockMetadata());
	}
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		nbt.setInteger("progress", progress);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		progress = pkt.func_148857_g().getInteger("progress");
    }
	
	public int progress;
	public boolean working;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       progress = nbt.getInteger("progress");
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
		if(inventory.length == 0 && inventory.length != 4 + getBlock().getNumberOfInputs())
			inventory = new ItemStack[4 + getBlock().getNumberOfInputs()];
		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory").getCompoundTag("slot" + i));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("progress", progress);
        saveInventory(nbt);
    }
	
	/**Output, Upgrade 1-3, Input**/
	public ItemStack[] inventory = new ItemStack[5];
	
	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(slot > inventory.length && slot < 4 + getBlock().getNumberOfInputs())
		{
			ItemStack[] tempInv = new ItemStack[4 + getBlock().getNumberOfInputs()];
			for (int i = 0; i < inventory.length; i++) {
				tempInv[i] = inventory[i];
			}
			inventory = tempInv;
		}
		if(slot < inventory.length)
			return inventory[slot];
		else
			return null;
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
		return getBlock().name;
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
		//TOOD Check if work
		ForgeDirection sideDirection = ForgeDirection.getOrientation(side);
		if(sideDirection == ForgeDirection.UP || getDirection().getRotation(ForgeDirection.UP) == sideDirection || getDirection() == sideDirection)
		{
			int[] slots = new int[getBlock().getNumberOfInputs()];
			for (int i = 0; i < slots.length; i++) {
				slots[i] = 4+i;
			}
			return slots;
		}
		if(sideDirection == ForgeDirection.DOWN || getDirection().getRotation(ForgeDirection.DOWN) == sideDirection || getDirection().getOpposite() == sideDirection)
			return new int[]{0};
		return new int[0];
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		if(slot == 0)
			return false;
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}
	
	public ArrayList<ItemStack> getInput()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for (int i = 0; i < getBlock().getNumberOfInputs(); i++) {
			stacks.add(getStackInSlot(4+i));
		}
		return stacks;
	}
	
	public boolean canDoProgress(MachineRecipe recipe)
    {
		ItemStack output = recipe.getOutput(getInput());
        if (getStackInSlot(0) == null) return true;
        if (!getStackInSlot(0).isItemEqual(output) || !ItemStack.areItemStackTagsEqual(getStackInSlot(0), output)) return false;
        int result = getStackInSlot(0).stackSize + output.stackSize;
        return result <= getInventoryStackLimit() && result <= getStackInSlot(0).getMaxStackSize() && result <= output.getMaxStackSize();
    }
	
	public int getNeededPower(int speed)
	{
		int power = speed*speed+19;
		float percent = 0;
		for(int i =  1; i < 4; i++) {
			if(inventory[i] != null)
				percent += RandomItemUpgrade.getUpgradeModifier(Upgrade.Power, inventory[i])*inventory[i].stackSize;
		}
		power -= (int)(percent*(float)power);
		if(power < 1)
			power = 1;
		return power;
	}
	
	public int playTimeLeft = 0;
	
	@Override
	public void updateEntity() 
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			if(RedstoneControlHelper.handleRedstoneInput(this, this))
			{
				MachineRecipe recipe = getBlock().getRecipe(getInput());
				if(recipe != null && canDoProgress(recipe))
				{
					int speed = getProgressSpeed();
					int neededPower = getNeededPower(speed);
					if(getCurrentPower() >= neededPower)
					{
						drainPower(neededPower);
						progress += speed;
						if(!working)
						{
							working = true;
							updateBlock();
						}
					}
				}
				else
				{
					if(working)
					{
						working = false;
						updateBlock();
					}
					progress = 0;
				}
				ArrayList<ItemStack> input = getInput();
				if(recipe != null && progress >= recipe.neededPower)
				{
					if(getStackInSlot(0) == null)
						setInventorySlotContents(0, recipe.getOutput(input).copy());
					else
						getStackInSlot(0).stackSize += recipe.getOutput(input).stackSize;
					for (int j = 0; j < recipe.input.size(); j++) {
						boolean done = false;
						for (int i = 0; i < input.size(); i++) {
							if(!done && recipe.isObjectEqual(input.get(i), recipe.input.get(j)))
							{
								ItemStack stack = getStackInSlot(4+i);
								stack.stackSize -= recipe.getStackSize(j);
								if(stack.stackSize == 0)
									stack = null;
								setInventorySlotContents(4+i, stack);
								done = true;
							}
						}
					}
					progress = 0;
				}
			}else{
				if(working)
				{
					working = false;
					updateBlock();
				}
			}
			if(working)
			{
				if(playTimeLeft == 0)
				{
					playTimeLeft = getBlock().getPlayTime();
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, RandomAdditions.modid + ":" + getBlock().name.toLowerCase(), getBlock().getPlayVolume(), 1);
				}
				playTimeLeft--;
			}
		}else{
			getBlock().onClientTick(this);
		}
	}
}
