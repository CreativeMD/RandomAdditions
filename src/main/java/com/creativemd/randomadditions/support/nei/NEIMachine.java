package com.creativemd.randomadditions.support.nei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.core.CreativeCore;
import com.creativemd.randomadditions.common.systems.machine.SubBlockMachine;
import com.creativemd.randomadditions.core.RandomAdditions;

public abstract class NEIMachine extends TemplateRecipeHandler
{
	
	public abstract SubBlockMachine getMachine();
	
	public class RecipePair extends CachedRecipe
    {
        public RecipePair(ArrayList<ItemStack> ingred, ItemStack result) {
        	ingreds = new ArrayList<PositionedStack>();
        	for (int i = 0; i < ingred.size(); i++) {
        		if(ingred.size() == 1)
        			ingreds.add(new PositionedStack(ingred.get(i), 41, 17));
        		else
        			ingreds.add(new PositionedStack(ingred.get(i), 41, -1+i*18));
			}
            this.result = new PositionedStack(result, 90, 17);
        }
        
        @Override
        public List<PositionedStack> getIngredients() {
        	return ingreds;
        }
        
        @Override
        public PositionedStack getResult() {
            return result;
        }
        
        @Override
        public PositionedStack getOtherStack() {
            return null;
        }
        
        ArrayList<PositionedStack> ingreds;
        PositionedStack result;
    }
    
	@Override
    public void loadTransferRects() {
    }
	
	public Map<ArrayList<ItemStack>, ItemStack> getList()
	{
		Map<ArrayList<ItemStack>, ItemStack> recipes = new HashMap();
		recipes.putAll(getMachine().getRecipesForNEI());
		return recipes;
	}
	
	@Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getMachine().name)) {//don't want subclasses getting a hold of this
            Map<ArrayList<ItemStack>, ItemStack> recipes = getList();
            for (Entry<ArrayList<ItemStack>, ItemStack> recipe : recipes.entrySet())
                arecipes.add(new RecipePair(recipe.getKey(), recipe.getValue()));
        } else
            super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        Map<ArrayList<ItemStack>, ItemStack> recipes = getList();
        for (Entry<ArrayList<ItemStack>, ItemStack> recipe : recipes.entrySet())
            if (NEIServerUtils.areStacksSameType(recipe.getValue(), result))
                arecipes.add(new RecipePair(recipe.getKey(), recipe.getValue()));
    }

    @Override
    public void loadUsageRecipes(String inputId, Object... ingredients) {
    	super.loadUsageRecipes(inputId, ingredients);
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        Map<ArrayList<ItemStack>, ItemStack> recipes = getList();
        for (Entry<ArrayList<ItemStack>, ItemStack> recipe : recipes.entrySet())
        {
        	for (int i = 0; i < recipe.getKey().size(); i++) {
	            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getKey().get(i), ingredient)) {
	            	RecipePair arecipe = new RecipePair(recipe.getKey(), recipe.getValue());
	                arecipe.setIngredientPermutation(Arrays.asList(arecipe.ingreds.get(i)), ingredient);
	                arecipes.add(arecipe);
	            }
        	}
        }
    }
    
    @Override
    public void drawExtras(int recipe) {
    	Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainerSub.background);
		//GL11.glTranslated(mc.displayWidth/8, mc.displayHeight/8, 0);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper2D.drawTexturedModalRect(63, 19, 194, 0, 25, 13);
		GL11.glPopMatrix();
    }
    
    @Override
	public String getGuiTexture() {
    	return CreativeCore.modid + ":textures/gui/GUI.png";
    }
    
	@Override
	public String getRecipeName() {
		return "Mechanic " + getMachine().name;
	}

}
