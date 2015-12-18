package com.creativemd.randomadditions.common.systems.assembly.gui;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiButton;
import com.creativemd.creativecore.common.gui.controls.GuiLabel;
import com.creativemd.creativecore.common.gui.controls.GuiProgressBar;
import com.creativemd.creativecore.common.gui.event.ControlClickEvent;
import com.creativemd.creativecore.common.packet.BlockUpdatePacket;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.handcraft.recipe.HandRecipe;
import com.creativemd.randomadditions.common.gui.controls.GuiGearInformation;
import com.creativemd.randomadditions.common.systems.assembly.TileEntityAssemblyMachine;
import com.n247s.api.eventapi.eventsystem.CustomEventSubscribe;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SubGuiAssemblyMachine extends SubGuiTileEntity{
	
	public TileEntityAssemblyMachine machine;
	
	public SubGuiAssemblyMachine(TileEntityAssemblyMachine machine)
	{
		super(200, 200, machine);
		this.machine = machine;
	}
	
	@Override
	public void onLayerClosed(SubGui gui, NBTTagCompound nbt)
	{
		HandRecipe recipe = HandRecipe.readFromNBT(nbt);
		if(recipe != null)
		{
			PacketHandler.sendPacketToServer(new BlockUpdatePacket(machine, nbt));
		}else
			super.onLayerClosed(gui, nbt);
	}
	
	@Override
	public SubGui createLayerFromPacket(World world, EntityPlayer player, NBTTagCompound nbt)
	{
		return new SubGuiSelectHandRecipe();
	}

	@Override
	public void createControls() {
		controls.add(new GuiLabel("Input", 19, 3));
		controls.add(new GuiLabel("Output", 150, 3));
		controls.add(new GuiGearInformation("gear", machine, 10, 80, 30, 30, machine.getBlockMetadata()));
		controls.add(new GuiProgressBar("progress", 10, 57, 180, 15));
		controls.add(new GuiButton("select recipe", 60, 73, 80, 20));
	}
	
	@CustomEventSubscribe
	public void onButtonClicked(ControlClickEvent event)
	{
		if(event.source.is("select recipe"))
			openNewLayer(new NBTTagCompound());
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		
	}
	
}