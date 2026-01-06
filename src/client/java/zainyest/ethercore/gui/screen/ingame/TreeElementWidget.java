package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.init.EtherRegistries;

import java.util.*;

public class TreeElementWidget extends AbstractWidget {
    private static final Identifier TREE_ELEMENT_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element.png");
    private static final Identifier TREE_ELEMENT_HIGHLIGHTED_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element_highlighted.png");
    private static final Identifier TREE_ELEMENT_TOOLTIP_SPRITE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "tree_element_tooltip");
    private final Minecraft client;
    private final Identifier icon;
    private final String name;
    private boolean selected = false;
    private boolean learned = false;
    private float angle = 0;
    private final TreeElementWidget parent;
    public LinkedList<TreeElementWidget> children = new LinkedList<>();
    public boolean matchesSearch = false;

    public TreeElementWidget(int x, int y, int width, int height, TreeElementWidget parent, String name, Identifier icon, Minecraft client) {
        super(x, y, width, height, Component.literal(""));
        this.client = client;
        this.icon = icon;
        this.name = name;
        this.parent = parent;
    }

    private boolean isSelectable() {
        if (parent == null) {
            return !this.learned && noChildLearnedOrSelected();
        }
        return !this.learned && (parent.learned || parent.selected) && (noChildLearnedOrSelected());
    }

    private boolean noChildLearnedOrSelected() {
        boolean anyChildLearnedOrSelected = false;
        for (TreeElementWidget child : children) {
            anyChildLearnedOrSelected = anyChildLearnedOrSelected || child.learned || child.selected;
        }
        return !anyChildLearnedOrSelected;
    }

    @Override
    public void onClick(@NonNull MouseButtonEvent click, boolean doubled) {
        if (this.isHovered() && this.isSelectable()) {
            this.selected = !this.selected;
        }
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent click, boolean doubled) {
        if (this.isActive()) {
            if (this.isValidClickButton(click.buttonInfo())) {
                boolean bl = this.isMouseOver(click.x(), click.y());
                if (bl) {
                    this.playDownSound(Minecraft.getInstance().getSoundManager());
                    this.onClick(click, doubled);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        context.fill(this.getX()+3, this.getY()+3, this.getX()+3+this.width-6, this.getY()+3+this.height-6, 0xff000000);
        context.blit(RenderPipelines.GUI_TEXTURED, this.icon, this.getX()+3, this.getY()+3, 0, 0, this.width-6, this.height-6, 16-6, 16-6, this.selected ? 0xffffffff : 0x7fffffff);
        context.blit(RenderPipelines.GUI_TEXTURED, this.isFocused() || this.isHovered() ? TREE_ELEMENT_HIGHLIGHTED_TEXTURE : TREE_ELEMENT_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16, this.learned ? 0xFFFFAA00 : 0xffffffff);
        if (this.matchesSearch) {
            context.blit(RenderPipelines.GUI_TEXTURED, TREE_ELEMENT_HIGHLIGHTED_TEXTURE, this.getX()-1, this.getY()-1, 0, 0, this.width+2, this.height+2, 16+2, 16+2, this.learned ? 0xFFFFAA00 : 0xffffff00);
        }
    }

    public String getTextContent() {

        return Component.translatable(Objects.requireNonNull(EtherRegistries.TECHNIQUES.getValue(EtherCore.id(this.name))).getTranslatableName()).getString() + "\n" +
                Component.translatable(Objects.requireNonNull(EtherRegistries.TECHNIQUES.getValue(EtherCore.id(this.name))).getDescription()).getString();
    }

    /// SPOOKY MAGIC NUMBER ZONE
    public void renderToolTip(GuiGraphics context, int screenWidth) {
        FormattedText titleVisitable = FormattedText.of(Component.translatable(Objects.requireNonNull(EtherRegistries.TECHNIQUES.getValue(EtherCore.id(this.name))).getTranslatableName()).getString(), Style.EMPTY.withBold(true));
        FormattedText descriptionVisitable = FormattedText.of(Component.translatable(Objects.requireNonNull(EtherRegistries.TECHNIQUES.getValue(EtherCore.id(this.name))).getDescription()).getString());
        List<FormattedCharSequence> title = client.font.split(titleVisitable, 95);
        List<FormattedCharSequence> description = client.font.split(descriptionVisitable, 95);
        int toolTip_X;
        if (this.getX() < screenWidth/2) {
            toolTip_X = this.width;
        } else {
            toolTip_X = -110;
        }
        int backgroundHeight = 15 + client.font.wordWrapHeight(titleVisitable, 95) + client.font.wordWrapHeight(descriptionVisitable, 95);
        context.blitSprite(RenderPipelines.GUI_TEXTURED, TREE_ELEMENT_TOOLTIP_SPRITE, this.getX() + toolTip_X, this.getY(), 110, backgroundHeight);
        drawText(context, title, this.getX() + toolTip_X + 6, this.getY() + 6, 0xFF0059FF);
        drawText(context, description, this.getX() + toolTip_X + 6, this.getY() + 6 + client.font.wordWrapHeight(titleVisitable, 95) + 3, 0xFFFFFFFF);
    }

    private void drawText(GuiGraphics context, List<FormattedCharSequence> text, int x, int y, int color) {
        Font textRenderer = this.client.font;

        for(int i = 0; i < text.size(); ++i) {
            FormattedCharSequence var10002 = text.get(i);
            Objects.requireNonNull(textRenderer);
            context.drawString(textRenderer, var10002, x, y + i * 9, color);
        }

    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput builder) {}

    public String getName() {
        return name;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public LinkedList<TreeElementWidget> getChildren() {
        return this.children;
    }
}
