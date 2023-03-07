package zainyest.ethercore.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
            //TODO make this its own rate per player
            EtherData.addEther(dataPlayer, 1 + (EtherData.getMaxEther(dataPlayer) / 500));

            if (EtherData.getMaxEther(dataPlayer) < 1000) {
                EtherData.setMaxEther(dataPlayer, 1000); // TODO make ether data persist through death
            }
            if (new Random().nextFloat() <= 0.005f) {
                EtherData.setMaxEther(dataPlayer, EtherData.getMaxEther(dataPlayer)+100);
                assert player != null;
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_STEP, SoundCategory.PLAYERS, 4.0f, 4.0f);
            }
        }
    }
}
