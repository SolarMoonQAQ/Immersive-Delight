package cn.solarmoon.immersive_delight.api.registry;

import cn.solarmoon.immersive_delight.api.network.NetworkPack;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本网络数据包注册表
 */
public abstract class BasePackRegistry {

    protected List<NetworkPack> packs;

    public BasePackRegistry() {
        this.packs = new ArrayList<>();
    }

    /**
     * 使用packs来添加NetworkPack
     */
    public abstract void addRegistry();

    public void register() {
        addRegistry();
        for (NetworkPack pack : packs) {
            pack.build();
        }
    }

}
