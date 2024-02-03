package cn.solarmoon.immersive_delight.api.common.capability;

import cn.solarmoon.immersive_delight.api.common.capability.serializable.RecipeSelectorData;
import cn.solarmoon.immersive_delight.common.registry.IMCapabilities;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerData implements ICapabilitySerializable<CompoundTag>, IPlayerData {

    private final Player player;
    private final LazyOptional<IPlayerData> playerData;
    private final RecipeSelectorData recipeSelectorData;

    public PlayerData(Player player) {
        this.player = player;
        this.playerData = LazyOptional.of(() -> this);
        this.recipeSelectorData = new RecipeSelectorData();
    }

    @Override
    public RecipeSelectorData getRecipeSelectorData() {
        return recipeSelectorData;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == IMCapabilities.PLAYER_DATA) {
            return playerData.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();

        tag.put("recipeSelectorData", recipeSelectorData.serializeNBT());

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        recipeSelectorData.deserializeNBT(nbt);

    }

}
