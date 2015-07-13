package com.creativemd.randomadditions.common.systems.producer.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

public class Mill extends SubBlockProducer{
	
	
	public Mill(SubBlockSystem system) {
		super("Mill", system);
	}
	
	public static float Generation;
	public static float speed;

	public CubeObject getArea(TileEntityProducer producer, int expand)
	{
		if(producer != null)
		{
			int[] modi = producer.getModifiers();
			int length = modi[1]-1;
			switch(producer.getDirection())
			{
			case EAST:
				return new CubeObject(0, -length, -length, expand, length, length);
			case WEST:
				return new CubeObject(-expand, -length, -length, 0, length, length);
			case SOUTH:
				return new CubeObject(-length, -length, 0, length, length, expand);
			case NORTH:
				return new CubeObject(-length, -length, -expand, length, length, 0);
			case UP:
				return new CubeObject(-length, 0, -length, length, expand, length);
			case DOWN:
				return new CubeObject(-length, -expand, -length, length, 0, length);
			default:
				return new CubeObject(0, 0, 0, 1, 1, 1);
			}
		}
		return new CubeObject(0, 0, 0, 1, 1, 1);
	}
	
	@Override
	public CubeObject getArea(TileEntityProducer producer)
	{
		return getArea(producer, 0);
	}
	
	@Override
	public ArrayList<ItemStack> getItemStacks()
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		ItemStack core = new ItemStack(system.block, 1, getID());
		core.stackTagCompound = new NBTTagCompound();
		for (int arm = 2; arm <= 8; arm++) {
			for (int length = 1; length <= 10; length++) {
				ItemStack temp = core.copy();
				temp.stackTagCompound.setInteger("arms", arm);
				temp.stackTagCompound.setInteger("length", length);
				items.add(temp);
			}	
		}
		
		return items;
	}
	
	@Override
	public boolean canProvidePower(TileEntityProducer producer, ForgeDirection direction)
	{
		return producer.getDirection().getOpposite() == direction;
	}
	
	public void renderArm(double x, double y, double z, double sizeX, double sizeY, double sizeZ, double rotation, ForgeDirection direction, int meta)
	{
		int blocks = (int) (sizeY/1D+1);
		for (int i = 0; i < blocks; i++) {
			
			double posX = x+0.5;
			double posY = y+1+i;
			double posZ = z+0.5;
			double height = 1;
			if(i == blocks-1)
				height = sizeY-i;
			if(height > 0)
			{
				
				GL11.glPushMatrix();
				
				GL11.glTranslated(posX, posY, posZ);
				
				int rotationY = 0;
				int rotationX = 0;
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
				case UP:
				case DOWN:
					rotationX = 90;
				default:
					break;
				}
				GL11.glRotated(rotationY, 0, 1, 0);
				
				double offsetY = 0.5+i; //could be: 1+i+height
				GL11.glTranslated(0, -offsetY, 0);
				GL11.glRotated(rotationX, 1, 0, 0);
				GL11.glRotated(rotation, 0, 0, 1);
				
				
				GL11.glRotated(45, 0, 1, 0);
				//GL11.glRotated(0, 0, 0, 1);
				GL11.glTranslated(-0, offsetY, -0);
				
				RenderHelper3D.renderer.setRenderBounds(0.5-sizeX/2, 0.001, 0.5-sizeZ/2+0.05, 0.5+sizeX/2, height, 0.5+sizeZ/2-0.1);
				RenderHelper3D.renderer.lockBlockBounds = true;
				RenderHelper3D.renderBlock(Blocks.wool, meta);
				RenderHelper3D.renderer.lockBlockBounds = false;
				
				if(i == blocks-1 || sizeY-(i+1) == 0)
					height += 0.1;
					
				RenderHelper3D.renderer.setRenderBounds(0, 0, 0.4, 0.2, height, 0.6);
				RenderHelper3D.renderer.lockBlockBounds = true;
				RenderHelper3D.renderBlock(Blocks.planks);
				RenderHelper3D.renderer.lockBlockBounds = false;
				
				RenderHelper3D.renderer.setRenderBounds(0.4, height-0.2, 0.2, 0.6, height, 1);
				RenderHelper3D.renderer.lockBlockBounds = true;
				RenderHelper3D.renderBlock(Blocks.planks);
				RenderHelper3D.renderer.lockBlockBounds = false;
				GL11.glPopMatrix();
			}
		}
	}
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z) {
		TileEntityProducer producer = (TileEntityProducer) entity;
		int[] modi = producer.getModifiers();
		int size = modi[0];
		int length = modi[1];
		double rotation = getRotation(producer, (double)System.nanoTime());
		int arms = modi[0];
		for (int i = 0; i < arms; i++) {
			double sizeX = 0.1;
			double sizeY = modi[1];
			double sizeZ = 1;
			rotation += (double)1/(double)arms*360D;
			//if(producer.getDirection() == ForgeDirection.DOWN || producer.getDirection() == ForgeDirection.UP)
				//GL11.glRotated(90, 1, 0, 0);
			renderArm(x, y, z, sizeX, sizeY, sizeZ, rotation, producer.getDirection(), modi[2]);
		}
		GL11.glPushMatrix();
		GL11.glTranslated(x+0.5, y+0.5, z+0.5);
		int rotationY = 0;
		switch(producer.getDirection())
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
		case UP:
		case DOWN:
			GL11.glRotated(90, 1, 0, 0);
		default:
			break;
		}
		GL11.glRotated(rotationY, 0, 1, 0);
		
		GL11.glRotated(rotation, 0, 0, 1);
		
		RenderHelper3D.renderer.setRenderBounds(0.1, 0.3, 0.3, 1, 0.7, 0.7);
		RenderHelper3D.renderer.lockBlockBounds = true;
		RenderHelper3D.renderBlock(Blocks.planks);
		RenderHelper3D.renderer.lockBlockBounds = false;
		GL11.glPopMatrix();
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.planks.getBlockTextureFromSide(side);
	}

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0, 0, 0.1, 1, 1, Blocks.planks));
		if(x == 0 && y == 0 && z == 0)
			cubes.add(new CubeObject(0.1, 0.3, 0.3, 1, 0.7, 0.7, Blocks.planks));
		return cubes;
	}
	
	@Override
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		TileEntityProducer producer = (TileEntityProducer) world.getTileEntity(x, y, z);
		CubeObject cube = getArea(producer, 0);
		return new CubeObject(cube.minX, cube.minY, cube.minZ, 1+cube.maxX, 1+cube.maxY, 1+cube.maxZ);
	}
	
	@Override
	public double getRotation(TileEntityProducer producer, double nanoTime)
	{
		int[] modi = producer.getModifiers();
		int size = modi[0];
		int length = modi[1];
		return (producer.rotationOffset + nanoTime/500000000D*producer.speed/((size+length)/2))*speed;
	}
	
	@Override
	public boolean onBlockActivated(EntityPlayer player, ItemStack stack, TileEntity tileEntity)
	{
		if(stack != null && stack.getItem() == Items.dye && tileEntity instanceof TileEntityProducer)
		{
			TileEntityProducer producer = (TileEntityProducer)tileEntity;
			int[] modi = producer.getModifiers();
			if(modi[2] != BlockColored.func_150032_b(stack.getItemDamage()))
			{
				modi[2] = BlockColored.func_150032_b(stack.getItemDamage());
				if(!player.capabilities.isCreativeMode)
				{
					stack.stackSize--;
					if(stack.stackSize == 0)
						stack = null;
				}
				producer.getWorldObj().markBlockForUpdate(producer.xCoord, producer.yCoord, producer.zCoord);
				producer.setModifiers(modi);;
				return true;
			}
		}
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		list.add("Arms: " + stack.stackTagCompound.getInteger("arms"));
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
			stack.stackTagCompound.setInteger("arms", modi[0]);
			stack.stackTagCompound.setInteger("length", modi[1]);
			stacks.add(stack);
		}
		return stacks;
	}

	@Override
	public int getMaxSpeed(TileEntityProducer producer, ItemStack stack) {
		if(producer != null)
		{
			CubeObject cube = getArea(producer, 50);
			int[] modi = producer.getModifiers();
			int possiblePower = modi[0]*modi[1]*4;;
			int power = possiblePower - getAirLevel(producer.getWorldObj(), (int)cube.minX+producer.xCoord, (int)cube.minY+producer.yCoord, (int)cube.minZ+producer.zCoord, (int)cube.maxX+producer.xCoord, (int)cube.maxY+producer.yCoord, (int)cube.maxZ+producer.zCoord, producer.yCoord);
			ForgeDirection direction = producer.getDirection();
			if(direction == ForgeDirection.UP || direction == ForgeDirection.DOWN)
				power /= 2;
			if(power < 0)
				power = 0;
			return (int) (power/Generation);
		}else if(stack != null){
			return (int) ((stack.stackTagCompound.getInteger("arms") * stack.stackTagCompound.getInteger("length") * 4)/Generation);
		}
		return 0;
	}
	
	public int getAirLevel(World world, int x, int y, int z, int x2, int y2, int z2, int centerY)
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
					if(!(block instanceof BlockAir) && block.isNormalCube(world, posX, posY, posZ))
					{
						power++;
					}
					//world.setBlock(posX, posY, posZ, Blocks.stone);
				}
			}
		}
		return power;
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		
		if(tileEntity instanceof TileEntityProducer)
		{
			int[] modi = ((TileEntityProducer) tileEntity).getModifiers();
			modi[0] = stack.stackTagCompound.getInteger("arms");
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
	public void updateTileEntity(TileEntityProducer producer) {
		
	}

	@Override
	public int getModifiers() {
		return 3;
	}
	
	@Override
	public void onBlockPlacedEvent(World world, Entity entity, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileEntityRandom)
		{
			((TileEntityRandom) tile).direction = (byte) RotationUtils.getIndex(ForgeDirection.getOrientation(side));
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

	@Override
	public int getPlayTime() {
		return 0;
	}
	
	
}
