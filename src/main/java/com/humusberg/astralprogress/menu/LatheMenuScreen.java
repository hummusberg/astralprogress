package com.humusberg.astralprogress.menu;

import java.util.List;

import com.humusberg.astralprogress.AstralProgress;
import com.humusberg.astralprogress.packets.PacketHandler;
import com.humusberg.astralprogress.packets.AddDirectionToTile;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LatheMenuScreen extends AbstractContainerScreen<LatheMenu> {
    private Button energyBar;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;
    private Button frontButton;
    private Button backButton;
    private Button configButton;
    private Button closeConfigButton;
	private Boolean openConfig = false;
    private Boolean showEnergyTruncated = true;
    @SuppressWarnings("unused")
	private static final Component pTitle = Component.literal("Lathe");
    @SuppressWarnings("removal")
    private static final ResourceLocation BACKGROUND = new ResourceLocation(AstralProgress.MODID, "textures/gui/machinery.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation SAW = new ResourceLocation(AstralProgress.MODID, "textures/gui/saw.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation ENERGY_BAR = new ResourceLocation(AstralProgress.MODID, "textures/gui/energy_bar.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation ARROW = new ResourceLocation(AstralProgress.MODID, "textures/gui/arrow.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation GREEN = new ResourceLocation(AstralProgress.MODID, "textures/gui/green_light.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation RED = new ResourceLocation(AstralProgress.MODID, "textures/gui/red_light.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation YELLOW = new ResourceLocation(AstralProgress.MODID, "textures/gui/yellow_light.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation CONFIG_TAB = new ResourceLocation(AstralProgress.MODID, "textures/gui/config_tab.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation OUTPUT_BUTTON = new ResourceLocation(AstralProgress.MODID, "textures/gui/output_button.png");

    public LatheMenuScreen(LatheMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }
    
    @Override
    protected void init() {
        super.init();
        this.energyBar = addWidget(
            Button.builder(null, button -> showEnergyTruncated = !showEnergyTruncated)
                .pos(this.leftPos + 165, this.topPos + 13)
                .size(4, 60)
                .build()
        );
        this.configButton = addWidget(
            Button.builder(null, button -> openConfig = true)
                .pos(this.leftPos + 172, this.topPos + 45)
                .size(24, 28)
                .build()
        );
        this.closeConfigButton = addWidget(
            Button.builder(null, button -> openConfig = false)
                .pos(this.leftPos + 172, this.topPos + 49)
                .size(10, 9)
                .build()
        );
        this.upButton = addWidget(
            Button.builder(null, button -> handleConfigButton(Direction.UP))
                .pos(this.leftPos + 200, this.topPos + 49)
                .size(12, 12)
                .build()
        );
        this.downButton = addWidget(
            Button.builder(null, button -> handleConfigButton(Direction.DOWN))
                .pos(this.leftPos + 200, this.topPos + 75)
                .size(12, 12)
                .build()
        );
        this.leftButton = addWidget(
            Button.builder(null, button -> handleConfigButton(Direction.WEST))
                .pos(this.leftPos + 187, this.topPos + 62)
                .size(12, 12)
                .build()
        );
        this.rightButton = addWidget(
            Button.builder(null, button -> handleConfigButton(Direction.EAST))
                .pos(this.leftPos + 213, this.topPos + 62)
                .size(12, 12)
                .build()
        );
        this.frontButton = addWidget(
            Button.builder(null, button -> handleConfigButton(Direction.NORTH))
                .pos(this.leftPos + 200, this.topPos + 62)
                .size(10, 9)
                .build()
        );
        this.backButton = addWidget(
            Button.builder(null, button -> handleConfigButton(Direction.SOUTH))
                .pos(this.leftPos + 213, this.topPos + 75)
                .size(10, 9)
                .build()
        );
    }
    
    private void handleConfigButton(Direction direction) {
        PacketHandler.sendToServer(new AddDirectionToTile(menu.getBlockEntity().getBlockPos(), (byte) direction.get3DDataValue()));
    }

    @Override
    public void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth + 53, this.imageHeight);
        pGuiGraphics.blit(SAW, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        pGuiGraphics.blit(ENERGY_BAR, this.leftPos, this.topPos, 0, 0, this.imageWidth, (int) ((1 - ((float) menu.getEnergyStored() / (float) menu.getMaxEnergy())) * 60 + 13));
        pGuiGraphics.blit(ARROW, this.leftPos, this.topPos, 0, 0, (int) ((float) menu.getTimer() / 60 * 26 + 75), imageHeight);
        if (menu.getError() == true) {
            pGuiGraphics.blit(RED, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        } else if (menu.getTimer() != 0) {
            pGuiGraphics.blit(GREEN, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        } else {
            pGuiGraphics.blit(YELLOW, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        }
        if (openConfig == true) {
            pGuiGraphics.blit(CONFIG_TAB, this.leftPos, this.topPos, 0, 0, this.imageWidth + 53, this.imageHeight);
            this.configButton.active = false;
            this.closeConfigButton.active = true;
            for (int i = 0; i < menu.getDirections().length; i++) {
                switch (menu.getDirections()[i]) {
                    case UP:
                        pGuiGraphics.blit(OUTPUT_BUTTON, upButton.getX(), upButton.getY(), 0, 0, this.imageWidth, this.imageHeight);
                        break;
                    case DOWN:
                        pGuiGraphics.blit(OUTPUT_BUTTON, downButton.getX(), downButton.getY(), 0, 0, this.imageWidth, this.imageHeight);
                        break;
                    case WEST:
                        pGuiGraphics.blit(OUTPUT_BUTTON, leftButton.getX(), leftButton.getY(), 0, 0, this.imageWidth, this.imageHeight);
                        break;
                    case EAST:
                        pGuiGraphics.blit(OUTPUT_BUTTON, rightButton.getX(), rightButton.getY(), 0, 0, this.imageWidth, this.imageHeight);
                        break;
                    case NORTH:
                        pGuiGraphics.blit(OUTPUT_BUTTON, frontButton.getX(), frontButton.getY(), 0, 0, this.imageWidth, this.imageHeight);
                        break;
                    case SOUTH:
                        pGuiGraphics.blit(OUTPUT_BUTTON, backButton.getX(), backButton.getY(), 0, 0, this.imageWidth, this.imageHeight);
                        break;
                    default:
                        break;
                }
            }
            this.upButton.active = true;
            this.downButton.active = true;
            this.leftButton.active = true;
            this.rightButton.active = true;
            this.frontButton.active = true;
            this.backButton.active = true;
        } else {
            this.configButton.active = true;
            this.closeConfigButton.active = false;

            this.upButton.active = false;
            this.downButton.active = false;
            this.leftButton.active = false;
            this.rightButton.active = false;
            this.frontButton.active = false;
            this.backButton.active = false;
        }
        if (configButton.isMouseOver(pMouseX, pMouseY)) {
            pGuiGraphics.renderComponentTooltip(font, List.of(Component.literal("Cofingure Auto Outputs")), pMouseX, pMouseY);
        }        
        if (energyBar.isMouseOver(pMouseX, pMouseY)) {
            if (showEnergyTruncated) {
                pGuiGraphics.renderComponentTooltip(font, List.of(Component.literal(Math.ceil((float) menu.getEnergyStored() / 100) / 10 + " kFE")), pMouseX, pMouseY);
            } else {
                pGuiGraphics.renderComponentTooltip(font, List.of(Component.literal(menu.getEnergyStored() + " FE")), pMouseX, pMouseY);
            }
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick); 
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}