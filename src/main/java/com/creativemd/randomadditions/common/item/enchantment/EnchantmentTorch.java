package com.creativemd.randomadditions.common.item.enchantment;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class EnchantmentTorch extends EnchantmentModifier{

	@Override
	public String getName() {
		return "Torch";
	}
	
	@Override
	public boolean onRightClick(EntityPlayer player, int x, int y, int z, Block block, int side, float hitX, float hitY, float hitZ)
	{
		if(block != null)
		{
			int index = -1;
			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				if(index == -1 && player.inventory.mainInventory[i] != null && Block.getBlockFromItem(player.inventory.mainInventory[i].getItem()) == Blocks.torch)
					index = i;
			}
			if((index != -1 || player.capabilities.isCreativeMode) && hasNormalEngouhEnergy(player, level) && ((ItemBlock)Item.getItemFromBlock(Blocks.torch)).onItemUse(new ItemStack(Blocks.torch), player, player.worldObj, x, y, z, side, hitX, hitY, hitZ) && cantakeNormalEnergy(player, level))
			{
				if(!player.capabilities.isCreativeMode)
					player.inventory.mainInventory[index].stackSize--;
				return true;
			}
		}
		return false;
	}
}
