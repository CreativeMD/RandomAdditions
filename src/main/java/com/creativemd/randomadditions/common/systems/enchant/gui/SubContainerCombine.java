package com.creativemd.randomadditions.common.systems.enchant.gui;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.tools.Tool;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;
import com.creativemd.randomadditions.server.slots.SlotOutput;

public class SubContainerCombine extends SubContainerTileEntity{
	
	public TileEntityUpgrade upgrade;
	
	public SubContainerCombine(TileEntityUpgrade upgrade, EntityPlayer player)
	{
		super(upgrade, player);
		this.upgrade = upgrade;
	}
	
	
	@Override
	public void onGuiPacket(int control, NBTTagCompound value, EntityPlayer player) {
		if(upgrade.inventory[0] != null && upgrade.inventory[1] != null && upgrade.inventory[2] == null)
		{
			if(ItemTool.getTool(upgrade.inventory[0]) != null && ItemTool.getTool(upgrade.inventory[0]) == ItemTool.getTool(upgrade.inventory[1]))
			{
				ItemStack stack = upgrade.inventory[0].copy();
				ItemStack stack2 = upgrade.inventory[1];
				ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
				if(stack2 != null)
				{
					for (int i = 0; i < RandomAdditions.materials.size(); i++) {
						Tool tool = ItemTool.getTool(stack2);
						EnchantmentModifier modifier = RandomAdditions.materials.get(i).enchantments.get(tool);
						int level = CraftMaterial.getLevel(RandomAdditions.materials.get(i), stack2);
						if(modifier != null && level == 3)
						{
							boolean compatible = true;
							ArrayList<EnchantmentModifier> incompatible = modifier.getIncompatibleModifiers();
							for (int j = 0; j < incompatible.size(); j++) {
								if(modifiers.contains(incompatible.get(j)))
									compatible = false;
							}
							if(compatible && !modifiers.contains(modifier))
							{
								CraftMaterial.setLevel(RandomAdditions.materials.get(i), stack, level);
								CraftMaterial.setLevel(RandomAdditions.materials.get(i), stack2, 0);
							}
						}
					}
				}
				upgrade.inventory[0] = null;
				upgrade.inventory[2] = stack;
				upgrade.getWorldObj().playAuxSFX(1021, upgrade.xCoord, upgrade.yCoord, upgrade.zCoord, 1);
			}
		}	
	}
	
	@Override
	public void createControls() {
		addSlotToContainer(new Slot((IInventory) tileEntity, 0, 26, 31));
		addSlotToContainer(new Slot((IInventory) tileEntity, 1, 71, 31));
		addSlotToContainer(new SlotOutput((IInventory) tileEntity, 2, 134, 31));
		addPlayerSlotsToContainer(player, 8, 84);
	}

}
