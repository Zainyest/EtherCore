package zainyest.ethercore.technique.techniquetree;

import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.Items;
import zainyest.ethercore.init.Sounds;
import zainyest.ethercore.technique.ActivatedTechnique;

public class CreateTimeFrozenArmament extends ActivatedTechnique {
    public CreateTimeFrozenArmament(String name, Identifier[] parents, Identifier[] children, Identifier icon) {
        super(name, parents, children, icon);
    }

    public CreateTimeFrozenArmament() {
        super("create_time_frozen_armament",
                new Identifier[]{Identifier.of(EtherCore.MOD_ID, "time_manipulation")},
                new Identifier[0],
                Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public void manifest(MinecraftServer server, ServerPlayerEntity serverPlayer) {
        serverPlayer.getEntityWorld().playSound(null, serverPlayer.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_RESONATE, SoundCategory.PLAYERS, 1f, 1f);
        serverPlayer.giveOrDropStack(new ItemStack(Items.TIME_FROZEN_ARMAMENT));
    }
}
