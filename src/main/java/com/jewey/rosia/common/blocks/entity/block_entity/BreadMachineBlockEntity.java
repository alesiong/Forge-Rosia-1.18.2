package com.jewey.rosia.common.blocks.entity.block_entity;

import com.eerussianguy.firmalife.common.blocks.FLFluids;
import com.eerussianguy.firmalife.common.util.ExtraFluid;
import com.jewey.rosia.common.blocks.entity.ModBlockEntities;
import com.jewey.rosia.common.container.BreadMachineContainer;
import com.jewey.rosia.networking.ModMessages;
import com.jewey.rosia.networking.packet.EnergySyncS2CPacket;
import com.jewey.rosia.networking.packet.FluidSyncS2CPacket;
import com.jewey.rosia.util.ModEnergyStorage;
import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.blockentities.TickableInventoryBlockEntity;
import net.dries007.tfc.common.capabilities.DelegateItemHandler;
import net.dries007.tfc.common.capabilities.InventoryItemHandler;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

import static com.jewey.rosia.Rosia.MOD_ID;

public class BreadMachineBlockEntity extends TickableInventoryBlockEntity<BreadMachineBlockEntity.BreadMachineInventory> implements ICalendarTickable {

    public @NotNull Component getDisplayName() {
        return new TextComponent("Bread Machine");
    }

    public static final int SLOT_FLUID_CONTAINER_IN = 0;
    public static final int SLOTS = 2;

    private static final TranslatableComponent NAME = Helpers.translatable(MOD_ID + ".block_entity.bread_machine");

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public static void serverTick(Level level, BlockPos pos, BlockState state, BreadMachineBlockEntity machine) {
        machine.checkForLastTickSync();
        machine.checkForCalendarUpdate();

        if (hasFluidItemInSourceSlot(machine)) {
            transferItemFluidToFluidTank(machine);
        }
    }

    private static boolean hasEnoughFluid(BreadMachineBlockEntity generator) {
        return generator.FLUID_TANK.getFluidAmount() >= 10;
    }

    private static void transferItemFluidToFluidTank(BreadMachineBlockEntity generator) {
        generator.itemHandler.getStackInSlot(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
            int drainAmount = Math.min(generator.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if (generator.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(generator, stack, handler.getContainer());
            }
        });
    }

    private static void fillTankWithFluid(BreadMachineBlockEntity generator, FluidStack stack, ItemStack container) {
        generator.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        generator.itemHandler.extractItem(0, 1, false);
        generator.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasFluidItemInSourceSlot(BreadMachineBlockEntity generator) {
        return generator.itemHandler.getStackInSlot(0).getCount() > 0;
    }


    //    private final IntArrayBuilder syncableData;
    private boolean needsRecipeUpdate;
    private int tick;

    private int lastFillTicks;
    private long lastUpdateTick; // for ICalendarTickable

    public BreadMachineBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BREAD_MACHINE_BLOCK_ENTITY.get(), pos, state, BreadMachineInventory::new, NAME);

        needsRecipeUpdate = true;
        lastFillTicks = 0;
        lastUpdateTick = Integer.MIN_VALUE;
        tick = 0;


//        syncableData = new IntArrayBuilder()
//                .add(() -> (int) temperature, value -> temperature = value);
    }


//    public ContainerData getSyncableData() {
//        return syncableData;
//    }


    @Override
    public void onCalendarUpdate(long ticks) {
        assert level != null;
    }

    @Override
    public int getSlotStackLimit(int slot) {
        return 1;
    }

    @Override
    @Deprecated
    public long getLastUpdateTick() {
        return lastUpdateTick;
    }

    @Override
    @Deprecated
    public void setLastUpdateTick(long tick) {
        lastUpdateTick = tick;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        ModMessages.sendToClients(new EnergySyncS2CPacket(this.ENERGY_STORAGE.getEnergyStored(), getBlockPos()));
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return BreadMachineContainer.create(this, player.getInventory(), containerId);
    }


    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    public void loadAdditional(CompoundTag nbt) {
        lastUpdateTick = nbt.getLong("lastUpdateTick");
        needsRecipeUpdate = true;
        ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));
        FLUID_TANK.readFromNBT(nbt);
        super.loadAdditional(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        nbt.putLong("lastUpdateTick", lastUpdateTick);
        nbt.putInt("energy", ENERGY_STORAGE.getEnergyStored());
        nbt = FLUID_TANK.writeToNBT(nbt);
        super.saveAdditional(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyFluidHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    private final ItemStackHandler itemHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 ->
                        stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent() || stack.getItem() instanceof BucketItem;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    public IFluidHandler getFluidStorage() {
        return FLUID_TANK;
    }

    public static class BreadMachineInventory implements DelegateItemHandler, INBTSerializable<CompoundTag> {
        private final BreadMachineBlockEntity generator;

        private final InventoryItemHandler inventory;

        BreadMachineInventory(InventoryBlockEntity<?> entity) {
            generator = (BreadMachineBlockEntity) entity;
            inventory = new InventoryItemHandler(entity, SLOTS);
        }

        @Override
        public IItemHandlerModifiable getItemHandler() {
            return inventory;
        }

        @Override
        public CompoundTag serializeNBT() {
            final CompoundTag nbt = new CompoundTag();
            nbt.put("inventory", inventory.serializeNBT());
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            inventory.deserializeNBT(nbt.getCompound("inventory"));
        }

    }

    /**
     * Energy stuff
     */

    private static final int maxTransfer = 50;

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(400, maxTransfer) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            if (getLevel() instanceof ServerLevel) {
                ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
            }
        }

        @Override
        public boolean canReceive() {
            return false;
        }
    };

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();


    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }


    /**
     * Fluid stuff
     */


    private final FluidTank FLUID_TANK = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == FLFluids.EXTRA_FLUIDS.get(ExtraFluid.YEAST_STARTER).getSource();
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public float render() {
        return (float) FLUID_TANK.getFluidAmount() / (float) FLUID_TANK.getCapacity();
    }

}


