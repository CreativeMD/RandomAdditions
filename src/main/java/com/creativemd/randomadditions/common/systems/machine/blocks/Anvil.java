package com.creativemd.randomadditions.common.systems.machine.blocks;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.item.ItemCore;
import com.creativemd.randomadditions.common.item.ItemRandomArmor;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.machine.CustomOreInput;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;

public class Anvil extends SubBlockMachine{

	public Anvil(SubBlockSystem system) {
		super("Anvil", system);
	}

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		Block block = Blocks.cobblestone;
		/*cubes.addAll(CubeObject.getBorder(new CubeObject(0, 0, 0, 1, 1, 0, block), 0.2, 0.1));
		cubes.addAll(CubeObject.getFillBorder(new CubeObject(0.01, 0.01, 0.01, 0.99, 0.99, 0.99, Blocks.glass), 0.2, 0.1));*/
		cubes.add(new CubeObject(0.1, 0.1, 0.1, 0.9, 0.2, 0.9, Blocks.obsidian));
		cubes.add(new CubeObject(0.2, 0.2, 0.2, 0.8, 0.25, 0.8, Blocks.wool.getIcon(0, 14)));
		double size = 0.4;
		/*cubes.add(new CubeObject(0.1, 1, 0.5-size/2D, 0.1+size, 1.02, 0.5+size/2, Blocks.wool.getIcon(0, 14)));
		cubes.add(new CubeObject(0.5, 1, 0.3-size/2D, 0.5+size, 1.02, 0.3+size/2, Blocks.wool.getIcon(0, 14)));
		cubes.add(new CubeObject(0.5, 1, 0.7-size/2D, 0.5+size, 1.02, 0.7+size/2, Blocks.wool.getIcon(0, 14)));*/
		cubes.addAll(CubeObject.getBlock(new CubeObject(0, 0, 0, 1, 1, 1, block), 0.1, ForgeDirection.NORTH));
		cubes.add(new CubeObject(0.9, 0, 0.1, 1, 0.25, 0.9, block));
		cubes.add(new CubeObject(0.9, 0.8, 0.1, 1, 1, 0.9, block));
		cubes.add(new CubeObject(0.9, 0.25, 0.1, 1, 0.8, 0.9, Blocks.glass));
		return cubes;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.anvil.getIcon(side, meta);
	}

	@Override
	public void drawRender(TileEntity entity, double x, double y, double z) {
		double height = 0.25;
		if(entity instanceof TileEntityMachine && ((TileEntityMachine) entity).progress > 0)
		{
			double time = (double)System.nanoTime()/300000000D;
			height = Math.sin(time)*0.5-0.25;
			if(height > 0.25)
				height = 0.25;
			if(height < -0.25)
			{
				if(height > -0.3 && Math.sin(time) > Math.sin(time+0.000001D))
				{
					for (int i = 0; i < 20; i++) {
						entity.getWorldObj().spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.obsidian) + "_0", entity.xCoord+0.5, entity.yCoord+0.25, entity.zCoord+0.5, 0, 0, 0);	
					}
				}
				height = -0.25;
			}
			
		}
		
		if(entity instanceof TileEntityMachine)
		{
			TileEntityMachine machine = (TileEntityMachine) entity;
			//if(machine.input.length != getNumberOfInputs()+4)
				//machine.inventory = new ItemStack[getNumberOfInputs()+4];
			if(machine.input != null)
			{
				MachineRecipe recipe = machine.getBlock().getRecipe(machine.getInput());
				ItemStack stack = machine.output.getStackInSlot(0);
				if(recipe != null && machine.progress > 0)
					stack = recipe.getOutput(machine.getInput());
				if(stack != null)
					RenderHelper3D.renderItem(stack, x, y-0.23, z, 90, 0, 0, 1, machine.getDirection(), 0, 0, 0);
			}
		}
		
		GL11.glColor3d(1, 1, 1);
		RenderHelper3D.renderBlock(Blocks.obsidian, x+0.5, y+0.1, z+0.5, 0.8, 0.1, 0.8, 0, 0, 0);
		RenderHelper3D.renderBlock(Blocks.obsidian, x+0.5, y+0.5+height, z+0.5, 0.8, 0.1, 0.8, 0, 0, 0);
	}
	
	public static ArrayList getList(Object... stacks)
	{
		ArrayList list = new ArrayList();
		for (int i = 0; i < stacks.length; i++) {
			list.add(stacks[i]);
		}
		return list;
	}
	
	@Override
	public void registerRecipes() {
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 3), new ItemStack(Items.iron_axe, 0)), RandomItem.getItemStack(RandomItem.axePlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 2), new ItemStack(Items.iron_sword, 0)), RandomItem.getItemStack(RandomItem.swordPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 3), new ItemStack(Items.iron_pickaxe, 0)), RandomItem.getItemStack(RandomItem.pickaxePlate), 250));
		//registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 4), new ItemStack(Items.iron_throwingstar, 0)), RandomItem.getItemStack(RandomItem.throwingstarPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 3), new ItemStack(Items.bow, 0)), RandomItem.getItemStack(RandomItem.bowPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 1), new ItemStack(Items.iron_shovel, 0)), RandomItem.getItemStack(RandomItem.shovelPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 5), new ItemStack(Items.iron_helmet, 0)), RandomItem.getItemStack(RandomItem.helmetPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 8), new ItemStack(Items.iron_chestplate, 0)), RandomItem.getItemStack(RandomItem.chestplatePlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 7), new ItemStack(Items.iron_leggings, 0)), RandomItem.getItemStack(RandomItem.leggingsPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 4), new ItemStack(Items.iron_boots, 0)), RandomItem.getItemStack(RandomItem.bootsPlate), 250));
		registerRecipe(new MachineRecipe(getList(new CustomOreInput("ingotIron", 2), new ItemStack(Items.iron_hoe, 0)), RandomItem.getItemStack(RandomItem.hoePlate), 250));
		
		
		for (int material = 0; material < RandomAdditions.materials.size(); material++) {
			for (int tool = 0; tool < ItemTool.tools.size(); tool++) {	
				
				ItemStack output = ItemTool.getStack(ItemTool.tools.get(tool), material);
				ItemCore.makeHot(output);
				CraftMaterial.setLevel(RandomAdditions.materials.get(material), output, 2);
				
				ItemStack plate = RandomItem.getItemStack(ItemTool.tools.get(tool).plate);
				plate.stackSize = 0;
				
				registerRecipe(new MachineRecipe(getList(new CustomOreInput(RandomAdditions.materials.get(material).itemName, ItemTool.tools.get(tool).cost), new ItemStack(Items.stick, 2), plate), output, 250));
			}
			for (int i = 0; i < 4; i++) {
				String name = "RAArmor" + ItemRandomArmor.getArmorType(i) + RandomAdditions.materials.get(material).displayName;
				
				ItemStack output = new ItemStack((Item)Item.itemRegistry.getObject(RandomAdditions.modid + ":" + name));
				ItemCore.makeHot(output);
				CraftMaterial.setLevel(RandomAdditions.materials.get(material), output, 2);
				
				ItemStack plate = ItemRandomArmor.getArmorPlate(i);
				plate.stackSize = 0;
				
				registerRecipe(new MachineRecipe(getList(new CustomOreInput(RandomAdditions.materials.get(material).itemName, ItemRandomArmor.getArmorCost(i)), plate), output, 250));
			}
		}
	}

	@Override
	public int getNumberOfInputs() {
		return 3;
	}

	@Override
	public void renderProgressField(double percent) {
		// TODO Add animation
		
	}

	@Override
	public void addRecipe(ItemStack battery, ItemStack output) {
		GameRegistry.addRecipe(output, new Object[]
				{
				"COC", "PAP", "COC", 'A', battery, 'O', Blocks.obsidian, 'C', Blocks.cobblestone, 'P', Blocks.piston
				});
	}

	@Override
	public int getPlayTime() {
		return 30;
	}
	
	@Override
	public float getPlayVolume(){
		return 1;
	}
}
