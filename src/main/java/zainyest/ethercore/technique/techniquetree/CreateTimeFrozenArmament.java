package zainyest.ethercore.technique.techniquetree;

import net.minecraft.world.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.Items;
import zainyest.ethercore.technique.ActivatedTechnique;

public class CreateTimeFrozenArmament extends ActivatedTechnique {
    public CreateTimeFrozenArmament() {
        super("create_time_frozen_armament",
                new Identifier[]{Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "time_manipulation")},
                new Identifier[0],
                Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void manifest(MinecraftServer server, ServerPlayer serverPlayer) {
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 1f, 1f);
        serverPlayer.handleExtraItemsCreatedOnUse(new ItemStack(Items.TIME_FROZEN_ARMAMENT));
    }
}
