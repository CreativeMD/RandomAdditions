package com.creativemd.randomadditions.common.subsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.SubContainerTileEntity;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.common.utils.RotationUtils;
import com.creativemd.randomadditions.core.RandomAdditions;

import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class SubBlock {
	
	private int id;
	
	public int getID()
	{
		return id;
	}
	
	public String name;
	
	public SubBlockSystem system;
	
	public SubBlock(String name, SubBlockSystem system)
	{
		this.name = name;
		this.system = system;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public ItemStack getItemStack()
	{
		return system.getItemStack(this);
	}
	
	@SideOnly(Side.CLIENT)
	public abstract SubGuiTileEntity getGui(TileEntity tileEntity, EntityPlayer player);
	
	public abstract SubContainerTileEntity getContainer(TileEntity tileEntity, EntityPlayer player);
	
	public abstract TileEntityRandom getTileEntity();
	
	public String getTileEntityName()
	{
		return "RA" + system.name;
	}
	
	public ArrayList<CubeObject> getCubes(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		return getCubes(world, x, y, z);
	}
	
	public ArrayList<CubeObject> getCubes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(new CubeObject());
		return cubes;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons;
	
	@SideOnly(Side.CLIENT)
	public void registerIcon(IIconRegister register)
	{
		if(hasBlockTexture())
			icons = new IIcon[]{register.registerIcon(RandomAdditions.modid + ":" + name)};
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side, int meta)
	{
		return getIcon(side, meta);
	}
	
	public abstract boolean isSolid(TileEntity tileEntity);
	
	public float getHardness()
	{
		return 2.0F;
	}
	
	public float getResistance()
	{
		return 10F;
	}
	
	public int getHarvestLevel()
	{
		return 0;
	}
	
	public boolean isSideSolid(TileEntity tileEntity, ForgeDirection side)
	{
		return isSolid(tileEntity);
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(icons == null || icons[0] == null)
			return ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(Minecraft.getMinecraft().renderEngine.getResourceLocation(1))).getAtlasSprite("missingno");
		return icons[0];
	}
	
	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
    {
        return 3;
    }
	
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random) {}
	
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {}
	
	public void drawRender(TileEntity entity, double x, double y, double z){}
	
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
	{
		
	}
	
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
    
    }
	
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return true;
	}
	
	public void onBlockPlacedEvent(World world, Entity entity, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
		int type = system.getSubBlock(meta).getRotation();
		int index = 0;
		ForgeDirection direction = null;
		if(type == 2 && useSideForRotation())
		{
			direction = ForgeDirection.getOrientation(side).getOpposite();
		}else{
			switch(type)
			{
			case 1:
				index = ((MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4);
				switch(index)
				{
				case 0:
					index = 2;
					break;
				case 1:
					index = 5;
					break;
				case 2:
					index = 3;
					break;
				case 3:
					index = 4;
					break;
				}
				break;
			case 2:
				index = BlockPistonBase.determineOrientation(world, x, y, z, (EntityLivingBase) entity);
				break;
			}
				direction = ForgeDirection.getOrientation(index);
				if(entity instanceof EntityPlayer && entity.isSneaking() && allowShiftRotation())
					direction = direction.getOpposite();
		}
		
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileEntityRandom)
		{
			((TileEntityRandom) tile).direction = (byte) RotationUtils.getIndex(direction);
		}
		
    }
	
	public boolean allowShiftRotation()
	{
		return true;
	}
	
	public void onBlockPlaced(ItemStack stack, TileEntity tileEntity)
	{
		
	}
	
	public void onBlockBreaks(TileEntity tileEntity)
	{
		
	}
	
	public boolean onBlockActivated(EntityPlayer player, ItemStack stack, TileEntity tileEntity)
	{
		return false;
	}
	
	/**Using tileEntity**/
	public ArrayList<ItemStack> getExtraDrop(TileEntity tileEntity)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		if(tileEntity instanceof IInventory)
		{
			int size = ((IInventory) tileEntity).getSizeInventory();
			for (int i = 0; i < size; i++) {
				stacks.add(((IInventory) tileEntity).getStackInSlot(i));
			}
		}
		return stacks;
	}
	
	public ArrayList<ItemStack> getDrop(IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(new ItemStack(system.block, 1, getID()));
		return stacks;
	}
	
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
    {
        return 0;
    }
	
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
		return false;
    }
	
	public ArrayList<CubeObject> getBoxes(IBlockAccess world, int x, int y, int z)
	{
		ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
		cubes.add(getBox(world, x, y, z));
		return cubes;
	}
	
	public CubeObject getBox(IBlockAccess world, int x, int y, int z)
	{
		return new CubeObject();
	}
	
	public CubeObject getSelBox(IBlockAccess world, int x, int y, int z)
	{
		return getBox(world, x, y, z);
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_){}
	
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		return world.setBlockToAir(x, y, z);
    }
	
	public ArrayList<ItemStack> getItemStacks()
	{
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(new ItemStack(system.block, 1, getID()));
		return stacks;
	}
	
	public ForgeDirection getDirection(IBlockAccess world, int x, int y, int z)
	{
		TileEntityRandom tileEntityRandom = getTileEntity(world, x, y, z);
		if(tileEntityRandom != null)
		{
			return ForgeDirection.getOrientation(tileEntityRandom.direction);
		}
		return ForgeDirection.UNKNOWN;
	}
	
	public TileEntityRandom getTileEntity(IBlockAccess world, int x, int y, int z)
	{
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity instanceof TileEntityRandom)
			return (TileEntityRandom) tileEntity;
		return null;
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
		return "tile.BlockRA" + system.name + "." + name; 
    }
	
	public boolean hasBlockTexture()
	{
		return true;
	}
	
	public void onRegister(){}
	
	/**0: none Rotation; 1: (North, South, West, East); 2: All**/
	public int getRotation()
	{
		return 0;
	}
	
	public boolean useSideForRotation()
	{
		return false;
	}
	
	@Method(modid = "Waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}
}
