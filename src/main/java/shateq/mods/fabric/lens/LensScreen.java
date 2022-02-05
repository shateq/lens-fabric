package shateq.mods.fabric.lens;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Option;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class LensScreen extends Screen {
    private static final int WHITE = 16777215;

    public LensScreen() {
        super(NarratorChatListener.NO_TITLE);
    }

    @Override
    protected void init() {
        assert this.minecraft != null;
        int horizontal = this.width / 2 - 75;
        int vertical = this.height / 6 + 80;
        this.addRenderableWidget(Option.FOV.createButton(this.minecraft.options, horizontal, vertical, 150)); // Calling Option.FOV
        super.init();
    }

    // Don't stop me now!
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private boolean checkToClose() {
        assert this.minecraft != null; // Complaining
        if (!InputConstants.isKeyDown(this.minecraft.getWindow().getWindow(), 96)) {
            this.minecraft.setScreen(null);
            return true;
        } else {
            return false;
        }
    }

    public void render(@NotNull PoseStack poseStack, int i, int j, float deltaTime) {
        if (!this.checkToClose()) {
            poseStack.pushPose();
            RenderSystem.enableBlend();

            poseStack.popPose();
            super.render(poseStack, i, j, deltaTime);
            drawCenteredString(poseStack, this.font, new TranslatableComponent("gui.lens.text"), this.width / 2, this.height / 2 + 12, WHITE);
            /* Blit-ing, whenever I'll want to add my graphics
            static final ResourceLocation LOCATION = new ResourceLocation("textures/gui/container/gamemode_switcher.png");
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, LOCATION);
            poseStack.pushPose()
            int k = this.width / 2 - 62;
            int l = this.height / 2 - 31 - 27;
            blit(poseStack, k, l, 0.0F, 0.0F, 125, 75, 128, 128);*/
        }
    }
}
