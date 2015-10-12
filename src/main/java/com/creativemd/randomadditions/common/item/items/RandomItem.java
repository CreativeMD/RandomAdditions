package com.creativemd.randomadditions.common.item.items;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.randomadditions.common.item.ItemCoreRandom;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class RandomItem {
	
	public static ArrayList<RandomItem> items = new ArrayList<RandomItem>();
	
	public static RandomItem SpeedUpgrade = new RandomItemSpeedUpgrade("SpeedUpgrade");
	
	public static RandomItem StorageUpgrade = new RandomItemStorageUpgrade("StorageUpgrade");
	
	public static RandomItem InputUpgrade = new RandomItemInputUpgrade("InputUpgrade");
	
	public static RandomItem PowerUpgrade = new RandomItemPowerUpgrade("PowerUpgrade");
	
	public static RandomItem aluminiumDust = new RandomItemDust("aluminum");
	public static RandomItem bronzeDust = new RandomItemDust("bronze");
	public static RandomItem coalDust = new RandomItemDust("coal");
	public static RandomItem copperDust = new RandomItemDust("copper");
	public static RandomItem diamondDust = new RandomItemDust("diamond");
	public static RandomItem emeraldDust = new RandomItemDust("emerald");
	public static RandomItem goldDust = new RandomItemDust("gold");
	public static RandomItem ironDust = new RandomItemDust("iron");
	public static RandomItem lapisDust = new RandomItemDust("lapis");
	public static RandomItem leadDust = new RandomItemDust("lead");
	public static RandomItem obsidianDust = new RandomItemDust("obsidian");
	public static RandomItem rubyDust = new RandomItemDust("ruby");
	public static RandomItem silverDust = new RandomItemDust("silver");
	public static RandomItem tinDust = new RandomItemDust("tin");
	public static RandomItem tourmalineDust = new RandomItemDust("tourmaline");
	
	public static RandomItem axePlate = new RandomItem("axePlate");
	public static RandomItem swordPlate = new RandomItem("swordPlate");
	public static RandomItem pickaxePlate = new RandomItem("pickaxePlate");
	public static RandomItem throwingstarPlate = new RandomItem("throwingstarPlate");
	public static RandomItem bowPlate = new RandomItem("bowPlate");
	public static RandomItem shovelPlate = new RandomItem("shovelPlate");
	public static RandomItem helmetPlate = new RandomItem("helmetPlate");
	public static RandomItem chestplatePlate = new RandomItem("chestplatePlate");
	public static RandomItem leggingsPlate = new RandomItem("leggingsPlate");
	public static RandomItem bootsPlate = new RandomItem("bootsPlate");
	public static RandomItem hoePlate = new RandomItem("hoePlate");
	
	public static RandomItem plastic = new RandomItemPlastic("plastic");	
	public static RandomItem compressedplastic = new RandomItemPlastic("compressedplastic");
	public static RandomItem compressedplastic2 = new RandomItemPlastic("compressedplastic2");
	public static RandomItem heatresistantplastic = new RandomItemPlastic("heatresistantplastic");
	
	public static RandomItem cobblestoneWeight1 = new RandomItemWeight("cobblestoneWeight1");
	public static RandomItem cobblestoneWeight2 = new RandomItemWeight("cobblestoneWeight2");
	public static RandomItem cobblestoneWeight3 = new RandomItemWeight("cobblestoneWeight3");
	
	public static RandomItem bronzeWeight1 = new RandomItemWeight("bronzeWeight1");
	public static RandomItem bronzeWeight2 = new RandomItemWeight("bronzeWeight2");
	public static RandomItem bronzeWeight3 = new RandomItemWeight("bronzeWeight3");
	
	public static RandomItem obsidianWeight1 = new RandomItemWeight("obsidianWeight1");
	public static RandomItem obsidianWeight2 = new RandomItemWeight("obsidianWeight2");
	public static RandomItem obsidianWeight3 = new RandomItemWeight("obsidianWeight3");
	
	public static RandomItem millarm = new RandomItemMillarm("millarm");
	public static RandomItem watermillarm = new RandomItemWatermillarm("watermillarm");
	
	public static RandomItem flour = new RandomItemFlour("flour");
	
	public static RandomItemBattery battery1 = new RandomItemBattery("battery1", 10000);
	public static RandomItemBattery battery2 = new RandomItemBattery("battery2", 100000);
	public static RandomItemBattery battery3 = new RandomItemBattery("battery3", 1000000);
	
	public static RandomItem wire = new RandomItemWire("itemwire");
	
	public static RandomItem zincDust = new RandomItemDust("zinc");
	public static RandomItem tungstenDust = new RandomItemDust("tungsten");
	public static RandomItem ferrousDust = new RandomItemDust("ferrous");
	
	public static RandomItem coil = new RandomItem("coil");
	
	public static RandomItem osmiumDust = new RandomItemDust("osmium");
	public static RandomItem yelloriteDust = new RandomItemDust("yellorite");
	
	public static void loadItems()
	{
		System.out.println("[RandomAdditions] Loaded RandomItems");
	}
	
	public static void startRegistry()
	{
		for (int i = 0; i < items.size(); i++) {
			items.get(i).onRegister();
		}
		GameRegistry.addRecipe(new ShapedOreRecipe(Items.bucket, new Object[]
				{
				"IOI", "OIO", Character.valueOf('I'), "ingotTin"
				}));
	}
	
	public static RandomItem getItemByName(String name)
	{
		for (int i = 0; i < items.size(); i++) {
			if(items.get(i).name.equals(name))
				return items.get(i);
		}
		return null;
	}
	
	public ItemStack getItemStack(int stackSize)
	{
		ItemStack stack = getItemStack();
		stack.stackSize = stackSize;
		return stack;
	}
	
	public ItemStack getItemStack()
	{
		return getItemStack(this);
	}
	
	public static ItemStack getItemStack(RandomItem item)
	{
		return new ItemStack(RandomAdditions.items, 1, item.id);
	}
	
	public static RandomItem getRandomItem(int damage)
	{
		if(damage > 0 && damage < items.size())
			return items.get(damage);
		return items.get(0);
	}
	
	public static RandomItem getRandomItem(ItemStack stack)
	{
		if(stack == null)
			return null;
		int damage = stack.getItemDamage();
		if(isRandomItem(stack))
			return getRandomItem(damage);
		return items.get(0);
	}
	
	public static boolean isRandomItem(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemCoreRandom;
	}
	
	public String name;
	public final int id;
	public IIcon[] icons;
	
	public RandomItem(String name)
	{
		this.id = items.size();
		items.add(this);
		this.name = name;
	}
	
	public boolean isItem(ItemStack stack)
	{
		return isRandomItem(stack) && stack.getItemDamage() == id;
	}
	
	public IIcon getIcon(ItemStack stack)
	{
		return icons[0];
	}
	
	public void registerIcon(IIconRegister iconRegister)
	{
		icons = new IIcon[]{iconRegister.registerIcon(RandomAdditions.modid + ":" + name)};
	}
	
	public void onRegister()
	{
		
	}
	
	public int getItemStackLimit(ItemStack stack)
    {
        return 64;
    }
	
	public int getMaxDamage(ItemStack stack)
    {
        return 0;
    }
	
	public boolean showDurabilityBar(ItemStack stack)
    {
        return false;
    }
	
	public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1;
    }
	
	public ArrayList<ItemStack> getSubItems(Item item)
	{
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		items.add(getItemStack());
		return items;
	}
	
	public String getUnlocalizedName(String name, ItemStack par1ItemStack)
	{
		return name + "." + this.name;
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_){}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        return false;
    }
}
