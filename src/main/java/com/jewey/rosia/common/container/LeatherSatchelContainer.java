package com.jewey.rosia.common.container;

import com.jewey.rosia.common.capabilities.food.RosiaFoodTraits;
import net.dries007.tfc.common.capabilities.VesselLike;
import net.dries007.tfc.common.capabilities.food.FoodCapability;
import net.dries007.tfc.common.container.CallbackSlot;
import net.dries007.tfc.common.container.ItemStackContainer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public class LeatherSatchelContainer extends ItemStackContainer {
    public static LeatherSatchelContainer create(ItemStack stack, InteractionHand hand, int slot, Inventory playerInv, int windowId)
    {
        return new LeatherSatchelContainer(stack, hand, slot, playerInv, windowId).init(playerInv, 8);
    }

    @Nullable
    private final VesselLike vessel;

    private LeatherSatchelContainer(ItemStack stack, InteractionHand hand, int slot, Inventory playerInv, int windowId)
    {
        super(ModContainerTypes.LEATHER_SATCHEL.get(), windowId, playerInv, stack, hand, slot);

        callback = vessel = VesselLike.get(stack);
    }

    @Override
    public boolean stillValid(Player playerIn)
    {
        return vessel != null && vessel.mode() == VesselLike.Mode.INVENTORY && vessel.getTemperature() == 0;
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
                {
                    case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, 0, 5, false);
                    case CONTAINER -> {
                        // Remove the preserved trait, pre-emptively, if the stack were to be transferred out. If any remains, then re-apply it.
                        FoodCapability.removeTrait(stack, RosiaFoodTraits.BOUND);
                        boolean result = !moveItemStackTo(stack, containerSlots, slots.size(), false);
                        if (result)
                        {
                            FoodCapability.applyTrait(stack, RosiaFoodTraits.BOUND);
                        }
                        yield result;
                    }
                };
    }

    @Override
    protected void addContainerSlots()
    {
        assert vessel != null;

        addSlot(new CallbackSlot(vessel, vessel, 0, 44, 39));
        addSlot(new CallbackSlot(vessel, vessel, 1, 62, 39));
        addSlot(new CallbackSlot(vessel, vessel, 2, 80, 39));
        addSlot(new CallbackSlot(vessel, vessel, 3, 98, 39));
        addSlot(new CallbackSlot(vessel, vessel, 4, 116, 39));
    }
}
