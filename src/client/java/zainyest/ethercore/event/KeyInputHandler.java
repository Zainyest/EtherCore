package zainyest.ethercore.event;


import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import zainyest.ethercore.gui.screen.ingame.TreeScreen;

public class KeyInputHandler {
    public static final KeyBinding.Category KEY_CATEGORY_ETHERCORE = KeyBinding.Category.create(Identifier.of("ethercore", "ethercore"));
    public static final String KEY_OPEN_MENU = "key.ethercore.open_menu";

    public static KeyBinding openMenuKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openMenuKey.wasPressed()) {
                Screen currentScreen = MinecraftClient.getInstance().currentScreen;
                MinecraftClient.getInstance().setScreen(new TreeScreen(Text.empty(), currentScreen));
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
        registerKeyInputs();
    }
}
