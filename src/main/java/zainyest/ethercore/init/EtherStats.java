package zainyest.ethercore.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.etherstat.EtherStat;

public class EtherStats {
    //public static final EtherStat EXAMPLE_STAT = registerStat("example_stat", new EtherStat("example_stat", 10));

    //Body
    public static final EtherStat STRENGTH = registerStat("strength", new EtherStat("strength", 10));
    public static final EtherStat DEXTERITY = registerStat("dexterity", new EtherStat("dexterity", 10));
    public static final EtherStat VIGOR = registerStat("vigor", new EtherStat("vigor", 10));
    public static final EtherStat ENDURANCE = registerStat("endurance", new EtherStat("endurance", 10));
    public static final EtherStat VITALITY = registerStat("vitality", new EtherStat("vitality", 10));
    public static final EtherStat RECOVERY = registerStat("recovery", new EtherStat("recovery", 10));
    public static final EtherStat PERCEPTION = registerStat("perception", new EtherStat("perception", 10));

    //Ether
    public static final EtherStat POWER = registerStat("power", new EtherStat("power", 0));
    public static final EtherStat ARCANA = registerStat("arcana", new EtherStat("arcana", 0));
    public static final EtherStat EMINENCE = registerStat("eminence", new EtherStat("eminence", 0));
    public static final EtherStat INFLUENCE = registerStat("influence", new EtherStat("influence", 0));
    public static final EtherStat ATTUNEMENT = registerStat("attunement", new EtherStat("attunement", 0));

    //Mental
    public static final EtherStat WILL = registerStat("will", new EtherStat("will", 10));
    public static final EtherStat INTELLIGENCE = registerStat("intelligence", new EtherStat("intelligence", 10));
    public static final EtherStat MIND = registerStat("mind", new EtherStat("mind", 10));
    public static final EtherStat FOCUS = registerStat("focus", new EtherStat("focus", 10));
    public static final EtherStat VISION = registerStat("vision", new EtherStat("vision", 0));

    private static <T extends EtherStat> T registerStat(String name, T stat) {
        return Registry.register(EtherRegistries.ETHER_STATS, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, name), stat);
    }

    public static void init() {

    }
}
