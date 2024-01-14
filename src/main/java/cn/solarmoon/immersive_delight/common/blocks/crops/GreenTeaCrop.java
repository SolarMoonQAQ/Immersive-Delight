package cn.solarmoon.immersive_delight.common.blocks.crops;

import cn.solarmoon.immersive_delight.common.IMItems;
import cn.solarmoon.immersive_delight.api.common.block.crop.BaseBashTeaCrop;
import net.minecraft.world.item.Item;

public class GreenTeaCrop extends BaseBashTeaCrop {

    public GreenTeaCrop() {

    }

    /**
     * 产物为绿茶叶
     */
    @Override
    public Item getHarvestItem() {
        return IMItems.GREEN_TEA_LEAVES.get();
    }

}
