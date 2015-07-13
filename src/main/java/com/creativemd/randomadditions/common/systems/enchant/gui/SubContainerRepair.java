package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.tools.Tool;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.server.slots.SlotOutput;

public class SubContainerRepair extends SubContainerTileEntity{
	
	public TileEntityUpgrade upgrade;
	
	public SubContainerRepair(TileEntityUpgrade upgrade, EntityPlayer player)
	{
		super(upgrade, player);
		this.upgrade = upgrade;
	}
	
	
	@Override
	public void onGuiPacket(int control, NBTTagCompound value, EntityPlayer player) {
		if(upgrade.inventory[0] != null && upgrade.inventory[2] == null)
		{
			ItemStack stackTool = null;
			for (int i = 0; i < 2; i++) {
				if(upgrade.inventory[i] != null && ItemTool.getTool(upgrade.inventory[i]) != null)
					stackTool = upgrade.inventory[i];
			}
			if(stackTool != null)
			{
				CraftMaterial material = ItemTool.getMaterial(stackTool);
				Tool tool = ItemTool.getTool(stackTool);
				ItemStack ingot = null;
				String ore = material.itemName;
				int index = -1;
				for (int i = 0; i < 2; i++) {
					if(upgrade.inventory[i] != null)
					{
						int[] ores = OreDictionary.getOreIDs(upgrade.inventory[i]);
						for (int j = 0; j < ores.length; j++) {
							if(ore.equals(OreDictionary.getOreName(ores[j])))
							{
								ingot = upgrade.inventory[i]; 
								index = i;
							}
						}
					}
				}
				if(ingot != null)
				{
					int amount = tool.cost;
					if(amount > 2)
						amount--;
					ItemStack output = stackTool.copy();
					int damage = ItemTool.getItemDamage(output);
					int repairPerIngot = (tool.durabilityFactor*material.durability)/amount;
					int ingots = (int) Math.ceil((double)damage/(double)repairPerIngot);
					if(ingot.stackSize < ingots)
						ingots = ingot.stackSize;
					
					//DO IT
					ingot.stackSize -= ingots;
					
					if(ingot.stackSize == 0)
						upgrade.inventory[index] = null;
					
					if(index == 0)
						upgrade.inventory[1] = null;
					else
						upgrade.inventory[0] = null;
					
					damage -= ingots*repairPerIngot;
					if(damage < 0)
						damage = 0;
					ItemTool.setItemDamage(output, damage);
					
					upgrade.inventory[2] = output;
					upgrade.getWorldObj().playAuxSFX(1021, upgrade.xCoord, upgrade.yCoord, upgrade.zCoord, 1);
				}
			}
		}	
	}

	@Override
	public void createControls() {
		addSlotToContainer(new Slot((IInventory) tileEntity, 0, 26, 31));
		addSlotToContainer(new Slot((IInventory) tileEntity, 1, 71, 31));
		addSlotToContainer(new SlotOutput((IInventory) tileEntity, 2, 134, 31));
		addPlayerSlotsToContainer(player);
	}
}
