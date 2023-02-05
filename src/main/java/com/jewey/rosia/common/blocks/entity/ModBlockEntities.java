package com.jewey.rosia.common.blocks.entity;

import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.ModBlocks;
import com.jewey.rosia.common.blocks.entity.custom.AutoQuernBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Rosia.MOD_ID);

    public static final RegistryObject<BlockEntityType<AutoQuernBlockEntity>> AUTO_QUERN_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("auto_quern_block_entity", () ->
                    BlockEntityType.Builder.of(AutoQuernBlockEntity::new,
                            ModBlocks.AUTO_QUERN.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
