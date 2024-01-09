package cn.solarmoon.immersive_delight.common;


import cn.solarmoon.immersive_delight.client.events.DrinkingEvent;
import cn.solarmoon.immersive_delight.common.events.RollingPinEvent;
import cn.solarmoon.immersive_delight.client.events.RollingPinClientEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static cn.solarmoon.immersive_delight.ImmersiveDelight.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class IMCommonEvents {

    //服务端事件订阅
    @SubscribeEvent
    public static void onFMLCommonSetupEvent(final FMLCommonSetupEvent event) {
        //擀面杖
        r(new RollingPinEvent());
        //倒水
        r(new DrinkingEvent());
    }

    public static void r(Object target) {
        MinecraftForge.EVENT_BUS.register(target);
    }

}