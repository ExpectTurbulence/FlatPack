package turbulence.flatpack.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import turbulence.flatpack.Registration;

public class Window extends Block {

    final public static String registryName = "window";

    public static final BooleanProperty IS_OPEN = BooleanProperty.create("is_open");
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty IS_BELOW = BooleanProperty.create("is_below");
    public static final BooleanProperty IS_ABOVE = BooleanProperty.create("is_above");
    public static final BooleanProperty IS_VALID_LOCATION = BooleanProperty.create("is_valid_location");

    public Window() {
        super(Properties.create(Material.GLASS)
            .sound(SoundType.GLASS)
            .hardnessAndResistance(1.0f)
        );
        setRegistryName(registryName);
        this.setDefaultState(this.stateContainer.getBaseState()
            .with(IS_ABOVE, false)
            .with(IS_BELOW, false)
            .with(IS_OPEN, false)
            .with(IS_VALID_LOCATION, true)
        );
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(FACING, IS_OPEN, IS_ABOVE, IS_BELOW, IS_VALID_LOCATION);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing());
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn,
            BlockPos currentPos, BlockPos facingPos) {
        boolean isAbove = stateIn.get(IS_ABOVE);
        boolean isBelow = stateIn.get(IS_BELOW);
        boolean isOpen = stateIn.get(IS_OPEN);

        if (facing == Direction.DOWN)
            isAbove = (facingState.getBlock() == Registration.WINDOW);
        if (facing == Direction.UP)
            isBelow = (facingState.getBlock() == Registration.WINDOW);
        if (facingState.getBlock() == Registration.WINDOW && (facing == Direction.UP || facing == Direction.DOWN))
            isOpen = facingState.get(IS_OPEN);

        if (!stateIn.isValidPosition(worldIn, currentPos))
            worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);

        return stateIn.with(IS_ABOVE, isAbove).with(IS_BELOW, isBelow).with(IS_OPEN, isOpen);
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (!state.isValidPosition(worldIn, pos))
            worldIn.destroyBlock(pos, true);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = worldIn.getBlockState(pos.offset(direction));
            if (blockstate.getBlock() == Registration.WINDOW)
                return false;
        }
        return true;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
            BlockRayTraceResult hit) {
        worldIn.setBlockState(pos, state.with(IS_OPEN, !state.get(IS_OPEN)), 1);
        return true;
    }
}