package com.jewey.rosia.common.container;

import com.jewey.rosia.common.blocks.entity.block_entity.FridgeBlockEntity;
import com.jewey.rosia.common.capabilities.food.RosiaFoodTraits;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;


public class FridgeContainer extends BlockEntityContainer<FridgeBlockEntity>
{

    public static FridgeContainer create(FridgeBlockEntity Fridge, Inventory playerInventory, int windowId)
    {
        return new FridgeContainer(Fridge, windowId).init(playerInventory, 0);
    }

    private FridgeContainer(FridgeBlockEntity Fridge, int windowId)
    {
        super(ModContainerTypes.FRIDGE.get(), windowId, Fridge);
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
                {
                    case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, 9, false);
                    case CONTAINER -> {
                        // Remove the preserved trait, pre-emptively, if the stack were to be transferred out. If any remains, then re-apply it.
                        FoodCapability.removeTrait(stack, RosiaFoodTraits.REFRIGERATED);
                        boolean result = !moveItemStackTo(stack, containerSlots, slots.size(), false);
                        if (result)
                        {
                            FoodCapability.applyTrait(stack, RosiaFoodTraits.REFRIGERATED);
                        }
                        yield result;
                    }
                };
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            addSlot(new CallbackSlot(blockEntity, handler, 0, 62, 19));
            addSlot(new CallbackSlot(blockEntity, handler, 1, 80, 19));
            addSlot(new CallbackSlot(blockEntity, handler, 2, 98, 19));
            addSlot(new CallbackSlot(blockEntity, handler, 3, 62, 37));
            addSlot(new CallbackSlot(blockEntity, handler, 4, 80, 37));
            addSlot(new CallbackSlot(blockEntity, handler, 5, 98, 37));
            addSlot(new CallbackSlot(blockEntity, handler, 6, 62, 55));
            addSlot(new CallbackSlot(blockEntity, handler, 7, 80, 55));
            addSlot(new CallbackSlot(blockEntity, handler, 8, 98, 55));
        });
    }
}