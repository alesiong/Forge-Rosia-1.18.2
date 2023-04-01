package com.jewey.rosia.common.container;

import com.jewey.rosia.common.blocks.entity.custom.FireBoxBlockEntity;
import com.jewey.rosia.common.blocks.entity.custom.SteamGeneratorBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import static com.jewey.rosia.common.blocks.entity.custom.FireBoxBlockEntity.SLOT_FUEL_MIN;

public class SteamGeneratorContainer extends BlockEntityContainer<SteamGeneratorBlockEntity>
{
    public final SteamGeneratorBlockEntity blockEntity;
    private FluidStack fluidStack;


    public static SteamGeneratorContainer create(SteamGeneratorBlockEntity forge, Inventory playerInventory, int windowId)
    {
        return new SteamGeneratorContainer(forge, windowId).init(playerInventory, 20);
    }

    private SteamGeneratorContainer(SteamGeneratorBlockEntity forge, int windowId)
    {
        super(ModContainerTypes.STEAM_GENERATOR.get(), windowId, forge);

        addDataSlots(forge.getSyncableData());

        blockEntity = (SteamGeneratorBlockEntity) forge;
        this.fluidStack = blockEntity.getFluidStack();
    }

    @Override
    protected void addContainerSlots()
    {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            int index = 0;
            addSlot(new CallbackSlot(blockEntity, handler, index, 97, 26));
        });
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex)
    {
        return switch (typeOf(slotIndex))
                {
                    case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, SteamGeneratorBlockEntity.SLOT_FLUID_CONTAINER_IN,
                            SteamGeneratorBlockEntity.SLOT_FLUID_CONTAINER_IN + 1, false);
                    case CONTAINER -> !moveItemStackTo(stack, containerSlots, slots.size(), false);
                };
    }

    public void setFluid(FluidStack fluidStack) {
        this.fluidStack = fluidStack;
    }
    public FluidStack getFluidStack() {
        return fluidStack;
    }
}
