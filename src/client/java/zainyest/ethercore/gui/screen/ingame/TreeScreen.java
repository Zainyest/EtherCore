package zainyest.ethercore.gui.screen.ingame;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
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
import zainyest.ethercore.util.init.EtherRegistries;
import zainyest.ethercore.util.init.TechniqueTrees;

import java.util.LinkedHashMap;

public class TreeScreen extends Screen {
    private final Screen parent;
    protected int backgroundWidth = 176;
    protected int backgroundHeight = 166;

    private static final Identifier INVENTORY_BACKDROP = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/inventory_backdrop.png");
    private static final Identifier CURRENT_TREE_VIEWPORT = Identifier.of(EtherCore.MOD_ID, "textures/gui/meridiansscreen/tree_view_large.png");

    private double treeOffset_x = 80, treeOffset_y = 75;
    private static final int treeTierOffset = 30;

    //public List<TreeElementWidget> treeElementWidgets = new LinkedList<>();
    public LinkedHashMap<String, TreeElementWidget> treeElementWidgets = new LinkedHashMap<String, TreeElementWidget>();

    public TreeScreen(Text title, Screen parent) {
        super(title);
        this.parent = parent;
    }

    @Override
    protected void init() {
        // Create widgets here

        // Tree List instantiation
        treeElementWidgets = new LinkedHashMap<String, TreeElementWidget>();
        Technique root = TechniqueTrees.TECHNIQUE_TREE.getRootTechnique();
        instantiateTreeList(root);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        //render here
        drawBackground(context, delta, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float deltaTicks, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        // background
        context.drawTexture(RenderPipelines.GUI_TEXTURED, INVENTORY_BACKDROP, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, CURRENT_TREE_VIEWPORT, x, y, 0, 0, backgroundWidth, backgroundHeight, 256, 256);
        // lerped tree port, oscillated vertically
        float lerpedAmount = MathHelper.abs(MathHelper.sin((float) (Util.getMeasuringTimeMs() / 10000.0)));
        int lerpedColor1 = ColorHelper.lerp(lerpedAmount, 0x140059ff, 0x28347aff);
        int lerpedColor2 = ColorHelper.lerp(lerpedAmount, 0x28347aff, 0x140059ff);
        context.fillGradient(x+8, y+8, x+8+160, y+8+150, lerpedColor1, lerpedColor2);

        drawTree(context, deltaTicks, mouseX, mouseY, x+8, y+8, 160, 150);
    }

    public void drawTree(DrawContext context, float deltaTicks, int mouseX, int mouseY, int pos_x, int pos_y, int viewWidth, int viewHeight) {
        // Needs to be pannable, clickable, scalable (maybe); scissored by viewWidth, viewHeight, and pos_x, pos_y
        // get player's tree
        NbtCompound player_tree = EtherCoreClient.clientPlayerData.getPersistentData().getCompoundOrEmpty(TechniqueTrees.TECHNIQUE_TREE.getName());

        // Parse tree data and render
        context.enableScissor(pos_x, pos_y, pos_x+viewWidth, pos_y+viewHeight);
        TreeElementWidget prevT = null;
        for (TreeElementWidget t : this.treeElementWidgets.sequencedValues()) { // TODO reimplement this with breadth-first iterator from root (and use entry<T, T>)
            if (player_tree.getCompoundOrEmpty(t.getName()).getBoolean("learned").isEmpty()) {
                continue;
            }
            if (player_tree.getCompound(t.getName()).orElseThrow().getBoolean("learned").isPresent()) {
                //t.setLearned(player_tree.getCompound(t.getName()).orElseThrow().getBoolean("learned").orElseThrow());
                t.visible = true;
            }
            if (!t.visible) {
                continue;
            }
            //change pos
            int currentPos_x = pos_x + (int) Math.round(this.treeOffset_x);
            int currentPos_y = pos_y + (int) Math.round(this.treeOffset_y);
            if (t == this.treeElementWidgets.sequencedValues().getFirst()) { // First element case
                t.setX(currentPos_x - (t.getWidth() / 2));
                t.setY(currentPos_y - (t.getHeight() / 2));
            } else {
                // TODO reference parent's position and dynamic layout here
                TreeElementWidget parent = this.treeElementWidgets.get(EtherRegistries.TECHNIQUES.get(Identifier.of(EtherCore.MOD_ID, t.getName())).getParents()[0].getPath());
                int parentX = parent.getX();
                int parentY = parent.getY();
                if (parent == prevT) {
                    t.setX(parentX);
                    t.setY(parentY + treeTierOffset); // TODO Get number of siblings and subdivide a semicircle with the number of siblings and extend from their angle
                } else {
                    t.setX(prevT.getX() + treeTierOffset);
                    t.setY(prevT.getY());
                }

                // Draw relation line
                Matrix3x2fStack matrices = context.getMatrices();

                // new matrix
                matrices.pushMatrix();

                double deltaX = parentX - t.getX();
                double deltaY = parentY - t.getY();
                float angle = (float) Math.atan2(deltaY, deltaX);
                matrices.rotateAbout(angle, (float) t.getX() + ((float) t.getWidth() / 2F), (float) t.getY() + ((float) t.getHeight() / 2F) + 0.5F);
                int length = (int) Math.round(Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));
                context.drawHorizontalLine(t.getX() + (t.getWidth() / 2), t.getX() + (t.getWidth() / 2) + length, t.getY() + (t.getHeight() / 2), 0xffffffff);

                matrices.popMatrix();
            }
            //EtherCore.LOGGER.info(t.toString() + ": " + t.getX() + ", " + t.getY());
            prevT = t;
        }

        for (TreeElementWidget t : this.treeElementWidgets.sequencedValues()) { // Render elements
            t.render(context, mouseX, mouseY, deltaTicks);
        }
        for (TreeElementWidget t : this.treeElementWidgets.sequencedValues()) { // Render tooltips
            if (t.isHovered()) {
                t.renderToolTip(context, width, deltaTicks);
            }
        }

        context.disableScissor();
    }

    /// Recursive function, adds all nodes in tree to treeElementWidgets
    private void instantiateTreeList(Technique current) {
        TreeElementWidget elementWidget = new TreeElementWidget(0, 0, 16, 16, current.getName(), current.getIcon(), this.client);
        elementWidget.visible = false;

        this.treeElementWidgets.put(current.getName(), elementWidget);
        //this.addDrawableChild(elementWidget);
        this.addSelectableChild(elementWidget);
        EtherCore.LOGGER.info(elementWidget.toString());

        for (Identifier id : current.getChildren()) {
            instantiateTreeList(EtherRegistries.TECHNIQUES.get(id));
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        // TODO implement tree.scale(), call here
        this.treeOffset_x -= (int) horizontalAmount;
        this.treeOffset_y += (int) verticalAmount;
        return true;
    }

    @Override
    public boolean mouseDragged(Click click, double offsetX, double offsetY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        //TechniqueTree window (x, y, width, height): x+8, y+8, 160, 150
        if (click.x() < x+8+160 && click.x() > x+8 && click.y() < y+8+150 && click.y() > y+8) {
            this.treeOffset_x += offsetX;
            this.treeOffset_y += offsetY;
            return true;
        }
        return false;
    }

    @Override
    public void close() {
        this.client.setScreen(this.parent);
    }

}
