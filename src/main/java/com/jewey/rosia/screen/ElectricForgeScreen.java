package com.jewey.rosia.screen;

import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.entity.block_entity.ElectricForgeBlockEntity;
import com.jewey.rosia.common.container.ElectricForgeContainer;
import com.jewey.rosia.screen.button.electric_forge.*;
import com.jewey.rosia.screen.renderer.EnergyInfoArea50Height;
import com.jewey.rosia.util.MouseUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.client.RenderHelpers;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.config.TFCConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Objects;
import java.util.Optional;

public class ElectricForgeScreen extends BlockEntityScreen<ElectricForgeBlockEntity, ElectricForgeContainer>
{
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(Rosia.MOD_ID,"textures/gui/electric_forge_gui.png");

    public ElectricForgeScreen(ElectricForgeContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, TEXTURE);
        inventoryLabelY += 20;
        imageHeight += 20;
    }

    private EnergyInfoArea50Height energyInfoArea;
    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        //render from top to bottom, right to left to avoid tooltip being hidden by buttons
        addRenderableWidget(new ElectricForgeButtonBrilliantWhite(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(1550)))));
        addRenderableWidget(new ElectricForgeButtonWhite(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(1410)))));
        addRenderableWidget(new ElectricForgeButtonYellowWhite(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(1310)))));
        addRenderableWidget(new ElectricForgeButtonYellow(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(1110)))));
        addRenderableWidget(new ElectricForgeButtonOrange(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(940)))));
        addRenderableWidget(new ElectricForgeButtonBrightRed(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(740)))));
        addRenderableWidget(new ElectricForgeButtonDarkRed(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(590)))));
        addRenderableWidget(new ElectricForgeButtonFaintRed(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(490)))));
        addRenderableWidget(new ElectricForgeButtonVeryHot(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(220)))));
        addRenderableWidget(new ElectricForgeButtonHot(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(90)))));
        addRenderableWidget(new ElectricForgeButtonWarming(blockEntity, getGuiLeft(), getGuiTop(), RenderHelpers.makeButtonTooltip(this, Objects.requireNonNull(TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(50)))));
        addRenderableWidget(new ElectricForgeButtonOff(blockEntity, getGuiLeft(), getGuiTop()));
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea50Height(leftPos  + 156, topPos + 26, menu.getBlockEntity().getEnergyStorage());
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        renderEnergyAreaTooltips(pPoseStack, pMouseX, pMouseY, leftPos, topPos);
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        this.font.draw(pPoseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int leftPos, int topPos) {
        if(isMouseAboveArea(pMouseX, pMouseY, leftPos, topPos, 156, 26, 8, 50)) {
            renderTooltip(pPoseStack, energyInfoArea.getTooltips(),
                    Optional.empty(), pMouseX - leftPos, pMouseY - topPos);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int leftPos, int topPos, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, leftPos + offsetX, topPos + offsetY, width, height);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        super.renderBg(poseStack, partialTicks, mouseX, mouseY);
        // Show target temperature
        int setTemp = (int) (51 * blockEntity.getBurnTemperature() / 1500);
        if (setTemp > 0)
        {
            blit(poseStack, leftPos + 8, topPos + 76 - Math.min(51, setTemp), 176, 6, 15, 5);
        }
        // Show actual temperature
        int temp = (int) (51 * blockEntity.getTemperature() / 1500);
        if (temp > 0)
        {
            blit(poseStack, leftPos + 8, topPos + 76 - Math.min(51, temp), 176, 0, 15, 5);
        }

        energyInfoArea.draw(poseStack);
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int mouseX, int mouseY)
    {
        super.renderTooltip(poseStack, mouseX, mouseY);
        if (RenderHelpers.isInside(mouseX, mouseY, leftPos + 8, topPos + 76 - 51, 15, 51))
        {
            final var text = TFCConfig.CLIENT.heatTooltipStyle.get().formatColored(blockEntity.getTemperature());
            if (text != null)
            {
                renderTooltip(poseStack, text, mouseX, mouseY);
            }
        }
    }
}
