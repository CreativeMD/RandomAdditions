package com.creativemd.randomadditions.common.systems.cable.blocks;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.cable.SubBlockCableBase;
import com.creativemd.randomadditions.common.systems.cable.tileentity.TileEntityWire;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockWire extends SubBlockCableBase{
	
	public SubBlockWire(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return Blocks.planks.getBlockTextureFromSide(side);
	}
	
	@Override
	public int getRotation()
	{
		return 2;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
	{
		if(!canBlockStay(world, x, y, z))
		{
			system.block.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityWire)
		{
			ForgeDirection direction = ((TileEntityWire) tileEntity).getDirection();
			ChunkCoordinates coord = new ChunkCoordinates(x, y, z);
			RotationUtils.applyDirection(direction, coord);
			Block block = world.getBlock(coord.posX, coord.posY, coord.posZ);
			if(block.isSideSolid(world, coord.posX, coord.posY, coord.posZ, direction.getOpposite()) || (direction.getOpposite() == ForgeDirection.UP && block.canPlaceTorchOnTop(world, coord.posX, coord.posY, coord.posZ)))
				return true;
			else
				return false;
		}
		return true;
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0.4, 0.4, 0.4, 1, 0.6, 0.6));
		cubes.add(new CubeObject(0.35, 0.35, 0.35, 0.65, 0.65, 0.65, Blocks.anvil.getBlockTextureFromSide(0)));
		return cubes;
	}
	
	@Override
	public String getTileEntityName()
	{
		return "RAWire";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void drawRender(TileEntity entity, double x, double y, double z)
	{
		if(entity instanceof TileEntityWire)
		{
			TileEntityWire wire = (TileEntityWire) entity;
			for (int i = 0; i < wire.connections.size(); i++) {
				TileEntityWire wire2 = wire.connections.get(i);
				double distance = wire.getDistance(wire2);
				double disX = (wire.xCoord+0.5) - (wire2.xCoord+0.5);
				double disY = (wire.yCoord+0.5) - (wire2.yCoord+0.5);
				double disZ = (wire.zCoord+0.5) - (wire2.zCoord+0.5);
				
				Vector3d vector = new Vector3d(disX, disY, disZ);
				Vector3d vector2 = new Vector3d(disX, 0, disZ);
				double length = vector.length();
				distance = length+0.5;
				double rotY = Math.toDegrees(Math.atan(disX/disZ));
				double rotX = 0;
				double rotZ = (double)System.nanoTime()/100000000D;
				//rotY = (double)System.nanoTime()/10000000D;
				rotZ = 0;
				double test = Math.cos(Math.toRadians(rotY));
				rotX = Math.toDegrees(vector.angle(vector2));
				//if(rotY == -0 || rotY < 0)
					//rotX = -Math.toDegrees(vector.angle(vector2));
				if(Double.isNaN(rotX))
					rotX = 0;
				if(Double.isNaN(rotY))
					rotY = 0;
				if(Double.isNaN(rotZ))
					rotZ = 0;
				
				boolean negZ = disZ < 0;
				boolean negY = disY < 0;
				if(!(negZ && !negY || !negZ && negY))
					rotX = -rotX;
				
				if(disX == 0 && disZ == 0)
					rotX = 90;
				//if(wire.yCoord > wire2.yCoord)
					//rotY += Math.sin(Math.toRadians(rotY))*180;
					//rotX = -rotX;
				
				double posX = x-disX/2+0.5;
				double posY = y-disY/2+0.5;
				double posZ = z-disZ/2+0.5;
				GL11.glPushMatrix();
				GL11.glTranslated(posX, posY, posZ);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glColor3d(0.1, 0.1, 0.1);
				//GL11.glRotated(90, 0, 1, 0);
				GL11.glRotated(rotY, 0, 1, 0);
				GL11.glRotated(rotX, 1, 0, 0);
				GL11.glRotated(rotZ, 0, 0, 1);
				GL11.glScaled(0.1, 0.1, distance-0.75);
				RenderHelper3D.renderBlock(Blocks.wool);
				GL11.glPopMatrix();
				//RenderHelper3D.renderTag("R:" + rotY + ";TOP:" + negY , x+0.5, y+0.9, z+0.5, 1000000);
			}
		}
	}
	
	@Override
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		CubeObject cube = new CubeObject(0.4, 0.4, 0.4, 1, 0.6, 0.6);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityWire)
		{
			RotationUtils.applyCubeRotation(cube, ((TileEntityWire) tileEntity).getDirection());
		}
		return cube;
	}
	
	@Override
	public boolean onBlockActivated(EntityPlayer player, ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityWire && !player.worldObj.isRemote)
			player.addChatMessage(new ChatComponentText(((TileEntityWire) tileEntity).connections.size() + " Connection(s)"));
		return false;
	}
	
	@Override
	public void onBlockBreaks(TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityWire)
		{
			TileEntityWire wire = (TileEntityWire) tileEntity;
			tileEntity.getWorldObj().setTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, null);
			for (int i = 0; i < wire.connections.size(); i++) {
				TileEntityWire wire2 = wire.connections.get(i);
				if(wire2.removeConnection(wire2.connections.indexOf(wire)))
				{
					wire2.updateBlock();
				}
			}
		}
		super.onBlockBreaks(tileEntity);
	}

	@Override
	public int getTransmitablePower(TileEntity tileEntity) {
		return 10000;
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityWire();
	}
	
}
