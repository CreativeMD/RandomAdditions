package com.creativemd.randomadditions.common.systems.deco.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityShelf extends TileEntityRandom implements ISidedInventory{
	
	public int plank;
	
	@Override
	@SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 200.0D;
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       plank = nbt.getInteger("plank");
       loadInventory(nbt);
    }
	
	@Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("plank", plank);
        saveInventory(nbt);
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
	{
		super.getDescriptionNBT(nbt);
		nbt.setInteger("plank", plank);
		saveInventory(nbt);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		plank = pkt.func_148857_g().getInteger("plank");
		loadInventory(pkt.func_148857_g());
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
		inventory = new ItemStack[27];
		for (int i = 0; i < inventory.length; i++) {
			inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory").getCompoundTag("slot" + i));
		}
	}
	
	public ItemStack[] inventory = new ItemStack[27];
	
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
		return "Shelf";
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
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return true;
	}
	
}
