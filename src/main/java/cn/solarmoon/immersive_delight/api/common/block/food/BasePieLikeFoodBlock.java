package cn.solarmoon.immersive_delight.api.common.block.food;

import cn.solarmoon.immersive_delight.api.common.block.BaseWaterBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class BasePieLikeFoodBlock extends BaseWaterBlock {

    public static final IntegerProperty REMAIN = IntegerProperty.create("remain", 0, 10);

    private final Block blockLeft;
    private final int count;

    /**
     * @param count 能吃的次数（分割形态数）
     */
    public BasePieLikeFoodBlock(int count, Properties properties) {
        super(properties);
        this.count = count;
        this.blockLeft = Blocks.AIR;
        this.registerDefaultState(this.getStateDefinition().any().setValue(REMAIN, count).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    /**
     * @param blockLeft 吃完后剩下的方块
     * @param count 能吃的次数（分割形态数）
     */
    public BasePieLikeFoodBlock(Block blockLeft, int count, Properties properties) {
        super(properties);
        this.count = count;
        this.blockLeft = blockLeft;
        this.registerDefaultState(this.getStateDefinition().any().setValue(REMAIN, count).setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (player.canEat(false)) {

            int remain = state.getValue(getRemain());
            if (remain > 0) {
                BlockState targetState = state.setValue(getRemain(), remain - 1);
                level.setBlock(pos, targetState, 3);
                player.eat(level, this.asItem().getDefaultInstance());

                if (targetState.getValue(getRemain()) == 0) {
                    level.setBlock(pos, blockLeft.defaultBlockState(), 3);
                }

                return InteractionResult.SUCCESS;
            }

        }
        return InteractionResult.PASS;
    }

    public IntegerProperty getRemain() {return REMAIN;}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(REMAIN);
    }


}
