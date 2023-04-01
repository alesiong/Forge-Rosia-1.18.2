package com.jewey.rosia.common;

import net.dries007.tfc.util.Helpers;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class RosiaTags {
    public RosiaTags() {
    }

    public static class Fluids {
        public static final TagKey<Fluid> STEAM = create("steam");

        public Fluids() {
        }

        private static TagKey<Fluid> create(String id) {
            return TagKey.create(Registry.FLUID_REGISTRY, Helpers.identifier(id));
        }
    }
}
