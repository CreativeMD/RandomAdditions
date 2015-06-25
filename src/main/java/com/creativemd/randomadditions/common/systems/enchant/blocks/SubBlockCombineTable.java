package com.creativemd.randomadditions.common.systems.enchant.blocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.enchant.SubBlockEnchant;
import com.creativemd.randomadditions.common.systems.enchant.gui.SubContainerCombine;
import com.creativemd.randomadditions.common.systems.enchant.gui.SubGuiCombine;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockCombineTable extends SubBlockEnchant{
	
	public SubBlockCombineTable(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z) {
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject(0, 0, 0, 1, 0.1, 1));
		cubes.add(new CubeObject(0.1, 0.1, 0.1, 0.2, 1, 0.2));
		cubes.add(new CubeObject(0.1, 0.1, 0.8, 0.2, 1, 0.9));
		cubes.add(new CubeObject(0.8, 0.1, 0.1, 0.9, 1, 0.2));
		cubes.add(new CubeObject(0.8, 0.1, 0.8, 0.9, 1, 0.9));
		cubes.add(new CubeObject(0, 0.8, 0, 1, 0.9, 1));
		cubes.add(new CubeObject(0.2, 0.9, 0, 0.8, 0.95, 1, Blocks.anvil));
		return cubes;
	}
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z) {
		TileEntityUpgrade upgrade = (TileEntityUpgrade) entity;
		RenderHelper3D.renderItem(new ItemStack(Blocks.glass), x, y, z, System.nanoTime()/200000000D, System.nanoTime()/100000000D, System.nanoTime()/30000000D, 1.5, upgrade.getDirection(), 0, -0.1, 0);
		RenderHelper3D.renderItem(RandomAdditions.tourmaline.getIngot(), x, y, z, System.nanoTime()/80000000D, System.nanoTime()/400000000D, System.nanoTime()/100000000D, 0.7, upgrade.getDirection(), 0, -0.1, 0);
		if(upgrade.inventory.length == 3)
		{
			if(upgrade.inventory[0] != null)
			{
				RenderHelper3D.renderItem(upgrade.inventory[0], x, y, z, 90, 0, 0, 0.8, upgrade.getDirection(), 0, 0.46, 0.3);
			}
			if(upgrade.inventory[1] != null)
			{
				RenderHelper3D.renderItem(upgrade.inventory[1], x, y, z, 90, 0, 0, 0.8, upgrade.getDirection(), 0, 0.46, 0);
			}
			if(upgrade.inventory[2] != null)
			{
				RenderHelper3D.renderItem(upgrade.inventory[2], x, y, z, 90, 0, 0, 0.8, upgrade.getDirection(), 0, 0.46, -0.3);
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
    {
		return Blocks.planks.getBlockTextureFromSide(side);
    }

	@Override
	public int getInventorySize() {
		return 3;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return new SubGuiCombine((TileEntityRandom) tileEntity);
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerCombine((TileEntityUpgrade) tileEntity, player);
	}
}
