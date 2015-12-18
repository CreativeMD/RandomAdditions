package com.creativemd.randomadditions.common.systems.cmachine.blocks;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.cmachine.SubBlockCMachine;
import com.creativemd.randomadditions.common.systems.cmachine.tileentity.TileEntityLightning;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockLightning extends SubBlockCMachine{

	public SubBlockLightning(SubBlockSystem system) {
		super("Lightning", system);
	}
	
	public static IIcon base;
	public static IIcon ring;
	public static IIcon stick;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister register)
	{
		base = register.registerIcon(RandomAdditions.modid + ":" + name + "_base");
		ring = register.registerIcon(RandomAdditions.modid + ":" + name + "_ring");
		stick = register.registerIcon(RandomAdditions.modid + ":" + name + "_stick");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return base;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return null;
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityLightning();
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(1, 0.2, 0.2, 0.85, 0.8, 0.8, base));
		cubes.add(new CubeObject(0.85, 0.4, 0.4, 0.05, 0.6, 0.6, stick));
		int amount = 3;
		double size = 0.7D/amount;
		for (int i = 0; i < amount; i++) {
			cubes.add(new CubeObject(i*size+size/2+0.05, 0.3, 0.3, i*size+size/2+0.15, 0.7, 0.7, ring));
		}
		if(stack != null)
		{
			for (int i = 0; i < cubes.size(); i++) {
				cubes.set(i, CubeObject.rotateCube(cubes.get(i), ForgeDirection.DOWN));
			}
		}
		return cubes;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawRender(TileEntity entity, double x, double y, double z)
	{
		if(((TileEntityLightning) entity).getCurrentPower() > ((TileEntityLightning) entity).usePerHit)
		{
			Entity living = ((TileEntityLightning) entity).getEntityInRange();
			boolean canrender = true;
			if(living instanceof EntityPlayer)
				canrender = !((EntityPlayer)living).capabilities.isCreativeMode;
			if(living != null && canrender)
			{
				Vec3 start = Vec3.createVectorHelper(0.5, 0.5, 0.5);
				Vec3 end = Vec3.createVectorHelper(living.posX-entity.xCoord+0.5, living.posY-entity.yCoord+0.5, living.posZ-entity.zCoord+0.5);
				RenderHelper3D.renderLightning(x, y, z, start, end, 0.6, 8, 2);
			}
		}
	}
	
	@Override
	public boolean useSideForRotation()
	{
		return true;
	}
	
	@Override
	public int getRotation()
	{
		return 2;
	}
}
