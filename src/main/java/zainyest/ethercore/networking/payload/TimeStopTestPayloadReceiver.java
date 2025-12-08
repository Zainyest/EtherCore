package zainyest.ethercore.networking.payload;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import zainyest.ethercore.init.Techniques;

public class TimeStopTestPayloadReceiver implements ServerPlayNetworking.PlayPayloadHandler<TimeStopTestPayload>{
    @Override
    public void receive(TimeStopTestPayload payload, ServerPlayNetworking.Context context) {
        Techniques.TIME_STOP.manifest(context.server(), context.player());
    }
}
