package com.infinity.world.portal;

import java.util.Random;

import javax.annotation.Nullable;

import com.infinity.common.Level.Dimension;
import com.infinity.common.events.tegg;
import com.infinity.common.lists.BlockList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Direction.Axis;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WeirdPortalBlock extends Block {
	public static final EnumProperty<Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
	protected static final VoxelShape X_AABB = Block.box(0.0, 0.0, 6.0, 16.0, 16.0, 10.0);
	protected static final VoxelShape Z_AABB = Block.box(6.0, 0.0, 0.0, 10.0, 16.0, 16.0);
    protected BlockPos portalEntrancePos;

	public WeirdPortalBlock(AbstractBlock.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Axis.X));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		switch (state.getValue(AXIS)) {
			case Z:
				return Z_AABB;
			case X:
			default:
				return X_AABB;
		}
	}

	@Override
	@Deprecated
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		Axis directionAxis = facing.getAxis();
		Axis stateAxis = stateIn.getValue(AXIS);
		boolean flag = stateAxis != directionAxis && directionAxis.isHorizontal();
		return (!flag && facingState.getBlock() != this && !(new WeirdPortalBlock.Size(worldIn, currentPos, stateAxis)).canCreatePortal()) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	  public void entityInside(BlockState p_196262_1_, World p_196262_2_, BlockPos p_196262_3_, Entity p_196262_4_) {
	      if (!p_196262_4_.isPassenger() && !p_196262_4_.isVehicle() && p_196262_4_.canChangeDimensions()) {
	         p_196262_4_.handleInsidePortal(p_196262_3_);
	      }

	   }
	public boolean trySpawnPortal(IWorld worldIn, BlockPos pos) {
		WeirdPortalBlock.Size aetherPortalSize = this.isPortal(worldIn, pos);
		if (aetherPortalSize != null) {
			aetherPortalSize.placePortalBlocks();
			return true;
		} else {
			return false;
		}
	}

	private void handleTeleportation(Entity entity) {
		World serverworld = entity.level;
		if (serverworld != null) {
			MinecraftServer minecraftserver = serverworld.getServer();
			RegistryKey<World> where2go = entity.level.dimension() == Dimension.WEIRD_DIMENSION_WORLD ? World.OVERWORLD : Dimension.WEIRD_DIMENSION_WORLD;
			if (minecraftserver != null) {
				ServerWorld destination = minecraftserver.getLevel(where2go);
				if (destination != null && minecraftserver.isNetherEnabled() && !entity.isPassenger()) {
					entity.level.getProfiler().push("aether_portal");
					entity.setPortalCooldown();
					entity.changeDimension(destination, new Teleporter(destination));
					entity.level.getProfiler().pop();
				}
			}
		}
	}
	
	@Override
	@OnlyIn(value = Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (rand.nextInt(100) == 0) {
			worldIn.playLocalSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i) {
			double x = pos.getX() + rand.nextDouble();
			double y = pos.getY() + rand.nextDouble();
			double z = pos.getZ() + rand.nextDouble();
			double sX = (rand.nextFloat() - 0.5) * 0.5;
			double sY = (rand.nextFloat() - 0.5) * 0.5;
			double sZ = (rand.nextFloat() - 0.5) * 0.5;
			int mul = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
				x = pos.getX() + 0.5 + 0.25 * mul;
				sX = rand.nextFloat() * 2.0 * mul;
			}
			else {
				z = pos.getZ() + 0.5 + 0.25 * mul;
				sZ = rand.nextFloat() * 2.0 * mul;
			}

			worldIn.addParticle(ParticleTypes.DRIPPING_WATER, x, y, z, sX, sY, sZ);
		}
	}
	
	@Override
	public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}
	
	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
			case COUNTERCLOCKWISE_90:
			case CLOCKWISE_90:
				switch (state.getValue(AXIS)) {
					case Z:
						return state.setValue(AXIS, Axis.X);
					case X:
						return state.setValue(AXIS, Axis.Z);
					default:
						return state;
				}
			default:
				return state;
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(AXIS);
	}


	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
		BlockPos pos = event.getPos();
		World world = (World) event.getWorld();
		BlockState blockstate = world.getBlockState(pos);
		FluidState fluidstate = world.getFluidState(pos);
		if (fluidstate.getType() == Fluids.WATER && !blockstate.isAir(world, pos)) {
			if (world.dimension() == World.OVERWORLD || world.dimension() == Dimension.WEIRD_DIMENSION_WORLD) {
				boolean tryPortal = false;
				for (Direction direction : Direction.values()) {
					if (world.getBlockState(pos.relative(direction)).getBlock().is(Blocks.END_STONE)) {
						if (BlockList.INFPORTAL.get().isPortal(world, pos) != null) {
							tryPortal = true;
							break;
						}
					}
				}
				if (tryPortal) {
					if (BlockList.INFPORTAL.get().trySpawnPortal(world, pos)) {
						event.setCanceled(true);
					}
				}
			}
		}
	}

	private static boolean fillPortalBlocks(World world, BlockPos pos, PlayerEntity player, Hand hand, ItemStack stack) {
		if (world.dimension() == World.OVERWORLD || world.dimension() == Dimension.WEIRD_DIMENSION_WORLD) {
			boolean tryPortal = false;
			for (Direction direction : Direction.values()) {
				if (world.getBlockState(pos.relative(direction)).getBlock().is(Blocks.END_STONE)) {
					if (BlockList.INFPORTAL.get().isPortal(world, pos) != null) {
						tryPortal = true;
						break;
					}
				}
			}
			if (tryPortal) {
				if (BlockList.INFPORTAL.get().trySpawnPortal(world, pos)) {
					player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
					player.swing(hand);
					if (!player.isCreative()) {
						if (stack.getCount() > 1) {
							stack.shrink(1);
							player.addItem(stack.hasContainerItem() ? stack.getContainerItem() : ItemStack.EMPTY);
						} else if (stack.isDamageableItem()) {
							stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
						} else {
							player.setItemInHand(hand, stack.hasContainerItem() ? stack.getContainerItem() : ItemStack.EMPTY);
						}
					}
					return true;
				}
			}
		}
		return false;
	}

	public static boolean fillPortalBlocksWithoutContext(World world, BlockPos pos, ItemStack stack) {
		if (world.dimension() == World.OVERWORLD || world.dimension() == Dimension.WEIRD_DIMENSION_WORLD) {
			boolean tryPortal = false;
			for (Direction direction : Direction.values()) {
				if (world.getBlockState(pos.relative(direction)).getBlock().is(Blocks.END_STONE)) {
					if (BlockList.INFPORTAL.get().isPortal(world, pos) != null) {
						tryPortal = true;
						break;
					}
				}
			}
			if (tryPortal) {
				if (BlockList.INFPORTAL.get().trySpawnPortal(world, pos)) {
					if (stack.isDamageableItem()) {
						int damage = stack.getDamageValue();
						stack.setDamageValue(damage + 1);
						if (stack.getDamageValue() >= stack.getMaxDamage()) {
							stack.shrink(1);
						}
					} else {
						stack.shrink(1);
					}
					return true;
				}
			}
		}
		return false;
	}

	@Nullable
	public WeirdPortalBlock.Size isPortal(IWorld world, BlockPos pos) {
		WeirdPortalBlock.Size aetherPortalSizeX = new WeirdPortalBlock.Size(world, pos, Axis.X);
		if (aetherPortalSizeX.isValid() && aetherPortalSizeX.portalBlockCount == 0) {
			return aetherPortalSizeX;
		}
		else {
			WeirdPortalBlock.Size aetherPortalSizeZ = new WeirdPortalBlock.Size(world, pos, Axis.Z);
			return aetherPortalSizeZ.isValid() && aetherPortalSizeZ.portalBlockCount == 0? aetherPortalSizeZ : null;
		}
	}

	public static class Size {
		protected final IWorld world;
		public final Direction.Axis axis;
		public final Direction rightDir;
		public final Direction leftDir;
		public int portalBlockCount;
		@Nullable
		public BlockPos bottomLeft;
		public int height;
		public int width;

		public Size(IWorld worldIn, BlockPos pos, Direction.Axis axisIn) {
			this.world = worldIn;
			this.axis = axisIn;
			if (axisIn == Direction.Axis.X) {
				this.leftDir = Direction.EAST;
				this.rightDir = Direction.WEST;
			}
			else {
				this.leftDir = Direction.NORTH;
				this.rightDir = Direction.SOUTH;
			}

			for (BlockPos blockpos = pos; pos.getY() > blockpos.getY() - 21 && pos.getY() > 0
				&& this.isEmptyBlock(worldIn.getBlockState(pos.below())); pos = pos.below()) {
				;
			}

			int i = this.getDistanceUntilEdge(pos, this.leftDir) - 1;
			if (i >= 0) {
				this.bottomLeft = pos.relative(this.leftDir, i);
				this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);
				if (this.width < 2 || this.width > 21) {
					this.bottomLeft = null;
					this.width = 0;
				}
			}

			if (this.bottomLeft != null) {
				this.height = this.calculatePortalHeight();
			}

		}

		protected int getDistanceUntilEdge(BlockPos pos, Direction directionIn) {
			int i;
			for (i = 0; i < 22; ++i) {
				BlockPos blockpos = pos.relative(directionIn, i);
				if (!this.isEmptyBlock(this.world.getBlockState(blockpos))
					|| !(this.world.getBlockState(blockpos.below()).getBlock().is(Blocks.END_STONE))) {
					break;
				}
			}

			BlockPos framePos = pos.relative(directionIn, i);
			return this.world.getBlockState(framePos).getBlock().is(Blocks.END_STONE) ? i : 0;
		}

		public int getHeight() {
			return this.height;
		}

		public int getWidth() {
			return this.width;
		}

		protected int calculatePortalHeight() {
			outerloop:
			for (this.height = 0; this.height < 21; ++this.height) {
				for (int i = 0; i < this.width; ++i) {
					BlockPos blockpos = this.bottomLeft.relative(this.rightDir, i).above(this.height);
					BlockState blockstate = this.world.getBlockState(blockpos);
					if (!this.isEmptyBlock(blockstate)) {
						break outerloop;
					}

					Block block = blockstate.getBlock();
					if (block == (BlockList.INFPORTAL.get())) {
						++this.portalBlockCount;
					}

					if (i == 0) {
						BlockPos framePos = blockpos.relative(this.leftDir);
						if (!(this.world.getBlockState(framePos).getBlock().is(Blocks.END_STONE))) {
							break outerloop;
						}
					}
					else if (i == this.width - 1) {
						BlockPos framePos = blockpos.relative(this.rightDir);
						if (!(this.world.getBlockState(framePos).getBlock().is(Blocks.END_STONE))) {
							break outerloop;
						}
					}
				}
			}

			for (int j = 0; j < this.width; ++j) {
				BlockPos framePos = this.bottomLeft.relative(this.rightDir, j).above(this.height);
				if (!(this.world.getBlockState(framePos).getBlock().is(Blocks.END_STONE))) {
					this.height = 0;
					break;
				}
			}

			if (this.height <= 21 && this.height >= 3) {
				return this.height;
			}
			else {
				this.bottomLeft = null;
				this.width = 0;
				this.height = 0;
				return 0;
			}
		}

		@SuppressWarnings("deprecation")
		protected boolean isEmptyBlock(BlockState pos) {
			Block block = pos.getBlock();
			return pos.isAir() || block == Blocks.WATER || block == BlockList.INFPORTAL.get();
		}

		public boolean isValid() {
			return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}

		public void placePortalBlocks() {
			for (int i = 0; i < this.width; ++i) {
				BlockPos blockpos = this.bottomLeft.relative(this.rightDir, i);

				for (int j = 0; j < this.height; ++j) {
					if (this.world instanceof World) {
						World world = (World) this.world;
						world.setBlockAndUpdate(blockpos.above(j), BlockList.INFPORTAL.get().defaultBlockState().setValue(WeirdPortalBlock.AXIS, this.axis));
					}
				}
			}

		}

		private boolean isLargeEnough() {
			return this.portalBlockCount >= this.width * this.height;
		}

		public boolean canCreatePortal() {
			return this.isValid() && this.isLargeEnough();
		}
	}
}