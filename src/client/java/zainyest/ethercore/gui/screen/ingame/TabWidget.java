package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public class TabWidget extends ClickableWidget {
    // 21x20
    private static final Identifier TAB_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/tab/tab.png");
    // 17x20
    private static final Identifier TAB_HIGHLIGHTED_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/tab/tab_highlighted.png");

    private final boolean isCurrent;
    private final TabWidget.ClickAction clickAction;

    public TabWidget(int x, int y, boolean isCurrent, TabWidget.ClickAction clickAction) {
        super(x, y, 21, 20, Text.literal(""));
        this.isCurrent = isCurrent;
        this.clickAction = clickAction;
    }

    public boolean isCurrent() {return this.isCurrent;}

    @Override
    public void onClick(Click click, boolean doubled) {
        if (this.isHovered()) {
            this.clickAction.onClick(this);
        }
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.isCurrent ? TAB_HIGHLIGHTED_TEXTURE : TAB_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, this.isCurrent ? 21 : 17, 20);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    public interface ClickAction {
        void onClick(TabWidget button);
    }
}
