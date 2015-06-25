package com.creativemd.randomadditions.common.systems.enchant.blocks;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.enchant.SubBlockEnchant;
import com.creativemd.randomadditions.common.systems.enchant.gui.SubContainerUpgrade;
import com.creativemd.randomadditions.common.systems.enchant.gui.SubGuiUpgrade;
import com.creativemd.randomadditions.common.systems.enchant.tileentity.TileEntityUpgrade;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockUpgradeTable extends SubBlockEnchant{

	public SubBlockUpgradeTable(String name, SubBlockSystem system) {
		super(name, system);
	}

	@Override
	public int getInventorySize() {
		return 1;
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
		cubes.add(new CubeObject(0.2, 0.9, 0.2, 0.8, 0.95, 0.8, Blocks.anvil));
		return cubes;
	}
	
	@Override
	public void drawRender(TileEntity entity, double x, double y, double z) {
		TileEntityUpgrade upgrade = (TileEntityUpgrade) entity;
		for (int i = 0; i < 10; i++) {
			RenderHelper3D.renderItem(new ItemStack(Items.book), x, y, z, 90, 0, Math.sin(i)*360, 1, upgrade.getDirection(), 0, i*0.03-0.35, 0);
		}
		if(upgrade.inventory.length > 0 && upgrade.inventory[0] != null)
		{
			try
			{
				RenderHelper3D.renderItem(upgrade.inventory[0], x, y, z, 90, 0, 0, 0.8, upgrade.getDirection(), 0, 0.46, 0);
			}catch(Exception e){
				
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
	@SideOnly(Side.CLIENT)
	public SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player) {
		return new SubGuiUpgrade((TileEntityUpgrade) tileEntity);
	}

	@Override
	public SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player) {
		return new SubContainerUpgrade((TileEntityUpgrade) tileEntity, player);
	}

}
