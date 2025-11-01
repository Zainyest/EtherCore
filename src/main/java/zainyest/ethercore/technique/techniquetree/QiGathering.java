package zainyest.ethercore.technique.techniquetree;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.technique.PassiveTechnique;
import zainyest.ethercore.technique.Technique;

public class QiGathering extends PassiveTechnique {
    public QiGathering(String name, Technique[] parents, Technique[] children, String techniqueType, String description, Identifier icon) {
        super(name, parents, children, techniqueType, description, icon);
    }

    public QiGathering() {
        super("qi_gathering", new Technique[0], new Technique[0], "Passive", "The first step", Identifier.of(EtherCore.MOD_ID, "icon.png"));
    }

    @Override
    public Technique fromNbt(NbtCompound nbtCompound) {

        QiGathering qiGathering = new QiGathering();

        //qiGathering.setName(nbtCompound.getString("name").orElse(qiGathering.getName()));
        // if null, this is a root
//        for (Technique p : this.parents) {
//            techniqueData.putString("parent", p.getName());
//        }
//        // if null, this is a leaf
//        for (Technique c : this.children) {
//            techniqueData.putString("child", c.getName());
//        }
//        techniqueData.putString("type", this.techniqueType);
//        techniqueData.putString("description", this.description);
//        techniqueData.putString("icon", this.icon.toString());
        qiGathering.setLearned(nbtCompound.getBoolean("learned", false));
        return qiGathering;
    }

    @Override
    public void tick() {

    }
}
