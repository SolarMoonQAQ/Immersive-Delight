package cn.solarmoon.immersive_delight.common.recipe;

import cn.solarmoon.immersive_delight.common.block_entity.base.AbstractKettleBlockEntity;
import cn.solarmoon.immersive_delight.util.FarmerUtil;
import cn.solarmoon.solarmoon_core.common.block_entity.ITankBlockEntity;
import cn.solarmoon.solarmoon_core.common.fluid.BaseFluid;
import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static cn.solarmoon.immersive_delight.common.registry.IMRecipes.KETTLE;

/**
 * 烧水配方，所有烧东西的容器可通用，以水壶为基准，也就是1000mB为基准
 */
public class KettleRecipe implements Recipe<RecipeWrapper> {

    private final static int BaseFluidAmount = 1000;
    private final ResourceLocation id;
    private final String input;
    private final int time;
    private final String output;

    public KettleRecipe(ResourceLocation id, String input, int time, String output) {
        this.id = id;
        this.input = input;
        this.time = time;
        this.output = output;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper inv, @NotNull Level level) {
        return false;
    }

    /**
     * 检查容器内液体是否匹配配方输入<br/>
     * 并且容器下方有热源，不要求数量，毕竟烧水多少都行
     */
    public boolean inputMatches(Level level, BlockPos pos) {
        Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(input));
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (fluid != null) {
            BlockState state = level.getBlockState(pos.below());
            boolean isHeated = FarmerUtil.isHeatSource(state);
            if(blockEntity instanceof ITankBlockEntity container) {
                FluidStack fluidStackIn = container.getTank().getFluid();
                FluidStack fluidStack = new FluidStack(fluid, container.getMaxCapacity());//这里get容量没有任何意义，只是占个位，实际上不会检测容量
                return !fluidStack.isEmpty() && fluidStack.equals(fluidStackIn) && isHeated;
            }
        }
        return false;
    }

    /**
     * 把烧水时间和当前要烧的水的容量绑定（实现动态烧水时间）
     */
    public int getActualTime(Level level, BlockPos pos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof ITankBlockEntity container) {
            double scale = (double) container.getTank().getFluidAmount() / BaseFluidAmount;
            return (int) (time * scale);
        }
        return time;
    }

    /**
     * 获取输出流体
     */
    public Fluid getOutputFluid() {
        return ForgeRegistries.FLUIDS.getValue(new ResourceLocation(output));
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper inv, @NotNull RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return KETTLE.getSerializer();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return KETTLE.get();
    }

    /**
     * 用于比较配方时的匹配逻辑
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KettleRecipe that = (KettleRecipe) o;

        if (!id.equals(that.id)) return false;
        if (!input.equals(that.input)) return false;
        if (getTime() != that.getTime()) return false;
        return output.equals(that.output);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + input.hashCode();
        result = 31 * result + getTime();
        result = 31 * result + (output != null ? output.hashCode() : 0);
        return result;
    }


    //序列化编码解码
    public static class Serializer implements RecipeSerializer<KettleRecipe> {

        public Serializer() {
        }

        @Override
        public @NotNull KettleRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {

            String input = GsonHelper.getAsString(json, "input");

            final int time = GsonHelper.getAsInt(json, "time");

            String output = GsonHelper.getAsString(json, "output");

            return new KettleRecipe(recipeId, input, time, output);
        }

        @Nullable
        @Override
        public KettleRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {

            String input = buffer.readUtf();

            int time = buffer.readInt();

            String output = buffer.readUtf();

            return new KettleRecipe(recipeId, input, time, output);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, KettleRecipe recipe) {

            buffer.writeUtf(recipe.input);

            buffer.writeInt(recipe.time);

            buffer.writeUtf(recipe.output);

        }

    }

}
