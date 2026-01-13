package zainyest.ethercore.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.EtherStat;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.init.EtherStats;

import java.util.concurrent.CompletableFuture;

public class EtherStatTagProvider extends FabricTagProvider<EtherStat> {

    public static final TagKey<EtherStat> BODY = TagKey.create(EtherRegistries.ETHER_STAT_KEY, EtherCore.id("body_stats"));
    public static final TagKey<EtherStat> ETHER = TagKey.create(EtherRegistries.ETHER_STAT_KEY, EtherCore.id("ether_stats"));
    public static final TagKey<EtherStat> MENTAL = TagKey.create(EtherRegistries.ETHER_STAT_KEY, EtherCore.id("mental_stats"));


    public EtherStatTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, EtherRegistries.ETHER_STAT_KEY, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider wrapperLookup) {
        builder(BODY)
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.STRENGTH).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.DEXTERITY).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.VIGOR).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.ENDURANCE).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.VITALITY).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.RECOVERY).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.PERCEPTION).orElseThrow());
        builder(ETHER)
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.POWER).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.ARCANA).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.EMINENCE).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.INFLUENCE).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.ATTUNEMENT).orElseThrow());
        builder(MENTAL)
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.WILL).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.INTELLIGENCE).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.MIND).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.FOCUS).orElseThrow())
                .add(EtherRegistries.ETHER_STATS.getResourceKey(EtherStats.VISION).orElseThrow());
    }
}
