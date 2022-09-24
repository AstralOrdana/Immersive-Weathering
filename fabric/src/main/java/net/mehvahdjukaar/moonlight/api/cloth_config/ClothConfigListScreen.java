package net.mehvahdjukaar.moonlight.api.cloth_config;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.fabricmc.loader.api.FabricLoader;
import net.mehvahdjukaar.moonlight.api.platform.configs.ConfigSpec;
import net.mehvahdjukaar.moonlight.api.platform.configs.fabric.FabricConfigSpec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

/**
 * Main config screen
 */
public class ClothConfigListScreen extends Screen {

    protected final Screen parent;
    protected final ConfigSpec[] configs;
    protected final ResourceLocation background;
    private final ItemStack mainIcon;
    private final String modId;
    private final String modURL;

    protected ConfigList list;

    public ClothConfigListScreen(String modId, ItemStack mainIcon, Component displayName, @Nullable ResourceLocation background,
                                 Screen parent,
                                 ConfigSpec... specs) {
        super(displayName);
        this.parent = parent;
        this.configs = specs;
        this.background = background;
        this.mainIcon = mainIcon;
        this.modId = modId;
        this.modURL = FabricLoader.getInstance().getModContainer(modId).get().getMetadata().getContact().get("homepage").orElse(null);
    }

    @Override
    protected void init() {
        this.list = new ConfigList(this.minecraft, this.width, this.height, 32, this.height - 32, 40,
                this.configs);
        this.addRenderableWidget(this.list);

        this.addExtraButtons();
    }

    protected void addExtraButtons() {
        this.addRenderableWidget(new Button(this.width / 2 - 155 + 160, this.height - 29, 150, 20,
                CommonComponents.GUI_DONE, button -> this.minecraft.setScreen(this.parent)));
    }

    @Override
    public void removed() {
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 15, 16777215);

        if (modURL != null && isMouseWithin((this.width / 2) - 90, 2 + 6, 180, 16 + 2, mouseX, mouseY)) {
            this.renderTooltip(poseStack, this.font.split(new TextComponent("open mod page"), 200), mouseX, mouseY);
        }
        int titleWidth = this.font.width(this.title) + 35;
        this.itemRenderer.renderAndDecorateFakeItem(this.mainIcon, (this.width / 2) + titleWidth / 2 - 17, 2 + 8);
        this.itemRenderer.renderAndDecorateFakeItem(this.mainIcon, (this.width / 2) - titleWidth / 2, 2 + 8);
    }

    private boolean isMouseWithin(int x, int y, int width, int height, int mouseX, int mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (modURL != null && isMouseWithin((this.width / 2) - 90, 2 + 6, 180, 16 + 2, (int) mouseX, (int) mouseY)) {
            Style style = Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL,  modURL));
            this.handleComponentClicked(style);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.parent);
    }

    protected class ConfigList extends ContainerObjectSelectionList<ConfigButton> {

        public ConfigList(Minecraft minecraft, int width, int height, int y0, int y1, int itemHeight, ConfigSpec... specs) {
            super(minecraft, width, height, y0, y1, itemHeight);
            this.centerListVertically = true;
            this.setRenderSelection(false);
            for (var s : specs) {
                this.addEntry(new ConfigButton(s, this.width, this.getRowWidth()));
            }
        }

        @Override
        public int getRowWidth() {
            return 260;
        }

        @Override
        protected int getScrollbarPosition() {
            return super.getScrollbarPosition() + 32;
        }

        /*
        @Override
        protected int getRowTop(int index) {
            if (!this.centerListVertically) return super.getRowTop(index);
            return (y1 - y0) / 2 - (this.children().size() * itemHeight) / 2 +
                    this.y0 + 4 - (int) this.getScrollAmount() + index * this.itemHeight + this.headerHeight;
        }*/

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            this.renderBackground(poseStack);

            var background = ClothConfigListScreen.this.background;

            int i = this.getScrollbarPosition();
            int j = i + 6;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            //this.hovered = this.isMouseOver((double)mouseX, (double)mouseY) ? this.getEntryAtPosition((double)mouseX, (double)mouseY) : null;

            RenderSystem.setShaderTexture(0, background);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            float f = 32.0F;
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.vertex(this.x0, this.y1, 0.0).uv((float) this.x0 / 32.0F, (float) (this.y1 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y1, 0.0).uv((float) this.x1 / 32.0F, (float) (this.y1 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y0, 0.0).uv((float) this.x1 / 32.0F, (float) (this.y0 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y0, 0.0).uv((float) this.x0 / 32.0F, (float) (this.y0 + (int) this.getScrollAmount()) / 32.0F).color(32, 32, 32, 255).endVertex();
            tesselator.end();


            int k = this.getRowLeft();
            int l = this.y0 + 4 - (int) this.getScrollAmount();


            this.renderList(poseStack, k, l,mouseX, mouseY, partialTick);

            RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
            RenderSystem.setShaderTexture(0, background);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(519);


            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.vertex(this.x0, this.y0, -100.0).uv(0.0F, (float) this.y0 / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, this.y0, -100.0).uv((float) this.width / 32.0F, (float) this.y0 / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, 0.0, -100.0).uv((float) this.width / 32.0F, 0.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0, 0.0, -100.0).uv(0.0F, 0.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.height, -100.0).uv(0.0F, (float) this.height / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, this.height, -100.0).uv((float) this.width / 32.0F, (float) this.height / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0 + this.width, this.y1, -100.0).uv((float) this.width / 32.0F, (float) this.y1 / 32.0F).color(64, 64, 64, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y1, -100.0).uv(0.0F, (float) this.y1 / 32.0F).color(64, 64, 64, 255).endVertex();
            tesselator.end();
            RenderSystem.depthFunc(515);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
            RenderSystem.disableTexture();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.vertex(this.x0, this.y0 + 4, 0.0).color(0, 0, 0, 0).endVertex();
            bufferBuilder.vertex(this.x1, this.y0 + 4, 0.0).color(0, 0, 0, 0).endVertex();
            bufferBuilder.vertex(this.x1, this.y0, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y0, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x0, this.y1, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y1, 0.0).color(0, 0, 0, 255).endVertex();
            bufferBuilder.vertex(this.x1, this.y1 - 4, 0.0).color(0, 0, 0, 0).endVertex();
            bufferBuilder.vertex(this.x0, this.y1 - 4, 0.0).color(0, 0, 0, 0).endVertex();
            tesselator.end();


            int o = this.getMaxScroll();
            if (o > 0) {
                RenderSystem.disableTexture();
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                int m = (int) ((float) ((this.y1 - this.y0) * (this.y1 - this.y0)) / (float) this.getMaxPosition());
                m = Mth.clamp(m, 32, this.y1 - this.y0 - 8);
                int n = (int) this.getScrollAmount() * (this.y1 - this.y0 - m) / o + this.y0;
                if (n < this.y0) {
                    n = this.y0;
                }

                bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                bufferBuilder.vertex(i, this.y1, 0.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.vertex(j, this.y1, 0.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.vertex(j, this.y0, 0.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.vertex(i, this.y0, 0.0).color(0, 0, 0, 255).endVertex();
                bufferBuilder.vertex(i, n + m, 0.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.vertex(j, n + m, 0.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.vertex(j, n, 0.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.vertex(i, n, 0.0).color(128, 128, 128, 255).endVertex();
                bufferBuilder.vertex(i, n + m - 1, 0.0).color(192, 192, 192, 255).endVertex();
                bufferBuilder.vertex(j - 1, n + m - 1, 0.0).color(192, 192, 192, 255).endVertex();
                bufferBuilder.vertex(j - 1, n, 0.0).color(192, 192, 192, 255).endVertex();
                bufferBuilder.vertex(i, n, 0.0).color(192, 192, 192, 255).endVertex();
                tesselator.end();
            }

            this.renderDecorations(poseStack, mouseX, mouseY);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
        }
    }

    protected class ConfigButton extends ContainerObjectSelectionList.Entry<ConfigButton> {

        private final List<AbstractWidget> children;

        private ConfigButton(AbstractWidget widget) {
            this.children = List.of(widget);
        }

        protected ConfigButton(ConfigSpec spec, int width, int buttonWidth) {
            this(new Button(width / 2 - buttonWidth / 2, 0, buttonWidth, 20, new TranslatableComponent(spec.getFileName()), (b) ->
                    Minecraft.getInstance().setScreen(ClothConfigCompat.makeScreen(
                            ClothConfigListScreen.this, (FabricConfigSpec) spec, ClothConfigListScreen.this.background))));
        }

        @Override
        public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            this.children.forEach((button) -> {
                button.y = top;
                button.render(poseStack, mouseX, mouseY, partialTick);
            });
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return this.children;
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return this.children;
        }
    }

}

