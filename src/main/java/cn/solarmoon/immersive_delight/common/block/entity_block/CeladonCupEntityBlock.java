package cn.solarmoon.immersive_delight.common.block.entity_block;

import cn.solarmoon.immersive_delight.common.block.base.entity_block.AbstractCupEntityBlock;
import cn.solarmoon.immersive_delight.common.registry.IMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * 青瓷杯方块
 */
public class CeladonCupEntityBlock extends AbstractCupEntityBlock {

    public CeladonCupEntityBlock() {
        super(Block.Properties.of()
                .sound(SoundType.GLASS)
        );
    }

    /**
     * 碰撞箱
     */
    protected static final VoxelShape[] SHAPE = new VoxelShape[]{
            Block.box(6.5D, 0.0D, 6.5D, 9.5D, 0.5D, 9.5D),
            Block.box(6.0D, 0.5D, 6.0D, 10.0D, 4.5D, 10.0D)
    };
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return Shapes.or(SHAPE[0], SHAPE[1]);
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return IMBlockEntities.LITTLE_CUP.get();
    }
}
