package com.jewey.rosia.common.container;

import com.jewey.rosia.common.blocks.entity.block_entity.CharcoalKilnBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;


public class CharcoalKilnContainer extends BlockEntityContainer<CharcoalKilnBlockEntity>
{
    public static CharcoalKilnContainer create(CharcoalKilnBlockEntity forge, Inventory playerInventory, int windowId)
    {
        return new CharcoalKilnContainer(forge, windowId).init(playerInventory, 0);
    }

    private CharcoalKilnContainer(CharcoalKilnBlockEntity forge, int windowId)
    {
        super(ModContainerTypes.CHARCOAL_KILN.get(), windowId, forge);
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
                {
                    case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, CharcoalKilnBlockEntity.SLOT_MIN,
                            CharcoalKilnBlockEntity.SLOT_MAX + 1, false);
                    case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
                };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            // Fuel slots
            addSlot(new CallbackSlot(blockEntity, handler, 0, 71, 23));
            addSlot(new CallbackSlot(blockEntity, handler, 1, 89, 23));
            addSlot(new CallbackSlot(blockEntity, handler, 2, 71, 41));
            addSlot(new CallbackSlot(blockEntity, handler, 3, 89, 41));

        });
    }

}