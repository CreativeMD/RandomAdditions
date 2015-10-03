package com.creativemd.randomadditions.common.systems.machine.config;

import java.util.ArrayList;

import com.creativemd.creativecore.client.avatar.Avatar;
import com.creativemd.creativecore.client.avatar.AvatarItemStack;
import com.creativemd.creativecore.common.container.slot.ContainerControl;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.creativecore.common.utils.stack.StackInfo;
import com.creativemd.ingameconfigmanager.api.common.branch.machine.ConfigMachineDisableBranch;
import com.creativemd.ingameconfigmanager.api.common.machine.RecipeMachine;
import com.creativemd.ingameconfigmanager.api.common.segment.machine.AddRecipeSegment;
import com.creativemd.ingameconfigmanager.api.tab.ModTab;
import com.creativemd.ingameconfigmanager.mod.block.AdvancedGridRecipe;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RARecipeMachine extends RecipeMachine<MachineRecipe>{
	
	public SubBlockMachine machine;
	
	public RARecipeMachine(ModTab tab, SubBlockMachine machine) {
		super(tab, machine.name);
		this.machine = machine;
		if(FMLCommonHandler.instance().getEffectiveSide().isClient())
		{
			initClient();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void initClient()
	{
		Avatar avatar = new AvatarItemStack(machine.getItemStack());
		if(hasDisableBranch())
			disableBranch.avatar = avatar;
		if(hasAddedBranch())
			addBranch.avatar = avatar;
	}
	
	@Override
	public int getWidth() {
		return 1;
	}

	@Override
	public int getHeight() {
		return machine.getNumberOfInputs();
	}

	@Override
	public int getOutputCount() {
		return 1;
	}

	@Override
	public void addRecipeToList(MachineRecipe recipe) {
		machine.getRecipes().add(recipe);
	}

	@Override
	public void clearRecipeList() {
		machine.getRecipes().clear();
	}

	@Override
	public ItemStack[] getOutput(MachineRecipe recipe) {
		return new ItemStack[]{recipe.output};
	}

	@Override
	public ArrayList<MachineRecipe> getAllExitingRecipes() {
		return machine.getRecipes();
	}

	@Override
	public void fillGrid(ItemStack[] grid, MachineRecipe recipe) {
		for (int i = 0; i < recipe.input.size(); i++)
			if(recipe.input.get(i) != null)
				grid[i] = recipe.input.get(i).getItemStack();
	}

	@Override
	public void fillGridInfo(StackInfo[] grid, MachineRecipe recipe) {
		for (int i = 0; i < recipe.input.size(); i++)
			if(recipe.input.get(i) != null)
				grid[i] = recipe.input.get(i);
	}
	
	@Override
	public void onBeforeSave(MachineRecipe recipe, NBTTagCompound nbt)
	{
		nbt.setInteger("power", recipe.neededPower);
	}
	
	@Override
	public void parseExtraInfo(NBTTagCompound nbt, AddRecipeSegment segment, ArrayList<GuiControl> guiControls, ArrayList<ContainerControl> containerControls)
	{
		for (int i = 0; i < guiControls.size(); i++) {
			if(guiControls.get(i).is("power"))
			{
				int power = 0;
				try
				{
					power = Integer.parseInt(((GuiTextfield)guiControls.get(i)).text);
				}catch(Exception e){
					power = 0;
				}
				nbt.setInteger("power", power);
			}
		}
	}
	
	@Override
	public void onControlsCreated(MachineRecipe recipe, boolean isAdded, int x, int y, int maxWidth, ArrayList<GuiControl> guiControls, ArrayList<ContainerControl> containerControls)
	{
		if(isAdded)
		{
			guiControls.add(new GuiTextfield("power", recipe != null ? "" + recipe.neededPower : "200", x+maxWidth-80, y, 70, 20).setNumbersOnly());
		}
	}

	@Override
	public MachineRecipe parseRecipe(StackInfo[] input, ItemStack[] output, NBTTagCompound nbt, int width, int height) {
		return new MachineRecipe(input, output[0], nbt.getInteger("power"));
	}

	@Override
	public ItemStack getAvatar() {
		return null;
	}

	@Override
	public boolean doesSupportStackSize() {
		return true;
	}

}
