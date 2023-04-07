package com.jewey.rosia.screen;

import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.entity.block_entity.FireBoxBlockEntity;
import com.jewey.rosia.common.container.FireBoxContainer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.dries007.tfc.client.screen.BlockEntityScreen;
import net.dries007.tfc.common.capabilities.heat.Heat;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FireBoxScreenFinal extends BlockEntityScreen<FireBoxBlockEntity, FireBoxContainer>
{
    public static final ResourceLocation TEXTURE =
            new ResourceLocation(Rosia.MOD_ID,"textures/gui/firebox_gui.png");

    public FireBoxScreenFinal(FireBoxContainer container, Inventory playerInventory, Component name)
    {
        super(container, playerInventory, name, TEXTURE);
        inventoryLabelY += 20;
        imageHeight += 20;
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTicks, int mouseX, int mouseY)
    {
        super.renderBg(poseStack, partialTicks, mouseX, mouseY);
        int temp = (int) (51 * blockEntity.getTemperature() / Heat.maxVisibleTemperature());
        if (temp > 0)
        {
            blit(poseStack, leftPos + 8, topPos + 76 - Math.min(51, temp), 176, 0, 15, 5);
        }
        float getBurnProgress = (blockEntity.getBurnTicks() / blockEntity.getBurnTicksInit()) * 15;
        blit(poseStack, leftPos + 78, topPos + 50 - (int) getBurnProgress, 176, 20 - (int) getBurnProgress, 19, (int) getBurnProgress);
    }
}
