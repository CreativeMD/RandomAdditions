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
import com.creativemd.randomadditions.common.utils.TileEntityMachineBase;
import com.creativemd.randomadditions.core.RandomAdditions;

public class TileEntityMachine extends TileEntityMachineBase{
	
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
       
       //Converting old nbt data to the new inventory system
       if(nbt.hasKey("inventory"))
       {
    	   ItemStack[] inventory = new ItemStack[nbt.getCompoundTag("inventory").getInteger("size")];
    	   if(inventory.length == 0 && inventory.length != 4 + getBlock().getNumberOfInputs())
    		   inventory = new ItemStack[4 + getBlock().getNumberOfInputs()];
    	   for (int i = 0; i < inventory.length; i++) {
    		   inventory[i] = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("inventory").getCompoundTag("slot" + i));
    	   }
    	   
    	   output.setInventorySlotContents(0, inventory[0]);
    	   upgrade.setInventorySlotContents(0, inventory[1]);
    	   upgrade.setInventorySlotContents(1, inventory[2]);
    	   upgrade.setInventorySlotContents(2, inventory[3]);
    	   
    	   
    	   input.setInventorySlotContents(0, inventory[4]);
    	   if(getBlock().getNumberOfInputs() > 1)
    	   {
    		   input.setInventorySlotContents(1, inventory[5]);
    		   input.setInventorySlotContents(2, inventory[6]);
    	   }
       }
    }
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("progress", progress);
        //saveInventory(nbt);
    }
	
	public ArrayList<ItemStack> getInput()
	{
		
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		if(input != null)
		{
			for (int i = 0; i < input.getSizeInventory(); i++) {
				stacks.add(input.getStackInSlot(i));
			}
		}
		return stacks;
	}
	
	public boolean canDoProgress(MachineRecipe recipe)
    {
		ItemStack output = recipe.getOutput(getInput());
        if (this.output.getStackInSlot(0) == null) return true;
        if (!this.output.getStackInSlot(0).isItemEqual(output) || !ItemStack.areItemStackTagsEqual(this.output.getStackInSlot(0), output)) return false;
        int result = this.output.getStackInSlot(0).stackSize + output.stackSize;
        return result <= this.output.getInventoryStackLimit() && result <= this.output.getStackInSlot(0).getMaxStackSize() && result <= output.getMaxStackSize();
    }
	
	public int playTimeLeft = 0;
	
	@Override
	public void updateEntity() 
	{
		super.updateEntity();
		if(!worldObj.isRemote)
		{
			if(input == null)
				getMachineBase();
			if(RedstoneControlHelper.handleRedstoneInput(this, this))
			{
				MachineRecipe recipe = getBlock().getRecipe(getInput());
				if(recipe != null && canDoProgress(recipe))
				{
					float speed = getCraftingSpeed()*5;
					float neededPower = getNeededPower(speed);
					if(getCurrentPower() >= neededPower)
					{
						drainPower(Math.round(neededPower));
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
					if(output.getStackInSlot(0) == null)
						output.setInventorySlotContents(0, recipe.getOutput(input).copy());
					else
						output.getStackInSlot(0).stackSize += recipe.getOutput(input).stackSize;
					for (int j = 0; j < recipe.input.size(); j++) {
						boolean done = false;
						for (int i = 0; i < input.size(); i++) {
							if(!done && recipe.isObjectEqual(input.get(i), recipe.input.get(j)))
							{
								ItemStack stack = this.input.getStackInSlot(i);
								stack.stackSize -= recipe.getStackSize(j);
								if(stack.stackSize == 0)
									stack = null;
								this.input.setInventorySlotContents(i, stack);
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
