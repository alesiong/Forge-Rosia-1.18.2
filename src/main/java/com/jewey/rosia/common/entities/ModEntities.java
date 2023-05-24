package com.jewey.rosia.common.entities;

import com.jewey.rosia.Rosia;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITIES, Rosia.MOD_ID);

    public static final RegistryObject<EntityType<BulletEntity>> BULLET = ENTITY_TYPES.register("bullet",
            () -> EntityType.Builder.of((EntityType.EntityFactory<BulletEntity>) BulletEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F).build("bullet"));


}
