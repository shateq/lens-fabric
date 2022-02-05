package shateq.mods.fabric.lens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_GRAVE_ACCENT;

public class LensClient implements ClientModInitializer {
    public static boolean pauseScreen = false;
    private final static String command = "fov";
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

        ClientCommandManager.DISPATCHER.register(ClientCommandManager.literal(command)
                .then(ClientCommandManager.argument("number", IntegerArgumentType.integer())
//                        .suggests((context, builder) -> {
//                            new IntegerSuggestion(new StringRange(30, 110), 70);
//                            return builder.buildFuture();
//                        })
                    .executes(context -> {
                        final LocalPlayer p = context.getSource().getPlayer();
                        int number = IntegerArgumentType.getInteger(context, "number");
                        number = evalFovNumber(number);
                        p.displayClientMessage(
                                new TranslatableComponent("command.lens.fov").append(Component.nullToEmpty(String.valueOf(number)).copy().withStyle(ChatFormatting.GREEN)),
                                false);
                        updateFov(number); // Doing a thing
                        return 0;
                    })
                ));
    }

    public static void updateFov(int number) {
        Minecraft.getInstance().options.fov = evalFovNumber(number);;
        Minecraft.getInstance().levelRenderer.needsUpdate();
    }

    public static int evalFovNumber(int num) {
        if(num > 110) {
            return 110;
        }
        if(num < 30) {
            return 30;
        }
        return num;
    }
}
