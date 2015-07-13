package com.creativemd.randomadditions.common.systems.machine.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.item.ItemCore;
import com.creativemd.randomadditions.common.item.ItemRandomArmor;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipeCool;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;

public class CoolingPool extends SubBlockMachine{

	public CoolingPool(SubBlockSystem system) {
		super("CoolingPool", system);
	}
	
	@Override
	public void registerRecipes() {
		//registerRecipe(new MachineRecipeCool(RandomAdditions.items, 100));
		registerRecipe(new MachineRecipeCool(RandomAdditions.tools, 100));
		registerRecipe(new MachineRecipe(Items.blaze_rod, new ItemStack(Items.stick, 16), 100));
		//registerRecipe(new MachineRecipeCool(RandomAdditions.itemIngot, 100));
		for (int j = 0; j < RandomAdditions.materials.size(); j++) {
			for (int i = 0; i < 4; i++) {
				String name = "RAArmor" + ItemRandomArmor.getArmorType(i) + RandomAdditions.materials.get(j).displayName;
				ItemStack output = new ItemStack((Item)Item.itemRegistry.getObject(RandomAdditions.modid + ":" + name));
				//ItemStack input = output.copy();
				//ItemCore.makeHot(input);
				registerRecipe(new MachineRecipe((Item)Item.itemRegistry.getObject(RandomAdditions.modid + ":" + name), output));
			}
		}
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		Block block = Blocks.planks;
		cubes.addAll(CubeObject.getBlock(new CubeObject(0, 0.6, 0, 1, 1, 1, Blocks.mossy_cobblestone), 0.06, ForgeDirection.UP));
		cubes.add(new CubeObject(0, 0, 0, 0.2, 0.6, 0.2, block));
		cubes.add(new CubeObject(0, 0, 0.8, 0.2, 0.6, 1, block));
		cubes.add(new CubeObject(0.8, 0, 0, 1, 0.6, 0.2, block));
		cubes.add(new CubeObject(0.8, 0, 0.8, 1, 0.6, 1, block));
		cubes.add(new CubeObject(0.06, 0.6, 0.06, 0.94, 0.9, 0.94, Blocks.water));
		return cubes;
	}

	@Override
	public int getNumberOfInputs() {
		return 1;
	}

	@Override
	public void renderProgressField(double percent) {
		for (int i = 0; i < 6; i++) {
        	RenderHelper2D.renderItem(new ItemStack(Blocks.flowing_water), 2+i*15, 34, percent, 0, 15, 13);
        	RenderHelper2D.renderItem(new ItemStack(Blocks.flowing_water), 2+i*15, 1, percent, 180, 15, 12);
		}
	}

	@Override
	public void addRecipe(ItemStack battery, ItemStack output) {
		GameRegistry.addRecipe(output, new Object[]
				{
				"XBX", "WWW", "WAW", 'A', battery, 'X', Blocks.cobblestone, 'W', Blocks.planks, 'B', Items.water_bucket
				});
	}

	@Override
	public int getPlayTime() {
		return 100;
	}
	
	@Override
	public float getPlayVolume(){
		return 1;
	}
	
}
