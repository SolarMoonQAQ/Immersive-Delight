package cn.solarmoon.immersive_delight.api.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class BaseRecipeRegistry {

    protected DeferredRegister<RecipeType<?>> RECIPE_TYPES;
    protected DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS;


    public BaseRecipeRegistry(DeferredRegister<RecipeType<?>> RECIPE_TYPES, DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS) {
        this.RECIPE_TYPES = RECIPE_TYPES;
        this.RECIPE_SERIALIZERS = RECIPE_SERIALIZERS;
    }

    public class TYPE extends BaseObjectRegistry<RecipeType<?>> {
        public TYPE() {
            super(RECIPE_TYPES);
        }
    }

    public class SERIALIZERS extends BaseObjectRegistry<RecipeSerializer<?>> {
        public SERIALIZERS() {
            super(RECIPE_SERIALIZERS);
        }
    }

    public void register(IEventBus bus) {
        new TYPE().register(bus);
        new SERIALIZERS().register(bus);
    }

}
