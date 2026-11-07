package dev.enderdevelopment.unsmelter.client;

import dev.enderdevelopment.unsmelter.UnsmelterMod;
import dev.enderdevelopment.unsmelter.UnsmelterScreenHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class UnsmelterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        net.minecraft.client.gui.screen.ingame.HandledScreens.register(UnsmelterMod.SCREEN_HANDLER, UnsmelterScreen::new);
    }

    private static final class UnsmelterScreen extends HandledScreen<UnsmelterScreenHandler> {
        private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/gui/container/furnace.png");

        private UnsmelterScreen(UnsmelterScreenHandler handler, PlayerInventory inventory, Text title) {
            super(handler, inventory, title);
            backgroundWidth = 176;
            backgroundHeight = 166;
        }

        @Override
        protected void init() {
            super.init();
            int left = x;
            int top = y;
            addDrawableChild(ButtonWidget.builder(Text.literal("<"), button -> client.interactionManager.clickButton(handler.syncId, 0))
                    .dimensions(left + 64, top + 28, 20, 20).build());
            addDrawableChild(ButtonWidget.builder(Text.literal(">"), button -> client.interactionManager.clickButton(handler.syncId, 1))
                    .dimensions(left + 92, top + 28, 20, 20).build());
        }

        @Override
        protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
            context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        }

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, float delta) {
            renderBackground(context, mouseX, mouseY, delta);
            super.render(context, mouseX, mouseY, delta);
            drawMouseoverTooltip(context, mouseX, mouseY);
        }
    }
}
