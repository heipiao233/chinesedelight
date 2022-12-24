package net.heipiao.chinesedelight.block;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;

import net.heipiao.chinesedelight.block.entity.ModBlockEntityTypes;
import net.heipiao.chinesedelight.block.entity.WokBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class WokBlock extends BlockWithEntity {
    public static final BooleanProperty SUPPORT = BooleanProperty.of("support");
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
    protected static final VoxelShape SHAPE_WITH_TRAY = VoxelShapes.union(SHAPE,
            Block.createCuboidShape(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

    protected WokBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getStateManager().getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(SUPPORT, false));
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos var1, BlockState var2) {
        return ModBlockEntityTypes.WOK.get().instantiate(var1, var2);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
            BlockEntityType<T> type) {
        return checkType(type, ModBlockEntityTypes.WOK.get(), WokBlockEntity::tick);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(new Property[] { FACING, SUPPORT });
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        return (BlockState) ((BlockState) this.getDefaultState().with(FACING, context.getPlayerFacing())).with(SUPPORT,
                this.getTrayState(context.getWorld(), context.getBlockPos()));
    }

    private boolean getTrayState(WorldAccess worldAccess, BlockPos pos) {
        return worldAccess.getBlockState(pos.down()).isIn(TagsRegistry.TRAY_HEAT_SOURCES);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Boolean.TRUE.equals(state.get(SUPPORT)) ? SHAPE_WITH_TRAY : SHAPE;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockHitResult hit) {
        if (world.isClient) return ActionResult.SUCCESS;
        if (world.getBlockEntity(pos) instanceof WokBlockEntity wokBlockEntity) {
            ItemStack stack = wokBlockEntity.use(player.getStackInHand(hand), world);
            if (!stack.isEmpty()) {
                if (!player.getInventory().insertStack(stack)) {
                    player.dropItem(stack, false);
                }

                world.playSound((PlayerEntity) null, pos, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS,
                        1.0F, 1.0F);
            }
        }
        return ActionResult.SUCCESS;
    }

}
