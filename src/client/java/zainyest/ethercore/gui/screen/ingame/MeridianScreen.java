package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import zainyest.ethercore.screenhandler.MeridianScreenHandler;

public class MeridianScreen extends HandledScreen<MeridianScreenHandler> {


    public MeridianScreen(MeridianScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {

    }

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {

    }


}
