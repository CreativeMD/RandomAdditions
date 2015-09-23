package com.creativemd.randomadditions.common.gui.controls;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.randomadditions.common.systems.machine.MachineRecipe;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.common.systems.machine.tileentity.TileEntityMachine;

public class GuiMachineProgressControl extends GuiControl{
	
	public SubBlockMachine block;
	public TileEntityMachine machine;
	
	public static final int amount = 6;
	
	public GuiMachineProgressControl(String name, SubBlockMachine block, TileEntityMachine machine, int x, int y, int width, int height) {
		super(name, x, y, width, height);
		this.block = block;
		this.machine = machine;
	}

	@Override
	public void drawControl(FontRenderer renderer) {
		Minecraft mc = Minecraft.getMinecraft();
		RenderHelper2D.renderIcon(Blocks.wool.getIcon(0, 15), 0, 0, 1, true, 0, width, height);
		int border = 3;
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderHelper2D.zLevel = 10;
		RenderHelper2D.drawRect(0+border, 0+border, 0+width-border, 0+height-border, Vec3.createVectorHelper(0.5, 0.5, 0.5), 1);
		RenderHelper2D.zLevel = 0;
		//RenderHelper2D.renderIcon(Blocks.brick_block.getBlockTextureFromSide(0), -43+border+mc.displayWidth/4, -75+border+mc.displayHeight/4, 0.2, true, 0, sizeX-border*2, sizeY-border*2);
		block.renderProgressField((double) machine.getCurrentPower() / (double) machine.getInteralStorage());
		
		if(machine.progress > 0)
        {
        	ArrayList<ItemStack> input = machine.getInput();
        	MachineRecipe recipe = block.getRecipe(input);
        	if(recipe != null)
        	{
        		double progress = (double)machine.progress/(double)recipe.neededPower;
        		for (int i = 0; i < recipe.input.size(); i++) {
	        		ItemStack stack = recipe.getItemStack(i, input);
	        		int index = i;
	        		for (int j = 0; j < input.size(); j++) {
						if(input.get(j) != null && input.get(j).isItemEqual(stack) && ItemStack.areItemStackTagsEqual(input.get(j), stack))
							index = j;
					}
	        		if(recipe.input.size() == 1)
	        			index = 1;
		        	RenderHelper2D.renderItem(stack, -7 + (int)(108*progress), (int) (20+(index-1)*18*(1F-(float)progress)), 1F-(float)progress);
	        	}
	        	float zLevel = RenderHelper2D.renderer.zLevel;
	        	RenderHelper2D.renderer.zLevel = 49;
	        	RenderHelper2D.renderItem(recipe.getOutput(input), -7 + (int)(108*progress), 20, (float)progress);
	        	RenderHelper2D.renderer.zLevel = zLevel;
        	}
        }
		
		int size = (width-border*2)/amount;
		int amountY = height/size;
		for (int i = 0; i < amount; i++) {
			for (int j = 0; j < amountY; j++) {
				RenderHelper2D.renderer.zLevel = 100;
				RenderHelper2D.renderIcon(Blocks.iron_bars.getBlockTextureFromSide(0), 0+i*size+border, 0+j*size+border, 0.7, true, 0, size, size);
				RenderHelper2D.renderer.zLevel = 0;
			}
		}
		
	}
	
	@Override
	public ArrayList<String> getTooltip()
	{
		if(machine.progress > 0)
		{
			ArrayList<String> strings = new ArrayList<String>();
			MachineRecipe recipe = block.getRecipe(machine.getInput());
			if(recipe != null)
			{
	        	double progress = (double)machine.progress/(double)recipe.neededPower;
	        	strings.add((int)(progress*100D) + "%");
			}
        	return strings;
		}
		return new ArrayList<String>();
	}

}
