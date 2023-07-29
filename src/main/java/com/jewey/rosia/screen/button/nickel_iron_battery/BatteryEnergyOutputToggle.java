package com.jewey.rosia.screen.button.nickel_iron_battery;

import com.jewey.rosia.common.blocks.entity.block_entity.NickelIronBatteryBlockEntity;
import com.jewey.rosia.screen.NickelIronBatteryScreen;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.network.PacketHandler;
import net.dries007.tfc.network.ScreenButtonPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.PacketDistributor;


public class BatteryEnergyOutputToggle extends Button {
    private final NickelIronBatteryBlockEntity battery;

    public BatteryEnergyOutputToggle(NickelIronBatteryBlockEntity battery, int guiLeft, int guiTop, OnTooltip tooltip)
    {
        super(guiLeft + 144, guiTop + 70, 25, 11, Component.nullToEmpty("Output Toggle"), button -> {
            PacketHandler.send(PacketDistributor.SERVER.noArg(), new ScreenButtonPacket(0, null));
        }, tooltip);

        this.battery = battery;
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick)
    {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, NickelIronBatteryScreen.TEXTURE);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        blit(poseStack, x, y, 177, 0, width, height, 256, 256);

        if (isHoveredOrFocused())
        {
            renderToolTip(poseStack, mouseX, mouseY);
        }
    }
}
