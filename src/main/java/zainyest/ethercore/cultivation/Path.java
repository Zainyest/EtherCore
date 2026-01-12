package zainyest.ethercore.cultivation;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import zainyest.ethercore.util.PlayerData;
import zainyest.ethercore.util.StateSaverAndLoader;

/// A tree structure containing every possible stage of a progression. <br>
/// To advance, one must Breathe... To remain stagnate is antithetical to growth.
public record Path(String name, Stage root, Identifier icon) {

    public PathView fromNbt(CompoundTag nbtIn) {
        CompoundTag nbt = nbtIn.getCompoundOrEmpty(this.name);
        return new PathView(this.name(),
                nbt.getInt("aura").orElse(0),
                nbt.getString("current_stage").orElse(null));
    }

    public PathView fromPlayerData(PlayerData playerData) {
        CompoundTag nbt = playerData.getPersistentData();
        return fromNbt(nbt);
    }

    private PathView instantiateView() {
        return new PathView(this.name(), 0, root.getName());
    }

    public PathView getOrCreateView(PlayerData playerData) {
        if (playerData.getPersistentData().getCompoundOrEmpty(this.name()).isEmpty()) {
            return instantiateView();
        }
        return fromPlayerData(playerData);
    }

    public void sync(ServerPlayer player) {
        StateSaverAndLoader.getPlayerState(player).markDirty(this.name());
    }

}
