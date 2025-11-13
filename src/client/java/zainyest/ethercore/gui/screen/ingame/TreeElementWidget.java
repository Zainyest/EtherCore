package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;

public class TreeElementWidget extends ClickableWidget {
    private static final Identifier TREE_ELEMENT_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element.png");
    private static final Identifier TREE_ELEMENT_HIGHLIGHTED_TEXTURE = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_element_highlighted.png");
    private Identifier icon;
    private String name;
    private boolean learned = false;

    public TreeElementWidget(int x, int y, int width, int height, String name, Identifier icon) {
        super(x, y, width, height, Text.literal(""));
        this.icon = icon;
        this.name = name;
    }

    @Override
    public void onClick(Click click, boolean doubled) {
        if (this.isHovered()) {
            this.learned = !this.learned;
        }
    }

//    @Override
//    public boolean isInteractable() {
//        return this.learned;
//    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        if (!this.isInteractable()) {
            return false;
        } else {
            if (this.isValidClickButton(click.buttonInfo())) {
                boolean bl = this.isMouseOver(click.x(), click.y());
                if (bl) {
                    this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    this.onClick(click, doubled);
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.icon, this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16, this.learned ? 0xffffffff : 0x7fffffff);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, this.isFocused() || this.isHovered() ? TREE_ELEMENT_HIGHLIGHTED_TEXTURE : TREE_ELEMENT_TEXTURE, this.getX(), this.getY(), 0, 0, this.width, this.height, 16, 16);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLearned() {
        return learned;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }
}
