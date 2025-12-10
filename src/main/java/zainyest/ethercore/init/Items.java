package zainyest.ethercore.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

import java.util.function.Function;

public class Items {
    public static final Item TIME_FROZEN_ARMAMENT = register(
            "time_frozen_armament",
            Item::new,
            new Item.Settings().sword(ToolMaterial.NETHERITE, 1f, 1f));

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(EtherCore.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT)
                .register((itemGroup) -> itemGroup.addAfter(net.minecraft.item.Items.NETHERITE_SWORD, Items.TIME_FROZEN_ARMAMENT));
    }
}
