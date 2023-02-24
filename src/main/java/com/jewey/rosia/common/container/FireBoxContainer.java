package com.jewey.rosia.common.container;

import com.jewey.rosia.common.blocks.entity.custom.FireBoxBlockEntity;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.dries007.tfc.common.capabilities.Capabilities;

public class FireBoxContainer extends BlockEntityContainer<FireBoxBlockEntity>
{
    public static FireBoxContainer create(FireBoxBlockEntity forge, Inventory playerInventory, int windowId)
    {
        return new FireBoxContainer(forge, windowId).init(playerInventory, 20);
    }

    private FireBoxContainer(FireBoxBlockEntity forge, int windowId)
    {
        super(ModContainerTypes.FIRE_BOX.get(), windowId, forge);

        addDataSlots(forge.getSyncableData());
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
                {
                    case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, FireBoxBlockEntity.SLOT_FUEL_MIN,
                            FireBoxBlockEntity.SLOT_FUEL_MAX + 1, false);
                    case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
                };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            // Fuel slots
            // Note: the order of these statements is important
            int index = FireBoxBlockEntity.SLOT_FUEL_MIN;
            addSlot(new CallbackSlot(blockEntity, handler, index++, 63, 58));
            addSlot(new CallbackSlot(blockEntity, handler, index++, 81, 58));
            addSlot(new CallbackSlot(blockEntity, handler, index++, 99, 58));
        });
    }
}

