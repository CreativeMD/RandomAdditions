package com.creativemd.randomadditions.common.systems.enchant.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.enchant.SubBlockEnchant;
import com.creativemd.randomadditions.common.systems.enchant.SubSystemEnchant;

import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityUpgrade extends TileEntityRandom implements ISidedInventory{
	
	public SubBlockEnchant getBlock()
	{
		return SubSystemEnchant.instance.getSubBlock(getBlockMetadata());
	}
	
	public void saveInventory(NBTTagCompound nbt)
	{
		if(inventory == null)
			inventory = new ItemStack[getBlock().getInventorySize()];
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
		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory").getCompoundTag("slot" + i));
		}
	}
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		saveInventory(nbt);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		loadInventory(pkt.func_148857_g());
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       loadInventory(nbt);
    }
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        saveInventory(nbt);
    }
	
	public ItemStack[] inventory = new ItemStack[0];
	
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
		return "Enchanter";
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
		int[] slots = new int[inventory.length];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = i;
		}
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return slot != 2;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}
}
