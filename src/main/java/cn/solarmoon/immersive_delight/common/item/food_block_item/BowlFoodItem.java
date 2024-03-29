package cn.solarmoon.immersive_delight.common.item.food_block_item;

import cn.solarmoon.immersive_delight.ImmersiveDelight;
import cn.solarmoon.immersive_delight.compat.create.util.PotionUtil;
import cn.solarmoon.immersive_delight.data.fluid_effects.serializer.FluidEffect;
import cn.solarmoon.immersive_delight.data.fluid_effects.serializer.PotionEffect;
import cn.solarmoon.immersive_delight.util.FarmerUtil;
import cn.solarmoon.solarmoon_core.util.LevelSummonUtil;
import cn.solarmoon.solarmoon_core.util.TextUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 碗装食物类，可以绑定data的液体效果，也能放置对应方块<br/>
 * 默认16格堆叠<br/>
 * 需要自己给食物属性！
 */
public class BowlFoodItem extends BlockItem {

    public final String fluidBound;

    /**
     * @param fluidBound 绑定的液体对应效果，格式同minecraft:water
     */
    public BowlFoodItem(String fluidBound, Block block, Properties properties) {
        super(block, properties.craftRemainder(Items.BOWL)
                .stacksTo(16)
                .craftRemainder(Items.BOWL));
        this.fluidBound = fluidBound;
    }

    public BowlFoodItem(String fluidBound, Block block, int nutrition, float saturation) {
        super(block, new Properties().food(
                new FoodProperties.Builder()
                        .nutrition(nutrition).saturationMod(saturation).build()
        ).stacksTo(16).craftRemainder(Items.BOWL));
        this.fluidBound = fluidBound;
    }

    public BowlFoodItem(Block block, int nutrition, float saturation) {
        super(block, new Properties().food(
                new FoodProperties.Builder()
                        .nutrition(nutrition).saturationMod(saturation).build()
        ).stacksTo(16).craftRemainder(Items.BOWL));
        this.fluidBound = "empty";
    }

    public BowlFoodItem(Block block, FoodProperties foodProperties) {
        super(block, new Properties().food(foodProperties)
                .stacksTo(16).craftRemainder(Items.BOWL));
        this.fluidBound = "empty";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        applyFluidEffect(level, entity);
        if (entity instanceof Player player && !player.isCreative()) {
            LevelSummonUtil.addItemToInventory(player, new ItemStack(Items.BOWL));
        }
        return super.finishUsingItem(stack, level, entity);
    }

    /**
     * 应用液体效果
     */
    public void applyFluidEffect(Level level, LivingEntity entity) { //此方法未来可能改为通用
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidBound));
        if (fluid != null) {
            FluidStack fluidStack = new FluidStack(fluid, 250);
            FarmerUtil.commonDrink(fluidStack, level, entity, false);
        }
    }

    /**
     * 显示药水信息
     */
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, level, components, flag);
        //data效果显示
        FluidEffect fluidEffect = FluidEffect.get(fluidBound);
        if(fluidEffect != null) {
            List<PotionEffect> potionEffects = fluidEffect.effects;
            if (potionEffects != null) {
                for (var effect : potionEffects) {
                    String name = effect.getEffect().getEffect().getDisplayName().getString();
                    String time = TextUtil.toMinuteFormat(effect.duration, true);
                    int amplifier = effect.amplifier + 1;
                    String levelRoman = TextUtil.toRoman(amplifier);
                    Component base = Component.literal(name + " " + levelRoman + " " + time).withStyle(ChatFormatting.BLUE);
                    components.add(base);
                }
            }
            if(fluidEffect.clear) components.add(ImmersiveDelight.TRANSLATOR.set("tooltip", "fluid_effect_clear", ChatFormatting.BLUE));
            if(fluidEffect.extinguishing) components.add(ImmersiveDelight.TRANSLATOR.set("tooltip", "fluid_effect_extinguishing", ChatFormatting.BLUE));
            if(fluidEffect.fire > 0) components.add(ImmersiveDelight.TRANSLATOR.set("tooltip", "fluid_effect_fire", ChatFormatting.BLUE, fluidEffect.fire));
        }

        //Create药水效果显示
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluidBound));
        if (fluid != null) {
            FluidStack fluidStack = new FluidStack(fluid, 250);
            PotionUtil.addPotionHoverText(fluidStack, components);
        }
    }



}
