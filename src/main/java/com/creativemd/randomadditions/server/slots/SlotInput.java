package com.creativemd.randomadditions.server.slots;

import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.SubContainerMachine;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot{
	
	public SubContainerMachine container;
	
	public SlotInput(SubContainerMachine container, IInventory inventory, int index, int x,
			int y) {
		super(inventory, index, x, y);
		this.container = container;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
        return container.block.isItemValid(stack);
    }

}
