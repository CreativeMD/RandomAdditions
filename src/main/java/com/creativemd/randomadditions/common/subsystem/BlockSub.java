package com.creativemd.randomadditions.common.subsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.block.BlockRenderHelper;
import com.creativemd.creativecore.client.block.IBlockAccessFake;
import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.container.SubContainer;
import com.creativemd.creativecore.common.gui.IGuiCreator;
import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.creativecore.core.CreativeCore;
import com.creativemd.littletiles.common.blocks.ILittleTile;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.utils.LittleTile;
import com.creativemd.littletiles.common.utils.LittleTilePreview;
import com.creativemd.randomadditions.common.gui.controls.GuiGearInformation;
import com.creativemd.randomadditions.common.redstone.RedstoneControlHelper;
import com.creativemd.randomadditions.common.systems.assembly.SubSystemAssembly;
import com.creativemd.randomadditions.common.systems.machine.blocks.Sawing;
import com.creativemd.randomadditions.core.RandomAdditions;
import com.creativemd.randomadditions.core.RandomAdditionsClient;

import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Interface(modid = "littletiles", iface = "com.creativemd.littletiles.common.blocks.ILittleTile")
public class BlockSub extends BlockContainer implements IGuiCreator, ILittleTile{
	
	public SubBlockSystem system;
	
	protected BlockSub(SubBlockSystem system) {
		super(system.getBlockMaterial());
		setCreativeTab(RandomAdditions.tab);
		setLightOpacity(3);
		this.system = system;
		this.opaque = isOpaqueCube();
	}
	
	@Override
	public int getLightOpacity(IBlockAccess world, int x, int y, int z)
    {
		return system.getSubBlock(world, x, y, z).getLightOpacity(world, x, y, z);
    }
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
    {
        return ((SubBlock)system.getSubBlock(world.getBlockMetadata(x, y, z))).getHardness();
    }
	
	public String getHarvestTool(int metadata)
    {
        return system.getHarvestTool();
    }
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		CubeObject box = system.getSubBlock(world.getBlockMetadata(x, y, z)).getBox(world, x, y, z);
		setBlockBounds((float)box.minX, (float)box.minY, (float)box.minZ, (float)box.maxX, (float)box.maxY, (float)box.maxZ);
	}
	
	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ)
    {
        return ((SubBlock)system.getSubBlock(world.getBlockMetadata(x, y, z))).getResistance();
    }
	
	@Override
	public int getHarvestLevel(int metadata)
    {
        return system.getSubBlock(metadata).getHarvestLevel();
    }
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List boxes, Entity entity)
    {
        ArrayList<CubeObject> cubes = system.getSubBlock(world, x, y, z).getBoxes(world, x, y, z);
        for (int i = 0; i < cubes.size(); i++) {
        	CubeObject box = cubes.get(i);
        	AxisAlignedBB axisalignedbb1 = AxisAlignedBB.getBoundingBox(x+box.minX, y+box.minY, z+box.minZ, x+box.maxX, y+box.maxY, z+box.maxZ);
        	
        	if (axisalignedbb1 != null && axis.intersectsWith(axisalignedbb1))
            {
        		boxes.add(axisalignedbb1);
            }
		}
        
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
		CubeObject box = system.getSubBlock(world.getBlockMetadata(x, y, z)).getBox(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x+box.minX, y+box.minY, z+box.minZ, x+box.maxX, y+box.maxY, z+box.maxZ);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
    {
		CubeObject box = system.getSubBlock(world.getBlockMetadata(x, y, z)).getSelBox(world, x, y, z);
		return AxisAlignedBB.getBoundingBox(x+box.minX, y+box.minY, z+box.minZ, x+box.maxX, y+box.maxY, z+box.maxZ);
    }
	
	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
		system.getSubBlock(par1World.getBlockMetadata(par2, par3, par4)).randomDisplayTick(par1World, par2, par3, par4, par5Random);
    }
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
    {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		SubBlock subBlock = system.getSubBlock(meta);
		ArrayList<ItemStack> stacks = subBlock.getExtraDrop(tileEntity);
		for (int i = 0; i < stacks.size(); i++) {
			if(stacks.get(i) != null)
				dropBlockAsItem(world, x, y, z, stacks.get(i));
		}
		subBlock.onBlockBreaks(tileEntity);
		super.breakBlock(world, x, y, z, block, meta);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
		int meta = world.getBlockMetadata(x, y, z);
        return system.getSubBlock(meta).getIcon(world, x, y, z, side, meta);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		try
		{
			return system.getSubBlock(meta).getIcon(side, meta);
		}catch(Exception e){
			return blockIcon;
		}
    }
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
    {
        return system.getSubBlock(world.getBlockMetadata(x, y, z)).canBlockStay(world, x, y, z);
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
	{
		system.getSubBlock(world.getBlockMetadata(x, y, z)).onBlockPlaced(stack, world.getTileEntity(x, y, z));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType()
    {
        return RandomAdditionsClient.modelID;
    }
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) 
	{
		system.getSubBlock(world.getBlockMetadata(x, y, z)).onNeighborBlockChange(world, x, y, z, block);
	}
	
	@Override
	public void onNeighborChange(IBlockAccess world, int x, int y, int z, int tileX, int tileY, int tileZ)
    {
		system.getSubBlock(world.getBlockMetadata(x, y, z)).onNeighborChange(world, x, y, z, tileX, tileY, tileZ);
    }
	
	public boolean renderInventoryBlock(Block machine, int metadata, int modelId, RenderBlocks renderer) {
		
		ArrayList<CubeObject> cubes = system.getSubBlock(metadata).getCubes(new ItemStack(machine, 1, metadata),null, 0, 0, 0);
		BlockRenderHelper.renderInventoryCubes(renderer, cubes, machine, metadata);
		return true;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		system.getSubBlock(world.getBlockMetadata(x, y, z)).onEntityCollidedWithBlock(world, x, y, z, entity);
	}
	
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		SubBlock subblock = system.getSubBlock(world.getBlockMetadata(x, y, z));
		ArrayList<CubeObject> cubes = subblock.getCubes(null, world, x, y, z);
		BlockRenderHelper.renderCubes(world, cubes, x, y, z, block, renderer, subblock.getRotation() != 0 ? subblock.getDirection(world, x, y, z) : ForgeDirection.EAST);
		/*for (int i = 0; i < cubes.size(); i++) {
			renderer.setRenderBounds(cubes.get(i).minX, cubes.get(i).minY, cubes.get(i).minZ, cubes.get(i).maxX, cubes.get(i).maxY, cubes.get(i).maxZ);
			if(subblock.getRotation() != 0)
				RenderHelper3D.applyBlockRotation(renderer, subblock.getDirection(world, x, y, z));
			if(cubes.get(i).icon != null)
				renderer.setOverrideBlockTexture(cubes.get(i).icon);
			
			if(cubes.get(i).block != null)
				if(cubes.get(i).meta != -1)
				{
					
					RenderHelper3D.renderBlocks.clearOverrideBlockTexture();
					RenderHelper3D.renderBlocks.setRenderBounds(cubes.get(i).minX, cubes.get(i).minY, cubes.get(i).minZ, cubes.get(i).maxX, cubes.get(i).maxY, cubes.get(i).maxZ);
					RenderHelper3D.renderBlocks.meta = cubes.get(i).meta;
					IBlockAccessFake fake = new IBlockAccessFake(renderer.blockAccess);
					RenderHelper3D.renderBlocks.blockAccess = fake;
					fake.overrideMeta = cubes.get(i).meta;
					RenderHelper3D.renderBlocks.renderBlockAllFaces(cubes.get(i).block, x, y, z);
					return true;
				}
				else
					renderer.setOverrideBlockTexture(cubes.get(i).block.getBlockTextureFromSide(0));
			
			renderer.renderStandardBlock(block, x, y, z);
			
			if(cubes.get(i).icon != null || cubes.get(i).block != null)
				renderer.clearOverrideBlockTexture();
		}*/
		return true;
	}
	
	@Override
	public int damageDropped(int meta)
    {
        return meta;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isBlockNormalCube()
    {
		return isNormalCube();
    }
	
	@Override
	public boolean isNormalCube()
    {
		if(system == null)
			return false;
		else
			return system.areBlocksSolid();
    }
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		SubBlock subBlock = system.getSubBlock(world.getBlockMetadata(x, y, z));
		if(subBlock.onBlockActivated(player, player.getHeldItem(), world.getTileEntity(x, y, z)))
			return true;
		SubContainer gui = subBlock.getContainer(world.getTileEntity(x, y, z), player);
		if(gui != null)
		{
			if(!world.isRemote)
			{
				((EntityPlayerMP)player).openGui(CreativeCore.instance, 0, world, x, y, z);
			}
			return true;
		}
		return false;
    }
	
	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
    {
		return system.getSubBlock(world.getBlockMetadata(x, y, z)).isSideSolid(world.getTileEntity(x, y, z), side);
    }
	
	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int meta)
    {
        return system.getSubBlock(meta).isSolid(world.getTileEntity(x, y, z));
    }
	
	@Override
	public boolean isNormalCube(IBlockAccess world, int x, int y, int z)
    {
        return system.getSubBlock(world.getBlockMetadata(x, y, z)).isSolid(world.getTileEntity(x, y, z));
    }
	
	@Override
	public boolean isOpaqueCube()
    {
        return isNormalCube();
    }
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}
	
	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        SubBlock block = system.getSubBlock(metadata);
        ret.addAll(block.getDrop(world, x, y, z, fortune));
        return ret;
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
		RedstoneControlHelper.ignore = register.registerIcon(RandomAdditions.modid + ":custom/signals/ignoresignal");
		RedstoneControlHelper.impulse = register.registerIcon(RandomAdditions.modid + ":custom/signals/impulsesignal");
		RedstoneControlHelper.nosignal = register.registerIcon(RandomAdditions.modid + ":custom/signals/nosignal");
		RedstoneControlHelper.signal = register.registerIcon(RandomAdditions.modid + ":custom/signals/signal");
		RedstoneControlHelper.icons = new IIcon[]{RedstoneControlHelper.ignore, RedstoneControlHelper.signal, RedstoneControlHelper.nosignal, RedstoneControlHelper.impulse};
		Sawing.sawblade = register.registerIcon(RandomAdditions.modid + ":custom/sawblade");
		
		RandomAdditions.gears = new IIcon[5];
		for (int i = 0; i < 5; i++) {
				RandomAdditions.gears[i] = register.registerIcon(RandomAdditions.modid + ":custom/amachine" + (i+1));
		}
		
		this.blockIcon = register.registerIcon(this.getTextureName());
		for (int j = 0; j < system.blocks.size(); j++) {
			((SubBlock) system.blocks.get(j)).registerIcon(register);
		}
    }
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
		for (int j = 0; j < system.blocks.size(); j++) {
			list.addAll(((SubBlock) system.blocks.get(j)).getItemStacks());
		}
    }
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return system.getSubBlock(meta).getTileEntity();
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
    {
        return ((SubBlock) system.getSubBlock(metadata)).getExpDrop(world, metadata, fortune);
    }
	
	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata)
    {
		return system.getSubBlock(metadata).canSilkHarvest(world, player, x, y, z, metadata);
    }
	
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		return system.getSubBlock(world.getBlockMetadata(x, y, z)).removedByPlayer(world, player, x, y, z, willHarvest);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public SubGui getGui(EntityPlayer player, ItemStack stack, World world,
			int x, int y, int z) {
		return system.getSubBlock(world.getBlockMetadata(x, y, z)).getGui(world.getTileEntity(x, y, z), player);
	}

	@Override
	public SubContainer getContainer(EntityPlayer player, ItemStack stack,
			World world, int x, int y, int z) {
		return system.getSubBlock(world.getBlockMetadata(x, y, z)).getContainer(world.getTileEntity(x, y, z), player);
	}

	@Override
	public ArrayList<LittleTilePreview> getLittlePreview(ItemStack stack) {
		SubBlock block = system.getSubBlock(stack.getItemDamage());
		if(block instanceof ILittleTile)
			return ((ILittleTile) block).getLittlePreview(stack);
		return null;
	}

	@Override
	public void rotateLittlePreview(ItemStack stack, ForgeDirection direction) {
		LittleTilePreview.rotatePreview(stack.stackTagCompound, direction);
	}

	@Override
	public LittleStructure getLittleStructure(ItemStack stack) {
		return null;
	}
	
	
}
