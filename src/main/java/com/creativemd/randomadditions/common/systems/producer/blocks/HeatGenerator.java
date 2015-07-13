package com.creativemd.randomadditions.common.systems.producer.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.producer.SubBlockProducer;
import com.creativemd.randomadditions.common.systems.producer.gui.SubContainerHeatGen;
import com.creativemd.randomadditions.common.systems.producer.gui.SubGuiHeatGen;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityHeatGenerator;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityProducer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class HeatGenerator extends SubBlockProducer{
	
	public static float fuelGeneration;
	
	public HeatGenerator(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public int getMaxSpeed(TileEntityProducer tileEntity, ItemStack stack) {
		return 0;
	}

	@Override
	public void updateTileEntity(TileEntityProducer producer) {
		
	}
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityHeatGenerator();
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.brick_block.getIcon(side, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		if(tileEntity instanceof TileEntityHeatGenerator)
			return new SubGuiHeatGen((TileEntityHeatGenerator) tileEntity, this);
		return null;
	}
	
	@Override
	public String getTileEntityName()
	{
		return "RA" + name;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		if(tileEntity instanceof TileEntityHeatGenerator)
			return new SubContainerHeatGen((TileEntityRandom) tileEntity, player);
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add("Contains 4 burning slots");
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		
		double thicknessOutside = 0.2;
		double thicknessInside = 0.1;
		cubes.add(new CubeObject(0, 0+thicknessOutside, 0+thicknessOutside, 0+thicknessInside, 1-thicknessOutside, 1-thicknessOutside, Blocks.glass));
		int size = cubes.size();
		
		for (int i = 0; i < 6; i++) {
			ForgeDirection direction = ForgeDirection.getOrientation(i);
			if(direction != ForgeDirection.UP)
			{
				for (int j = 0; j < size; j++) {
					cubes.add(CubeObject.rotateCube(cubes.get(j), direction));
				}
			}
		}
		
		cubes.addAll(CubeObject.getBorder(Blocks.brick_block.getBlockTextureFromSide(0), 0.2, 0.1));
		//cubes.addAll(CubeObject.getFillBorder(new CubeObject(0, 0.2, 0, 1, 1, 1, Blocks.glass), 0.2, 0.1));
		cubes.add(new CubeObject(0.1, 0, 0.1, 0.9, 0.2, 0.9));
		cubes.add(new CubeObject(0.1, 0.2, 0.1, 0.9, 0.22, 0.9, Blocks.coal_block));
		return cubes;
	}
	
	public boolean hasEngouhSpace(World world, int x, int y, int z, int x2, int y2, int z2, int centerX, int centerY, int centerZ)
	{
		return true;
	}
	
	public void drawRender(TileEntity entity, double x, double y, double z) {
		int arms = 10;
		
		
		if(entity instanceof TileEntityHeatGenerator)
		{
			TileEntityHeatGenerator heat = (TileEntityHeatGenerator) entity;
			double rotation = System.nanoTime()/5000000D;
			if(!heat.isActive)
				rotation = 0;
			else
			{
				for (int i = 0; i < heat.fuel.length; i++)
					if(heat.fuel[i] > 0)
						heat.getWorldObj().spawnParticle("smoke", heat.xCoord+0.5, heat.yCoord+0.2, heat.zCoord+0.5, Math.cos(Math.toRadians(i*90))*0.05, 0.01, Math.sin(Math.toRadians(i*90))*0.05);
			}
			for (int arm = 0; arm < arms; arm++) {
				GL11.glPushMatrix();
				GL11.glTranslated(x+0.5, y+0.8, z+0.5);
				GL11.glRotated(90, 0, 0, 1);
				GL11.glRotated(rotation+(double)arm/(double)arms*360D, 1, 0, 0);
				
				GL11.glRotated(30, 0, 1, 0);
				RenderHelper3D.renderer.setRenderBounds(0.45, 0.1, 0.475, 0.55, 0.45, 0.525);
				RenderHelper3D.renderer.lockBlockBounds = true;
				RenderHelper3D.renderBlock(Blocks.nether_brick);
				RenderHelper3D.renderer.lockBlockBounds = false;
				GL11.glPopMatrix();
			}
			GL11.glPushMatrix();
			GL11.glTranslated(x+0.5, y+0.5, z+0.5);
			GL11.glRotated(rotation, 0, 1, 0);
			RenderHelper3D.renderer.setRenderBounds(0.45, 0.1, 0.45, 0.55, 0.9, 0.55);
			RenderHelper3D.renderer.lockBlockBounds = true;
			RenderHelper3D.renderBlock(Blocks.nether_brick);
			RenderHelper3D.renderer.lockBlockBounds = false;
			GL11.glPopMatrix();
			
			for (int i = 0; i < heat.inventory.length; i++) {
				if(heat.inventory[i] != null)
					RenderHelper3D.renderItem(heat.inventory[i], x, y, z, 90, 0, 0, 0.8, heat.getDirection(), Math.cos(Math.toRadians(i*90))*0.25, -0.2, Math.sin(Math.toRadians(i*90))*0.25);
			}
		}
	}
	
	@Override
	public int getRotation()
	{
		return 0;
	}
	
	@Override
	public int getModifiers() {
		return 2;
	}

	@Override
	public int getPlayTime() {
		return 120;
	}

}
