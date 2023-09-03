package com.jewey.rosia.common.blocks.entity;

import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.ModBlocks;
import com.jewey.rosia.common.blocks.entity.block_entity.*;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Rosia.MOD_ID);

    public static final RegistryObject<BlockEntityType<AutoQuernBlockEntity>> AUTO_QUERN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("auto_quern_block_entity", () ->
                    BlockEntityType.Builder.of(AutoQuernBlockEntity::new,
                            ModBlocks.AUTO_QUERN.get()).build(null));


    public static final RegistryObject<BlockEntityType<FireBoxBlockEntity>> FIRE_BOX_BLOCK_ENTITY =
            register("fire_box_block_entity", FireBoxBlockEntity::new, ModBlocks.FIRE_BOX);

    public static final RegistryObject<BlockEntityType<SteamGeneratorBlockEntity>> STEAM_GENERATOR_BLOCK_ENTITY =
            register("steam_generator_block_entity", SteamGeneratorBlockEntity::new, ModBlocks.STEAM_GENERATOR);

    public static final RegistryObject<BlockEntityType<NickelIronBatteryBlockEntity>> NICKEL_IRON_BATTERY_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("nickel_iron_battery_block_entity", () ->
                    BlockEntityType.Builder.of(NickelIronBatteryBlockEntity::new,
                            ModBlocks.NICKEL_IRON_BATTERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<WaterPumpBlockEntity>> WATER_PUMP_BLOCK_ENTITY =
            register("water_pump_block_entity", WaterPumpBlockEntity::new, ModBlocks.WATER_PUMP);

    public static final RegistryObject<BlockEntityType<ExtrudingMachineBlockEntity>> EXTRUDING_MACHINE_BLOCK_ENTITY =
            register("extruding_machine_block_entity", ExtrudingMachineBlockEntity::new, ModBlocks.EXTRUDING_MACHINE);

    public static final RegistryObject<BlockEntityType<RollingMachineBlockEntity>> ROLLING_MACHINE_BLOCK_ENTITY =
            register("rolling_machine_block_entity", RollingMachineBlockEntity::new, ModBlocks.ROLLING_MACHINE);

    public static final RegistryObject<BlockEntityType<ElectricForgeBlockEntity>> ELECTRIC_FORGE_BLOCK_ENTITY =
            register("electric_forge_block_entity", ElectricForgeBlockEntity::new, ModBlocks.ELECTRIC_FORGE);

    public static final RegistryObject<BlockEntityType<ElectricGrillBlockEntity>> ELECTRIC_GRILL_BLOCK_ENTITY =
            register("electric_grill_block_entity", ElectricGrillBlockEntity::new, ModBlocks.ELECTRIC_GRILL);

    public static final RegistryObject<BlockEntityType<FridgeBlockEntity>> FRIDGE_BLOCK_ENTITY =
            register("fridge_block_entity", FridgeBlockEntity::new, ModBlocks.FRIDGE);

    public static final RegistryObject<BlockEntityType<ElectricLanternBlockEntity>> ELECTRIC_LANTERN_BLOCK_ENTITY =
            register("electric_lantern_block_entity", ElectricLanternBlockEntity::new, ModBlocks.ELECTRIC_LANTERN);

    public static final RegistryObject<BlockEntityType<CharcoalKilnBlockEntity>> CHARCOAL_KILN_BLOCK_ENTITY =
            register("charcoal_kiln_block_entity", CharcoalKilnBlockEntity::new, ModBlocks.CHARCOAL_KILN);

    public static final RegistryObject<BlockEntityType<CanningPressBlockEntity>> CANNING_PRESS_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("canning_press_block_entity", () ->
                    BlockEntityType.Builder.of(CanningPressBlockEntity::new,
                            ModBlocks.CANNING_PRESS.get()).build(null));

    public static final RegistryObject<BlockEntityType<ElectricLoomBlockEntity>> ELECTRIC_LOOM_BLOCK_ENTITY =
            register("electric_loom_block_entity", ElectricLoomBlockEntity::new, ModBlocks.ELECTRIC_LOOM);


    public static final RegistryObject<BlockEntityType<BreadMachineBlockEntity>> BREAD_MACHINE_BLOCK_ENTITY =
            register("bread_machine_block_entity", BreadMachineBlockEntity::new, ModBlocks.BREAD_MACHINE);

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends Block> block)
    {
        return RegistrationHelpers.register(BLOCK_ENTITIES, name, factory, block);
    }

}
