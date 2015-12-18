package com.creativemd.randomadditions.common.systems.assembly.gui;

import com.creativemd.handcraft.gui.HandRecipeControl;
import com.creativemd.handcraft.recipe.HandRecipe;

public class HandRecipeSelection extends HandRecipeControl{

	public HandRecipeSelection(HandRecipeControl control) {
		super(control.name, control.posX, control.posY, control.width, control.height, control.recipe);
		this.parent = control.parent;
		this.resetID();
		this.setID(control.getID());
	}
	
	@Override
	public boolean mousePressed(int posX, int posY, int button){
		return true;
	}

}
