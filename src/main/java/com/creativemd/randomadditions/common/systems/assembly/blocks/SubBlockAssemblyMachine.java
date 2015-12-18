package com.creativemd.randomadditions.common.systems.assembly.blocks;

import java.util.ArrayList;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.handcraft.recipe.HandRecipe;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.assembly.SubBlockAssembly;
import com.creativemd.randomadditions.common.systems.assembly.TileEntityAssemblyMachine;
import com.creativemd.randomadditions.common.systems.assembly.gui.SubContainerAssemblyMachine;
import com.creativemd.randomadditions.common.systems.assembly.gui.SubGuiAssemblyMachine;
import com.creativemd.randomadditions.common.systems.cmachine.SubBlockCMachine;
import com.creativemd.randomadditions.common.systems.cmachine.blocks.SubBlockLightning;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;
import com.creativemd.randomadditions.common.utils.IMachineBase;
import com.creativemd.randomadditions.common.utils.TileEntityMachineBase;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class SubBlockAssemblyMachine extends SubBlockAssembly implements IMachineBase{
	
	public int inputCount;
	
	public float craftingSpeed;
	
	public int upgradeSlots;
	
	public Block block;
	public Block block2;
	
	public SubBlockAssemblyMachine(String name, SubBlockSystem system, int inputCount, float craftingSpeed, int upgradeSlots, Block block, Block block2) {
		super(name, system);
		this.inputCount = Math.min(inputCount, HandRecipe.maxInput);
		this.craftingSpeed = craftingSpeed;
		this.upgradeSlots = upgradeSlots;
		this.block = block;
		this.block2 = block2;
	}
	

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return new SubGuiAssemblyMachine((TileEntityAssemblyMachine)tileEntity);
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerAssemblyMachine((TileEntityAssemblyMachine)tileEntity, player);
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityMachineBase)
		{
			((TileEntityMachineBase) tileEntity).createInventory();
		}
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityAssemblyMachine();
	}
	
	@Override
	public int getInputWidth() {
		if(inputCount > 4)
			return inputCount/2;
		return inputCount;
	}
	
	@Override
	public int getInputHeight() {
		if(inputCount > 4)
			return 2;
		return 1;
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return block.getIcon(side, 0);
	}
	
	@Override
	public int getNumberOfOutputs() {
		return 1;
	}
	
	@Override
	public int getNumberOfUpgradeSlots() {
		return upgradeSlots;
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();	
		
		/*cubes.add(new CubeObject(0.1, 0.9, 0.3, 0.9, 0.95, 0.4, block));
		cubes.add(new CubeObject(0.1, 0.9, 0.6, 0.9, 0.95, 0.7, block));
		
		cubes.add(new CubeObject(0.3, 0.9, 0.1, 0.4, 0.95, 0.9, block));
		cubes.add(new CubeObject(0.6, 0.9, 0.1, 0.7, 0.95, 0.9, block));*/
		
		//cubes.add(new CubeObject(0.075, 0.2, 0.075, 0.925, 0.7, 0.925, SubBlockLightning.stick));
		
		cubes.add(new CubeObject(0.075, 0.7, 0.075, 0.925, 0.75, 0.925));
		cubes.add(new CubeObject(0.075, 0.15, 0.075, 0.925, 0.2, 0.925));
		
		cubes.add(new CubeObject(0.2, 0.9, 0.2, 0.8, 0.92, 0.8, Blocks.wool.getIcon(0, 15)));
		
		cubes.add(new CubeObject(0.11, 0.2, 0.11, 0.89, 0.21, 0.89, Blocks.obsidian));
		double size = 0.225;
		
		ArrayList<CubeObject> side = new ArrayList<CubeObject>();
		side.addAll(CubeObject.getGrid(new CubeObject(0.1, size, 0.1+size, 0.11, 0.9-size, 0.9-size), 0.02, 3));
		

		for (int i = 0; i < 6; i++) {
			ForgeDirection direction = ForgeDirection.getOrientation(i);
			
			if(direction != ForgeDirection.DOWN && direction != ForgeDirection.UP)
			{
				for (int j = 0; j < side.size(); j++) {
					cubes.add(CubeObject.rotateCube(side.get(j), direction));
				}
			}
		}
		
		cubes.add(new CubeObject(0.2, 0.1, 0.2, 0.8, 0.86, 0.8, Blocks.stained_hardened_clay, 0));
		
		if(block2 != null)
			cubes.addAll(CubeObject.getBorder(new CubeObject(0.1, 0, 0.1, 0.9, 0.9, 0.9, block2), size, 0.01));
		else
			cubes.addAll(CubeObject.getBorder(new CubeObject(0.1, 0, 0.1, 0.9, 0.9, 0.9, SubBlockLightning.ring), size, 0.01));
		
		//cubes.add(new CubeObject(0.1, 0, 0.1, 0.9, 0.9, 0.9, SubBlockLightning.ring));
		return cubes;
	}
	
	@Override
	public int getRotation()
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawRender(TileEntity entity, double x, double y, double z)
	{
		if(entity instanceof TileEntityAssemblyMachine)
		{
			TileEntityAssemblyMachine machine = (TileEntityAssemblyMachine) entity;
			float rotation = System.nanoTime()/6000000F;
			
			for (int i = 0; i < 6; i++) {
				ForgeDirection direction = ForgeDirection.getOrientation(i);
				
				if(direction != ForgeDirection.DOWN && direction != ForgeDirection.UP)
				{
					RenderHelper3D.renderItem(new ItemStack(Blocks.torch), x, y, z, 0, -90, rotation, 1, direction, 0.35, 0-0.05, 0, RandomAdditions.gears[entity.getBlockMetadata()]);
				}
			}
			
			RenderHelper3D.renderItem(new ItemStack(Items.iron_axe), x, y, z, -90, 180, -90, 0.8, machine.getDirection(), -0.1, 0.42, -0.25);
			
			for (int i = 0; i < 4; i++) {
				RenderHelper3D.renderItem(new ItemStack(Items.paper), x, y, z, 90, 0, 0+Math.cos(i*60)*360, 0.5, machine.getDirection(), -0.25, 0.42+i*0.02, 0.2);
			}
			
		}
	}
	
}
