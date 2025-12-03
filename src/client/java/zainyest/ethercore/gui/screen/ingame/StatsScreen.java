package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
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

    private static final Identifier TREE_SCREEN_BACKDROP = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_screen_backdrop.png");
    private static final Identifier CURRENT_TREE_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_view_large.png");

    //private double offset_x = 80;
    private double offset_y = 0;

    public StatsScreen() {
        super(Text.empty());
        this.parent = null;
    }

    public StatsScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        //offset_x = (double) (backgroundWidth - 16) / 2;
        offset_y = 0;

        // Create widgets here
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        TabWidget treeScreenTabWidget = new TabWidget(x - 17, y + 22, false, (btn) -> MinecraftClient.getInstance().setScreen(new TreeScreen()));
        this.addDrawableChild(treeScreenTabWidget);

        TabWidget statsScreenTabWidget = new TabWidget(x - 17, y + 22*2, true, (btn) -> {});
        this.addDrawableChild(statsScreenTabWidget);

        TabWidget techniqueManagerScreenTabWidget = new TabWidget(x - 17, y + 22*3, false, (btn) -> MinecraftClient.getInstance().setScreen(new TreeScreen()));
        this.addDrawableChild(techniqueManagerScreenTabWidget);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        //render here
        drawBackground(context, delta, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TREE_SCREEN_BACKDROP, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CURRENT_TREE_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        // lerped tree port, oscillated vertically
        float lerpedAmount = MathHelper.abs(MathHelper.sin((float) (Util.getMeasuringTimeMs() / 1000.0)));
        int lerpedColor1 = ColorHelper.lerp(lerpedAmount, 0x140059ff, 0x28347aff);
        int lerpedColor2 = ColorHelper.lerp(lerpedAmount, 0x28347aff, 0x140059ff);
        context.fillGradient(x+8, y+8, x+8+241, y+8+241, lerpedColor1, lerpedColor2);

        drawStatsText(context, deltaTicks, mouseX, mouseY, x+8, y+8, 240, 240);

        for (Element e : this.children()) { // render active tab over background
            if (e instanceof TabWidget) {
                if (((TabWidget) e).isCurrent()) {
                    ((TabWidget) e).renderWidget(context, mouseX, mouseY, deltaTicks);
                }
            }
        }
    }

    public void drawStatsText(DrawContext context, float deltaTicks, int mouseX, int mouseY, int pos_x, int pos_y, int viewWidth, int viewHeight) {
        int currentPos_y = pos_y + (int) Math.round(this.offset_y);
        // Parse tree data and render
        context.enableScissor(pos_x, pos_y, pos_x+viewWidth, pos_y+viewHeight);
        int statOffset = 0;
        for (EtherStatView v : PlayerEtherStats.fromPlayerData(EtherCoreClient.clientPlayerData).statViewList().sequencedValues()) {
            statOffset += renderStatsText(context, pos_x, currentPos_y + statOffset, viewWidth, v);
        }

        context.disableScissor();
    }

    public int renderStatsText(DrawContext context, int x, int y, int viewWidth, EtherStatView statView) {
        StringVisitable titleVisitable = StringVisitable.styled(Text.translatable(Objects.requireNonNull(EtherRegistries.ETHER_STATS.get(Identifier.of(EtherCore.MOD_ID, statView.name()))).getTranslatableName()).getString(), Style.EMPTY.withBold(true));
        StringVisitable descriptionVisitable = StringVisitable.plain(Text.translatable(Objects.requireNonNull(EtherRegistries.ETHER_STATS.get(Identifier.of(EtherCore.MOD_ID, statView.name()))).getTranslatableDescription()).getString());
        StringVisitable statCompoundVisitable = StringVisitable.plain(statView.base() + " + " + (statView.getStatTotal() - statView.base()));
        assert client != null;
        List<OrderedText> title = client.textRenderer.wrapLines(titleVisitable, viewWidth);
        List<OrderedText> description = client.textRenderer.wrapLines(descriptionVisitable, viewWidth - 6);
        List<OrderedText> statCompound = client.textRenderer.wrapLines(statCompoundVisitable, viewWidth);
        int titleHeight = client.textRenderer.getWrappedLinesHeight(titleVisitable, viewWidth);
        int descriptionHeight = client.textRenderer.getWrappedLinesHeight(descriptionVisitable, viewWidth);
        int statCompoundHeight = client.textRenderer.getWrappedLinesHeight(statCompoundVisitable, viewWidth);

        int backgroundHeight = 15
                + titleHeight
                + descriptionHeight
                + statCompoundHeight;

        drawText(context, title, x + 6, y + 6, 0xFF0059FF);
        drawText(context, description, x + 6, y + 6 + titleHeight + 3, 0xFFFFFFFF);
        drawText(context, statCompound, x + 6, y + 6 + titleHeight + descriptionHeight + 3, 0xFFFFFFFF);

        return backgroundHeight;
    }

    private void drawText(DrawContext context, List<OrderedText> text, int x, int y, int color) {
        assert this.client != null;
        TextRenderer textRenderer = this.client.textRenderer;

        for(int i = 0; i < text.size(); ++i) {
            OrderedText var10002 = text.get(i);
            Objects.requireNonNull(textRenderer);
            context.drawTextWithShadow(textRenderer, var10002, x, y + i * 9, color);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        //this.offset_x -= (int) horizontalAmount;
        this.offset_y += (int) verticalAmount * 4;
        this.offset_y = Math.clamp(this.offset_y, -999, 0);
        return true;
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //TechniqueTree window (x, y, width, height): x+8, y+8, 160, 150
        if (click.x() < x+8+241 && click.x() > x+8 && click.y() < y+8+241 && click.y() > y+8) {
            //this.offset_x += offsetX;
            this.offset_y += offsetY;
            this.offset_y = Math.clamp(this.offset_y, -999, 0);
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }
}
