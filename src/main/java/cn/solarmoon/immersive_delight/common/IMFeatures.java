package cn.solarmoon.immersive_delight.common;

import cn.solarmoon.immersive_delight.ImmersiveDelight;
import cn.solarmoon.immersive_delight.common.level.feature.AppleTreeFeature;
import cn.solarmoon.immersive_delight.common.level.feature.DurianTreeFeature;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import static cn.solarmoon.immersive_delight.ImmersiveDelight.MOD_ID;
import static cn.solarmoon.immersive_delight.init.ObjectRegister.FEATURES;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IMFeatures {

    //苹果树
    public static final RegistryObject<Feature> APPLE_TREE = FEATURES.register("apple_tree", AppleTreeFeature::new);
    //榴莲树
    public static final RegistryObject<Feature> DURIAN_TREE = FEATURES.register("durian_tree", DurianTreeFeature::new);

    public static class Placed {
        //苹果树
        public static final ResourceKey<PlacedFeature> APPLE_TREE = createKey("apple_tree");
        //榴莲树
        public static final ResourceKey<PlacedFeature> DURIAN_TREE = createKey("durian_tree");

        private static ResourceKey<PlacedFeature> createKey(String name) {
            return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(ImmersiveDelight.MOD_ID, name));
        }
    }

    public static class Configured {
        //苹果树
        public static final ResourceKey<ConfiguredFeature<?, ?>> APPLE_TREE = createKey("apple_tree");
        //榴莲树
        public static final ResourceKey<ConfiguredFeature<?, ?>> DURIAN_TREE = createKey("durian_tree");

        private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
            return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(ImmersiveDelight.MOD_ID, name));
        }
    }

}