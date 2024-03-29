package cn.solarmoon.immersive_delight.common.block_entity;

import cn.solarmoon.immersive_delight.common.block_entity.base.AbstractSteamerBlockEntity;
import cn.solarmoon.immersive_delight.common.registry.IMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SteamerBlockEntity extends AbstractSteamerBlockEntity {

    public SteamerBlockEntity(BlockPos pos, BlockState state) {
        super(IMBlockEntities.STEAMER.get(), pos, state);
    }

}
