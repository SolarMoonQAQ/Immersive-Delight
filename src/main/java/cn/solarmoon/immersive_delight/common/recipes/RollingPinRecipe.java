package cn.solarmoon.immersive_delight.common.recipes;

import cn.solarmoon.immersive_delight.util.RecipeHelper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.solarmoon.immersive_delight.common.IMRecipes.ROLLING_RECIPE;
import static cn.solarmoon.immersive_delight.common.IMRecipes.ROLLING_RECIPE_SERIALIZER;


public class RollingPinRecipe implements Recipe<RecipeWrapper> {

    private final ResourceLocation id;
    private final Ingredient input;
    private final int time;
    private final Ingredient output;
    private final NonNullList<RecipeHelper.ChanceResult> results;

    public RollingPinRecipe(ResourceLocation id, Ingredient input, int time, Ingredient output, NonNullList<RecipeHelper.ChanceResult> results) {
        this.id = id;
        this.input = input;
        this.time = time;
        this.output = output;
        this.results = results;
    }

    @Override
    public boolean matches(@NotNull RecipeWrapper inv, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull RecipeWrapper inv, @NotNull RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    public int getTime() {
        return this.time;
    }

    public Ingredient getInput() {
        return this.input;
    }

    public Ingredient getOutput() {
        return this.output;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    public List<ItemStack> getResults() {
        return getRollableResults().stream()
                .map(RecipeHelper.ChanceResult::stack)
                .collect(Collectors.toList());
    }

    public NonNullList<RecipeHelper.ChanceResult> getRollableResults() {
        return this.results;
    }

    public List<ItemStack> rollResults(RandomSource rand, int fortuneLevel) {
        List<ItemStack> results = new ArrayList<>();
        NonNullList<RecipeHelper.ChanceResult> rollableResults = getRollableResults();
        for (RecipeHelper.ChanceResult output : rollableResults) {
            ItemStack stack = output.rollOutput(rand, fortuneLevel);
            if (!stack.isEmpty())
                results.add(stack);
        }
        return results;
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return this.id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ROLLING_RECIPE_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return ROLLING_RECIPE.get();
    }

    //配方哈希值比较
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RollingPinRecipe that = (RollingPinRecipe) o;

        if (!id.equals(that.id)) return false;
        if (!input.equals(that.input)) return false;
        if (getTime() != that.getTime()) return false;
        if (!output.equals(that.output)) return false;
        return getResults().equals(that.getResults());
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + input.hashCode();
        result = 31 * result + getTime();
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + getResults().hashCode();
        return result;
    }


    //序列化编码解码
    public static class Serializer implements RecipeSerializer<RollingPinRecipe> {

        public Serializer() {
        }

        private static NonNullList<RecipeHelper.ChanceResult> readResults(JsonArray resultArray) {
            NonNullList<RecipeHelper.ChanceResult> results = NonNullList.create();
            for (JsonElement result : resultArray) {
                results.add(RecipeHelper.ChanceResult.deserialize(result));
            }
            return results;
        }

        @Override
        public @NotNull RollingPinRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {

            Ingredient input = Ingredient.fromJson(json.get("input"));

            final int time = GsonHelper.getAsInt(json, "time");

            Ingredient output = null;
            if (json.has("output")) {
                output = Ingredient.fromJson(json.get("output"));
            }

            NonNullList<RecipeHelper.ChanceResult> results = NonNullList.create();
            if (json.has("result")) {
                results = readResults(GsonHelper.getAsJsonArray(json, "result"));
            }

            return new RollingPinRecipe(recipeId, input, time, output, results);
        }

        @Nullable
        @Override
        public RollingPinRecipe fromNetwork(@NotNull ResourceLocation recipeId, @NotNull FriendlyByteBuf buffer) {

            Ingredient input = Ingredient.fromNetwork(buffer);

            int time = buffer.readInt();

            Ingredient output = null;
            if (buffer.readBoolean()) {
                output = Ingredient.fromNetwork(buffer);
            }

            int i = buffer.readVarInt();
            NonNullList<RecipeHelper.ChanceResult> resultsIn = NonNullList.withSize(i, RecipeHelper.ChanceResult.EMPTY);
            resultsIn.replaceAll(ignored -> RecipeHelper.ChanceResult.read(buffer));

            return new RollingPinRecipe(recipeId, input, time, output, resultsIn);
        }

        @Override
        public void toNetwork(@NotNull FriendlyByteBuf buffer, RollingPinRecipe recipe) {

            recipe.input.toNetwork(buffer);

            buffer.writeInt(recipe.time);

            buffer.writeBoolean(recipe.output != null);
            if (recipe.output != null) {
                recipe.output.toNetwork(buffer);
            }

            buffer.writeVarInt(recipe.results.size());
            for (RecipeHelper.ChanceResult result : recipe.results) {
                result.write(buffer);
            }

        }

    }

}
