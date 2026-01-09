package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Util;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.EtherCoreClient;
import zainyest.ethercore.etherstat.EtherStatView;
import zainyest.ethercore.etherstat.PlayerEtherStats;
import zainyest.ethercore.init.EtherRegistries;

import java.util.List;
import java.util.Objects;

public class StatsScreen extends Screen {
    private final Screen parent;
    protected int backgroundWidth = 256;
    protected int backgroundHeight = 256;

    private static final Identifier TREE_SCREEN_BACKDROP = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_screen_backdrop.png");
    private static final Identifier CURRENT_TREE_VIEWPORT = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_view_large.png");
    public static final Identifier ICON_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/tab/stat_screen_symbol.png");

    private double offset_y = 0;
    private int contentHeight = 240;

    public StatsScreen() {
        super(Component.empty());
        this.parent = null;
    }

    public StatsScreen(Component title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        offset_y = 0;

        // Create widgets here
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        TabWidget treeScreenTabWidget = new TabWidget(TreeScreen.ICON_TEXTURE, x - 17, y + 22, false, (btn) -> Minecraft.getInstance().setScreen(new TreeScreen()));
        this.addRenderableWidget(treeScreenTabWidget);

        TabWidget statsScreenTabWidget = new TabWidget(ICON_TEXTURE, x - 17, y + 22*2, true, (btn) -> {});
        this.addRenderableWidget(statsScreenTabWidget);

        TabWidget techniqueManagerScreenTabWidget = new TabWidget(x - 17, y + 22*3, false, (btn) -> Minecraft.getInstance().setScreen(new TreeScreen()));
        this.addRenderableWidget(techniqueManagerScreenTabWidget);
    }

    @Override
    public void render(@NonNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        //render here
        drawBackground(context, delta, mouseX, mouseY);
    }

    protected void drawBackground(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // background
        context.blit(RenderPipelines.GUI_TEXTURED, TREE_SCREEN_BACKDROP, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.blit(RenderPipelines.GUI_TEXTURED, CURRENT_TREE_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        // lerped tree port, oscillated vertically
        float lerpedAmount = Mth.abs(Mth.sin((float) (Util.getMillis() / 1000.0)));
        int lerpedColor1 = ARGB.srgbLerp(lerpedAmount, 0x140059ff, 0x28347aff);
        int lerpedColor2 = ARGB.srgbLerp(lerpedAmount, 0x28347aff, 0x140059ff);
        context.fillGradient(x+8, y+8, x+8+241, y+8+241, lerpedColor1, lerpedColor2);

        drawStatsText(context, deltaTicks, mouseX, mouseY, x+8, y+8, 240, 240);

        for (GuiEventListener e : this.children()) { // render active tab over background
            if (e instanceof TabWidget) {
                if (((TabWidget) e).isCurrent()) {
                    ((TabWidget) e).renderWidget(context, mouseX, mouseY, deltaTicks);
                }
            }
        }
    }

    public void drawStatsText(GuiGraphics context, float deltaTicks, int mouseX, int mouseY, int pos_x, int pos_y, int viewWidth, int viewHeight) {
        int currentPos_y = pos_y + (int) Math.round(this.offset_y);
        // Parse tree data and render
        context.enableScissor(pos_x, pos_y, pos_x+viewWidth, pos_y+viewHeight);
        int statOffset = 0;
        for (EtherStatView v : PlayerEtherStats.fromPlayerData(EtherCoreClient.clientPlayerData).statViewList().sequencedValues()) {
            statOffset += renderStatsText(context, mouseX, mouseY, pos_x, currentPos_y + statOffset, viewWidth, v);
        }
        contentHeight = statOffset - viewHeight;
        context.disableScissor();
    }

    public int renderStatsText(GuiGraphics context, int mouseX, int mouseY, int x, int y, int viewWidth, EtherStatView statView) {
        FormattedText titleVisitable = FormattedText.of(Component.translatable(Objects.requireNonNull(EtherRegistries.ETHER_STATS.getValue(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, statView.name()))).getTranslatableName()).getString(), Style.EMPTY.withBold(true));
        FormattedText descriptionVisitable = FormattedText.of(Component.translatable(Objects.requireNonNull(EtherRegistries.ETHER_STATS.getValue(Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, statView.name()))).getTranslatableDescription()).getString());
        FormattedText statCompoundVisitable = FormattedText.of(statView.base() + " + " + (statView.getStatTotal() - statView.base()));
        FormattedText statModifiersVisitable = FormattedText.of(statView.statModifiersTranslated());
        List<FormattedCharSequence> title = minecraft.font.split(titleVisitable, viewWidth);
        List<FormattedCharSequence> description = minecraft.font.split(descriptionVisitable, viewWidth - 6);
        List<FormattedCharSequence> statCompound = minecraft.font.split(statCompoundVisitable, viewWidth);
        List<FormattedCharSequence> statModifiers = minecraft.font.split(statModifiersVisitable, viewWidth);
        int titleHeight = minecraft.font.wordWrapHeight(titleVisitable, viewWidth);
        int descriptionHeight = minecraft.font.wordWrapHeight(descriptionVisitable, viewWidth);
        int statCompoundHeight = minecraft.font.wordWrapHeight(statCompoundVisitable, viewWidth);
        int statModifiersHeight = minecraft.font.wordWrapHeight(statModifiersVisitable, viewWidth);

        int backgroundHeight = 15
                + titleHeight
                + descriptionHeight
                + statCompoundHeight
                + statModifiersHeight;

        drawText(context, title, x + 6, y + 6, 0xFF0059FF);
        drawText(context, description, x + 6, y + 6 + titleHeight + 3, 0xFFFFFFFF);
        drawText(context, statCompound, x + 6, y + 6 + titleHeight + descriptionHeight + 3, 0xFFFFFFFF);
        drawText(context, statModifiers, x + 6, y + 6 + titleHeight + descriptionHeight + statCompoundHeight + 3, 0xFFFFFFFF);

        return backgroundHeight;
    }

    private void drawText(GuiGraphics context, List<FormattedCharSequence> text, int x, int y, int color) {
        Font textRenderer = this.minecraft.font;

        for(int i = 0; i < text.size(); ++i) {
            FormattedCharSequence var10002 = text.get(i);
            Objects.requireNonNull(textRenderer);
            context.drawString(textRenderer, var10002, x, y + i * 9, color);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.offset_y += (int) verticalAmount * 4;
        this.offset_y = Math.clamp(this.offset_y, -this.contentHeight, 0);
        return true;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent click, double offsetX, double offsetY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //TechniqueTree window (x, y, width, height): x+8, y+8, 160, 150
        if (click.x() < x+8+241 && click.x() > x+8 && click.y() < y+8+241 && click.y() > y+8) {
            this.offset_y += offsetY;
            this.offset_y = Math.clamp(this.offset_y, -this.contentHeight, 0);
            return true;
        }
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }
}
