package com.jewey.rosia.common.blocks.entity.block_entity;

import com.jewey.rosia.common.blocks.custom.steam_generator;
import com.jewey.rosia.common.blocks.entity.ModBlockEntities;
import com.jewey.rosia.common.container.SteamGeneratorContainer;
import com.jewey.rosia.networking.ModMessages;
import com.jewey.rosia.networking.packet.EnergySyncS2CPacket;
import com.jewey.rosia.networking.packet.FluidSyncS2CPacket;
import com.jewey.rosia.util.ModEnergyStorage;
import net.dries007.tfc.common.blockentities.InventoryBlockEntity;
import net.dries007.tfc.common.blockentities.TickableInventoryBlockEntity;
import net.dries007.tfc.common.capabilities.*;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.capabilities.heat.IHeatBlock;
import net.dries007.tfc.util.*;
import net.dries007.tfc.util.calendar.ICalendarTickable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
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

public class SteamGeneratorBlockEntity extends TickableInventoryBlockEntity<SteamGeneratorBlockEntity.SteamGeneratorInventory> implements ICalendarTickable
{

    public @NotNull Component getDisplayName() {
        return new TextComponent("Steam Generator");
    }
    public static final int SLOT_FLUID_CONTAINER_IN = 0;
    public static final int SLOTS = 1;

    private static final TranslatableComponent NAME = Helpers.translatable(MOD_ID + ".block_entity.steam_generator");
    private static final int TARGET_TEMPERATURE_STABILITY_TICKS = 5;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    public static void serverTick(Level level, BlockPos pos, BlockState state, SteamGeneratorBlockEntity generator) {
        generator.checkForLastTickSync();
        generator.checkForCalendarUpdate();

        if (generator.needsRecipeUpdate) {
            generator.needsRecipeUpdate = false;
        }

        if (generator.temperature != generator.targetTemperature) {
            generator.temperature = HeatCapability.adjustTempTowards(generator.temperature, generator.targetTemperature);
        }
        if (generator.targetTemperatureStabilityTicks > 0) {
            generator.targetTemperatureStabilityTicks--;
        }
        if (generator.targetTemperature > 0 && generator.targetTemperatureStabilityTicks == 0) {
            // generator target temperature decays constantly, since it is set externally. As long as we don't consider ourselves 'stable' (received an external setTemperature() call within the last 5 ticks)
            generator.targetTemperature = HeatCapability.adjustTempTowards(generator.targetTemperature, 0);
        }

        if(generator.temperature <= 0) {
            generator.ticks = 0;
        }
        if(generator.ticks > 0) {
            generator.ticks -= 1;
        }

    //Power Generation
        if (generator.temperature > 100 && generator.ticks == 0 && hasEnoughFluid(generator)
                && generator.ENERGY_STORAGE.getEnergyStored() < generator.ENERGY_STORAGE.getMaxEnergyStored()) {
            //higher temperatures = higher "steam pressure" = faster power generation
            int pressure = Mth.clamp((int) ((1800 - generator.temperature) / 80), 0, 23);

            if(generator.temperature >= 2014) {
            //at max temperature for coal 4 FE every tick
                generator.ENERGY_STORAGE.receiveEnergy(4,false);
                generator.ticks += pressure;
            } else {
            //otherwise get 3 FE every-other-tick + pressure
                generator.ENERGY_STORAGE.receiveEnergy(3, false);
                generator.ticks += pressure + 1;
            }
            generator.FLUID_TANK.drain(10, IFluidHandler.FluidAction.EXECUTE);

            level.setBlock(pos, state.setValue(steam_generator.STEAM, true), Block.UPDATE_ALL);
        }

        if(generator.ENERGY_STORAGE.getEnergyStored() >= generator.ENERGY_STORAGE.getMaxEnergyStored()
                || !hasEnoughFluid(generator) || generator.temperature <= 0) {
            level.setBlock(pos, state.setValue(steam_generator.STEAM, false), Block.UPDATE_ALL);
        }

        if(hasFluidItemInSourceSlot(generator)) {
            transferItemFluidToFluidTank(generator);
        }
    }

    private static boolean hasEnoughFluid(SteamGeneratorBlockEntity generator) {
        return generator.FLUID_TANK.getFluidAmount() >= 10;
    }

    private static void transferItemFluidToFluidTank(SteamGeneratorBlockEntity generator) {
        generator.itemHandler.getStackInSlot(0).getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(handler -> {
            int drainAmount = Math.min(generator.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if(generator.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(generator, stack, handler.getContainer());
            }
        });
    }

    private static void fillTankWithFluid(SteamGeneratorBlockEntity generator, FluidStack stack, ItemStack container) {
        generator.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);
        generator.itemHandler.extractItem(0, 1, false);
        generator.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasFluidItemInSourceSlot(SteamGeneratorBlockEntity generator) {
        return generator.itemHandler.getStackInSlot(0).getCount() > 0;
    }


    private final SidedHandler.Noop<IHeatBlock> sidedHeat;
    private final IntArrayBuilder syncableData;
    private float temperature;
    private float targetTemperature;
    private boolean needsRecipeUpdate;
    private float ticks;

    /**
     * Prevent the target temperature from "hovering" around a particular value.
     * Effectively means that setTemperature() sets for the next 5 ticks, before it starts to decay naturally.
     */
    private int targetTemperatureStabilityTicks;
    private int lastFillTicks;
    private long lastUpdateTick; // for ICalendarTickable

    public SteamGeneratorBlockEntity(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.STEAM_GENERATOR_BLOCK_ENTITY.get(), pos, state, SteamGeneratorInventory::new, NAME);

        needsRecipeUpdate = true;
        temperature = targetTemperature = 0;
        lastFillTicks = 0;
        lastUpdateTick = Integer.MIN_VALUE;
        ticks = 0;


        // Heat can be accessed from all sides
        sidedHeat = new SidedHandler.Noop<>(inventory);

        syncableData = new IntArrayBuilder()
                .add(() -> (int) temperature, value -> temperature = value);
    }

    public float getTemperature()
    {
        return temperature;
    }

    public ContainerData getSyncableData()
    {
        return syncableData;
    }


    @Override
    public void onCalendarUpdate(long ticks)
    {
        assert level != null;

        targetTemperature = HeatCapability.adjustTempTowards(targetTemperature, 0, ticks);
        temperature = HeatCapability.adjustTempTowards(temperature, targetTemperature, ticks);
    }

    @Override
    public int getSlotStackLimit(int slot)
    {
        return 1;
    }

    @Override
    @Deprecated
    public long getLastUpdateTick()
    {
        return lastUpdateTick;
    }

    @Override
    @Deprecated
    public void setLastUpdateTick(long tick)
    {
        lastUpdateTick = tick;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player)
    {
        ModMessages.sendToClients(new EnergySyncS2CPacket(this.ENERGY_STORAGE.getEnergyStored(), getBlockPos()));
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return SteamGeneratorContainer.create(this, player.getInventory(), containerId);
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
    public void loadAdditional(CompoundTag nbt)
    {
        temperature = nbt.getFloat("temperature");
        targetTemperature = nbt.getFloat("targetTemperature");
        targetTemperatureStabilityTicks = nbt.getInt("targetTemperatureStabilityTicks");
        lastUpdateTick = nbt.getLong("lastUpdateTick");
        needsRecipeUpdate = true;
        ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));
        ticks = nbt.getFloat("ticks");
        FLUID_TANK.readFromNBT(nbt);
        super.loadAdditional(nbt);
    }

    @Override
    public void saveAdditional(CompoundTag nbt)
    {
        nbt.putFloat("temperature", temperature);
        nbt.putFloat("targetTemperature", targetTemperature);
        nbt.putInt("targetTemperatureStabilityTicks", targetTemperatureStabilityTicks);
        nbt.putLong("lastUpdateTick", lastUpdateTick);
        nbt.putInt("energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putFloat("ticks", ticks);
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
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(side == null) {
                return lazyItemHandler.cast();
            }
        }
        if(cap == CapabilityEnergy.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return lazyFluidHandler.cast();
        }
        if (cap == HeatCapability.BLOCK_CAPABILITY)
        {
            return sidedHeat.getSidedHandler(side).cast();
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
                case 0 -> stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent() || stack.getItem() instanceof BucketItem;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    public IFluidHandler getFluidStorage() {
        return FLUID_TANK;
    }

    public static class SteamGeneratorInventory implements DelegateItemHandler, INBTSerializable<CompoundTag>, IHeatBlock
    {
        private final SteamGeneratorBlockEntity generator;

        private final InventoryItemHandler inventory;

        SteamGeneratorInventory(InventoryBlockEntity<?> entity)
        {
            generator = (SteamGeneratorBlockEntity) entity;
            inventory = new InventoryItemHandler(entity, SLOTS);
        }

        @Override
        public IItemHandlerModifiable getItemHandler()
        {
            return inventory;
        }

        @Override
        public CompoundTag serializeNBT()
        {
            final CompoundTag nbt = new CompoundTag();
            nbt.put("inventory", inventory.serializeNBT());
            return nbt;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt)
        {
            inventory.deserializeNBT(nbt.getCompound("inventory"));
        }

        @Override
        public float getTemperature()
        {
            return generator.temperature;
        }

        @Override
        public void setTemperature(float temperature)
        {
            generator.targetTemperature = temperature;
            generator.targetTemperatureStabilityTicks = TARGET_TEMPERATURE_STABILITY_TICKS;
            generator.markForSync();
        }

        @Override
        public void setTemperatureIfWarmer(float temperature)
        {
            // Override to still cause an update to the stability ticks
            if (temperature >= generator.temperature)
            {
                generator.temperature = temperature;
                generator.targetTemperatureStabilityTicks = TARGET_TEMPERATURE_STABILITY_TICKS;
                generator.markForSync();
            }
        }

    }
    /**
     * Energy stuff
     */


    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(800, 10) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
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
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };

    public void setFluid(FluidStack stack){
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


