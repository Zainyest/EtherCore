package zainyest.ethercore.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;

import java.util.function.Function;


public class Items {
    public static final Item TIME_FROZEN_ARMAMENT = register(
            "time_frozen_armament",
            Item::new,
            new Item.Properties().sword(ToolMaterial.NETHERITE, 1f, 1f));

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, name));
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.addAfter(net.minecraft.world.item.Items.NETHERITE_SWORD, Items.TIME_FROZEN_ARMAMENT));
    }
}
