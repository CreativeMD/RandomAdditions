package com.creativemd.randomadditions.common.item;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.tools.Tool;
import com.creativemd.randomadditions.common.item.tools.ToolArmor;
import com.creativemd.randomadditions.common.item.tools.ToolAxe;
import com.creativemd.randomadditions.common.item.tools.ToolBow;
import com.creativemd.randomadditions.common.item.tools.ToolHoe;
import com.creativemd.randomadditions.common.item.tools.ToolPickaxe;
import com.creativemd.randomadditions.common.item.tools.ToolShovel;
import com.creativemd.randomadditions.common.item.tools.ToolStar;
import com.creativemd.randomadditions.common.item.tools.ToolSword;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTool extends ItemCore{
	
	public ItemTool()
	{
		hasSubtypes = true;
		setMaxStackSize(1);
		setFull3D();
	}
	
	public static final ArrayList<Tool> tools = new ArrayList<Tool>();
	public static Tool pickaxe = new ToolPickaxe(RandomItem.pickaxePlate, "pickaxe").setCost(3).setHarvest(1);
	public static Tool axe = new ToolAxe(RandomItem.axePlate, "axe").setCost(3).setHarvest(2);
	public static Tool shovel = new ToolShovel(RandomItem.shovelPlate, "shovel");
	public static Tool hoe = new ToolHoe(RandomItem.hoePlate, "hoe").setCost(2);
	public static Tool sword = new ToolSword(RandomItem.swordPlate, "sword").setCost(2).setHarvest(-1).setMaxDuration(72000);
	public static Tool throwingStar = new ToolStar(RandomItem.throwingstarPlate, "throwingstar").setCost(4); //TODO Not finished ThrowingStar/Bow
	public static Tool bow = new ToolBow(RandomItem.bowPlate, "bow").setCost(3).setMaxDuration(72000);
	
	public static Tool helmet = new ToolArmor(RandomItem.helmetPlate, "helmet", 0, 5);
	public static Tool chestplate = new ToolArmor(RandomItem.chestplatePlate, "chestplate", 1, 8);
	public static Tool leggings = new ToolArmor(RandomItem.leggingsPlate, "leggings", 2, 7);
	public static Tool boots = new ToolArmor(RandomItem.bootsPlate, "boots", 3, 4);
	
	public static void registerRecipes()
	{
		for (int i = 0; i < tools.size(); i++) {
			if(!tools.get(i).external)
			{
				for (int j = 0; j < RandomAdditions.materials.size(); j++) {
					Object input = RandomAdditions.materials.get(j).ingot;
					if(input == null && !RandomAdditions.materials.get(j).itemName.equals(""))
						input = RandomAdditions.materials.get(j).itemName;
					if (input == null)
						input = RandomAdditions.materials.get(j).getIngot();
					tools.get(i).addRecipe(input, getStack(tools.get(i), RandomAdditions.materials.get(j).id));
				}
			}
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
    {
		Tool tool = getTool(par1ItemStack);
		if(tool.damageAmount > 0)
		{
			damageItem(par1ItemStack, 1, par3EntityLivingBase);
			ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(par1ItemStack);
			float damage = getMaterial(par1ItemStack).getDamage(CraftMaterial.getLevel(ItemTool.getMaterial(par1ItemStack), par1ItemStack));
	    	for (int i = 0; i < modifiers.size(); i++) {
	    		damage = modifiers.get(i).getDamageOnEntity((EntityPlayer) par3EntityLivingBase, par2EntityLivingBase, damage);
			}
			par2EntityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) par3EntityLivingBase), (float) damage*tool.damageAmount);
		}
        return true;
    }
	
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return getTool(par1ItemStack).maxduration;
    }
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(getTool(par1ItemStack).maxduration > 0 && getTool(par1ItemStack).canExtend(par3EntityPlayer))
			par3EntityPlayer.setItemInUse(par1ItemStack, getTool(par1ItemStack).maxduration);
        return par1ItemStack;
    }
	
	public static void damageItem(ItemStack stack, int amount, EntityLivingBase entity)
	{
		if (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).capabilities.isCreativeMode)
        {
            if (stack.isItemStackDamageable())
            {
            	if(stack.stackTagCompound == null)
            		stack.stackTagCompound = new NBTTagCompound();
            	
            	ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
            	for (int i = 0; i < modifiers.size(); i++) {
					amount = modifiers.get(i).onToolTakenDamage((EntityPlayer)entity, stack, amount);
				}
            	Tool tool = getTool(stack);
            	CraftMaterial material = getMaterial(stack);
            	if(getItemDamage(stack) < tool.durabilityFactor*material.durability)
            		setItemDamage(stack, getItemDamage(stack)+amount);
            	if(getItemDamage(stack) >= tool.durabilityFactor*material.durability && CraftMaterial.getLevel(material, stack) == 0)
            	{
	            	entity.renderBrokenItemStack(stack);
	                --stack.stackSize;
	
	                if (entity instanceof EntityPlayer)
	                {
	                    EntityPlayer entityplayer = (EntityPlayer)entity;
	                    entityplayer.addStat(StatList.objectBreakStats[Item.getIdFromItem(stack.getItem())], 1);
	
	                    if (stack.stackSize == 0)
	                    {
	                        entityplayer.destroyCurrentEquippedItem();
	                    }
	                }
	
	                if (stack.stackSize < 0)
	                {
	                    stack.stackSize = 0;
	                }
	                stack.setItemDamage(0);
            	}
            }
        }
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return getTool(par1ItemStack).action;
    }
	
	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		getTool(par1ItemStack).onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
		
		for (int i = 0; i < RandomAdditions.materials.size(); i++) {
			int level = CraftMaterial.getLevel(RandomAdditions.materials.get(i), par1ItemStack);
			if(level > 0)
			{
				String levelName = getStringFromLevel(level);
				EnchantmentModifier modifier = RandomAdditions.materials.get(i).getModifier(par1ItemStack);
				if(modifier != null)
					par3List.add(modifier.getName() + " " + levelName);
				else
					par3List.add("Level " + levelName);
			}
		}
		Tool tool = getTool(par1ItemStack);
		CraftMaterial material = getMaterial(par1ItemStack);
		if(tool != null && material != null)
		{
			int max = material.durability*tool.durabilityFactor;
			int damage = getItemDamage(par1ItemStack);
			par3List.add((max-damage + " Uses are left"));
		}
		String modifier = getTool(par1ItemStack).getAttributeModifiers(par1ItemStack);
		if(!modifier.equals(""))
		{
			par3List.add(EnumChatFormatting.BLUE + modifier);
		}
	}
	
	public static String getStringFromLevel(int level)
	{
		if(level == 2)
			return "II";
		if(level == 3)
			return "III";
		return "I";
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
    {
        return getTool(stack).durabilityFactor*getMaterial(stack.getItemDamage()).durability;
    }
	
	@Override
	public boolean showDurabilityBar(ItemStack stack)
    {
        return getItemDamage(stack) > 0;
    }
	
	@Override
	public double getDurabilityForDisplay(ItemStack stack)
    {
        return (double)getItemDamage(stack) / (double)stack.getMaxDamage();
    }
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass)
    {
		if(getTool(stack).harvest == 1)
			return getMaterial(stack).harvestLevel;
		return 0;
    }
	
	@Override
	public int getItemStackLimit(ItemStack stack)
    {
        return 1;
    }
	
	@Override
	public boolean canHarvestBlock(Block par1Block, ItemStack itemStack)
    {
		if(itemStack.stackTagCompound.getInteger("damage") >= ItemTool.getTool(itemStack).durabilityFactor*ItemTool.getMaterial(itemStack).durability)
			return false;
        return getTool(itemStack).isToolEffective(itemStack, par1Block);  
    }
	
	public static int getItemDamage(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemRandomArmor)
			return stack.getItemDamage();
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		return stack.stackTagCompound.getInteger("damage");
	}
	
	public static void setItemDamage(ItemStack stack, int damage)
	{
		if(stack.getItem() instanceof ItemRandomArmor)
			stack.setItemDamage(damage);
		else{
			if(stack.stackTagCompound == null)
				stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setInteger("damage", damage);
		}
	}
	
	@Override
	public float getSmeltingExperience(ItemStack item)
    {
        return -1; //-1 will default to the old lookups.
    }
	
	@Override
	public boolean onBlockDestroyed(ItemStack itemstack, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if (getTool(itemstack).isToolEffective(itemstack, block) && (double)block.getBlockHardness(world, x, y, z) != 0.0D)
        {
        	damageItem(itemstack, 1, entity);
        }

        return true;
    }
	
	public static float getDigSpeed(EntityPlayer player, ItemStack itemstack, Block block, int metadata)
    {
		if(getTool(itemstack).isToolEffective(itemstack, block))
		{
			float speed = getMaterial(itemstack).getSpeed(CraftMaterial.getLevel(getMaterial(itemstack), itemstack));
			ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(itemstack);
	    	for (int i = 0; i < modifiers.size(); i++) {
	    		speed = (int) modifiers.get(i).getMiningSpeed(player, true, block, speed);
			}
			return speed;
		}
        return 1F;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return CraftMaterial.hasEffect(par1ItemStack);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
		{			
			for(int toolZahl = 0; toolZahl < tools.size(); toolZahl++)
			{
				if(!tools.get(toolZahl).external)
					tools.get(toolZahl).registerIcon(par1IconRegister, RandomAdditions.materials.get(zahl));
			}
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
	{
        return getIcon(stack, 0, null, null, 0);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        return getTool(stack).getIcon(stack, usingItem, useRemaining);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
		for(int zahl = 0; zahl < RandomAdditions.materials.size(); zahl++)
		{
			ItemStack stack = new ItemStack(item, 1, zahl);
			stack.stackTagCompound = new NBTTagCompound();
			
			for(int toolZahl = 0; toolZahl < tools.size(); toolZahl++)
			{
				if(!tools.get(toolZahl).external)
				{
					ItemStack newStack = stack.copy();
					newStack.stackTagCompound.setString("type", tools.get(toolZahl).name);
					list.add(newStack);
				}
			}
		}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return this.getUnlocalizedName() + "." + getMaterial(par1ItemStack).name + "." + getTool(par1ItemStack).displayName;
    }
	
	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack)
    {
		String name = this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name";
		if(StatCollector.canTranslate(name))
			return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(par1ItemStack) + ".name")).trim();
		return getMaterial(par1ItemStack).displayName + " " + getTool(par1ItemStack).displayName;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		ArrayList<EnchantmentModifier> modifiers = CraftMaterial.getModifiers(stack);
    	for (int i = 0; i < modifiers.size(); i++) {
    		if(modifiers.get(i).onRightClick(player, x, y, z, world.getBlock(x, y, z), side, hitX, hitY, hitZ))
    			return true;
		}
    	Tool tool = getTool(stack);
    	if(tool != null)
    		return tool.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
        return false;
    }
	
	public static ItemStack getStack(Tool tool, int meta)
	{
		ItemStack result = new ItemStack(RandomAdditions.tools, 1, meta);
		result.stackTagCompound = new NBTTagCompound();
		result.stackTagCompound.setString("type", tool.name);
		return result;
	}
	
	public static Tool getTool(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemRandomArmor)
		{
			return ((ItemRandomArmor)stack.getItem()).getTool();
		}
		String type = "";
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		type = stack.stackTagCompound.getString("type");
		for(int zahl = 0; zahl < tools.size(); zahl++)
			if(tools.get(zahl).name.equals(type))
				return tools.get(zahl);
		return null;
	}
	
	public static int getToolID(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemRandomArmor)
		{
			return ((ItemRandomArmor)stack.getItem()).getToolID();
		}
		String type = "";
		if(stack.stackTagCompound == null)
			stack.stackTagCompound = new NBTTagCompound();
		type = stack.stackTagCompound.getString("type");
		for(int zahl = 0; zahl < tools.size(); zahl++)
			if(tools.get(zahl).name.equals(type))
				return zahl;
		return -1;
	}
	
	public static CraftMaterial getMaterial(ItemStack stack)
	{
		if(stack.getItem() instanceof ItemRandomArmor)
		{
			return ((ItemRandomArmor)stack.getItem()).material;
		}
		return getMaterial(stack.getItemDamage());
	}
	
	public static CraftMaterial getMaterial(int meta)
	{
		if(meta >= 0 && meta < RandomAdditions.materials.size())
			return RandomAdditions.materials.get(meta);
		else
			return RandomAdditions.materials.get(0);
	}
}
