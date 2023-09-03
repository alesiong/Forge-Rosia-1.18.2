package com.jewey.rosia.common.container;

import com.jewey.rosia.common.blocks.entity.block_entity.BreadMachineBlockEntity;
import net.dries007.tfc.common.capabilities.Capabilities;
import net.dries007.tfc.common.container.BlockEntityContainer;
import net.dries007.tfc.common.container.ButtonHandlerContainer;
import net.dries007.tfc.common.container.CallbackSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class BreadMachineContainer extends BlockEntityContainer<BreadMachineBlockEntity> {
    public final BreadMachineBlockEntity blockEntity;
    private FluidStack fluidStack;


    public static BreadMachineContainer create(BreadMachineBlockEntity forge, Inventory playerInventory, int windowId) {
        return new BreadMachineContainer(forge, windowId).init(playerInventory, 20);
    }

    private BreadMachineContainer(BreadMachineBlockEntity forge, int windowId) {
        super(ModContainerTypes.BREAD_MACHINE.get(), windowId, forge);

//        addDataSlots(forge.getSyncableData());

        blockEntity = forge;
        this.fluidStack = blockEntity.getFluidStack();
    }

    @Override
    protected void addContainerSlots() {
        blockEntity.getCapability(Capabilities.ITEM).ifPresent(handler -> {
            int index = 0;
            addSlot(new CallbackSlot(blockEntity, handler, index, 97, 26));
        });
    }

    @Override
    protected boolean moveStack(ItemStack stack, int slotIndex) {
        return switch (typeOf(slotIndex)) {
            case MAIN_INVENTORY, HOTBAR -> !moveItemStackTo(stack, BreadMachineBlockEntity.SLOT_FLUID_CONTAINER_IN,
                    BreadMachineBlockEntity.SLOT_FLUID_CONTAINER_IN + 1, false);
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
