package com.creativemd.randomadditions.common.systems.machine;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;
import com.creativemd.randomadditions.server.slots.SlotInput;
import com.creativemd.randomadditions.server.slots.SlotOutput;
import com.creativemd.randomadditions.server.slots.SlotUpgrade;

public class SubContainerMachine extends SubContainerTileEntity{
	
	public SubBlockMachine block;
	
	public SubContainerMachine(TileEntityMachine machine, SubBlockMachine block, EntityPlayer player)
	{
		super(machine, player);
		this.block = block;
	}
	
	@Override
	public void onGuiPacket(int control, String value, EntityPlayer player) {
		
	}

	@Override
	public ArrayList<Slot> getSlots(EntityPlayer player) {
		
		ArrayList<Slot> slots = new ArrayList<Slot>();
		if(tileEntity instanceof TileEntityMachine)
		{
			slots.add(new SlotOutput((IInventory) tileEntity, 0, 145, 28));
			int x = 66;
			slots.add(new SlotUpgrade((IInventory) tileEntity, 1, x, 62));
			slots.add(new SlotUpgrade((IInventory) tileEntity, 2, x+18, 62));
			slots.add(new SlotUpgrade((IInventory) tileEntity, 3, x+18*2, 62));
			int inputs = block.getNumberOfInputs();
			if(tileEntity.getWorldObj().isRemote && ((TileEntityMachine)tileEntity).inventory.length != 4 + inputs)
				((TileEntityMachine)tileEntity).inventory = new ItemStack[4+inputs];
				
			for (int i = 0; i < inputs; i++) {
				if(inputs == 1)
					slots.add(new SlotInput(this, (IInventory) tileEntity, 4+i, 26, 28));
				else
					slots.add(new SlotInput(this, (IInventory) tileEntity, 4+i, 26, 10+i*18));
			}
		}
		slots.addAll(getPlayerSlots(player, 8, 84));
		return slots;
	}

	@Override
	public boolean doesGuiNeedUpdate() {
		return true;
	}

}
