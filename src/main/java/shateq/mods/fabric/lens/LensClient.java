package shateq.mods.fabric.lens;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;

public class LensClient implements ClientModInitializer {
    public static boolean pauseScreen = false;
    private static KeyMapping key;

    @Override
    public void onInitializeClient() {
        key = KeyBindingHelper.registerKeyBinding(
                new KeyMapping("key.lens.menu", InputConstants.Type.KEYSYM, GLFW_KEY_GRAVE_ACCENT, "category.lens")
        );

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if(key.consumeClick()) {
                client.setScreen(new LensScreen());
            }
        });
    }
}
