package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.entity.EntityRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.EtherCoreClient;
import zainyest.ethercore.screenhandler.MeridianScreenHandler;

public class MeridianScreen extends HandledScreen<MeridianScreenHandler> {

    private static final Identifier INVENTORY_BACKDROP = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/inventory_backdrop.png");
    private static final Identifier CATEGORIES = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/categories.png");
    private static final Identifier MERIDIANS_DISPLAY = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/meridians_display.png");
    private static final Identifier PLAYER_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/player_viewport.png");
    private static final Identifier CURRENT_TREE_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/current_tree_viewport.png");

    private int treeOffset_x = 0, treeOffset_y = 0;

    public MeridianScreen(MeridianScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        MeridianWidget head = MeridianWidget.mBuilder(Text.of("Head"), (btn) -> {
            // happens on button click
            client.player.sendMessage(Text.literal("BUTON head"), false);
        }).dimensions(this.x+8, this.y+9, 49, 16).highlightRenderOffset(35, 0).texturePaths(new ButtonTextures(Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/head_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/head_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/head_meridians_highlighted.png"))).build();
        this.addDrawableChild(head);

        MeridianWidget torso = MeridianWidget.mBuilder(Text.of("Torso"), (btn) -> {
            // happens on button click
            client.player.sendMessage(Text.literal("BUTON torso"), false);
        }).dimensions(this.x+8, this.y+44, 49, 16).highlightRenderOffset(35, 9).texturePaths(new ButtonTextures(Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/torso_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/torso_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/torso_meridians_highlighted.png"))).build();
        this.addDrawableChild(torso);

        MeridianWidget arm = MeridianWidget.mBuilder(Text.of("Arm"), (btn) -> {
            // happens on button click
            client.player.sendMessage(Text.literal("BUTON arm"), false);
        }).dimensions(this.x+8, this.y+26, 49, 16).highlightRenderOffset(48, 0).texturePaths(new ButtonTextures(Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/arm_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/arm_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/arm_meridians_highlighted.png"))).build();
        this.addDrawableChild(arm);

        MeridianWidget leg = MeridianWidget.mBuilder(Text.of("Leg"), (btn) -> {
            // happens on button click
            client.player.sendMessage(Text.literal("BUTON leg"), false);
        }).dimensions(this.x+8, this.y+61, 49, 16).highlightRenderOffset(40, 1).texturePaths(new ButtonTextures(Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/leg_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/leg_meridians.png"), Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/leg_meridians_highlighted.png"))).build();
        this.addDrawableChild(leg);

        // TODO create loop to instantiate all techniques as widgets
        TreeElementWidget testTreeWidget = new TreeElementWidget(0, 0, 16, 16, Text.of("Test"));
        this.addDrawableChild(testTreeWidget);
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {}

    @Override
    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, INVENTORY_BACKDROP, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CATEGORIES, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, MERIDIANS_DISPLAY, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, PLAYER_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CURRENT_TREE_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        // lerped tree port, oscillated vertically
        float lerpedAmount = MathHelper.abs(MathHelper.sin((float) (Util.getMeasuringTimeMs() / 10000.0)));
        int lerpedColor1 = ColorHelper.lerp(lerpedAmount, 0x140059ff, 0x28347aff);
        int lerpedColor2 = ColorHelper.lerp(lerpedAmount, 0x28347aff, 0x140059ff);
        context.fillGradient(x+8, y+84, x+6+162, y+82+76, lerpedColor1, lerpedColor2);
        // player rendered
        int i = this.x;
        int j = this.y;
        drawEntity(context, i + 64, j + 8, i + 113, j + 78, 30, 0.0625F, mouseX, mouseY, this.client.player);

        // TODO add widgets; Meridian buttons, Invest/Devest Buttons, TreeElement (subfunction for drawing tree lines)
        // TODO add end-portal-like background for tree window and player window
        // TODO add proof of concept tree display

        drawTree(context, deltaTicks, mouseX, mouseY, 8, 84, 160, 74);
    }

    /// From net.minecraft.client.gui.screen.ingame.InventoryScreen in vanilla
    public static void drawEntity(DrawContext context, int x1, int y1, int x2, int y2, int size, float scale, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float)(x1 + x2) / 2.0F;
        float g = (float)(y1 + y2) / 2.0F;
        context.enableScissor(x1, y1, x2, y2);
        float h = (float)Math.atan((double)((f - mouseX) / 40.0F));
        float i = (float)Math.atan((double)((g - mouseY) / 40.0F));
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
        Quaternionf quaternionf2 = (new Quaternionf()).rotateX(i * 20.0F * ((float)Math.PI / 180F));
        quaternionf.mul(quaternionf2);
        float j = entity.bodyYaw;
        float k = entity.getYaw();
        float l = entity.getPitch();
        float m = entity.lastHeadYaw;
        float n = entity.headYaw;
        entity.bodyYaw = 180.0F + h * 20.0F;
        entity.setYaw(180.0F + h * 40.0F);
        entity.setPitch(-i * 20.0F);
        entity.headYaw = entity.getYaw();
        entity.lastHeadYaw = entity.getYaw();
        float o = entity.getScale();
        Vector3f vector3f = new Vector3f(0.0F, entity.getHeight() / 2.0F + scale * o, 0.0F);
        float p = (float)size / o;
        drawEntity(context, x1, y1, x2, y2, p, vector3f, quaternionf, quaternionf2, entity);
        entity.bodyYaw = j;
        entity.setYaw(k);
        entity.setPitch(l);
        entity.lastHeadYaw = m;
        entity.headYaw = n;
        context.disableScissor();
    }

    public static void drawEntity(DrawContext drawer, int x1, int y1, int x2, int y2, float scale, Vector3f translation, Quaternionf rotation, @Nullable Quaternionf overrideCameraAngle, LivingEntity entity) {
        EntityRenderManager entityRenderManager = MinecraftClient.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> entityRenderer = entityRenderManager.getRenderer(entity);
        EntityRenderState entityRenderState = entityRenderer.getAndUpdateRenderState(entity, 1.0F);
        entityRenderState.light = 15728880;
        entityRenderState.hitbox = null;
        entityRenderState.shadowPieces.clear();
        entityRenderState.outlineColor = 0;
        drawer.addEntity(entityRenderState, scale, translation, rotation, overrideCameraAngle, x1, y1, x2, y2);
    }

    public static void drawTree(DrawContext context, float deltaTicks, int mouseX, int mouseY, int pos_x, int pos_y, int viewWidth, int viewHeight) {
        // TODO Complete this once TechniqueTree is completed
        // Needs to be pannable, clickable, scalable; scissored by viewWidth, viewHeight, and pos_x, pos_y
        // most likely custom implementations of onClick, onDrag functions
        // use treeOffset_x and treeOffset_y for starting point

        // get EtherData
        NbtCompound technique_tree = EtherCoreClient.clientPlayerData.getPersistentData().getCompoundOrEmpty("technique_tree");

        // Parse tree data and render
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
        // TODO implement tree.scale(), call here
        return false;
    }

    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        super.mouseDragged(click, offsetX, offsetY);
        // TODO implement tree.move(), call here
        return false;
    }
}
