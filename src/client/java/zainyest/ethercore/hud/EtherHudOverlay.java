package zainyest.ethercore.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.DeltaTracker;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.Identifier;
import org.apache.logging.log4j.core.pattern.NotANumber;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.EtherCoreClient;
import zainyest.ethercore.etherpool.EtherPoolView;
import zainyest.ethercore.init.EtherPools;

public class EtherHudOverlay {
    private static final Identifier ETHER_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/ether/pool_bar_blue.png");

    public static void render(GuiGraphics context, DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null) {
            return;
        }


        // TODO make these three into "main" bars ("MainPool"s), delegate to "PassiveTechnique"s for access, render sub-bars below with unique renderers as "CustomPool"
        // render stamina bar top left
        EtherPoolView stamina = EtherPools.STAMINA.fromPlayerData(EtherCoreClient.clientPlayerData);
        float staminaPercentFilled = ((float) stamina.val()) / ((float) stamina.max());
        context.blit(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 4, 0, 5, (int) (182*staminaPercentFilled), 5, 182,10, 0xff7fff7f);
        context.blit(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 4, 0, 0, 182, 5, 182,10);
        context.drawString(client.font, Component.literal(stamina.val() + "/" + stamina.max()).withStyle(Style.EMPTY.withColor(ChatFormatting.GREEN)), 188, 4, 0xff7fff7f);

        // render mental energy bar top left
        EtherPoolView mentalEnergy = EtherPools.MENTAL_ENERGY.fromPlayerData(EtherCoreClient.clientPlayerData);
        float mentalEnergyPercentFilled = ((float) mentalEnergy.val()) / ((float) mentalEnergy.max());
//        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 14, 0, 5, (int) (182*mentalEnergyPercentFilled), 5, 182,10, 0xff7f7fff);
//        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 14, 0, 0, 182, 5, 182,10);
//        context.drawTextWithShadow(client.textRenderer, Text.literal(mentalEnergy.val() + "/" + mentalEnergy.max()).fillStyle(Style.EMPTY.withColor(Formatting.BLUE)), 188, 14, 0xff7f7fff);

        // render ether bar top left
        EtherPoolView ether = EtherPools.ETHER.fromPlayerData(EtherCoreClient.clientPlayerData);
        float etherPercentFilled = ((float) ether.val()) / ((float) ether.max());
//        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 24, 0, 5, (int) (182*etherPercentFilled), 5, 182,10);
//        context.drawTexture(RenderPipelines.GUI_TEXTURED, ETHER_TEXTURE, 4, 24, 0, 0, 182, 5, 182,10);
//        context.drawTextWithShadow(client.textRenderer, Text.literal(ether.val() + "/" + ether.max()).fillStyle(Style.EMPTY.withColor(Formatting.AQUA)), 188, 24, 0xff7fff7f);

        int x = 2;
        int y = context.guiHeight() - 42;
        // render poe-like ether pool and mental energy pool
        context.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/mentalpool_and_etherpool/border.png"), x, y, 0, 0, 40, 40, 40, 40);
        context.blit(RenderPipelines.GUI_TEXTURED, Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/mentalpool_and_etherpool/semicircle_border.png"), x, y, 0, 0, 40, 40, 40, 40);

        int etherTextureNum = Float.isNaN(etherPercentFilled) ? 27 : Math.clamp(Math.round((1-etherPercentFilled) * 27), 1, 27);
        Identifier etherFilledTexture = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/mentalpool_and_etherpool/main_pool" + etherTextureNum + ".png");
        context.blit(RenderPipelines.GUI_TEXTURED, etherFilledTexture, x, y, 0, 0, 40, 40, 40, 40);

        int mentalTextureNum = Float.isNaN(mentalEnergyPercentFilled) ? 53 : Math.clamp(Math.round((1-mentalEnergyPercentFilled) * 53), 1, 53);
        Identifier mentalFilledTexture = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/mentalpool_and_etherpool/semicircle_pool" + mentalTextureNum + ".png");
        context.blit(RenderPipelines.GUI_TEXTURED, mentalFilledTexture, x, y, 0, 0, 40, 40, 40, 40);

        context.drawString(client.font, Component.literal(ether.val() + "/" + ether.max()).withStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), x + 42, y, 0xff7fff7f);
        context.drawString(client.font, Component.literal(mentalEnergy.val() + "/" + mentalEnergy.max()).withStyle(Style.EMPTY.withColor(ChatFormatting.BLUE)), x + 42, y + 10, 0xff7f7fff);
    }
}
