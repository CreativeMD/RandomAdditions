package com.creativemd.randomadditions.common.systems.producer.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.producer.SubBlockProducer;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityProducer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class Watermill extends SubBlockProducer{

	public Watermill(SubBlockSystem system) {
		super("Watermill", system);
	}
	
	public static float Generation;
	public static float speed;

	@Override
	public int getMaxSpeed(TileEntityProducer tileEntity, ItemStack stack) {
		if(stack != null)
		{
			int size = stack.stackTagCompound.getInteger("size");
			return (int) ((size * stack.stackTagCompound.getInteger("length") * (size*2+1))/Generation);
		}
		if(tileEntity instanceof TileEntityProducer)
		{
			CubeObject cube = getArea(tileEntity);
			return (int) ((getWaterLevel(tileEntity.getWorldObj(), (int)cube.minX+tileEntity.xCoord, (int)cube.minY+tileEntity.yCoord, (int)cube.minZ+tileEntity.zCoord, (int)cube.maxX+tileEntity.xCoord, (int)cube.maxY+tileEntity.yCoord, (int)cube.maxZ+tileEntity.zCoord, tileEntity.yCoord)/Generation));
		}
		return 0;
	}
	
	@Override
	public boolean allowShiftRotation()
	{
		return false;
	}
	
	@Override
	public CubeObject getArea(TileEntityProducer producer)
	{
		int[] modi = producer.getModifiers();
		int radius = modi[0];
		int length = modi[1]-1;
		switch(producer.getDirection())
		{
		case EAST:
			return new CubeObject(-length, -radius, -radius, 0, radius, radius);
		case WEST:
			return new CubeObject(0, -radius, -radius, length, radius, radius);
		case SOUTH:
			return new CubeObject(-radius, -radius, -length, radius, radius, 0);
		case NORTH:
			return new CubeObject(-radius, -radius, 0, radius, radius, length);	
		default:
			return new CubeObject(0, 0, 0, 1, 1, 1);
		}
	}

	@Override
	public void updateTileEntity(TileEntityProducer producer) {
		
	}

	@Override
	public int getModifiers() {
		return 3;
	}
	
	@Override
	public boolean doesAcceptBlock(Block block)
	{
		if(super.doesAcceptBlock(block))
			return true;
		return block == Blocks.water || block == Blocks.flowing_water;
	}
	
	@Override
	public boolean canProvidePower(TileEntityProducer producer, ForgeDirection direction)
	{
		return producer.getDirection() == direction;
	}
	
	@Override
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		TileEntityProducer producer = (TileEntityProducer) world.getTileEntity(x, y, z);
		CubeObject cube = getArea(producer);
		return new CubeObject(cube.minX, cube.minY, cube.minZ, 1+cube.maxX, 1+cube.maxY, 1+cube.maxZ);
	}
	
	public void rotateFromDirection(ForgeDirection direction)
	{
		int rotationY = 0;
		switch(direction)
		{
		case EAST:
			rotationY += 270;
			break;
		case NORTH:
			rotationY += 0;
			break;
		case SOUTH:
			rotationY += 180;
			break;
		case WEST:
			rotationY += 90;
			break;	
		default:
			break;
		}
		GL11.glRotated(rotationY, 0, 1, 0);
	}
	
	public void renderArm(double x, double y, double z, double sizeX, double sizeY, double sizeZ, double rotation, ForgeDirection direction, int arms)
	{
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y+0.5+sizeY/2D, z+0.5);
		
		rotateFromDirection(direction);
		
		GL11.glTranslated(0, 0, -0.25+sizeZ/2);
		
		GL11.glTranslated(0, -sizeY/2, 0);
		GL11.glRotated(rotation, 0, 0, 1);
		GL11.glTranslated(0, sizeY/2, 0);
		
		RenderHelper3D.renderBlock(Blocks.planks, sizeX-0.05, sizeY, sizeZ-0.05);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		
		double degree = ((double)360D/arms)/2D;
		rotation += degree;
		//sizeY += 0.4;
		double sin = Math.sin(Math.toRadians(degree/2));
		
		double length = sin*sizeY*4;
		
		double cos = Math.cos(Math.toRadians(degree/2))*(sizeY*(1-0.06)); //1.2
		//length += 0.2;
		GL11.glTranslated(x+0.5, y+0.5+cos, z+0.5);
		
		rotateFromDirection(direction);
		
		GL11.glTranslated(0, 0, -0.25+sizeZ/2);
		
		GL11.glTranslated(0, -cos, 0);
		GL11.glRotated(rotation, 0, 0, 1);
		GL11.glTranslated(0, cos, 0);
		
		GL11.glRotated(90, 0, 0, 1);
		
		RenderHelper3D.renderBlock(Blocks.planks, sizeX, length, sizeZ);
		//renderBlock(Blocks.wool, x+0.5, y+0.7+length, z+0.5, 0.2, length, 0.2, rotation, direction, length);
		GL11.glPopMatrix();
	}
	
	@Override
	public double getRotation(TileEntityProducer producer, double nanoTime)
	{
		int[] modi = producer.getModifiers();
		int size = modi[0];
		int length = modi[1];
		return (producer.rotationOffset + nanoTime/500000000D*producer.speed/(size+length)) * speed;
	}
	
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z) {
		TileEntityProducer producer = (TileEntityProducer) entity;
		int[] modi = producer.getModifiers();
		int arms = 8 + (int)((double)modi[0]/8*8);
		int size = modi[0];
		int length = modi[1];
		double rotation = getRotation(producer, (double)System.nanoTime());
		for (int i = 0; i < arms; i++) {
			double sizeX = 0.2 + (double)modi[0]/8*0.2;
			double sizeY = modi[0];
			double sizeZ = 0.5 + (length-1);
			rotation += (double)1/(double)arms*360D;
			renderArm(x, y, z, sizeX, sizeY, sizeZ, rotation, producer.getDirection(), arms);
		}
		GL11.glPushMatrix();
		double lengthY = 0.9 + (length-1);
		GL11.glTranslated(x+0.5, y+0.5, z+0.5);
		
		rotateFromDirection(producer.getDirection());
		
		GL11.glTranslated(0, 0, -0.4+lengthY/2);
		
		GL11.glRotated(rotation, 0, 0, 1);
		
		RenderHelper3D.renderBlock(Blocks.log, 0.4, 0.4, lengthY, 8);
		
		/*RenderHelper3D.renderer.setRenderBounds(0.1, 0.3, 0.3, 1, 0.7, 0.7);
		RenderHelper3D.renderer.lockBlockBounds = true;
		RenderHelper3D.renderBlock(Blocks.planks);
		RenderHelper3D.renderer.lockBlockBounds = false;*/
		GL11.glPopMatrix();
		
		/*GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y+0.5, z+0.5);
		
		RenderHelper3D.renderBlock(Blocks.planks, 1, 2, 3);
		GL11.glPopMatrix();*/
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getBlockTextureFromSide(side);
	}

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0.9, 0, 0, 1, 1, 1, Blocks.planks));
		if(x == 0 && y == 0 && z == 0)
			cubes.add(new CubeObject(0, 0.3, 0.3, 0.9, 0.7, 0.7, Blocks.planks));
		return cubes;
	}
	
	@Override
	public void onBlockPlacedEvent(World world, Entity entity, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
		super.onBlockPlacedEvent(world, entity, x, y, z, side, hitX, hitY, hitZ, meta);
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityRandom)
			((TileEntityRandom) tileEntity).direction = (byte) RotationUtils.getIndex(((TileEntityRandom) tileEntity).getDirection().getOpposite());
    }
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		
		if(tileEntity instanceof TileEntityProducer)
		{
			int[] modi = ((TileEntityProducer) tileEntity).getModifiers();
			modi[0] = stack.stackTagCompound.getInteger("size");
			modi[1] = stack.stackTagCompound.getInteger("length");
			((TileEntityProducer) tileEntity).setModifiers(modi);
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrop(IBlockAccess world, int x, int y, int z, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public ArrayList<ItemStack> getItemStacks()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack core = new ItemStack(system.block, 1, getID());
		core.stackTagCompound = new NBTTagCompound();
		for (int arm = 1; arm <= 8; arm++) {
			for (int length = 1; length <= 10; length++) {
				ItemStack temp = core.copy();
				temp.stackTagCompound.setInteger("size", arm);
				temp.stackTagCompound.setInteger("length", length);
				items.add(temp);
			}	
		}
		
		return items;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		list.add("Size: " + stack.stackTagCompound.getInteger("size"));
		list.add("Length: " + stack.stackTagCompound.getInteger("length"));
		
		super.addInformation(stack, player, list, p_77624_4_);
	}

	@Override
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = super.getExtraDrop(tileEntity);
		if(tileEntity instanceof TileEntityProducer)
		{
			ItemStack stack = new ItemStack(system.block, 1, getID());
			stack.stackTagCompound = new NBTTagCompound();
			int[] modi = ((TileEntityProducer) tileEntity).getModifiers();
			stack.stackTagCompound.setInteger("size", modi[0]);
			stack.stackTagCompound.setInteger("length", modi[1]);
			stacks.add(stack);
		}
		return stacks;
	}
	
	public int getWaterLevel(World world, int x, int y, int z, int x2, int y2, int z2, int centerY)
	{
		int power = 0;
		if(x > x2)
		{
			int temp = x;
			x = x2;
			x2 = temp;
		}
		if(y > y2)
		{
			int temp = y;
			y = y2;
			y2 = temp;
		}
		if(z > z2)
		{
			int temp = z;
			z = z2;
			z2 = temp;
		}
		for (int posX = x; posX <= x2; posX++) {
			for (int posY = y; posY <= y2; posY++) {
				for (int posZ = z; posZ <= z2; posZ++) {
					Block block = world.getBlock(posX, posY, posZ);
					if(block == Blocks.water || block == Blocks.flowing_water)
					{
						if(posY < centerY)
							power++;
						else
							power--;
					}
				}
			}
		}
		if(power < 0)
			power = 0;
		return power;
	}
	
	@Override
	public float getPlayVolume(TileEntityProducer producer)
	{
		return Math.min(Math.max((float)producer.speed/130F, 0.05F), 1);
	}

	@Override
	public int getPlayTime() {
		return 220;
	}
	
}
