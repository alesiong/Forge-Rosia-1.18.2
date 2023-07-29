package com.jewey.rosia.common.container;

import com.jewey.rosia.common.blocks.entity.block_entity.ElectricForgeBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.ButtonHandlerContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;


public class ElectricForgeContainer extends BlockEntityContainer<ElectricForgeBlockEntity> implements ButtonHandlerContainer
{

    public static ElectricForgeContainer create(ElectricForgeBlockEntity forge, Inventory playerInventory, int windowId)
    {
        return new ElectricForgeContainer(forge, windowId).init(playerInventory, 20);
    }

    private ElectricForgeContainer(ElectricForgeBlockEntity forge, int windowId)
    {
        super(ModContainerTypes.ELECTRIC_FORGE.get(), windowId, forge);

        addDataSlots(forge.getSyncableData());
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
                {
                    case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, ElectricForgeBlockEntity.SLOT_EXTRA_MIN,
                            ElectricForgeBlockEntity.SLOT_EXTRA_MAX + 1, false)
                            && !moveItemStackTo(stack, ElectricForgeBlockEntity.SLOT_INPUT_MIN,
                            ElectricForgeBlockEntity.SLOT_INPUT_MAX + 1, false);
                    case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
                };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            // Input slots
            // Note: the order of these statements is important
            int index = ElectricForgeBlockEntity.SLOT_INPUT_MIN;
            addSlot(new CallbackSlot(blockEntity, handler, index++, 44, 19));
            addSlot(new CallbackSlot(blockEntity, handler, index++, 62, 19));
            addSlot(new CallbackSlot(blockEntity, handler, index++, 80, 19));
            addSlot(new CallbackSlot(blockEntity, handler, index++, 98, 19));
            addSlot(new CallbackSlot(blockEntity, handler, index, 116, 19));

            // Extra slots (for ceramic molds)
            int i = ElectricForgeBlockEntity.SLOT_EXTRA_MIN;
            {
                addSlot(new CallbackSlot(blockEntity, handler, i, 80, 39));
            }
        });
    }

    @Override
    public void onButtonPress(int buttonID, @Nullable CompoundTag compoundTag) {
        if (player instanceof ServerPlayer serverPlayer)
        {
            if(buttonID == 0){ blockEntity.setTemp(serverPlayer, 1550); }
            if(buttonID == 1){ blockEntity.setTemp(serverPlayer, 1410); }
            if(buttonID == 2){ blockEntity.setTemp(serverPlayer, 1310); }
            if(buttonID == 3){ blockEntity.setTemp(serverPlayer, 1110); }
            if(buttonID == 4){ blockEntity.setTemp(serverPlayer, 940); }
            if(buttonID == 5){ blockEntity.setTemp(serverPlayer, 740); }
            if(buttonID == 6){ blockEntity.setTemp(serverPlayer, 590); }
            if(buttonID == 7){ blockEntity.setTemp(serverPlayer, 490); }
            if(buttonID == 8){ blockEntity.setTemp(serverPlayer, 220); }
            if(buttonID == 9){ blockEntity.setTemp(serverPlayer, 90); }
            if(buttonID == 10){ blockEntity.setTemp(serverPlayer, 50); }
            if(buttonID == 11){ blockEntity.setTemp0(serverPlayer, 0); }
        }
    }
}