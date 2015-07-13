package com.creativemd.randomadditions.common.systems.producer;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.producer.gui.SubContainerProducer;
import com.creativemd.randomadditions.common.systems.producer.gui.SubGuiProducer;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityProducer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlockProducer extends SubBlock {

	public SubBlockProducer(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		if(tileEntity instanceof TileEntityProducer)
			return new SubGuiProducer((TileEntityProducer) tileEntity, this);
		return null;
	}
	
	@Override
	public boolean hasBlockTexture()
	{
		return false;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		if(tileEntity instanceof TileEntityProducer)
			return new SubContainerProducer((TileEntityRandom) tileEntity, player);
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		int maxspeed = getMaxSpeed(null, stack);
		if(maxspeed < 1)
			maxspeed = 1;
		list.add("MaxSpeed: " + maxspeed);
	}
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityProducer();
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return false;
	}
	
	@Override
	public int getRotation()
	{
		return 1;
	}
	
	public boolean canProvidePower(TileEntityProducer producer, ForgeDirection direction)
	{
		return true;
	}
	
	public CubeObject getArea(TileEntityProducer producer)
	{
		return new CubeObject();
	}
	
	public boolean canBlockStay(TileEntityProducer producer, World world, int x, int y, int z)
	{
		CubeObject cube = getArea(producer);
		return hasEngouhSpace(world, (int)cube.minX+x, (int)cube.minY+y, (int)cube.minZ+z, (int)cube.maxX+x, (int)cube.maxY+y, (int)cube.maxZ+z, x, y, z);
	}
	
	public boolean doesAcceptBlock(Block block)
	{
		return block instanceof BlockAir;
	}
	
	public boolean hasEngouhSpace(World world, int x, int y, int z, int x2, int y2, int z2, int centerX, int centerY, int centerZ)
	{
		for (int posX = x; posX <= x2; posX++) {
			for (int posY = y; posY <= y2; posY++) {
				for (int posZ = z; posZ <= z2; posZ++) {
					if(!doesAcceptBlock(world.getBlock(posX, posY, posZ)) && !(centerX == posX && centerY == posY && centerZ == posZ))
						return false;
				}
			}
		}
		return true;
	}
	
	/**tileEntity can be null, for inventory stack != null**/
	public abstract int getMaxSpeed(TileEntityProducer tileEntity, ItemStack stack);
	
	public abstract void updateTileEntity(TileEntityProducer producer);
	
	public abstract int getModifiers();
	
	public double getRotation(TileEntityProducer producer, double Time)
	{
		return 0;
	}
	
	public abstract int getPlayTime();
	
	public float getPlayVolume(TileEntityProducer producer)
	{
		float test = Math.max((float)producer.speed/130F, 0.05F);
		return Math.min(Math.max((float)producer.speed/130F, 0.05F), 1);
	}
}
