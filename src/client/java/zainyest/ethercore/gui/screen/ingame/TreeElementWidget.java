package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public class TreeElementWidget extends ClickableWidget {
    private static final Identifier TREE_ELEMENT_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element.png");
    private static final Identifier TREE_ELEMENT_HIGHLIGHTED_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element_highlighted.png");

    public TreeElementWidget(int x, int y, int width, int height, Text message) {
        super(x, y, width, height, message);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.isFocused() ? TREE_ELEMENT_HIGHLIGHTED_TEXTURE : TREE_ELEMENT_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}
}
