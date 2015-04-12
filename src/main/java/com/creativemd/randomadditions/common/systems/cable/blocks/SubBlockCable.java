package com.creativemd.randomadditions.common.systems.cable.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import com.creativemd.randomadditions.common.energy.core.EnergyCore;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.cable.SubBlockCableBase;
import com.creativemd.randomadditions.common.systems.cable.tileentity.TileEntityCable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockCable extends SubBlockCableBase{
	
	public int limit;
	public Block block;
	public Block corner;
	public int speedFactor;
	
	public SubBlockCable(int limit, Block block, int speedFactor, Block corner, String name, SubBlockSystem system) {
		super(name, system);
		this.limit = limit;
		this.block = block;
		this.corner = corner;
		this.speedFactor = speedFactor;
	}

	@Override
	public int getTransmitablePower(TileEntity tileEntity) {
		return limit;
	}

	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityCable();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add("Can transmit up to " + limit + " RA/tick");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return block.getIcon(0, 0);
    }
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		boolean needRotationBlock = true;
		boolean coverd = false;
		if(world == null){
			cubes.add(new CubeObject(0.0D, 0.4D, 0.4D, 1.0D, 0.6D, 0.6D, block));
		}else{
			TileEntityCable cable = (TileEntityCable) world.getTileEntity(x, y, z);
			needRotationBlock = cable.needRotationBlock();
			coverd = cable.isCoverd();
			if(coverd)
			{
				cubes.add(new CubeObject(0, 0, 0, 1, 1, 1, cable.block, cable.meta));
			}
		}
		if(needRotationBlock && !coverd)
		{
			cubes.add(new CubeObject(0.3, 0.3, 0.3, 0.7, 0.7, 0.7, corner));
			if(world != null)
			{
				
				for (int i = 0; i < 6; i++) {
					ChunkCoordinates coord = new ChunkCoordinates(x, y, z);
					ForgeDirection direction = ForgeDirection.getOrientation(i);
					RotationUtils.applyDirection(direction, coord);
					if(world.isSideSolid(coord.posX, coord.posY, coord.posZ, direction.getOpposite(), true) && !(world.getTileEntity(coord.posX, coord.posY, coord.posZ) instanceof EnergyCore))
					{
						CubeObject cube = new CubeObject(0.6D, 0.4D, 0.4D, 1.0D, 0.6D, 0.6D, block);
						RotationUtils.applyCubeRotation(cube, direction);
						cubes.add(cube);
					}
				}
			}
		}
		return cubes;
	}
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z)
	{
		TileEntityCable cable = (TileEntityCable) entity;
		if(!cable.isCoverd())
		{
			for(int zahl = 0; zahl < 6; zahl++)
			{			
				if(cable.connection[zahl])
				{
					GL11.glPushMatrix();
					switch(ForgeDirection.getOrientation(zahl))
					{
					case EAST:
						GL11.glTranslatef((float)x+0.75F, (float)y+0.5F, (float)z+0.5F);
						break;
					case WEST:
						GL11.glTranslatef((float)x+0.25F, (float)y+0.5F, (float)z+0.5F);
						break;
					case UP:
						GL11.glTranslatef((float)x+0.5F, (float)y+0.75F, (float)z+0.5F);
						break;
					case DOWN:
						GL11.glTranslatef((float)x+0.5F, (float)y+0.25F, (float)z+0.5F);
						break;
					case NORTH:
						GL11.glTranslatef((float)x+0.5F, (float)y+0.5F, (float)z+0.25F);
						break;
					case SOUTH:
						GL11.glTranslatef((float)x+0.5F, (float)y+0.5F, (float)z+0.75F);
						break;
					default:
						break;
					}
					
					GL11.glEnable(GL12.GL_RESCALE_NORMAL);
					float rotate = (float) cable.getRotation((double)System.nanoTime());
					switch(ForgeDirection.getOrientation(zahl))
					{
					case EAST:
					case WEST:
						GL11.glRotatef(0, 0, 1, 0);
						GL11.glRotatef(rotate, 1, 0, 0);
						break;
					case UP:
					case DOWN:
						GL11.glRotatef(90, 0, 0, 1);
						GL11.glRotatef(rotate, 1, 0, 0);
						break;
					case NORTH:
					case SOUTH:
						GL11.glRotatef(90, 0, 1, 0);
						GL11.glRotatef(rotate, 1, 0, 0);
						break;
					default:
						break;
					}
					GL11.glScaled(0.5, 0.25, 0.25);
					RenderHelper3D.renderBlock(block);
			        GL11.glPopMatrix();
				}
			}
		}
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		onNeighborBlockChange(tileEntity.getWorldObj(), tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord, null);
	}
	
	@Override
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		TileEntity entity = world.getTileEntity(x, y, z);
		if(entity instanceof TileEntityCable)
		{
			TileEntityCable cable = (TileEntityCable) entity;
			if(cable.isCoverd())
				return new CubeObject();
			double minX = 0.3;
			if(cable.connection[4])
				minX = 0;
			double maxX = 0.7;
			if(cable.connection[5])
				maxX = 1;
			double minY = 0.3;
			if(cable.connection[0])
				minY = 0;
			double maxY = 0.7;
			if(cable.connection[1])
				maxY = 1;
			double minZ = 0.3;
			if(cable.connection[2])
				minZ = 0;
			double maxZ = 0.7;
			if(cable.connection[3])
				maxZ = 1;
			return new CubeObject(minX, minY, minZ, maxX, maxY, maxZ);
		}
		return new CubeObject();
	}
	

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		TileEntityCable cable = (TileEntityCable) world.getTileEntity(x, y, z);
		if(cable.isCoverd())
		{
			if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops") && !player.capabilities.isCreativeMode)
	        {
	            float f = 0.7F;
	            double d0 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            double d1 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            double d2 = (double)(world.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
	            EntityItem entityitem = new EntityItem(world, (double)x + d0, (double)y + d1, (double)z + d2, new ItemStack(cable.block, 1, cable.meta));
	            entityitem.delayBeforeCanPickup = 10;
	            world.spawnEntityInWorld(entityitem);
	        }
			world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), cable.block.stepSound.getBreakSound(), (cable.block.stepSound.getVolume() + 1.0F) / 2.0F, cable.block.stepSound.getPitch() * 0.8F);
			cable.block = null;
			cable.meta = 0;
			world.markBlockForUpdate(x, y, z);
			return false;
		}
		return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}
	
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {	
		TileEntityCable cable = (TileEntityCable) par1World.getTileEntity(par2, par3, par4);
		
		if(cable.transmitedPower > 0 && cable.needRotationBlock() && !cable.isCoverd())
		{
	        Random random = par1World.rand;
	        double d = 0.0625D;
	
	        for (int i = 0; i < 6; i++)
	        {

	        	double d1 = (float)par2 + random.nextFloat();
	            double d2 = (float)par3 + random.nextFloat();
	            double d3 = (float)par4 + random.nextFloat();

	            if (i == 0 && !par1World.isBlockNormalCubeDefault(par2, par3 + 1, par4, true))
	            {
	                d2 = (double)(par3 + 1) + d;
	            }

	            if (i == 1 && !par1World.isBlockNormalCubeDefault(par2, par3 - 1, par4, true))
	            {
	                d2 = (double)(par3 + 0) - d;
	            }

	            if (i == 2 && !par1World.isBlockNormalCubeDefault(par2, par3, par4 + 1, true))
	            {
	                d3 = (double)(par4 + 1) + d;
	            }

	            if (i == 3 && !par1World.isBlockNormalCubeDefault(par2, par3, par4 - 1, true))
	            {
	                d3 = (double)(par4 + 0) - d;
	            }

	            if (i == 4 && !par1World.isBlockNormalCubeDefault(par2 + 1, par3, par4, true))
	            {
	                d1 = (double)(par2 + 1) + d;
	            }

	            if (i == 5 && !par1World.isBlockNormalCubeDefault(par2 - 1, par3, par4, true))
	            {
	                d1 = (double)(par2 + 0) - d;
	            }
	
	            if (d1 < (double)par2 || d1 > (double)(par2 + 1) || d2 < 0.0D || d2 > (double)(par3 + 1) || d3 < (double)par4 || d3 > (double)(par4 + 1))
	            {
	                par1World.spawnParticle("smoke", d1, d2, d3, 0.0D, 0.0D, 0.0D);
	            }
	        }
		}
    }
	
	@Override
	public boolean onBlockActivated(EntityPlayer player, ItemStack stack, TileEntity tileEntity)
    {
		if(stack != null && stack.getItem() instanceof ItemBlock && tileEntity instanceof TileEntityCable)
		{
			TileEntityCable cable = (TileEntityCable) tileEntity;
			if(cable.isCoverd()) return false;
			Block block = Block.getBlockFromItem(stack.getItem());
			if(!block.isNormalCube() && !(block instanceof BlockGlass))
			{
					return false;
			}
			cable.block = block;
			cable.meta = stack.getItemDamage();
			cable.updateBlock();
			cable.getWorldObj().playSoundEffect((double)((float)cable.xCoord + 0.5F), (double)((float)cable.yCoord + 0.5F), (double)((float)cable.zCoord + 0.5F), block.stepSound.func_150496_b(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
			if(!player.capabilities.isCreativeMode)stack.stackSize--;
			return true;
		}
		return false;
    }
	
	@Override
	public boolean isSolid(TileEntity tileEntity)
	{
		return tileEntity instanceof TileEntityCable && ((TileEntityCable)tileEntity).isCoverd();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		TileEntityCable cable = (TileEntityCable) world.getTileEntity(x, y, z);
		cable.updateConnections();
	}
	
}
