package com.creativemd.randomadditions.common.systems.rf;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.energy.core.EnergyComponent;
import com.creativemd.randomadditions.common.subsystem.SubBlock;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.rf.container.SubContainerRF;
import com.creativemd.randomadditions.common.systems.rf.container.SubGuiRF;
import com.creativemd.randomadditions.common.systems.rf.tileentity.TileEntityRAtoRF;
import com.creativemd.randomadditions.common.systems.rf.tileentity.TileEntityRFtoRA;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockRF extends SubBlock{
	
	public boolean isRA;
	
	public SubBlockRF(String name, SubBlockSystem system, boolean isRA) {
		super(name, system);
		this.isRA = isRA;
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.addAll(CubeObject.getBorder(new CubeObject(), 0.25, 0.25));
		cubes.add(new CubeObject(0.25, 0.25, 0.25, 0.75, 0.75, 0.75, Blocks.stonebrick));
		cubes.add(new CubeObject(0.25, 0.25, 0.25, 0.75, 0.75, 0.75, SubSystemBattery.instance.block, 3));
		return cubes;
	}
	
	@Override
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		if(tileEntity instanceof TileEntityRAtoRF)
			((TileEntityRAtoRF)tileEntity).updateAdjacentHandlers();
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityRAtoRF)
			((TileEntityRAtoRF)tileEntity).updateAdjacentHandlers();
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityRAtoRF)
			((TileEntityRAtoRF)tileEntity).updateAdjacentHandlers();
	}

	@Override
	public boolean isSolid(TileEntity tileEntity) {
		return true;
	}
	
	@Override
	public String getTileEntityName()
	{
		return "RA" + name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		if(tileEntity instanceof EnergyComponent)
			return new SubGuiRF((EnergyComponent) tileEntity, this);
		return null;
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerRF((TileEntityRandom) tileEntity, player);
	}

	@Override
	public TileEntityRandom getTileEntity() {
		if(isRA)
			return new TileEntityRAtoRF();
		else
			return new TileEntityRFtoRA();
	}

}
