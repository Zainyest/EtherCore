package zainyest.ethercore.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.util.EtherData;
import zainyest.ethercore.util.IEntityDataSaver;

public class EtherHudOverlay implements HudRenderCallback {
    private static final Identifier FILLED_ETHER = new Identifier(EtherCore.MOD_ID, "textures/ether/filled_ether.png");
    private static final Identifier EMPTY_ETHER = new Identifier(EtherCore.MOD_ID, "textures/ether/empty_ether.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        int x = 0;
        int y = 0;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client != null) {
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();

            x = width / 2;
            y = height;
        }

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, EMPTY_ETHER);
        for (int i = 0; i < 10; i++) {
            DrawableHelper.drawTexture(matrixStack, x - 93 + (i*8), y-53, 0, 0, 12, 12, 12 ,12);
        }

        RenderSystem.setShaderTexture(0, FILLED_ETHER);


        assert client != null;
        assert client.player != null;
        float percentFilled = ((float) EtherData.ETHER.getVal((IEntityDataSaver) client.player)) / ((float) EtherData.ETHER.getMax((IEntityDataSaver) client.player));
        for (int i = 0; i < 10; i++) {
            if ((percentFilled * 10.0f) > i) {
                DrawableHelper.drawTexture(matrixStack, x - 93 + (i*8), y-53, 0, 0, 12, 12, 12 ,12);
            } else {
                break;
            }
        }
    }
}
