package com.creativemd.randomadditions.common.systems.assembly.gui;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.container.slot.SlotInput;
import com.creativemd.creativecore.common.container.slot.SlotOutput;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.premade.SubContainerDialog;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.handcraft.HandCraft;
import com.creativemd.handcraft.recipe.HandRecipe;
import com.creativemd.randomadditions.common.systems.assembly.TileEntityAssemblyMachine;
import com.creativemd.randomadditions.server.slots.SlotUpgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SubContainerAssemblyMachine extends SubContainerTileEntity{
	
	public TileEntityAssemblyMachine machine;
	public int inputs = 0;
	
	public SubContainerAssemblyMachine(TileEntityAssemblyMachine machine, EntityPlayer player) {
		super(machine, player);
		this.machine = machine;
	}

	@Override
	public void createControls() {
		int rows = 1;
		if(machine.input.getSizeInventory() > 4)
			rows = 2;
		//machine.setRecipe(HandCraft.recipes.get(machine.getBlockMetadata()));
		int slotsPerRow = machine.input.getSizeInventory()/rows;
		for (int i = 0; i < machine.input.getSizeInventory(); i++) {
			int row = i/slotsPerRow;
			StackInfo info = null;
			if(machine.hasRecipe())
				if(i < machine.getRecipe().input.length)
					info = machine.getRecipe().input[i];
			addSlotToContainer(new SlotInput(machine.input, i, 19+18*(i-row*slotsPerRow), 15+row*18, info));
		}
		for (int i = 0; i < machine.output.getSizeInventory(); i++) {
			addSlotToContainer(new SlotOutput(machine.output, i, 165+18*i, 15));
		}
		for (int i = 0; i < machine.upgrade.getSizeInventory(); i++) {
			addSlotToContainer(new SlotUpgrade(machine.upgrade, i, 100+18*i-machine.upgrade.getSizeInventory()*9, 96));
		}
		addPlayerSlotsToContainer(player, 19, 118);
	}
	
	@Override
	public SubContainer createLayerFromPacket(World world, EntityPlayer player, NBTTagCompound nbt)
	{
		return new SubContainerDialog(player);
	}

	@Override
	public void onGuiPacket(int controlID, NBTTagCompound nbt, EntityPlayer player) {
		
	}

}
