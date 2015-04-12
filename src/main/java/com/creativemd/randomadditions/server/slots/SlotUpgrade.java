package com.creativemd.randomadditions.server.slots;

import com.creativemd.randomadditions.common.item.items.RandomItemUpgrade;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUpgrade extends Slot{

	public SlotUpgrade(IInventory inventory, int index, int x,
			int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
    {
        return RandomItemUpgrade.isUpgrade(stack);
    }
	
	@Override
	public int getSlotStackLimit()
    {
        return 3;
    }
}
