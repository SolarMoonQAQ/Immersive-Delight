package cn.solarmoon.immersive_delight.common.entity_blocks;

import cn.solarmoon.immersive_delight.common.registry.IMEntityBlocks;
import cn.solarmoon.immersive_delight.api.common.entity_block.specific.AbstractKettleEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * 水壶方块
 */
public class KettleEntityBlock extends AbstractKettleEntityBlock {

    public KettleEntityBlock() {
        super(Block.Properties.of()
                .sound(SoundType.LANTERN)
                .strength(1.0f, 6.0F)
                .mapColor(MapColor.METAL)
        );
    }

    /**
     * 碰撞箱
     */
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D);
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
        return IMEntityBlocks.KETTLE_ENTITY.get();
    }
}
