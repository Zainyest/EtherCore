package zainyest.ethercore.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.EtherCoreClient;
import zainyest.ethercore.util.EtherData;

public class EtherHudOverlay {
    private static final Identifier ETHER_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/ether/pool_bar_blue.png");

    public static void render(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }


        // TODO make these three into "main" bars ("MainPool"s), delegate to "PassiveTechnique"s for access, render sub-bars below with unique renderers as "CustomPool"
        // render stamina bar top left
        float staminaPercentFilled = ((float) EtherData.STAMINA.getVal(EtherCoreClient.clientPlayerData.persistentData)) / ((float) EtherData.STAMINA.getMax(EtherCoreClient.clientPlayerData.persistentData));
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 4, 0, 5, (int) (182*staminaPercentFilled), 5, 182,10, 0xff7fff7f);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 4, 0, 0, 182, 5, 182,10);
        context.drawTextWithShadow(client.textRenderer, Text.literal(EtherData.STAMINA.getVal(EtherCoreClient.clientPlayerData.persistentData) + "/" + EtherData.STAMINA.getMax(EtherCoreClient.clientPlayerData.persistentData)).fillStyle(Style.EMPTY.withColor(Formatting.GREEN)), 188, 4, 0xff7fff7f);

        // render mental energy bar top left
        float mentalEnergyPercentFilled = ((float) EtherData.MENTAL_ENERGY.getVal(EtherCoreClient.clientPlayerData.persistentData)) / ((float) EtherData.MENTAL_ENERGY.getMax(EtherCoreClient.clientPlayerData.persistentData));
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 14, 0, 5, (int) (182*mentalEnergyPercentFilled), 5, 182,10, 0xff7f7fff);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 14, 0, 0, 182, 5, 182,10);
        context.drawTextWithShadow(client.textRenderer, Text.literal(EtherData.MENTAL_ENERGY.getVal(EtherCoreClient.clientPlayerData.persistentData) + "/" + EtherData.MENTAL_ENERGY.getMax(EtherCoreClient.clientPlayerData.persistentData)).fillStyle(Style.EMPTY.withColor(Formatting.BLUE)), 188, 14, 0xff7f7fff);

        // render ether bar top left
        float etherPercentFilled = ((float) EtherData.ETHER.getVal(EtherCoreClient.clientPlayerData.persistentData)) / ((float) EtherData.ETHER.getMax(EtherCoreClient.clientPlayerData.persistentData));
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 24, 0, 5, (int) (182*etherPercentFilled), 5, 182,10);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 24, 0, 0, 182, 5, 182,10);
        context.drawTextWithShadow(client.textRenderer, Text.literal(EtherData.ETHER.getVal(EtherCoreClient.clientPlayerData.persistentData) + "/" + EtherData.ETHER.getMax(EtherCoreClient.clientPlayerData.persistentData)).fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), 188, 24, 0xff7fff7f);
    }
}
