package zainyest.ethercore.screenhandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import org.jetbrains.annotations.Nullable;
import zainyest.ethercore.EtherCore;

public class MeridianScreenHandler extends ScreenHandler {
    public static final ScreenHandlerType<MeridianScreenHandler> MERIDIAN_SCREEN_HANDLER_TYPE = Registry.register(Registries.SCREEN_HANDLER, EtherCore.MOD_ID, new ScreenHandlerType<>(MeridianScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    protected final ScreenHandlerContext context;

    public MeridianScreenHandler(int syncId, PlayerInventory playerInventory, PlayerEntity player, ScreenHandlerContext context) {
        super(MERIDIAN_SCREEN_HANDLER_TYPE, syncId);
        this.context = context;
    }

    public MeridianScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(MERIDIAN_SCREEN_HANDLER_TYPE, syncId);
        this.context = ScreenHandlerContext.EMPTY;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
