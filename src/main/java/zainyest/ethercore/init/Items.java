package zainyest.ethercore.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.Consumables;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.item.consume_effects.ApplyStageConsumeEffect;

import java.util.function.Function;


public class Items {
    public static final Item TIME_FROZEN_ARMAMENT = register(
            "time_frozen_armament",
            Item::new,
            new Item.Properties().sword(ToolMaterial.NETHERITE, 1f, 1f).fireResistant());

    public static final Item AWAKENING_STONE = register(
            "awakening_stone",
            Item::new,
            new Item.Properties()
                    .fireResistant()
                    .rarity(Rarity.EPIC)
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .component(DataComponents.CONSUMABLE, Consumables.defaultDrink()
                            .consumeSeconds(2)
                            .onConsume(new ApplyStageConsumeEffect(EtherRegistries.STAGES.getKey(Stages.ENERGY_GATHERING)))
                            .animation(ItemUseAnimation.BOW)
                            .sound(Holder.direct(SoundEvents.AMETHYST_BLOCK_RESONATE))
                            .soundAfterConsume(SoundEvents.SOUL_ESCAPE)
                            .build()));

    public static Item register(String name, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, name));
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT)
                .register((itemGroup) -> itemGroup.addAfter(net.minecraft.world.item.Items.NETHERITE_SWORD, Items.TIME_FROZEN_ARMAMENT));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                .register((itemGroup) -> itemGroup.addAfter(net.minecraft.world.item.Items.ENCHANTED_GOLDEN_APPLE, Items.AWAKENING_STONE));
    }
}
