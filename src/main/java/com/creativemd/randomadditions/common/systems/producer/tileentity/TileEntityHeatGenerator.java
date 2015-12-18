package com.creativemd.randomadditions.common.systems.producer.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.utils.InventoryUtils;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;
import com.creativemd.randomadditions.common.systems.producer.blocks.HeatGenerator;
import com.creativemd.randomadditions.core.RandomAdditions;

public class TileEntityHeatGenerator extends EnergyComponent implements ISidedInventory{
	
	public static int producePerTick = 60;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
    {
       super.readFromNBT(nbt);
       inventory = InventoryUtils.loadInventory(nbt.getCompoundTag("inventory"));
       if(inventory == null || inventory.length < 4)
			inventory = new ItemStack[4];
       fuel = nbt.getIntArray("fuel");
       maxfuel = nbt.getIntArray("maxfuel");
    }
	
	@Override
	public void getDescriptionNBT(NBTTagCompound nbt)
    {
		super.getDescriptionNBT(nbt);
		nbt.setTag("inventory", InventoryUtils.saveInventory(inventory));
		nbt.setBoolean("active", isActive);
		nbt.setIntArray("fuel", fuel);
		nbt.setIntArray("maxfuel", maxfuel);
    }
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
		super.onDataPacket(net, pkt);
		inventory = InventoryUtils.loadInventory(pkt.func_148857_g().getCompoundTag("inventory"));
		if(inventory == null || inventory.length < 4)
			inventory = new ItemStack[4];
		isActive = pkt.func_148857_g().getBoolean("active");
		fuel = pkt.func_148857_g().getIntArray("fuel");
		maxfuel = pkt.func_148857_g().getIntArray("maxfuel");
    }
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setTag("inventory", InventoryUtils.saveInventory(inventory));
        nbt.setIntArray("fuel", fuel);
        nbt.setIntArray("maxfuel", maxfuel);
    }
	
	/**Output, Upgrade 1-3, Input**/
	public ItemStack[] inventory = new ItemStack[4];
	
	public int[] fuel = new int[4];
	public int[] maxfuel = new int[4];
	
	public boolean isActive;
	
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
	
	public int playTimeLeft = 0;
	public static final int playTime = 120;
	
	@Override
	public void updateEntity() 
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			boolean tempActive = isActive;
			if(RedstoneControlHelper.handleRedstoneInput(this, this))
			{
				if(getInteralStorage()-getCurrentPower() > 0)
				{
					for (int i = 0; i < inventory.length; i++) {
						if(fuel[i] <= 0)
						{
							if(inventory[i] != null)
							{
								int burn = TileEntityFurnace.getItemBurnTime(inventory[i]);
								if(burn > 0)
								{
									inventory[i].stackSize--;
									if(inventory[i].stackSize == 0)
										inventory[i] = null;
									fuel[i] = (int) (burn/HeatGenerator.fuelGeneration);
									maxfuel[i] = fuel[i];
									if(!isActive)
									{
										isActive = true;
										updateBlock();
									}
								}
							}
						}else{
							float power = producePerTick;
							if(fuel[i] < power)
								power = fuel[i];
							
							power = receivePower(power);
							fuel[i] -= power;
							isActive = true;
						}
					}
				}else
					isActive = false;
			}else{
				isActive = false;
			}
			if(isActive != tempActive)
			{
				updateBlock();
			}
			if(isActive)
			{
				if(playTimeLeft == 0)
				{
					playTimeLeft = playTime;
					worldObj.playSoundEffect(xCoord, yCoord, zCoord, RandomAdditions.modid + ":furnace", 1, 1);
				}
				playTimeLeft--;
			}
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
		return "HeatGenerator";
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
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,1,2,3};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack,
			int side) {
		return true;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack,
			int side) {
		return true;
	}

	@Override
	public boolean canRecieveEnergy(ForgeDirection direction) {
		return false;
	}
	
	@Override
	public ForgeDirection getDirection()
	{
		return ForgeDirection.EAST;
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
		return (int) getCurrentPower();
	}

	@Override
	public int getMaxInput() {
		return 0;
	}
}
