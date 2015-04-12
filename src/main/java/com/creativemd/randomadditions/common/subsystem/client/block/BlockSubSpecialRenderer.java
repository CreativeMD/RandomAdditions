package com.creativemd.randomadditions.common.subsystem.client.block;

import com.creativemd.randomadditions.common.subsystem.BlockSub;
import com.creativemd.randomadditions.core.RandomAdditions;
import com.creativemd.randomadditions.core.RandomAdditionsClient;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockSubSpecialRenderer implements ISimpleBlockRenderingHandler{

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		if(block instanceof BlockSub)
			((BlockSub) block).renderInventoryBlock(block, metadata, modelId, renderer);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		if(block instanceof BlockSub && ((BlockSub) block).renderWorldBlock(world, x, y, z, block, modelId, renderer))
			return true;
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RandomAdditionsClient.modelID;
	}

}
