package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.cursor.StandardCursors;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import zainyest.ethercore.EtherCore;

public class MeridianWidget extends ButtonWidget {

    public ButtonTextures textures;
    public int highlightRenderOffset_X = 0, highlightRenderOffset_Y = 0;

    public static MeridianWidget.Builder mBuilder(Text message, PressAction onPress) {
        return new Builder(message, onPress);
    }

    protected MeridianWidget(int x, int y, int width, int height, Text message, PressAction onPress, NarrationSupplier narrationSupplier) {
        super(x, y, width, height, message, onPress, narrationSupplier);
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        //EtherCore.LOGGER.info(String.valueOf(textures.get(this.active, this.isSelected())));
        //context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, textures.get(this.active, this.isSelected()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
        context.drawTexture(RenderPipelines.GUI_TEXTURED, textures.get(this.active, this.isSelected()), this.getX(), this.isSelected() ? this.getY()-highlightRenderOffset_Y : this.getY(), 0, 0, this.isSelected() ? this.getWidth()+highlightRenderOffset_X : this.getWidth(), this.isSelected() ? this.getHeight()+highlightRenderOffset_Y : this.getHeight(), this.isSelected() ? 49+highlightRenderOffset_X : 49, this.isSelected() ? 16+highlightRenderOffset_Y : 16);
        int i = ColorHelper.withAlpha(this.alpha, this.active ? -1 : -6250336);
        this.drawMessage(context, minecraftClient.textRenderer, i);
        if (this.isHovered()) {
            context.setCursor(this.isInteractable() ? StandardCursors.POINTING_HAND : StandardCursors.NOT_ALLOWED);
        }
    }

    public static class Builder {
        private ButtonTextures textures = new ButtonTextures(Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/head_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/head_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/head_meridians_highlighted.png"));
        private int x;
        private int y;
        private int width;
        private int height;
        private Text message;
        private PressAction onPress;
        private NarrationSupplier narrationSupplier;
        @Nullable
        private Tooltip tooltip;
        private int highlightRenderOffset_X = 0, highlightRenderOffset_Y = 0;

        public Builder(Text message, PressAction onPress) {
            this.narrationSupplier = ButtonWidget.DEFAULT_NARRATION_SUPPLIER;
            this.message = message;
            this.onPress = onPress;
        }

        public Builder texturePaths(ButtonTextures textures) {
            this.textures = textures;
            return this;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public Builder tooltip(@Nullable Tooltip tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Builder narrationSupplier(NarrationSupplier narrationSupplier) {
            this.narrationSupplier = narrationSupplier;
            return this;
        }

        public Builder highlightRenderOffset(int offset_x, int offset_y) {
            this.highlightRenderOffset_X = offset_x;
            this.highlightRenderOffset_Y = offset_y;
            return this;
        }

        public MeridianWidget build() {
            MeridianWidget meridianWidget = new MeridianWidget(this.x, this.y, this.width, this.height, this.message, this.onPress, this.narrationSupplier);
            meridianWidget.setTooltip(this.tooltip);
            meridianWidget.textures = this.textures;
            meridianWidget.highlightRenderOffset_X = this.highlightRenderOffset_X;
            meridianWidget.highlightRenderOffset_Y = this.highlightRenderOffset_Y;
            return meridianWidget;
        }
    }
}
