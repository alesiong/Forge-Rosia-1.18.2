package com.jewey.rosia.common.items;

import com.jewey.rosia.Rosia;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Rosia.MOD_ID);


    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> COPPER_COIL = ITEMS.register("copper_coil",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> NICHROME_INGOT = ITEMS.register("nichrome_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> NICHROME_SHEET = ITEMS.register("nichrome_sheet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> NICHROME_WIRE = ITEMS.register("nichrome_wire",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> HEAT_COIL = ITEMS.register("heat_coil",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> MAGNETITE_POWDER = ITEMS.register("magnetite_powder",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> COMPRESSED_MAGNETITE = ITEMS.register("compressed_magnetite",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> UNFIRED_FIRECLAY_INGOT = ITEMS.register("unfired_fireclay_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> INGOT_FIRECLAY_EMPTY = ITEMS.register("ingot_fireclay_empty",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> MAGNET = ITEMS.register("magnet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
