package com.creativemd.randomadditions.common.item;

import java.util.List;

import com.creativemd.randomadditions.common.item.enchantment.EnchantmentModifier;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.item.tools.Tool;
import com.creativemd.randomadditions.core.CraftMaterial;
import com.creativemd.randomadditions.core.RandomAdditions;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemRandomArmor extends ItemArmor{
	
	public CraftMaterial material;
	
	public ItemRandomArmor(CraftMaterial material, ArmorMaterial armor, int p_i45325_2_,
			int armorType) {
		super(armor, p_i45325_2_, armorType);
		this.material = material;
		setCreativeTab(RandomAdditions.tab);
	}
	
	public static ItemStack getArmorPlate(int index)
	{
		switch(index)
		{
		case 0:
			return RandomItem.helmetPlate.getItemStack();
		case 1:
			return RandomItem.chestplatePlate.getItemStack();
		case 2:
			return RandomItem.leggingsPlate.getItemStack();
		case 3:
			return RandomItem.bootsPlate.getItemStack();
		default:
			return RandomItem.helmetPlate.getItemStack();
		}
	}
	
	public static int getArmorCost(int index)
	{
		switch(index)
		{
		case 0:
			return 5;
		case 1:
			return 8;
		case 2:
			return 7;
		case 3:
			return 4;
		default:
			return 0;
		}
	}
	
	public static String getArmorType(int index)
	{
		switch(index)
		{
		case 0:
			return "Helmet";
		case 1:
			return "Chestplate";
		case 2:
			return "Leggings";
		case 3:
			return "Boots";
		default:
			return "";
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public String getIconString()
    {
        return RandomAdditions.modid + ":" + material.name + getArmorType(armorType);
    }
	
	public static void registerArmor()
	{
		for (int i = 0; i < RandomAdditions.materials.size(); i++) {
			ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial("RA_" + RandomAdditions.materials.get(i).displayName, RandomAdditions.materials.get(i).durability/16, RandomAdditions.materials.get(i).protection, 0);
			for (int j = 0; j < 4; j++) {
				String name = "RAArmor" + getArmorType(j) + RandomAdditions.materials.get(i).displayName;
				GameRegistry.registerItem(new ItemRandomArmor(RandomAdditions.materials.get(i), material, 0, j).setUnlocalizedName(name), name);
			}
		}
	}
	
	public static void registerRecipes()
	{
		for (int j = 0; j < RandomAdditions.materials.size(); j++) {
			Object input = RandomAdditions.materials.get(j).ingot;
			if(input == null && !RandomAdditions.materials.get(j).itemName.equals(""))
				input = RandomAdditions.materials.get(j).itemName;
			if (input == null)
				input = RandomAdditions.materials.get(j).getIngot();
			
			for (int i = 0; i < 4; i++) {
				String name = "RAArmor" + getArmorType(i) + RandomAdditions.materials.get(j).displayName;
				ItemStack output = new ItemStack((Item)Item.itemRegistry.getObject(RandomAdditions.modid + ":" + name));
				switch(i)
				{
				case 0:
					GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
							{
							"XXX", "XSX", Character.valueOf('X'), input
							}));
					break;
				case 1:
					GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
							{
							"XSX", "XXX", "XXX", Character.valueOf('X'), input
							}));
					break;
				case 2:
					GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
							{
							"XXX", "XSX", "XSX", Character.valueOf('X'), input
							}));
					break;
				case 3:
					GameRegistry.addRecipe(new ShapedOreRecipe(output, new Object[]
							{
							"XSX", "XSX", Character.valueOf('X'), input
							}));
					break;
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int pass)
	{
		if(!ItemCore.isHot(stack))
			return super.getColorFromItemStack(stack, pass);
		int result = (int) (((Math.sin((double)System.nanoTime()/(double)100000000)+1)/2) * 255);
		String hex = String.format("%02x%02x%02x", 255, result, result);
		return (int) Long.parseLong(hex, 16);
    }
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_)
	{
		if(ItemCore.isHot(stack) && entity instanceof EntityPlayer)
			entity.attackEntityFrom(DamageSource.inFire, 1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
	{
		if(ItemCore.isHot(par1ItemStack))
		{
			par3List.add("Heated");
			par3List.add("Take care! It damages you!");
		}
		
		for (int i = 0; i < RandomAdditions.materials.size(); i++) {
			int level = CraftMaterial.getLevel(RandomAdditions.materials.get(i), par1ItemStack);
			if(level > 0)
			{
				String levelName = ItemTool.getStringFromLevel(level);
				EnchantmentModifier modifier = RandomAdditions.materials.get(i).getModifier(par1ItemStack);
				if(modifier != null)
					par3List.add(modifier.getName() + " " + levelName);
				else
					par3List.add("Level " + levelName);
			}
		}
		Tool tool = ItemTool.getTool(par1ItemStack);
		CraftMaterial material = ItemTool.getMaterial(par1ItemStack);
		if(tool != null && material != null)
		{
			int max = material.durability*tool.durabilityFactor;
			int damage = par1ItemStack.getItemDamage();
			par3List.add((max-damage + " Uses are left"));
		}
		String modifier = ItemTool.getTool(par1ItemStack).getAttributeModifiers(par1ItemStack);
		if(!modifier.equals(""))
		{
			par3List.add(EnumChatFormatting.BLUE + modifier);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return CraftMaterial.hasEffect(par1ItemStack);
    }
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
		if(slot == 3 || slot == 1 || slot == 0)
			return RandomAdditions.modid + ":textures/armor/" + material.displayName + "_layer_1.png";
		else
			return RandomAdditions.modid + ":textures/armor/" + material.displayName + "_layer_2.png";
    }
	
	public Tool getTool()
	{
		switch(armorType)
		{
		case 0:
			return ItemTool.helmet;
		case 1:
			return ItemTool.chestplate;
		case 2:
			return ItemTool.leggings;
		case 3:
			return ItemTool.boots;
		default:
			return ItemTool.helmet;
		}
	}
	
	public int getToolID()
	{
		Tool tool = getTool();
		for (int i = 0; i < ItemTool.tools.size(); i++) {
			if(ItemTool.tools.get(i) == tool)
				return i;
		}
		return -1;
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem)
    {
		ItemStack stack = entityItem.getEntityItem();
		if(ItemCore.isHot(stack) && entityItem.isInsideOfMaterial(Material.water))
		{
			entityItem.playSound("random.fizz", 0.4F, 2.0F + entityItem.worldObj.rand.nextFloat() * 0.4F);
			for(int zahl = 0; zahl < 100; zahl++)
				entityItem.worldObj.spawnParticle("smoke", entityItem.posX, entityItem.posY, entityItem.posZ, (entityItem.worldObj.rand.nextFloat()*0.1)-0.05, 0.2*entityItem.worldObj.rand.nextDouble(), (entityItem.worldObj.rand.nextFloat()*0.1)-0.05);
			if(!entityItem.worldObj.isRemote)
			{
				ItemCore.removeHeat(stack);
			}
		}
        return false;
    }
	
}
