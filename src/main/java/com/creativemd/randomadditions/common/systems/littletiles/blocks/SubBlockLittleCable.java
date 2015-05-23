package com.creativemd.randomadditions.common.systems.littletiles.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.creativemd.littletiles.common.blocks.ILittleTile;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.common.utils.LittleTile.LittleTileSize;
import com.creativemd.littletiles.common.utils.LittleTile.LittleTileVec;
import com.creativemd.littletiles.common.utils.LittleTilePreview;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.subsystem.TileEntityRandom;
import com.creativemd.randomadditions.common.systems.littletiles.SubBlockLittle;
import com.creativemd.randomadditions.common.systems.littletiles.littleblocks.LittleCable;
import com.creativemd.randomadditions.common.systems.littletiles.tileentity.TileEntityLittleCable;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SubBlockLittleCable extends SubBlockLittle implements ILittleTile{
	
	public static final int[] power = new int[]{200, 100, 10000, 200000};
	
	public SubBlockLittleCable(String name, SubBlockSystem system) {
		super(name, system);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister register)
	{
		if(hasBlockTexture())
			icons = new IIcon[]{register.registerIcon(RandomAdditions.modid + ":" + name)};
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		list.add("Can transmit up to " + power[stack.getItemDamage()] + " RA/tick");
	}
	
	@Override
	public TileEntityRandom getTileEntity() {
		return new TileEntityLittleCable();
	}

	@Override
	public LittleTilePreview getLittlePreview(ItemStack stack) {
		
		return new LittleTilePreview(new LittleTileSize(1, 1, 1));
	}

	@Override
	public ArrayList<LittleTile> getLittleTile(ItemStack stack, World world, int x, int y,
			int z) {
		ArrayList<LittleTile> tiles = new ArrayList<LittleTile>();
		TileEntityLittleCable cable = new TileEntityLittleCable();
		cable.setWorldObj(world);
		cable.xCoord = x;
		cable.yCoord = y;
		cable.zCoord = z;
		tiles.add(new LittleCable(stack.getItemDamage(), cable));
		return tiles;
	}
}
