package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.screenhandler.MeridianScreenHandler;

public class MeridianScreen extends HandledScreen<MeridianScreenHandler> {

    private static final Identifier INVENTORY_BACKDROP = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/inventory_backdrop.png");
    private static final Identifier CATEGORIES = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/categories.png");
    private static final Identifier MERIDIANS_DISPLAY = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/meridians_display.png");
    private static final Identifier PLAYER_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/player_viewport.png");
    private static final Identifier CURRENT_TREE_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/current_tree_viewport.png");

    public MeridianScreen(MeridianScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        context.drawTexture(RenderPipelines.GUI_TEXTURED, INVENTORY_BACKDROP, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CATEGORIES, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, MERIDIANS_DISPLAY, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, PLAYER_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CURRENT_TREE_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
    }
}
