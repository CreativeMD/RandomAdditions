package com.creativemd.randomadditions.common.systems.machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlockMachine extends SubBlock{
	
	private ArrayList<MachineRecipe> recipes = new ArrayList<MachineRecipe>();
	public Class<?> neiClass;
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, meta);
	}
	
	public ArrayList<MachineRecipe> getRecipes()
	{
		return recipes;
	}
	
	@Override
	public boolean hasBlockTexture()
	{
		return false;
	}
	
	public void registerRecipe(MachineRecipe recipe)
	{
		recipes.add(recipe);
	}
	
	public MachineRecipe getRecipe(ArrayList<ItemStack> stacks)
	{
		ArrayList<MachineRecipe> recipes = getRecipes();
		for (int i = 0; i < recipes.size(); i++) {
			if(recipes.get(i).canDo(stacks))
				return recipes.get(i);
		}
		return null;
	}
	
	public SubBlockMachine(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity)
	{
		return new SubGuiMachine(this, (TileEntityMachine) tileEntity);
	}
	
	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity)
	{
		return new SubContainerMachine((TileEntityMachine) tileEntity, this);
	}
	
	public boolean isItemValid(ItemStack stack)
	{
		ArrayList<MachineRecipe> recipes = getRecipes();
		for (int i = 0; i < recipes.size(); i++) {
			if(recipes.get(i).doesContainItem(stack))
				return true;
		}
		return false;
	}
	
	@Override
	public boolean isSolid(TileEntity tileEntity)
	{
		return false;
	}
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityMachine();
	}
	
	public abstract int getNumberOfInputs();
	
	public abstract void registerRecipes();
	
	public abstract void renderProgressField(double percent);
	
	public abstract void addRecipe(ItemStack battery, ItemStack output);
	
	public void onRegister()
	{
		registerRecipes();
		addRecipe(SubSystemBattery.instance.getItemStack(0), getItemStack());
	}
	
	@Override
	public int getRotation()
	{
		return 1;
	}
	
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityMachine)
		{
			((TileEntityMachine) tileEntity).inventory = new ItemStack[4 + getNumberOfInputs()];
		}
	}

	public Map<ArrayList<ItemStack>, ItemStack> getRecipesForNEI()
	{
		Map<ArrayList<ItemStack>, ItemStack> result = new HashMap();
		ArrayList<MachineRecipe> recipes = getRecipes();
		for (int i = 0; i < recipes.size(); i++) {
			result.putAll(recipes.get(i).getNEIRecipes());
		}
		return result;
	}
}
