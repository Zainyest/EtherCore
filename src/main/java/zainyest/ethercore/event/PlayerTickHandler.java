package zainyest.ethercore.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

import java.util.Random;

public class PlayerTickHandler implements ServerTickEvents.StartTick{
    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
            //TODO make this its own rate per player
            EtherData.addEther(dataPlayer, 1);

            if (EtherData.getMaxEther(dataPlayer) < 1000) {
                EtherData.setMaxEther(dataPlayer, 1000);
            }
            if (new Random().nextFloat() <= 0.001f) {
                EtherData.setMaxEther(dataPlayer, EtherData.getMaxEther(dataPlayer)+100);
                //player.sendMessage(Text.literal("Added 1 Max Ether"));
            }
        }
    }
}
