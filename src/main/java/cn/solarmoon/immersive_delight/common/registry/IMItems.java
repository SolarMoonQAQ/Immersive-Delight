package cn.solarmoon.immersive_delight.common.registry;


import cn.solarmoon.immersive_delight.ImmersiveDelight;
import cn.solarmoon.immersive_delight.common.item.CulinaryTreasureItem;
import cn.solarmoon.immersive_delight.common.item.food_block_item.BowlFoodItem;
import cn.solarmoon.immersive_delight.common.item.block_item.*;
import cn.solarmoon.immersive_delight.common.item.equipment.BathrobeItem;
import cn.solarmoon.immersive_delight.common.item.food_block_item.*;
import cn.solarmoon.immersive_delight.common.item.product.*;
import cn.solarmoon.immersive_delight.common.item.recipe_item.ChineseCleaverItem;
import cn.solarmoon.immersive_delight.common.item.recipe_item.RollingPinItem;
import cn.solarmoon.immersive_delight.common.item.sapling.AppleSaplingItem;
import cn.solarmoon.immersive_delight.common.item.sapling.DurianSaplingItem;
import cn.solarmoon.immersive_delight.common.item.seed.*;
import cn.solarmoon.immersive_delight.compat.patchouli.Patchouli;
import cn.solarmoon.immersive_delight.util.useful_data.FoodProperty;
import cn.solarmoon.solarmoon_core.common.item.*;
import cn.solarmoon.solarmoon_core.registry.core.IRegister;
import cn.solarmoon.solarmoon_core.registry.object.ItemEntry;

@SuppressWarnings("unused")
public enum IMItems implements IRegister {
    INSTANCE;

    //擀面杖
    public static final ItemEntry<RollingPinItem> ROLLING_PIN = ImmersiveDelight.REGISTRY.item()
            .id("rolling_pin")
            .bound(RollingPinItem::new)
            .build();

    //中式菜刀
    public static final ItemEntry<ChineseCleaverItem> CHINESE_CLEAVER = ImmersiveDelight.REGISTRY.item()
            .id("chinese_cleaver")
            .bound(ChineseCleaverItem::new)
            .build();

    //青瓷杯
    public static final ItemEntry<CeladonCupItem> CELADON_CUP = ImmersiveDelight.REGISTRY.item()
            .id("celadon_cup")
            .bound(CeladonCupItem::new)
            .build();

    //玉瓷杯
    public static final ItemEntry<JadeChinaCupItem> JADE_CHINA_CUP = ImmersiveDelight.REGISTRY.item()
            .id("jade_china_cup")
            .bound(JadeChinaCupItem::new)
            .build();

    //水壶
    public static final ItemEntry<KettleItem> KETTLE = ImmersiveDelight.REGISTRY.item()
            .id("kettle")
            .bound(KettleItem::new)
            .build();

    //汤锅
    public static final ItemEntry<SoupPotItem> SOUP_POT = ImmersiveDelight.REGISTRY.item()
            .id("soup_pot")
            .bound(SoupPotItem::new)
            .build();

    //砧板
    public static final ItemEntry<CuttingBoardItem> CUTTING_BOARD = ImmersiveDelight.REGISTRY.item()
            .id("cutting_board")
            .bound(CuttingBoardItem::new)
            .build();

    //烧烤架
    public static final ItemEntry<GrillItem> GRILL = ImmersiveDelight.REGISTRY.item()
            .id("grill")
            .bound(GrillItem::new)
            .build();

    //餐盘
    public static final ItemEntry<ServicePlateItem> SERVICE_PLATE = ImmersiveDelight.REGISTRY.item()
            .id("service_plate")
            .bound(ServicePlateItem::new)
            .build();

    //蒸笼
    public static final ItemEntry<SteamerItem> STEAMER = ImmersiveDelight.REGISTRY.item()
            .id("steamer")
            .bound(SteamerItem::new)
            .build();

    //蒸笼底座
    public static final ItemEntry<SteamerBaseItem> STEAMER_BASE = ImmersiveDelight.REGISTRY.item()
            .id("steamer_base")
            .bound(SteamerBaseItem::new)
            .build();

    //蒸笼盖
    public static final ItemEntry<SteamerLidItem> STEAMER_LID = ImmersiveDelight.REGISTRY.item()
            .id("steamer_lid")
            .bound(SteamerLidItem::new)
            .build();

    //---

    //长瓷盘
    public static final ItemEntry<SimpleBlockItem> LONG_PORCELAIN_PLATE = ImmersiveDelight.REGISTRY.item()
            .id("long_porcelain_plate")
            .bound(() -> new SimpleBlockItem(IMBlocks.LONG_PORCELAIN_PLATE.get()))
            .build();

    //食物————————————————————————————————————————

    //面团
    public static final ItemEntry<WheatDoughItem> WHEAT_DOUGH = ImmersiveDelight.REGISTRY.item()
            .id("wheat_dough")
            .bound(WheatDoughItem::new)
            .build();

    //面饼
    public static final ItemEntry<FlatbreadDoughItem> FLATBREAD_DOUGH = ImmersiveDelight.REGISTRY.item()
            .id("flatbread_dough")
            .bound(FlatbreadDoughItem::new)
            .build();

    //碗装蛋液
    public static final ItemEntry<BowlFoodItem> EGG_LIQUID_BOWL = ImmersiveDelight.REGISTRY.item()
            .id("egg_liquid_bowl")
            .bound(() -> new BowlFoodItem(IMBlocks.EGG_LIQUID_BOWL.get(),
                    FoodProperty.PRIMARY_HUNGER_PRODUCT))
            .build();

    //鸡蛋羹
    public static final ItemEntry<BowlFoodItem> STEAMED_EGG_CUSTARD = ImmersiveDelight.REGISTRY.item()
            .id("steamed_egg_custard")
            .bound(() -> new BowlFoodItem(IMBlocks.STEAMED_EGG_CUSTARD.get(),
                    5, 0.75f))
            .build();

    //馒头
    public static final ItemEntry<SteamedBunItem> STEAMED_BUN = ImmersiveDelight.REGISTRY.item()
            .id("steamed_bun")
            .bound(SteamedBunItem::new)
            .build();

    //南瓜汤
    public static final ItemEntry<BowlFoodItem> PUMPKIN_SOUP = ImmersiveDelight.REGISTRY.item()
            .id("pumpkin_soup")
            .bound(() -> new BowlFoodItem("immersive_delight:pumpkin_soup_fluid",
                    IMBlocks.PUMPKIN_SOUP.get(),
                    6, 0.6f))
            .build();

    //紫菜蛋花汤
    public static final ItemEntry<BowlFoodItem> SEAWEED_EGG_DROP_SOUP = ImmersiveDelight.REGISTRY.item()
            .id("seaweed_egg_drop_soup")
            .bound(() -> new BowlFoodItem("immersive_delight:seaweed_egg_drop_soup_fluid",
                    IMBlocks.SEAWEED_EGG_DROP_SOUP.get(),
                    6, 0.6f))
            .build();

    //藏书羊肉汤
    public static final ItemEntry<BowlFoodItem> CANGSHU_MUTTON_SOUP = ImmersiveDelight.REGISTRY.item()
            .id("cangshu_mutton_soup")
            .bound(() -> new BowlFoodItem("immersive_delight:cangshu_mutton_soup_fluid",
                    IMBlocks.CANGSHU_MUTTON_SOUP.get(),
                    6, 1.5f))
            .build();

    //--//

    //生羊肉丁
    public static final ItemEntry<SimpleFoodItem> RAW_DICED_MUTTON = ImmersiveDelight.REGISTRY.item()
            .id("raw_diced_mutton")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT))
            .build();

    //生牛肉丁
    public static final ItemEntry<SimpleFoodItem> RAW_DICED_BEEF = ImmersiveDelight.REGISTRY.item()
            .id("raw_diced_beef")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT))
            .build();

    //生猪肉丁
    public static final ItemEntry<SimpleFoodItem> RAW_DICED_PORK = ImmersiveDelight.REGISTRY.item()
            .id("raw_diced_pork")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT))
            .build();

    //生鸡肉丁
    public static final ItemEntry<SimpleFoodItem> RAW_DICED_CHICKEN = ImmersiveDelight.REGISTRY.item()
            .id("raw_diced_chicken")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT))
            .build();

    //熟羊肉丁
    public static final ItemEntry<SimpleFoodItem> COOKED_DICED_MUTTON = ImmersiveDelight.REGISTRY.item()
            .id("cooked_diced_mutton")
            .bound(() -> new SimpleFoodItem(4, 0.5f))
            .build();

    //熟牛肉丁
    public static final ItemEntry<SimpleFoodItem> COOKED_DICED_BEEF = ImmersiveDelight.REGISTRY.item()
            .id("cooked_diced_beef")
            .bound(() -> new SimpleFoodItem(4, 0.5f))
            .build();

    //熟猪肉丁
    public static final ItemEntry<SimpleFoodItem> COOKED_DICED_PORK = ImmersiveDelight.REGISTRY.item()
            .id("cooked_diced_pork")
            .bound(() -> new SimpleFoodItem(4, 0.5f))
            .build();

    //熟鸡肉丁
    public static final ItemEntry<SimpleFoodItem> COOKED_DICED_CHICKEN = ImmersiveDelight.REGISTRY.item()
            .id("cooked_diced_chicken")
            .bound(() -> new SimpleFoodItem(4, 0.5f))
            .build();

    //生羊肉串
    public static final ItemEntry<SimpleFoodItem> RAW_MUTTON_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("raw_mutton_skewer")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT_1))
            .build();

    //生牛肉串
    public static final ItemEntry<SimpleFoodItem> RAW_BEEF_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("raw_beef_skewer")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT_1))
            .build();

    //生猪肉串
    public static final ItemEntry<SimpleFoodItem> RAW_PORK_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("raw_pork_skewer")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT_1))
            .build();

    //生鸡肉串
    public static final ItemEntry<SimpleFoodItem> RAW_CHICKEN_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("raw_chicken_skewer")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_HUNGER_PRODUCT_1))
            .build();

    //烤羊肉串
    public static final ItemEntry<SimpleFoodItem> COOKED_MUTTON_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("cooked_mutton_skewer")
            .bound(() -> new SimpleFoodItem(6, 0.5f))
            .build();

    //烤牛肉串
    public static final ItemEntry<SimpleFoodItem> COOKED_BEEF_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("cooked_beef_skewer")
            .bound(() -> new SimpleFoodItem(6, 0.5f))
            .build();

    //烤猪肉串
    public static final ItemEntry<SimpleFoodItem> COOKED_PORK_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("cooked_pork_skewer")
            .bound(() -> new SimpleFoodItem(6, 0.5f))
            .build();

    //烤鸡肉串
    public static final ItemEntry<SimpleFoodItem> COOKED_CHICKEN_SKEWER = ImmersiveDelight.REGISTRY.item()
            .id("cooked_chicken_skewer")
            .bound(() -> new SimpleFoodItem(6, 0.5f))
            .build();

    //烤榴莲
    public static final ItemEntry<SimpleFoodItem> ROASTED_DURIAN = ImmersiveDelight.REGISTRY.item()
            .id("roasted_durian")
            .bound(() -> new SimpleFoodItem(6, 0.75f))
            .build();

    //未烹饪的清蒸鲑鱼
    public static final ItemEntry<LongPorcelainPlateFoodItem> UNCOOKED_STEAMED_SALMON = ImmersiveDelight.REGISTRY.item()
            .id("uncooked_steamed_salmon")
            .bound(() -> new LongPorcelainPlateFoodItem(IMBlocks.UNCOOKED_STEAMED_SALMON.get(), FoodProperty.PRIMARY_HUNGER_PRODUCT_1))
            .build();

    //清蒸鲑鱼
    public static final ItemEntry<SimpleBlockItem> STEAMED_SALMON = ImmersiveDelight.REGISTRY.item()
            .id("steamed_salmon")
            .bound(() -> new SimpleBlockItem(IMBlocks.STEAMED_SALMON.get()))
            .build();

    //作物————————————————————————————————————————

    //红茶叶
    public static final ItemEntry<SimpleItem> BLACK_TEA_LEAVES = ImmersiveDelight.REGISTRY.item()
            .id("black_tea_leaf")
            .bound(SimpleItem::new)
            .build();

    //红茶种子
    public static final ItemEntry<BlackTeaSeedsItem> BLACK_TEA_SEEDS = ImmersiveDelight.REGISTRY.item()
            .id("black_tea_seeds")
            .bound(BlackTeaSeedsItem::new)
            .build();

    //绿茶叶
    public static final ItemEntry<SimpleItem> GREEN_TEA_LEAVES = ImmersiveDelight.REGISTRY.item()
            .id("green_tea_leaf")
            .bound(SimpleItem::new)
            .build();

    //绿茶种子
    public static final ItemEntry<GreenTeaSeedsItem> GREEN_TEA_SEEDS = ImmersiveDelight.REGISTRY.item()
            .id("green_tea_seeds")
            .bound(GreenTeaSeedsItem::new)
            .build();

    //苹果核
    public static final ItemEntry<AppleCoreItem> APPLE_CORE = ImmersiveDelight.REGISTRY.item()
            .id("apple_core")
            .bound(AppleCoreItem::new)
            .build();

    //苹果树苗
    public static final ItemEntry<AppleSaplingItem> APPLE_SAPLING = ImmersiveDelight.REGISTRY.item()
            .id("apple_sapling")
            .bound(AppleSaplingItem::new)
            .build();

    //榴莲块
    public static final ItemEntry<DurianItem> DURIAN = ImmersiveDelight.REGISTRY.item()
            .id("durian")
            .bound(DurianItem::new)
            .build();

    //榴莲种子
    public static final ItemEntry<DurianCoreItem> DURIAN_CORE = ImmersiveDelight.REGISTRY.item()
            .id("durian_core")
            .bound(DurianCoreItem::new)
            .build();

    //榴莲树苗
    public static final ItemEntry<DurianSaplingItem> DURIAN_SAPLING = ImmersiveDelight.REGISTRY.item()
            .id("durian_sapling")
            .bound(DurianSaplingItem::new)
            .build();

    //榴莲肉
    public static final ItemEntry<DurianFleshItem> DURIAN_FLESH = ImmersiveDelight.REGISTRY.item()
            .id("durian_flesh")
            .bound(DurianFleshItem::new)
            .build();

    //榴莲壳
    public static final ItemEntry<SimpleItem> DURIAN_SHELL = ImmersiveDelight.REGISTRY.item()
            .id("durian_shell")
            .bound(SimpleItem::new)
            .build();

    //葱
    public static final ItemEntry<SimpleSeedFoodBlockItem> SPRING_ONION = ImmersiveDelight.REGISTRY.item()
            .id("spring_onion")
            .bound(() -> new SimpleSeedFoodBlockItem(IMBlocks.SPRING_ONION_CROP.get(), FoodProperty.PRIMARY_PRODUCT))
            .build();

    //姜
    public static final ItemEntry<SimpleSeedFoodBlockItem> GINGER = ImmersiveDelight.REGISTRY.item()
            .id("ginger")
            .bound(() -> new SimpleSeedFoodBlockItem(IMBlocks.GINGER_CROP.get(), FoodProperty.PRIMARY_PRODUCT))
            .build();

    //姜片
    public static final ItemEntry<SimpleFoodItem> GINGER_SLICE = ImmersiveDelight.REGISTRY.item()
            .id("ginger_slice")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_PRODUCT))
            .build();

    //姜丝
    public static final ItemEntry<SimpleFoodItem> SHREDDED_GINGER = ImmersiveDelight.REGISTRY.item()
            .id("shredded_ginger")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_PRODUCT))
            .build();

    //大蒜
    public static final ItemEntry<GarlicItem> GARLIC = ImmersiveDelight.REGISTRY.item()
            .id("garlic")
            .bound(GarlicItem::new)
            .build();

    //蒜瓣
    public static final ItemEntry<SimpleSeedFoodBlockItem> GARLIC_CLOVE = ImmersiveDelight.REGISTRY.item()
            .id("garlic_clove")
            .bound(() -> new SimpleSeedFoodBlockItem(IMBlocks.GARLIC_CROP.get(), FoodProperty.PRIMARY_PRODUCT))
            .build();

    //蒜苗
    public static final ItemEntry<SimpleFoodItem> GARLIC_SPROUTS = ImmersiveDelight.REGISTRY.item()
            .id("garlic_sprouts")
            .bound(() -> new SimpleFoodItem(FoodProperty.PRIMARY_PRODUCT))
            .build();

    //野生作物————————————————————————————————————————————————————————————————————————————

    //野生葱
    public static final ItemEntry<SimpleBlockItem> WILD_SPRING_ONION = ImmersiveDelight.REGISTRY.item()
            .id("wild_spring_onion")
            .bound(() -> new SimpleBlockItem(IMBlocks.WILD_SPRING_ONION.get()))
            .build();

    //野生姜
    public static final ItemEntry<SimpleBlockItem> WILD_GINGER = ImmersiveDelight.REGISTRY.item()
            .id("wild_ginger")
            .bound(() -> new SimpleBlockItem(IMBlocks.WILD_GINGER.get()))
            .build();

    //野生大蒜
    public static final ItemEntry<SimpleBlockItem> WILD_GARLIC = ImmersiveDelight.REGISTRY.item()
            .id("wild_garlic")
            .bound(() -> new SimpleBlockItem(IMBlocks.WILD_GARLIC.get()))
            .build();

    //装备————————————————————————————————————————————————————————————————————————————————

    //浴衣
    public static final ItemEntry<BathrobeItem> BATHROBE = ImmersiveDelight.REGISTRY.item()
            .id("bathrobe")
            .bound(BathrobeItem::new)
            .build();

    //其它————————————————————————————————————————————————————————————————————————————————

    //帕秋莉手册联动
    public static final ItemEntry<CulinaryTreasureItem> CULINARY_TREASURE = ImmersiveDelight.REGISTRY.item()
            .id("culinary_treasure")
            .bound(CulinaryTreasureItem::new)
            .condition(Patchouli.isLoaded())
            .build();

}
