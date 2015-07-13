package com.creativemd.randomadditions.common.systems.producer.blocks;

import java.util.ArrayList;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.producer.SubBlockProducer;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityProducer;

public class CreativeProducer extends SubBlockProducer{

	public CreativeProducer(SubBlockSystem system) {
		super("Creative", system);
	}

	@Override
	public int getMaxSpeed(TileEntityProducer tileEntity, ItemStack stack) {
		return 100000000;
	}

	@Override
	public void updateTileEntity(TileEntityProducer producer) {
		if(!producer.getWorldObj().isRemote)
			producer.receivePower(producer.getInteralStorage() - producer.getCurrentPower());
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return Blocks.brick_block.getIcon(side, meta);
	}
	
	public boolean canBlockStay(TileEntityProducer producer, World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y,
			int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.addAll(CubeObject.getBorder(Blocks.brick_block.getBlockTextureFromSide(0), 0.2, 0.1));
		cubes.addAll(CubeObject.getFillBorder(Blocks.cobblestone, 0.2, 0.1));
		//cubes.add(new CubeObject(0, 0, 0, 1, 1, 1));
		return cubes;
	}

	@Override
	public int getModifiers() {
		return 0;
	}

	@Override
	public int getPlayTime() {
		return 0;
	}
	
}
