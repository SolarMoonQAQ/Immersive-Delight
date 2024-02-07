package cn.solarmoon.immersive_delight.common.entity_block.core;

import cn.solarmoon.immersive_delight.api.common.entity_block.BaseTCEntityBlock;
import cn.solarmoon.immersive_delight.api.common.entity_block.entity.BaseTCBlockEntity;
import cn.solarmoon.immersive_delight.api.common.entity_block.entity.BaseTankBlockEntity;
import cn.solarmoon.immersive_delight.common.item.core.AbstractCupItem;
import cn.solarmoon.immersive_delight.api.util.FluidUtil;
import cn.solarmoon.immersive_delight.api.util.RecipeUtil;
import cn.solarmoon.immersive_delight.common.recipes.CupRecipe;
import cn.solarmoon.immersive_delight.common.registry.IMRecipes;
import cn.solarmoon.immersive_delight.data.fluid_effects.serializer.FluidEffect;
import cn.solarmoon.immersive_delight.data.fluid_foods.serializer.FluidFood;
import cn.solarmoon.immersive_delight.data.tags.IMFluidTags;
import cn.solarmoon.immersive_delight.util.CoreUtil;
import cn.solarmoon.immersive_delight.util.CountingDevice;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.List;
import java.util.Objects;


/**
 * 作为大部分可饮用方块（如各类杯子）的抽象类
 */
public abstract class AbstractCupEntityBlock extends BaseTCEntityBlock {

    protected AbstractCupEntityBlock(Properties properties) {
        super(properties
                .strength(0.5f)
                .noOcclusion()
        );
    }

    /**
     * 使得该类方块能够与液体桶类物品进行液体交互
     * 使得该类方块能够每几下被喝走一定数量的液体
     */
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        BaseTankBlockEntity tankEntity = (BaseTankBlockEntity) blockEntity;
        if(tankEntity == null) return InteractionResult.FAIL;
        FluidTank tank = tankEntity.tank;

        //空手shift+右键快速拿杯子
        if(getThis(player, level, pos, state, hand)) return InteractionResult.SUCCESS;

        //计数装置
        CompoundTag playerTag = CountingDevice.player(player, pos, level);
        if(canEat(tankEntity, player)) {
            CoreUtil.deBug("点击次数：" + CountingDevice.getCount(playerTag), level);
        }

        //自动检测是否是流体容器，进行液体存取
        if(loadFluid(tankEntity, player, level, pos, hand)) return InteractionResult.SUCCESS;

        //存取任意单个物品
        if(storage(tankEntity, player, hand)) {
            level.playSound(null , pos, SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 0.2f, 1f);
            return InteractionResult.SUCCESS;
        }

        /*喝！
        默认每两下喝一下，消耗默认50，小于50则全喝完不触发任何效果
        喝时触发效果：声音、余下同item
         */
        else {
            int tankAmount = tank.getFluidAmount();
            if (tankAmount >= getDrinkVolume(tank.getFluid())) {
                //能吃却不能吃 不让用
                if(!canEat(blockEntity, player)) return InteractionResult.PASS;
                if(CountingDevice.getCount(playerTag) >= getPressCount()) {
                    AbstractCupItem.commonUse(tank.getFluid(), level, player);
                    tank.drain(getDrinkVolume(tank.getFluid()), IFluidHandler.FluidAction.EXECUTE);
                    tankEntity.setChanged();
                    CountingDevice.resetCount(playerTag, -1);
                    CoreUtil.deBug("已有tag：" + blockEntity.getPersistentData(), level);
                    if(!level.isClientSide) level.playSound(null, pos, SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1F, 1F);
                }
                return InteractionResult.SUCCESS;
            } else if (tankAmount > 0) {
                if(CountingDevice.getCount(playerTag) >= getPressCount()) {
                    tank.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE);
                    tankEntity.setChanged();
                    CountingDevice.resetCount(playerTag, -1);
                    if(!level.isClientSide) level.playSound(null, pos, SoundEvents.GENERIC_DRINK, SoundSource.PLAYERS, 1F, 1F);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean pouringSound() { return true; }

    @Override
    public int fluidLoadSetting() { return 1; }

    /**
     * 可放入和配方输入匹配的物品，如果有物品且空手就取出
     * 物品的nbt自然需要保存
     */
    @Override
    public boolean storage(BlockEntity blockEntity, Player player, InteractionHand hand) {
        if(blockEntity instanceof BaseTCBlockEntity cupBlockEntity) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (!heldItem.isEmpty() && cupBlockEntity.inventory.getStackInSlot(0).isEmpty()) {
                List<CupRecipe> recipes = RecipeUtil.getRecipes(player.level(), IMRecipes.CUP_RECIPE.get());
                for (var recipe :recipes) {
                    Ingredient ingredient = recipe.getInputIngredient();
                    if (ingredient.test(heldItem)) {
                        player.setItemInHand(hand, cupBlockEntity.insertItem(heldItem));
                        return true;
                    }
                }
            } else if (!cupBlockEntity.inventory.getStackInSlot(0).isEmpty() && heldItem.isEmpty()) {
                player.setItemInHand(hand, cupBlockEntity.extractItem());
                return true;
            }
        }
        return false;
    }

    /**
     * @return 设置每次喝掉的液体量（默认50）
     */
    public int getDrinkVolume(FluidStack fluidStack) {
        FluidFood fluidFood = FluidFood.getByFluid(fluidStack.getFluid());
        if (fluidFood != null) {
            return fluidFood.fluidAmount;
        }
        return 50;
    }

    /**
     * @return 设置需要右键多少下喝一下（默认2）
     */
    public int getPressCount() {
        return 2 - 1;
    }

    /**
     * 识别容器内的液体是否可被吃，且玩家能吃
     * 当然本身不能吃的话就直接通过
     */
    public boolean canEat(BlockEntity blockEntity, Player player) {
        FluidEffect fluidEffect = FluidEffect.getFluidEffectFromBlock(blockEntity);
        if(fluidEffect == null) return true;
        if(fluidEffect.canAlwaysDrink) return true;
        if(fluidEffect.getFoodValue().isValid()) return player.canEat(false);
        return true;
    }

    /**
     * shift才能放置
     */
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        if(!Objects.requireNonNull(context.getPlayer()).isCrouching()) return null;
        return super.getStateForPlacement(context);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        super.tick(level, pos, state, blockEntity);

        //液体容器动画计时器
        if(blockEntity instanceof BaseTankBlockEntity t) {
            if(t.ticks <= 100) t.ticks++;
            else t.ticks = 0;
        }

        //配方
        if(blockEntity instanceof BaseTCBlockEntity cupBlockEntity) {
            CupRecipe recipe = getCheckedRecipe(level, pos, blockEntity);
            if(recipe != null) {
                cupBlockEntity.recipeTime = recipe.getTime();
                FluidStack fluidStack = FluidUtil.getFluidStack(blockEntity);
                cupBlockEntity.time++;
                if (cupBlockEntity.time > recipe.getTime()) {
                    //选取最小容量输出
                    int amount = Math.min(fluidStack.getAmount(), recipe.getFluidAmount());
                    FluidStack fluidStackOut = new FluidStack(recipe.getOutputFluid(), amount);
                    cupBlockEntity.tank.setFluid(fluidStackOut);
                    cupBlockEntity.time = 0;
                    cupBlockEntity.extractItem();
                    cupBlockEntity.setChanged();
                }
            } else {
                cupBlockEntity.time = 0;
                cupBlockEntity.recipeTime = 0;
            }
        }

    }

    /**
     * 获取匹配的配方（杯中有物品阶段的匹配），不匹配返回null
     */
    public CupRecipe getCheckedRecipe(Level level, BlockPos pos, BlockEntity blockEntity) {
        if(blockEntity instanceof BaseTankBlockEntity tankBlockEntity) {
            FluidStack fluidStack = tankBlockEntity.tank.getFluid();
            ItemStack stackIn = ItemStack.EMPTY;
            if (tankBlockEntity instanceof BaseTCBlockEntity cupBlockEntity) {
                stackIn = cupBlockEntity.inventory.getStackInSlot(0);
            }
            List<CupRecipe> recipes = RecipeUtil.getRecipes(level, IMRecipes.CUP_RECIPE.get());
            for (var recipe : recipes) {
                if (recipe.inputMatches(level, fluidStack, stackIn, pos)) {
                    return recipe;
                }
            }
        }
        return null;
    }

    /**
     * 热气粒子
     */
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof BaseTCBlockEntity cup) {
            Vec3 spoutPos = pos.getCenter();
            FluidStack fluidStack = cup.tank.getFluid();
            if(!fluidStack.isEmpty() && fluidStack.getFluid().defaultFluidState().is(IMFluidTags.WARM_FLUID)) {
                if (random.nextInt(5) == 3)
                    level.addParticle(ParticleTypes.CLOUD, spoutPos.x, spoutPos.y, spoutPos.z, 0, 0.01, 0);
            }
        }
    }

}
