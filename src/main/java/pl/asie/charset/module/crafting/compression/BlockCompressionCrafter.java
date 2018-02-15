package pl.asie.charset.module.crafting.compression;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import pl.asie.charset.lib.Properties;
import pl.asie.charset.lib.block.BlockBase;

import javax.annotation.Nullable;

public class BlockCompressionCrafter extends BlockBase implements ITileEntityProvider {
	public BlockCompressionCrafter() {
		super(Material.ROCK);
		setUnlocalizedName("charset.compression_crafter");
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, Properties.FACING);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(Properties.FACING, EnumFacing.getFront(meta & 7));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(Properties.FACING).ordinal();
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(Properties.FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileCompressionCrafter();
	}
}