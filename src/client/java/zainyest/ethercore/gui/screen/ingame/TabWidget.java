package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import zainyest.ethercore.EtherCore;

public class TabWidget extends AbstractWidget {
    // 21x20
    private static final Identifier TAB_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/tab/tab.png");
    // 17x20
    private static final Identifier TAB_HIGHLIGHTED_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "textures/gui/tab/tab_highlighted.png");

    private Identifier ICON_TEXTURE = Identifier.fromNamespaceAndPath(EtherCore.MOD_ID, "icon.png");

    private final boolean isCurrent;
    private final TabWidget.ClickAction clickAction;

    public TabWidget(int x, int y, boolean isCurrent, TabWidget.ClickAction clickAction) {
        super(x, y, 21, 20, Component.literal(""));
        this.isCurrent = isCurrent;
        this.clickAction = clickAction;
    }

    public TabWidget(Identifier icon, int x, int y, boolean isCurrent, TabWidget.ClickAction clickAction) {
        super(x, y, 21, 20, Component.literal(""));
        this.isCurrent = isCurrent;
        this.clickAction = clickAction;
        this.ICON_TEXTURE = icon;
    }

    public boolean isCurrent() {return this.isCurrent;}

    @Override
    public void onClick(MouseButtonEvent click, boolean doubled) {
        if (this.isHovered()) {
            this.clickAction.onClick(this);
        }
    }

    @Override
    protected void renderWidget(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        context.blit(RenderPipelines.GUI_TEXTURED, this.isCurrent ? TAB_HIGHLIGHTED_TEXTURE : TAB_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, this.isCurrent ? 21 : 17, 20);
        context.blit(RenderPipelines.GUI_TEXTURED, ICON_TEXTURE, this.getX() + 5, this.getY() + 5, 0, 0, 10, 10, 10, 10, this.isCurrent ? 0xFFFFFFFF : 0xDDFFFFFF);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {}

    public interface ClickAction {
        void onClick(TabWidget button);
    }
}
