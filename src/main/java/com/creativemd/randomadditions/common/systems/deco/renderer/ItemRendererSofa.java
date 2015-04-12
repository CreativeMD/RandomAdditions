package com.creativemd.randomadditions.common.systems.deco.renderer;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.creativemd.randomadditions.common.systems.deco.SubBlockDeco;
import com.creativemd.randomadditions.common.systems.deco.SubSystemDeco;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRendererSofa implements IItemRenderer{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		SubBlockDeco.renderedItemStack = item;
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		/*SubBlockDeco block = SubSystemDeco.instance.getSubBlock(item.getItemDamage());
		Tessellator tesselator = Tessellator.instance;
		ArrayList<CubeObject> cubes = block.getCubes(item,null, 0, 0, 0);
		for (int i = 0; i < cubes.size(); i++)
        {
			renderer.setRenderBounds(cubes.get(i).minX, cubes.get(i).minY, cubes.get(i).minZ, cubes.get(i).maxX, cubes.get(i).maxY, cubes.get(i).maxZ);
            Block block = machine;
            int meta = metadata;
            if(cubes.get(i).block != null)
            {
            	block = cubes.get(i).block;
            	meta = 0;
            }
            if(cubes.get(i).icon != null){
            	GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
                tesselator.startDrawingQuads();
                tesselator.setNormal(0.0F, -1.0F, 0.0F);
                renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, cubes.get(i).icon);
                tesselator.draw();
                tesselator.startDrawingQuads();
                tesselator.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, cubes.get(i).icon);
                tesselator.draw();
                tesselator.startDrawingQuads();
                tesselator.setNormal(0.0F, 0.0F, -1.0F);
                renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, cubes.get(i).icon);
                tesselator.draw();
                tesselator.startDrawingQuads();
                tesselator.setNormal(0.0F, 0.0F, 1.0F);
                renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, cubes.get(i).icon);
                tesselator.draw();
                tesselator.startDrawingQuads();
                tesselator.setNormal(-1.0F, 0.0F, 0.0F);
                renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, cubes.get(i).icon);
                tesselator.draw();
                tesselator.startDrawingQuads();
                tesselator.setNormal(1.0F, 0.0F, 0.0F);
                renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, cubes.get(i).icon);
                tesselator.draw();
                GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }else{
	            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	            tesselator.startDrawingQuads();
	            tesselator.setNormal(0.0F, -1.0F, 0.0F);
	            renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
	            tesselator.draw();
	            tesselator.startDrawingQuads();
	            tesselator.setNormal(0.0F, 1.0F, 0.0F);
	            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
	            tesselator.draw();
	            tesselator.startDrawingQuads();
	            tesselator.setNormal(0.0F, 0.0F, -1.0F);
	            renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
	            tesselator.draw();
	            tesselator.startDrawingQuads();
	            tesselator.setNormal(0.0F, 0.0F, 1.0F);
	            renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
	            tesselator.draw();
	            tesselator.startDrawingQuads();
	            tesselator.setNormal(-1.0F, 0.0F, 0.0F);
	            renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
	            tesselator.draw();
	            tesselator.startDrawingQuads();
	            tesselator.setNormal(1.0F, 0.0F, 0.0F);
	            renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
	            tesselator.draw();
	            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            }
        }*/
	}

}
