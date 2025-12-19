package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.joml.Matrix3x2fStack;
import zainyest.ethercore.EtherCore;
import zainyest.ethercore.EtherCoreClient;
import zainyest.ethercore.technique.Technique;
import zainyest.ethercore.init.EtherRegistries;
import zainyest.ethercore.init.TechniqueTrees;
import zainyest.ethercore.util.Trie;

import java.util.LinkedHashMap;
import java.util.Objects;

public class TreeScreen extends Screen {
    private final Screen parent;
    protected int backgroundWidth = 256;
    protected int backgroundHeight = 256;

    private static final Identifier TREE_SCREEN_BACKDROP = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_screen_backdrop.png");
    private static final Identifier CURRENT_TREE_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_view_large.png");

    private double treeOffset_x = 80, treeOffset_y = 75;
    private static final int treeTierOffset = 30;

    public LinkedHashMap<String, TreeElementWidget> treeElementWidgets = new LinkedHashMap<>();

    private Trie searchTrie;

    public TreeScreen() {
        super(Text.empty());
        this.parent = null;
    }

    public TreeScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        treeOffset_x = (double) (backgroundWidth - 16) / 2;
        treeOffset_y = (double) (backgroundHeight - 16) / 2;

        // Create widgets here
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        TabWidget treeScreenTabWidget = new TabWidget(x - 17, y + 22, true, (btn) -> {});
        this.addDrawableChild(treeScreenTabWidget);

        TabWidget statsScreenTabWidget = new TabWidget(x - 17, y + 22*2, false, (btn) -> MinecraftClient.getInstance().setScreen(new StatsScreen()));
        this.addDrawableChild(statsScreenTabWidget);

        TabWidget techniqueManagerScreenTabWidget = new TabWidget(x - 17, y + 22*3, false, (btn) -> MinecraftClient.getInstance().setScreen(new TreeScreen()));
        this.addDrawableChild(techniqueManagerScreenTabWidget);

        // Tree List instantiation
        treeElementWidgets = new LinkedHashMap<>();
        Technique root = TechniqueTrees.TECHNIQUE_TREE.rootTechnique();
        instantiateTreeList(root, null);

        this.searchTrie = new Trie(treeElementWidgets); // TODO add TextFieldWidget a la RecipeBookWidget
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        //render here
        drawBackground(context, delta, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) { // TODO: create a "fullscreen" [<->] / [>-<] button
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TREE_SCREEN_BACKDROP, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CURRENT_TREE_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        // lerped tree port, oscillated vertically
        float lerpedAmount = MathHelper.abs(MathHelper.sin((float) (Util.getMeasuringTimeMs() / 1000.0)));
        int lerpedColor1 = ColorHelper.lerp(lerpedAmount, 0x140059ff, 0x28347aff);
        int lerpedColor2 = ColorHelper.lerp(lerpedAmount, 0x28347aff, 0x140059ff);
        context.fillGradient(x+8, y+8, x+8+241, y+8+241, lerpedColor1, lerpedColor2);

        drawTree(context, deltaTicks, mouseX, mouseY, x+8, y+8, 240, 240);

        for (Element e : this.children()) { // render active tab over background
            if (e instanceof TabWidget) {
                if (((TabWidget) e).isCurrent()) {
                    ((TabWidget) e).renderWidget(context, mouseX, mouseY, deltaTicks);
                }
            }
        }
    }

    public void drawTree(DrawContext context, float deltaTicks, int mouseX, int mouseY, int pos_x, int pos_y, int viewWidth, int viewHeight) { // TODO: add [Apply] button to send technique learn updates
        // Needs to be pannable, clickable, scalable (maybe); scissored by viewWidth, viewHeight, and pos_x, pos_y
        // get player's tree
        NbtCompound player_tree = EtherCoreClient.clientPlayerData.getPersistentData().getCompoundOrEmpty(TechniqueTrees.TECHNIQUE_TREE.treeName());

        // Parse tree data and render
        context.enableScissor(pos_x, pos_y, pos_x+viewWidth, pos_y+viewHeight);

        setTreeElementWidgetPositions(context, deltaTicks, mouseX, mouseY, pos_x, pos_y, viewWidth, viewHeight, player_tree);

        for (TreeElementWidget t : this.treeElementWidgets.sequencedValues()) { // Render elements
            t.render(context, mouseX, mouseY, deltaTicks);
        }
        for (TreeElementWidget t : this.treeElementWidgets.sequencedValues()) { // Render tooltips
            if (t.isHovered()) {
                t.renderToolTip(context, width);
            }
        }

        context.disableScissor();
    }

    private void setTreeElementWidgetPositions(DrawContext context, float deltaTicks, int mouseX, int mouseY, int pos_x, int pos_y, int viewWidth, int viewHeight, NbtCompound player_tree) {
        TreeElementWidget root = this.treeElementWidgets.firstEntry().getValue();
        if (player_tree.getCompoundOrEmpty(root.getName()).getBoolean("learned").isEmpty()) {
            return;
        }
        if (player_tree.getCompound(root.getName()).orElseThrow().getBoolean("learned").isPresent()) {
            root.visible = true;
        }
        if (!root.visible) {
            return;
        }
        int currentPos_x = pos_x + (int) Math.round(this.treeOffset_x);
        int currentPos_y = pos_y + (int) Math.round(this.treeOffset_y);
        root.setX(currentPos_x - (root.getWidth() / 2));
        root.setY(currentPos_y - (root.getHeight() / 2));

        recursiveSetTreeElementChildren(context, player_tree, root, 1);
    }

    private void recursiveSetTreeElementChildren(DrawContext context, NbtCompound player_tree, TreeElementWidget parent, int tier) {
        int childCount = parent.getChildren().size();
        if (childCount == 0) {
            return;
        }
        TreeElementWidget prevT = parent;
        int currentChild = 1;
        float startingAngle = (float) (parent.getAngle() + (childCount > 1 ? (-Math.PI / 2) : 0)); // starting angle in radians
        float rootSegmentAngle = (float) (Math.PI * 2 / (childCount)); // segment angle for first tier in radians
        float segmentAngle = (float) (Math.PI / (childCount + 1)); // segment angle in radians
        for (TreeElementWidget child : parent.getChildren()) {
            if (player_tree.getCompoundOrEmpty(child.getName()).getBoolean("learned").isEmpty()) {
                continue;
            }
            if (player_tree.getCompound(child.getName()).orElseThrow().getBoolean("learned").isPresent()) {
                child.visible = true;
            }
            if (!child.visible) {
                continue;
            }

            int dx;
            int dy;
            if (parent == prevT) {
                if (tier == 1) {
                    child.setAngle(startingAngle);
                } else {
                    child.setAngle(startingAngle + (childCount > 1 ? segmentAngle : 0));
                }
            } else {
                if (tier == 1) {
                    child.setAngle(startingAngle + rootSegmentAngle * (currentChild - 1));
                } else {
                    child.setAngle(startingAngle + segmentAngle * (currentChild));
                }
            }
            dx = Math.toIntExact(Math.round(treeTierOffset * Math.cos(child.getAngle())));
            dy = Math.toIntExact(Math.round(treeTierOffset * Math.sin(child.getAngle())));
            child.setX(parent.getX() + dx);
            child.setY(parent.getY() + dy);

            // Draw relation line
            Matrix3x2fStack matrices = context.getMatrices();
            // new matrix
            matrices.pushMatrix();
            double deltaX = parent.getX() - child.getX();
            double deltaY = parent.getY() - child.getY();
            float angle = (float) Math.atan2(deltaY, deltaX);
            matrices.rotateAbout(angle, (float) child.getX() + ((float) child.getWidth() / 2F), (float) child.getY() + ((float) child.getHeight() / 2F) + 0.5F);
            int length = (int) Math.round(Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
            context.drawHorizontalLine(child.getX() + (child.getWidth() / 2), child.getX() + (child.getWidth() / 2) + length, child.getY() + (child.getHeight() / 2), 0xffffffff);
            matrices.popMatrix();

            prevT = child;
            recursiveSetTreeElementChildren(context, player_tree, child, tier + 1);
            currentChild++;
        }
    }

    /// Recursive function, adds all nodes in tree to treeElementWidgets
    private void instantiateTreeList(Technique current, TreeElementWidget parent) {
        TreeElementWidget elementWidget = new TreeElementWidget(0, 0, 16, 16, parent, current.getName(), current.getIcon(), this.client);
        elementWidget.visible = false;

        this.treeElementWidgets.put(current.getName(), elementWidget);
        this.addSelectableChild(elementWidget);

        if (parent != null) {
            parent.children.add(elementWidget);
        }

        for (Identifier id : current.getChildren()) {
            instantiateTreeList(Objects.requireNonNull(EtherRegistries.TECHNIQUES.get(id)), elementWidget);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        this.treeOffset_x -= (int) horizontalAmount;
        this.treeOffset_y += (int) verticalAmount;
        return true;
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //TechniqueTree window (x, y, width, height): x+8, y+8, 160, 150
        if (click.x() < x+8+241 && click.x() > x+8 && click.y() < y+8+241 && click.y() > y+8) {
            this.treeOffset_x += offsetX;
            this.treeOffset_y += offsetY;
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

}
