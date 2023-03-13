package com.jewey.rosia.common.blocks.entity.custom;

import com.jewey.rosia.common.blocks.custom.fire_box;
import com.jewey.rosia.common.blocks.entity.ModBlockEntities;
import com.jewey.rosia.recipe.FireBoxRecipe;
import com.jewey.rosia.screen.FireBoxMenu;

import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.blockentities.BellowsBlockEntity;
import net.dries007.tfc.common.capabilities.heat.HeatCapability;
import net.dries007.tfc.common.recipes.HeatingRecipe;
import net.dries007.tfc.util.Fuel;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.IntArrayBuilder;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;


public class FireBoxBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            switch (slot) {
                case 0,1,2: return Helpers.isItem(stack.getItem(), TFCTags.Items.FORGE_FUEL);
            }
            return super.isItemValid(slot, stack);
        }
    };

    private static final Component NAME = Helpers.translatable("rosia.block_entity.fire_box");

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress= 1;         // short progress to instantly start consuming //
                                        // and not allow for burnTicks to drop below decrease threshold //


    public FireBoxBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FIRE_BOX_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            public int get(int index) {
                return switch (index) {
                    case 0 -> FireBoxBlockEntity.this.progress;
                    case 1 -> FireBoxBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            public void set(int index, int value) {
                switch (index) {
                    case 0 -> FireBoxBlockEntity.this.progress = value;
                    case 1 -> FireBoxBlockEntity.this.maxProgress = value;
                }
            }


            public int getCount() {
                return 2;
            }
        };
        temperature = 0;
        burnTemperature = 0;
        burnTicks = 0;
        airTicks = 0;
        lastPlayerTick = Integer.MIN_VALUE;
        syncableData = (new IntArrayBuilder()).add(() -> (int) temperature, (value) -> {
            temperature = (float)value;
        });

        Arrays.fill(cachedRecipes, null);
    }

    protected final ContainerData syncableData;
    private final HeatingRecipe[] cachedRecipes = new HeatingRecipe[1];
    private boolean needsSlotUpdate = false;
    private static float temperature; // Current Temperature
    private static int burnTicks; // Ticks remaining on the current item of fuel
    private static float burnTemperature; // Target temperature provided from the current item of fuel
    private static int airTicks; // Ticks of air provided by bellows
    private long lastPlayerTick; // Last player tick this forge was ticked (for purposes of catching up)
    private boolean needsRecipeUpdate; // Set to indicate on tick, the cached recipes need to be re-updated

    public void intakeAir(int amount)
    {
        airTicks += amount;
        if (airTicks > BellowsBlockEntity.MAX_DEVICE_AIR_TICKS)
        {
            airTicks = BellowsBlockEntity.MAX_DEVICE_AIR_TICKS;
        }
    }

    @Deprecated
    public long getLastUpdateTick()
    {
        return lastPlayerTick;
    }


    @Deprecated
    public void setLastUpdateTick(long tick)
    {
        lastPlayerTick = tick;
    }

    public ContainerData getSyncableData()
    {
        return syncableData;
    }

    public static float getTemperature()
    {
        return temperature;
    }
    public float getBurnTicks()
    {
        return burnTicks;
    }

    public int getAirTicks()
    {
        return airTicks;
    }

    @Override
    public Component getDisplayName() {
        return new TextComponent("Fire-Box");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new FireBoxMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @javax.annotation.Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps()  {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("fire_box.progress", progress);
        tag.putFloat("temperature", temperature);
        tag.putInt("burnTicks", burnTicks);
        tag.putInt("airTicks", airTicks);
        tag.putFloat("burnTemperature", burnTemperature);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("fire_box.progress");
        temperature = nbt.getFloat("temperature");
        burnTicks = nbt.getInt("burnTicks");
        airTicks = nbt.getInt("airTicks");
        burnTemperature = nbt.getFloat("burnTemperature");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, FireBoxBlockEntity pBlockEntity) {
        updateEntity(pLevel, pPos, pState);
        cascadeFuelSlots(pBlockEntity);
        if(hasRecipe(pBlockEntity)) {
            if(burnTicks <= 0) {
                pBlockEntity.progress++;
                setChanged(pLevel, pPos, pState);
                if(pBlockEntity.progress > pBlockEntity.maxProgress) {
                    consumeFuel(pBlockEntity);
                    pBlockEntity.resetProgress();
                    if(pState.getValue(fire_box.HEAT) < 1) {
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(fire_box.HEAT, 1));       // update heat //
                    }
                }
            }
        } else {
            pBlockEntity.resetProgress();
            setChanged(pLevel, pPos, pState);
            // check for burnTicks and fuel remaining, otherwise begin cooling //
            if(burnTicks <= 0 && pBlockEntity.itemHandler.getStackInSlot(0) == ItemStack.EMPTY) {
                burnTemperature = 0;
            }
        }
    }

    private static boolean hasRecipe(FireBoxBlockEntity entity) {
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FireBoxRecipe> match = level.getRecipeManager()
                .getRecipeFor(FireBoxRecipe.Type.INSTANCE, inventory, level);

        return match.isPresent();
    }



    public static void consumeFuel(FireBoxBlockEntity entity) {
        final ItemStack fuelStack = entity.itemHandler.getStackInSlot(0);
        Level level = entity.level;
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        Optional<FireBoxRecipe> match = level.getRecipeManager()
                .getRecipeFor(FireBoxRecipe.Type.INSTANCE, inventory, level);

        if(match.isPresent()) {
            if(burnTicks <= 0) {
                entity.itemHandler.extractItem(0, 1, false);
                Fuel fuel = Fuel.get(fuelStack);
                if (fuel != null) {
                    burnTicks += (fuel.getDuration() * 1.1);          //*** burns 10% more efficiently ***//
                    burnTemperature = fuel.getTemperature();          // may need to change for balance //
                }

                entity.resetProgress();
            }
        }
    }

    private static void cascadeFuelSlots(FireBoxBlockEntity entity) {
        // move from slot 1 to 0 if match and not empty
        if(entity.itemHandler.getStackInSlot(0) == entity.itemHandler.getStackInSlot(1)
                && entity.itemHandler.getStackInSlot(1) != ItemStack.EMPTY) {
            entity.itemHandler.extractItem(1,1, false);
            entity.itemHandler.extractItem(0,-1, false);
        }
        // move stack from slot 1 to 0 if empty
        else if(entity.itemHandler.getStackInSlot(0) == ItemStack.EMPTY
                && entity.itemHandler.getStackInSlot(1) != ItemStack.EMPTY ) {
            entity.itemHandler.setStackInSlot(0, entity.itemHandler.getStackInSlot(1).copy());
            entity.itemHandler.setStackInSlot(1, ItemStack.EMPTY);
        }
        // move from slot 2 to 0 if match and not empty
        else if(entity.itemHandler.getStackInSlot(0) == entity.itemHandler.getStackInSlot(2)
                && entity.itemHandler.getStackInSlot(2) != ItemStack.EMPTY
                && entity.itemHandler.getStackInSlot(1) == ItemStack.EMPTY) {
            entity.itemHandler.extractItem(2,1, false);
            entity.itemHandler.extractItem(0,-1, false);
        }
        // move stack from slot 2 to 0 if empty
        else if(entity.itemHandler.getStackInSlot(0) == ItemStack.EMPTY
                && entity.itemHandler.getStackInSlot(2) != ItemStack.EMPTY ) {
            entity.itemHandler.setStackInSlot(0, entity.itemHandler.getStackInSlot(2).copy());
            entity.itemHandler.setStackInSlot(2, ItemStack.EMPTY);
        }
    }

    private void resetProgress() {
        this.progress = 0;
    }

    public static void updateEntity(Level pLevel, BlockPos pPos, BlockState state) {
        updateTemperature(pPos, pLevel);
        updateBurnTicks(pLevel, pPos);
        updateHeat(state, pLevel, pPos);

    }

    public static void updateTemperature(BlockPos pos, Level level) {
        if(temperature < burnTemperature) {
            temperature += 1;                           // speed of temp increase //
        }
        if(temperature > burnTemperature) {
            temperature -= 0.5;                         // speed of temp decrease //
        }
        HeatCapability.provideHeatTo(level, pos.above(), temperature);  // provides heat to device above itself //
    }

    public static void updateBurnTicks(Level level, BlockPos pos) {
        if(burnTicks > 0) {
            burnTicks -= 1;
        }
        boolean isRaining = level.isRainingAt(pos);
        if (burnTicks > 0)
        {
            burnTicks -= airTicks > 0 || isRaining ? 2 : 1; // Fuel burns twice as fast using bellows, or in the rain
        }
    }

    public static void updateHeat(BlockState state, Level level, BlockPos pPos) {
        if (burnTemperature <= 0 && burnTicks <=0) {
            assert level != null;
            level.setBlockAndUpdate(pPos, state.setValue(fire_box.HEAT, 0));           // extinguish firebox if no longer burning fuel //
        }
    }



}
