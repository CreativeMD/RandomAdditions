package com.creativemd.randomadditions.common.systems.machine.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Sawing extends SubBlockMachine{
	
	@SideOnly(Side.CLIENT)
	public static IIcon sawblade;
	
	public Sawing(SubBlockSystem system) {
		super("Sawing", system);
	}

	@Override
	public int getNumberOfInputs() {
		return 1;
	}

	@Override
	public void registerRecipes() {
		for (int i = 0; i < 6; i++) {
			int logIndex = i;
			Block block = Blocks.log;
			if(i > 3)
			{
				logIndex = i-4;
				block = Blocks.log2;
			}
			registerRecipe(new MachineRecipe(new ItemStack(block, 1, logIndex), new ItemStack(Blocks.planks, 6, i)));
		}
		
	}

	@Override
	public void renderProgressField(double percent) {
		RenderHelper2D.renderIcon(sawblade, 6, 6, percent, true, System.nanoTime()/3000000D, 40, 40);
		RenderHelper2D.renderIcon(sawblade, 51, 6, percent, true, System.nanoTime()/3000000D, 40, 40);
	}

	@Override
	public void addRecipe(ItemStack battery, ItemStack output) {
		GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
				{
				"AIA", "CBC", "SIS", Character.valueOf('A'), Blocks.planks, Character.valueOf('C'), Blocks.log, 'B', battery, Character.valueOf('I'), "ingotIron", Character.valueOf('S'), Blocks.stone_slab
				}));
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0.8, 0, 0.2, 0.85, 1));
		cubes.add(new CubeObject(0.8, 0.8, 0, 1, 0.85, 1));
		cubes.add(new CubeObject(0.2, 0.8, 0, 0.8, 0.85, 0.45));
		cubes.add(new CubeObject(0.2, 0.8, 0.55, 0.8, 0.85, 1));
		
		cubes.add(new CubeObject(0.45, 0.85, 0.35, 0.55, 1, 0.45, Blocks.anvil));
		cubes.add(new CubeObject(0.45, 0.85, 0.55, 0.55, 1, 0.65, Blocks.anvil));
		
		cubes.add(new CubeObject(0.1, 0.04, 0.1, 0.2, 0.9, 0.2, Blocks.log.getBlockTextureFromSide(2)));
		cubes.add(new CubeObject(0.8, 0.04, 0.1, 0.9, 0.9, 0.2, Blocks.log.getBlockTextureFromSide(2)));
		cubes.add(new CubeObject(0.1, 0.04, 0.8, 0.2, 0.9, 0.9, Blocks.log.getBlockTextureFromSide(2)));
		cubes.add(new CubeObject(0.8, 0.04, 0.8, 0.9, 0.9, 0.9, Blocks.log.getBlockTextureFromSide(2)));
		
		cubes.add(new CubeObject(0.15, 0.3, 0.15, 0.85, 0.4, 0.85, Blocks.log.getBlockTextureFromSide(2)));
		
		cubes.add(new CubeObject(0, 0.0, 0, 1, 0.04, 1, Blocks.stone_slab));
		
		return cubes;
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getIcon(side, 0);
	}

	@Override
	public void drawRender(TileEntity entity, double x, double y, double z) {
		if(entity instanceof TileEntityMachine)
		{
			TileEntityMachine machine = (TileEntityMachine) entity;
			
			double rotation = 0;
			if(machine.progress > 0)
			{
				for (int i = 0; i < 2; i++) {
					entity.getWorldObj().spawnParticle("blockcrack_" + Block.getIdFromBlock(Blocks.planks) + "_0", entity.xCoord+0.5, entity.yCoord+0.75, entity.zCoord+0.5, 0, 0, 0);	
				}
				rotation = System.nanoTime()/3000000D;
			}
			
			RenderHelper3D.renderItem(new ItemStack(Blocks.torch), x, y, z, 0, 0, rotation, 1, machine.getDirection(), 0, 0.45, 0, sawblade);
			
			//RenderHelper3D.renderItem(new ItemStack(Blocks.torch), x, y, z, 0, 0, System.nanoTime()/10000000D, 1, machine.getDirection(), 0, 0.5, 0, sawblade);
			RenderHelper3D.renderBlock(Blocks.iron_block, x+0.5, y+0.95, z+0.5, 0.05, 0.05, 0.4, 0, 0, rotation, machine.getDirection());
			
			
			
			//RenderHelper3D.applyDirection(machine.getDirection());
		}
	}

	@Override
	public int getPlayTime() {
		return 49;
	}
	
	@Override
	public float getPlayVolume(){
		return 0.3F;
	}

}
