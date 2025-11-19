package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.EtherRegistries;

import java.util.List;
import java.util.Objects;

public class TreeElementWidget extends ClickableWidget {
    private static final Identifier TREE_ELEMENT_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element.png");
    private static final Identifier TREE_ELEMENT_HIGHLIGHTED_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element_highlighted.png");
    private static final Identifier TREE_ELEMENT_TOOLTIP_SPRITE = Identifier.of(EtherCore.MOD_ID, "tree_element_tooltip");
    private final MinecraftClient client;
    private final Identifier icon;
    private final String name;
    private boolean learned = false;

    public TreeElementWidget(int x, int y, int width, int height, String name, Identifier icon, MinecraftClient client) {
        super(x, y, width, height, Text.literal(""));
        this.client = client;
        this.icon = icon;
        this.name = name;
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if (this.isHovered()) {
            this.learned = !this.learned;
        }
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (this.isInteractable()) {
            if (this.isValidClickButton(click.buttonInfo())) {
                boolean bl = this.isMouseOver(click.x(), click.y());
                if (bl) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    this.onClick(click, doubled);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.fill(this.getX()+3, this.getY()+3, this.getX()+3+this.width-6, this.getY()+3+this.height-6, 0xff000000);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.icon, this.getX()+3, this.getY()+3, 0, 0, this.width-6, this.height-6, 16-6, 16-6, this.learned ? 0xffffffff : 0x7fffffff);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.isFocused() || this.isHovered() ? TREE_ELEMENT_HIGHLIGHTED_TEXTURE : TREE_ELEMENT_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16);
    }

    /// SPOOKY MAGIC NUMBER ZONE
    public void renderToolTip(DrawContext context, int screenWidth) {
        StringVisitable titleVisitable = StringVisitable.styled(Text.translatable(Objects.requireNonNull(EtherRegistries.TECHNIQUES.get(Identifier.of(EtherCore.MOD_ID, this.name))).getTranslatableName()).getString(), Style.EMPTY.withBold(true));
        StringVisitable descriptionVisitable = StringVisitable.plain(Text.translatable(Objects.requireNonNull(EtherRegistries.TECHNIQUES.get(Identifier.of(EtherCore.MOD_ID, this.name))).getDescription()).getString());
        List<OrderedText> title = client.textRenderer.wrapLines(titleVisitable, 95);
        List<OrderedText> description = client.textRenderer.wrapLines(descriptionVisitable, 95);
        int toolTip_X;
        if (this.getX() < screenWidth/2) {
            toolTip_X = this.width;
        } else {
            toolTip_X = -100;
        }
        int backgroundHeight = 15 + client.textRenderer.getWrappedLinesHeight(titleVisitable, 95) + client.textRenderer.getWrappedLinesHeight(descriptionVisitable, 95);
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, TREE_ELEMENT_TOOLTIP_SPRITE, this.getX() + toolTip_X, this.getY(), 100, backgroundHeight);
        drawText(context, title, this.getX() + toolTip_X + 6, this.getY() + 6, 0xFF0059FF);
        drawText(context, description, this.getX() + toolTip_X + 6, this.getY() + 6 + client.textRenderer.getWrappedLinesHeight(titleVisitable, 95) + 3, 0xFFFFFFFF);
    }

    private void drawText(DrawContext context, List<OrderedText> text, int x, int y, int color) {
        TextRenderer textRenderer = this.client.textRenderer;

        for(int i = 0; i < text.size(); ++i) {
            OrderedText var10002 = text.get(i);
            Objects.requireNonNull(textRenderer);
            context.drawTextWithShadow(textRenderer, var10002, x, y + i * 9, color);
        }

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    public String getName() {
        return name;
    }

}
