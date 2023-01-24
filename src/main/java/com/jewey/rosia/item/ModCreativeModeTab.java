package com.jewey.rosia.item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab ROSIA_TAB = new CreativeModeTab("rosiatab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.COPPER_WIRE.get());
        }
    };
}
