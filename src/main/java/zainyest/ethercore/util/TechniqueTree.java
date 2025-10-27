package zainyest.ethercore.util;

import net.minecraft.nbt.NbtCompound;

public class TechniqueTree {
    private String treeName;
    private Technique rootTechnique;

    public TechniqueTree(String treeName, Technique rootTechnique) {
        this.treeName = treeName;
        this.rootTechnique = rootTechnique;
    }

    public void setName(String name) {
        this.treeName = name;
    }
    public String getName() {
        return treeName;
    }
    public void setRootTechnique(Technique rootTechnique) {
        this.rootTechnique = rootTechnique;
    }
    public Technique getRootTechnique() {
        return this.rootTechnique;
    }

    public void setTechnique(PlayerData playerData, Technique technique) {
        NbtCompound treeData = playerData.persistentData.getCompoundOrEmpty(treeName);
        treeData.put("technique", technique.toNbt());
        playerData.persistentData.put(treeName, treeData);
    }
}
