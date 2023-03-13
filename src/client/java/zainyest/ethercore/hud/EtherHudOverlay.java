package zainyest.ethercore.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

public class EtherHudOverlay implements HudRenderCallback {
    private static final Identifier ETHER_TEXTURE = new Identifier(EtherCore.MOD_ID, "textures/ether/pool_bar_blue.png");
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        int x = 0;
        int y = 0;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();
        x = width / 2;
        y = height;

        /*// render ether bar above toolbar
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, ETHER_TEXTURE);
        DrawableHelper.drawTexture(matrixStack, x-91, y-45, 0, 0, 182, 5, 182,10);

        float etherPercentFilled = ((float) EtherData.ETHER.getVal((IEntityDataSaver) client.player)) / ((float) EtherData.ETHER.getMax((IEntityDataSaver) client.player));
        DrawableHelper.drawTexture(matrixStack, x-91, y-45, 0, 5, (int) (182*etherPercentFilled), 5, 182,10);*/

        // render stamina bar top left
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(0.5f, 1.0f, 0.5f, 1.0f);
        RenderSystem.setShaderTexture(0, ETHER_TEXTURE);
        DrawableHelper.drawTexture(matrixStack, 4, 4, 0, 0, 182, 5, 182,10);

        float staminaPercentFilled = ((float) EtherData.STAMINA.getVal((IEntityDataSaver) client.player)) / ((float) EtherData.STAMINA.getMax((IEntityDataSaver) client.player));
        DrawableHelper.drawTexture(matrixStack, 4, 4, 0, 5, (int) (182*staminaPercentFilled), 5, 182,10);
        DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, Text.literal(EtherData.STAMINA.getVal((IEntityDataSaver) client.player) + "/" + EtherData.STAMINA.getMax((IEntityDataSaver) client.player)).fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), 188, 4, 0);

        // render mental energy bar top left
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(0.5f, 0.5f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, ETHER_TEXTURE);
        DrawableHelper.drawTexture(matrixStack, 4, 14, 0, 0, 182, 5, 182,10);

        float mentalEnergyPercentFilled = ((float) EtherData.MENTAL_ENERGY.getVal((IEntityDataSaver) client.player)) / ((float) EtherData.MENTAL_ENERGY.getMax((IEntityDataSaver) client.player));
        DrawableHelper.drawTexture(matrixStack, 4, 14, 0, 5, (int) (182*mentalEnergyPercentFilled), 5, 182,10);
        DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, Text.literal(EtherData.MENTAL_ENERGY.getVal((IEntityDataSaver) client.player) + "/" + EtherData.MENTAL_ENERGY.getMax((IEntityDataSaver) client.player)).fillStyle(Style.EMPTY.withColor(Formatting.BLUE)), 188, 14, 0);

        // render ether bar top left
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, ETHER_TEXTURE);
        DrawableHelper.drawTexture(matrixStack, 4, 24, 0, 0, 182, 5, 182,10);

        float etherPercentFilled = ((float) EtherData.ETHER.getVal((IEntityDataSaver) client.player)) / ((float) EtherData.ETHER.getMax((IEntityDataSaver) client.player));
        DrawableHelper.drawTexture(matrixStack, 4, 24, 0, 5, (int) (182*etherPercentFilled), 5, 182,10);
        DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, Text.literal(EtherData.ETHER.getVal((IEntityDataSaver) client.player) + "/" + EtherData.ETHER.getMax((IEntityDataSaver) client.player)).fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), 188, 24, 0);

        // for single-texture non-bar files (repeats a texture)
        /*for (int i = 0; i < 10; i++) {
            if ((percentFilled * 10.0f) > i) {
                DrawableHelper.drawTexture(matrixStack, x - 93 + (i*8), y-53, 0, 0, 12, 12, 12 ,12);
            } else {
                break;
            }
        }*/
    }
}
