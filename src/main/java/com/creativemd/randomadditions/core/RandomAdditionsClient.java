package com.creativemd.randomadditions.core;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.creativemd.randomadditions.client.entity.RenderRandomArrow;
import com.creativemd.randomadditions.client.entity.RenderThrow;
import com.creativemd.randomadditions.common.energy.core.EnergyCable;
import com.creativemd.randomadditions.common.entity.EntityRandomArrow;
import com.creativemd.randomadditions.common.entity.EntityThrow;
import com.creativemd.randomadditions.common.subsystem.client.block.BlockSubSpecialRenderer;
import com.creativemd.randomadditions.common.systems.deco.SubSystemDeco;
import com.creativemd.randomadditions.common.systems.deco.renderer.ItemRendererSofa;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class RandomAdditionsClient extends RandomAdditionsServer{
	
	public static int modelID;
	
	@Override
	public void loadSide()
	{
		modelID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(modelID, new BlockSubSpecialRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityThrow.class, new RenderThrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityRandomArrow.class, new RenderArrow());
		
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(SubSystemDeco.instance.block), new ItemRendererSofa());
	}
}
