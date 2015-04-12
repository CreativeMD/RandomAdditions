package com.creativemd.randomadditions.client.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.randomadditions.common.entity.EntityThrow;

public class RenderThrow extends Render{

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		if(entity instanceof EntityThrow)
		{
			EntityThrow star = (EntityThrow) entity;
			if(star.stack != null)
				RenderHelper3D.renderItem(star.stack, x-star.width/2, y-star.height/2D, z-star.width/2, 90, 0, 0, 1, ForgeDirection.UNKNOWN, 0, 0, 0);
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null;
	}

}
