package zainyest.ethercore.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import zainyest.ethercore.EtherCore;

public class DataAttachments {
    public static final AttachmentType<Boolean> IS_TEMPORALLY_IMMUNE = AttachmentRegistry.create(
            EtherCore.id("is_temporally_immune"),
            booleanBuilder -> booleanBuilder
                    .initializer(() -> false)
                    .syncWith(
                            ByteBufCodecs.BOOL,
                            AttachmentSyncPredicate.all())
                    .persistent(Codec.BOOL)
    );

    public static void init() {}
}
