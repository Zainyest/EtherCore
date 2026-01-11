package zainyest.ethercore.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.Items;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public static final TagKey<Item> CONJURED_ITEMS = TagKey.create(Registries.ITEM, EtherCore.id("conjured_items"));

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) { // REMEMBER TO RUN DATAGEN WHEN THIS IS CHANGED
        valueLookupBuilder(CONJURED_ITEMS)
                .add(Items.TIME_FROZEN_ARMAMENT);
        valueLookupBuilder(ItemTags.SWORDS)
                .add(Items.TIME_FROZEN_ARMAMENT);
    }
}
