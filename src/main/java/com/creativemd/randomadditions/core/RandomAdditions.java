package com.creativemd.randomadditions.core;

import java.util.ArrayList;
import java.util.EnumMap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.creativecore.common.packet.CreativeCorePacket;
import com.creativemd.randomadditions.client.RandomTab;
import com.creativemd.randomadditions.common.entity.EntityRandomArrow;
import com.creativemd.randomadditions.common.entity.EntityThrow;
import com.creativemd.randomadditions.common.event.EventHandlerRandom;
import com.creativemd.randomadditions.common.item.ItemCoreRandom;
import com.creativemd.randomadditions.common.item.ItemRandom;
import com.creativemd.randomadditions.common.item.ItemRandomArmor;
import com.creativemd.randomadditions.common.item.ItemTool;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentAutoSmelt;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentCharge;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentEnderman;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentFlame;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentFortune;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentIgnoreArmor;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentInfinity;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentKnockback;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentLooting;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentPosion;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentPower;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentSharpness;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentSilkTouch;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentSpeed;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentTorch;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentUnbreaking;
import com.creativemd.randomadditions.common.item.enchantment.EnchantmentWater;
import com.creativemd.randomadditions.common.item.enchantment.armor.EnchantmentFireResistance;
import com.creativemd.randomadditions.common.item.items.RandomItem;
import com.creativemd.randomadditions.common.packet.RedstonePacket;
import com.creativemd.randomadditions.common.subsystem.SubBlockSystem;
import com.creativemd.randomadditions.common.systems.assembly.SubSystemAssembly;
import com.creativemd.randomadditions.common.systems.battery.SubSystemBattery;
import com.creativemd.randomadditions.common.systems.cable.SubSystemCable;
import com.creativemd.randomadditions.common.systems.cmachine.SubSystemCMachine;
import com.creativemd.randomadditions.common.systems.deco.SubSystemDeco;
import com.creativemd.randomadditions.common.systems.enchant.SubSystemEnchant;
import com.creativemd.randomadditions.common.systems.ic2.SubBlockIC2;
import com.creativemd.randomadditions.common.systems.ic2.SubSystemIC2;
import com.creativemd.randomadditions.common.systems.littletiles.SubSystemLittle;
import com.creativemd.randomadditions.common.systems.machine.SubSystemMachine;
import com.creativemd.randomadditions.common.systems.machine.config.RAConfigLoader;
import com.creativemd.randomadditions.common.systems.ore.SubSystemOre;
import com.creativemd.randomadditions.common.systems.producer.SubBlockProducer;
import com.creativemd.randomadditions.common.systems.producer.SubSystemProducer;
import com.creativemd.randomadditions.common.systems.producer.blocks.HeatGenerator;
import com.creativemd.randomadditions.common.systems.producer.blocks.Mill;
import com.creativemd.randomadditions.common.systems.producer.blocks.Watermill;
import com.creativemd.randomadditions.common.systems.producer.tileentity.TileEntityHeatGenerator;
import com.creativemd.randomadditions.common.systems.rf.SubSystemRF;
import com.creativemd.randomadditions.common.world.WorldGenerator;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = RandomAdditions.modid, version = RandomAdditions.version, name = "RandomAdditions")
public class RandomAdditions {
	
	public static final String modid = "randomadditions";
	public static final String version = "0.11.21";
	
	@Instance(RandomAdditions.modid)
	public static RandomAdditions instance = new RandomAdditions();
	
	public static Configuration config;
	
	public static CreativeTabs tab = new RandomTab("RandomAdditions");
	
	public static ArrayList<CraftMaterial> materials = new ArrayList<CraftMaterial>();
	
	@SidedProxy(clientSide = "com.creativemd.randomadditions.core.RandomAdditionsClient", serverSide = "com.creativemd.randomadditions.core.RandomAdditionsServer")
	public static RandomAdditionsServer proxy;
	
	//Materials
	public static CraftMaterial bronze;
	public static CraftMaterial copper;
	public static CraftMaterial tin;
	public static CraftMaterial ruby;
	public static CraftMaterial tourmaline;
	public static CraftMaterial emerald;
	public static CraftMaterial obsidian;
	public static CraftMaterial lapis;
	
	@SideOnly(Side.CLIENT)
	public static IIcon[] gears;
	
	//Items
	public static Item itemIngot = new ItemRandom().setUnlocalizedName("ItemRAIngot").setCreativeTab(tab);
	public static Item tools = new ItemTool().setUnlocalizedName("RATools").setCreativeTab(tab);
	
	public static Item items = new ItemCoreRandom().setUnlocalizedName("ItemRA").setCreativeTab(tab);
	
	@EventHandler
    public void PreInit(FMLPreInitializationEvent event)
    {
		config = new Configuration(event.getSuggestedConfigurationFile());
		SubSystemIC2.converter = config.getFloat("PowerConverter", "IC2", 1, 0, 1000000, "EU = RA * PowerConverter");
		SubSystemRF.converter = config.getFloat("PowerConverter", "RF", 4F, 0, 1000000, "RF = RA * PowerConverter");
		HeatGenerator.fuelGeneration = config.getFloat("FuelGeneration", "MachineConfig", 0.25F, 0, 100000, "Fuel/FuelGeneration; example: Coal=1600");
		TileEntityHeatGenerator.producePerTick = config.getInt("FuelGeneration", "producePerTick", 60, 0, 100, "Use of one heat producer (one core) per Tick");
		Watermill.Generation = config.getFloat("WatermillGeneration", "MachineConfig", 6, 0, 100000, "(All Waterblocks below the center)/Generation");
		Watermill.speed = config.getFloat("WatermillSpeed", "MachineConfig", 1, 0, 100000, "normalspeed*configuratedspeed");
		Mill.Generation = config.getFloat("MillGeneration", "MachineConfig", 2, 0, 100000, "(arms * length * 4)/Generation");
		Mill.speed = config.getFloat("MillSpeed", "MachineConfig", 1, 0, 100000, "normalspeed*configuratedspeed");
		WorldGenerator.generateCopper = config.getBoolean("GenerateCopper", "WorldGeneration", true, "");
		WorldGenerator.generateTin = config.getBoolean("GenerateTin", "WorldGeneration", true, "");
		WorldGenerator.generateRuby = config.getBoolean("GenerateRuby", "WorldGeneration", true, "");
		WorldGenerator.generateTourmaline = config.getBoolean("GenerateTourmaline", "WorldGeneration", true, "");
		config.save();
    }
	@EventHandler
    public void Init(FMLInitializationEvent event)
    {
		GameRegistry.registerItem(itemIngot, itemIngot.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(tools, tools.getUnlocalizedName().substring(5));
		GameRegistry.registerItem(items, items.getUnlocalizedName().substring(5));
		
		bronze = new CraftMaterial("bronze", 0, 1000, 9, 6, 20, 3, 6, 5, 2).setOres("", "ingotBronze").setAllInternalModifier(new EnchantmentUnbreaking());
		copper = new CraftMaterial("copper", 3, 600, 6.5, 5, 10, 2, 4, 4, 2).setOres("oreCopper", "ingotCopper").setSwordModifier(new EnchantmentPosion()).setHarvestLevel(2).setBowModifier(new EnchantmentInfinity());
		tin = new CraftMaterial("tin", 3, 500, 6, 4, 10, 2, 4, 3, 1).setOres("oreTin", "ingotTin").setToolModifier(new EnchantmentTorch()).setSwordModifier(new EnchantmentEnderman()).setHarvestLevel(2);
		ruby = new CraftMaterial("ruby", 2, 900, 8, 7, 25, 3, 7, 5, 3).setOres("oreRuby", "gemRuby").setChestplateModifier(new EnchantmentFireResistance()).setToolModifier(new EnchantmentAutoSmelt()).setSwordModifier(new EnchantmentFlame()).setDropItem().setBowModifier(new EnchantmentFlame());
		tourmaline = new CraftMaterial("tourmaline", 1, 3000, 10, 8, 35, 3, 8, 6, 3).setOres("oreTourmaline", "gemTourmaline").setToolModifier(new EnchantmentFortune()).setSwordModifier(new EnchantmentLooting()).setDropItem().setHarvestLevel(3).setBowModifier(new EnchantmentLooting());
		emerald = new CraftMaterial("emerald", 0, 2700, 10, 8, 35, 3, 8, 6, 3).setOres("", "gemEmerald").setCustomIngot("gemEmerald").setToolModifier(new EnchantmentSilkTouch()).setSwordModifier(new EnchantmentIgnoreArmor()).setBowModifier(new EnchantmentPower());
		obsidian = new CraftMaterial("obsidian", 0, 5000, 7.5, 6, 25, 3, 7, 5, 2).setOres("", "ingotObsidian").setToolModifier(new EnchantmentSpeed()).setSwordModifier(new EnchantmentSharpness()).setBowModifier(new EnchantmentCharge());
		lapis = new CraftMaterial("lapis", 0, 1000, 7.5, 6, 20, 3, 6, 4, 2).setOres("", "blockLapis").setCustomIngot("blockLapis").setToolModifier(new EnchantmentWater()).setSwordModifier(new EnchantmentKnockback()).setBowModifier(new EnchantmentKnockback());
		
		ItemRandomArmor.registerArmor();
		
		if(!WorldGenerator.generateCopper)
			copper.generateOre = -1;
		if(!WorldGenerator.generateTin)
			tin.generateOre = -1;
		if(!WorldGenerator.generateRuby)
			ruby.generateOre = -1;
		if(!WorldGenerator.generateTourmaline)
			tourmaline.generateOre = -1;
		
		SubBlockSystem.registerSystem(new SubSystemOre());
		SubBlockSystem.registerSystem(new SubSystemMachine());
		SubBlockSystem.registerSystem(new SubSystemBattery());
		SubBlockSystem.registerSystem(new SubSystemProducer());
		SubBlockSystem.registerSystem(new SubSystemCable());	
		SubBlockSystem.registerSystem(new SubSystemEnchant());
		SubBlockSystem.registerSystem(new SubSystemDeco());
		SubBlockSystem.registerSystem(new SubSystemCMachine());
		
		if(Loader.isModLoaded("CoFHCore"))
			SubBlockSystem.registerSystem(new SubSystemRF());
		if(Loader.isModLoaded("IC2"))
			SubBlockSystem.registerSystem(new SubSystemIC2());
		
		if(Loader.isModLoaded("littletiles"))
			SubBlockSystem.registerSystem(new SubSystemLittle());
		
		if(Loader.isModLoaded("ingameconfigmanager"))
			RAConfigLoader.loadConfig();
		
		if(Loader.isModLoaded("handcraft"))
			SubBlockSystem.registerSystem(new SubSystemAssembly());
		
		proxy.loadSide();
		
		CraftMaterial.load();
		
		MinecraftForge.EVENT_BUS.register(new EventHandlerRandom());
		FMLCommonHandler.instance().bus().register(new EventHandlerRandom());
				
		//if(Loader.isModLoaded("ForgeMultipart"))
			//new RegisterBlockPart().init();
		
		OreDictionary.registerOre("ingotBronze", new ItemStack(itemIngot, 1, 0));
		OreDictionary.registerOre("ingotCopper", new ItemStack(itemIngot, 1, 1));
		OreDictionary.registerOre("ingotTin", new ItemStack(itemIngot, 1, 2));
		OreDictionary.registerOre("gemRuby", new ItemStack(itemIngot, 1, 3));
		OreDictionary.registerOre("gemTourmaline", new ItemStack(itemIngot, 1, 4));
		OreDictionary.registerOre("ingotObsidian", new ItemStack(itemIngot, 1, 5));
		
		OreDictionary.registerOre("oreRuby", new ItemStack(SubSystemOre.instance.block, 1, 0));
		OreDictionary.registerOre("oreCopper", new ItemStack(SubSystemOre.instance.block, 1, 1));
		OreDictionary.registerOre("oreTin", new ItemStack(SubSystemOre.instance.block, 1, 2));
		OreDictionary.registerOre("oreTourmaline", new ItemStack(SubSystemOre.instance.block, 1, 3));
		ArrayList<ItemStack> coals = OreDictionary.getOres("gemCoal");
		boolean found = false;
		for (int i = 0; i < coals.size(); i++) {
			if(coals.get(i).equals(Items.coal) || coals.get(i).equals(new ItemStack(Items.coal, 1, 0)))
				found = true;
		}
		if(!found)
			OreDictionary.registerOre("gemCoal", Items.coal);
		
		GameRegistry.addRecipe(new ShapedOreRecipe(RandomAdditions.obsidian.getIngot(), new Object[]
				{
				"OOO", "OOO", "OOO", 'O', Blocks.obsidian
				}));
		
		ItemStack bronze = RandomAdditions.bronze.getIngot();
		bronze.stackSize = 4;
		GameRegistry.addRecipe(new ShapedOreRecipe(bronze, new Object[]
				{
				"IO", "OO", Character.valueOf('I'), "ingotTin", Character.valueOf('O'), "ingotCopper"
				}));
		
		bronze = RandomItem.bronzeDust.getItemStack();
		bronze.stackSize = 4;
		GameRegistry.addRecipe(new ShapedOreRecipe(bronze, new Object[]
				{
				"IO", "OO", Character.valueOf('I'), "dustTin", Character.valueOf('O'), "dustCopper"
				}));
		
		EntityRegistry.registerModEntity(EntityThrow.class, "Throw", 0, this, 250, 250, true);
		EntityRegistry.registerModEntity(EntityRandomArrow.class, "RAArrow", 1, this, 250, 250, true);
		
		
		RandomItem.startRegistry();
		
		SubBlockSystem.raiseOnRegisterEvent();
		
		ItemTool.registerRecipes();
		ItemRandomArmor.registerRecipes();
		
		GameRegistry.registerWorldGenerator(new WorldGenerator(), 1);
		
		CreativeCorePacket.registerPacket(RedstonePacket.class, "RedstonePacket");
		
		FMLInterModComms.sendMessage("Waila", "register", "com.creativemd.randomadditions.common.subsystem.waila.WailaHandler.callbackRegister");
    }
}
