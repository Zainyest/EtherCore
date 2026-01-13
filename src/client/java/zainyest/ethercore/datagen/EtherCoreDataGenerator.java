package zainyest.ethercore.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import zainyest.ethercore.tag.EtherStatTagProvider;
import zainyest.ethercore.tag.ItemTagProvider;

public class EtherCoreDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(EtherStatTagProvider::new);
    }
}
