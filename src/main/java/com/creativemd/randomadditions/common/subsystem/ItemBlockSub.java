package com.creativemd.randomadditions.common.subsystem;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockSub extends ItemBlock{
	
	public SubBlockSystem system;
	
	public ItemBlockSub(Block block) {
		super(block);
		this.system = ((BlockSub) block).system;
		hasSubtypes = true;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return system.getSubBlock(par1ItemStack.getItemDamage()).getUnlocalizedName(par1ItemStack);
    }
	
	@Override
	public int getMetadata(int par1)
    {
        return par1;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_)
	{
		Block block = Block.getBlockFromItem(stack.getItem());
		system.getSubBlock(stack.getItemDamage()).addInformation(stack, player, list, p_77624_4_);
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
    {
		if(super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata))
		{
			system.getSubBlock(metadata).onBlockPlacedEvent(world, player, x, y, z, side, hitX, hitY, hitZ, metadata);
			return true;
		}
		return false;
    }
}
