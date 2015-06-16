package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.systems.enchant.SubSystemEnchant;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;
import com.creativemd.randomadditions.core.CraftMaterial;

public class SubContainerUpgrade extends SubContainerTileEntity{
	
	public TileEntityUpgrade upgrade;
	
	public SubContainerUpgrade(TileEntityUpgrade upgrade)
	{
		super(upgrade);
		this.upgrade = upgrade;
	}
	
	@Override
	public void onGuiPacket(int control, String value, EntityPlayer player) {
		ItemStack stack = upgrade.getStackInSlot(0);
		if(SubSystemEnchant.canEnchantItem(stack, player))
		{
			player.addExperienceLevel(-SubSystemEnchant.getRequiredLevel(stack));
			CraftMaterial.setLevel(ItemTool.getMaterial(stack), stack, SubSystemEnchant.getLevel(stack)+1);
			upgrade.getWorldObj().playAuxSFX(1021, upgrade.xCoord, upgrade.yCoord, upgrade.zCoord, 1);
		}
	}

	@Override
	public ArrayList<Slot> getSlots(EntityPlayer player) {
		ArrayList<Slot> slots = new ArrayList<Slot>();
		slots.add(new Slot((IInventory) tileEntity, 0, 26, 26));
		slots.addAll(getPlayerSlots(player, 8, 84));
		return slots;
	}

	@Override
	public boolean doesGuiNeedUpdate() {
		return false;
	}

}
