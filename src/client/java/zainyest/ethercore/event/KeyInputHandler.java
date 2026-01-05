package zainyest.ethercore.event;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import zainyest.ethercore.gui.screen.ingame.TreeScreen;
import zainyest.ethercore.networking.payload.TimeStopTestPayload;

public class KeyInputHandler {
    public static final KeyMapping.Category KEY_CATEGORY_ETHERCORE = KeyMapping.Category.register(Identifier.fromNamespaceAndPath("ethercore", "ethercore"));
    public static final String KEY_OPEN_MENU = "key.ethercore.open_menu";
    public static final String KEY_TIME_STOP_TEST = "key.ethercore.time_stop_test";

    public static KeyMapping openMenuKey;
    public static KeyMapping timeStopTestKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.consumeClick()) {
                Screen currentScreen = Minecraft.getInstance().screen;
                Minecraft.getInstance().setScreen(new TreeScreen(Component.empty(), currentScreen));
            }
            if (timeStopTestKey.consumeClick()) {
                ClientPlayNetworking.send(new TimeStopTestPayload(0));
            }
        });
    }

    public static void register() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_OPEN_MENU,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_APOSTROPHE,
                KEY_CATEGORY_ETHERCORE
        ));
        timeStopTestKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                KEY_TIME_STOP_TEST,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_CAPS_LOCK,
                KEY_CATEGORY_ETHERCORE
        ));
        registerKeyInputs();
    }
}
