package zainyest.ethercore.event;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import zainyest.ethercore.networking.ModPackets;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_ETHERCORE = "key.category.ethercore.ethercore";
    public static final String KEY_OPEN_MENU = "key.ethercore.open_menu";
    public static final String KEY_CAST_ETHER_BOLT = "key.ethercore.cast_ether_bolt";

    public static KeyBinding openMenuKey;
    public static KeyBinding castEtherBoltKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.wasPressed()) {
                ClientPlayNetworking.send(ModPackets.MENU_OPEN_ID, PacketByteBufs.create()); // Menu packet sent
            }
            if (castEtherBoltKey.wasPressed()) {
                ClientPlayNetworking.send(ModPackets.CAST_ETHER_BOLT_ID, PacketByteBufs.create()); // Ether Bolt cast
            }
        });
    }

    public static void register() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_MENU,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_APOSTROPHE,
                KEY_CATEGORY_ETHERCORE
        ));
        castEtherBoltKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_CAST_ETHER_BOLT,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F,
                KEY_CATEGORY_ETHERCORE
        ));
        registerKeyInputs();
    }
}
