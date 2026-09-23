package com.humusberg.astralprogress.menu;

import java.util.List;

import com.humusberg.astralprogress.AstralProgress;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SolidBurnerMenuScreen extends AbstractContainerScreen<SolidBurnerMenu> {
    private Button energyBar;
    private Boolean showEnergyTruncated = true;

    @SuppressWarnings("removal")
    private static final ResourceLocation BACKGROUND = new ResourceLocation(AstralProgress.MODID, "textures/gui/generator.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation ENERGY_BAR = new ResourceLocation(AstralProgress.MODID, "textures/gui/energy_bar.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation HEAT = new ResourceLocation(AstralProgress.MODID, "textures/gui/heat.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation GREEN = new ResourceLocation(AstralProgress.MODID, "textures/gui/green_light.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation RED = new ResourceLocation(AstralProgress.MODID, "textures/gui/red_light.png");
    @SuppressWarnings("removal")
	private static final ResourceLocation YELLOW = new ResourceLocation(AstralProgress.MODID, "textures/gui/yellow_light.png");
    
    public SolidBurnerMenuScreen(SolidBurnerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        renderBackground(pGuiGraphics);
        pGuiGraphics.blit(BACKGROUND, this.leftPos, this.topPos, 0, 0, this.imageWidth + 53, this.imageHeight);
        pGuiGraphics.blit(HEAT, this.leftPos - 1, this.topPos + 24, 0, 0, this.imageWidth, (int) ((1 - (float) menu.getTimer() / (float) menu.getBurnTime()) * 13 + 26));
        pGuiGraphics.blit(ENERGY_BAR, this.leftPos, this.topPos, 0, 0, this.imageWidth, (int) ((1 - ((float) menu.getEnergyStored() / (float) menu.getMaxEnergy())) * 60 + 13));
        if (menu.getError() == true) {
            pGuiGraphics.blit(RED, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        } else if (menu.getTimer() != 0) {
            pGuiGraphics.blit(GREEN, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        } else {
            pGuiGraphics.blit(YELLOW, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
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
    protected void init() {
        super.init();
        this.energyBar = addWidget(
            Button.builder(null, button -> showEnergyTruncated = !showEnergyTruncated)
                .pos(this.leftPos + 165, this.topPos + 13)
                .size(4, 60)
                .build()
        );
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
