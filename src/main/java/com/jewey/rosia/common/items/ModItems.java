package com.jewey.rosia.common.items;

import com.jewey.rosia.Rosia;
import net.dries007.tfc.common.items.PropickItem;
import net.dries007.tfc.common.items.TFCHoeItem;
import net.dries007.tfc.common.items.ToolItem;
import net.minecraft.world.item.*;
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

    public static final RegistryObject<Item> INVAR_INGOT = ITEMS.register("invar_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> INVAR_DOUBLE_INGOT = ITEMS.register("invar_double_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> INVAR_SHEET = ITEMS.register("invar_sheet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> INVAR_DOUBLE_SHEET = ITEMS.register("invar_double_sheet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> INVAR_ROD = ITEMS.register("invar_rod",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> WEAK_PURPLE_STEEL_INGOT = ITEMS.register("weak_purple_steel_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_INGOT = ITEMS.register("purple_steel_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_DOUBLE_INGOT = ITEMS.register("purple_steel_double_ingot",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_SHEET = ITEMS.register("purple_steel_sheet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_DOUBLE_SHEET = ITEMS.register("purple_steel_double_sheet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_ROD = ITEMS.register("purple_steel_rod",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_WIRE = ITEMS.register("purple_steel_wire",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_COIL = ITEMS.register("purple_steel_coil",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> MAGNETITE_POWDER = ITEMS.register("magnetite_powder",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> COMPRESSED_MAGNETITE = ITEMS.register("compressed_magnetite",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> MAGNET = ITEMS.register("magnet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> MOTOR = ITEMS.register("motor",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> STEEL_GRINDSTONE = ITEMS.register("steel_grindstone",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB).durability(1000)));

    public static final RegistryObject<Item> STEEL_MACHINE_DIE = ITEMS.register("steel_machine_die",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB).durability(500)));

    public static final RegistryObject<Item> STEEL_ROLLERS = ITEMS.register("steel_rollers",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB).durability(500)));

    public static final RegistryObject<Item> SATCHEL_PART = ITEMS.register("satchel_part",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> LEATHER_SATCHEL = ITEMS.register("leather_satchel",
            () -> new SatchelItem(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB).stacksTo(1)));

    public static final RegistryObject<Item> PURPLE_STEEL_RIFLE = ITEMS.register("purple_steel_rifle",
            () -> new RifleItem(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB).durability(2000)));

    public static final RegistryObject<Item> BULLET = ITEMS.register("bullets",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> RIFLE_AMMO = ITEMS.register("rifle_ammo",
            () -> new BulletItem(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    //TOOLS & TOOL HEADS
    public static final RegistryObject<Item> PURPLE_STEEL_PICKAXE = ITEMS.register("purple_steel_pickaxe",
            () -> new PickaxeItem(ModTiers.PURPLE_STEEL, -3,-2.8F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_PICKAXE_HEAD = ITEMS.register("purple_steel_pickaxe_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_PROPICK = ITEMS.register("purple_steel_propick",
            () -> new PropickItem(ModTiers.PURPLE_STEEL, -6,-2.8F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_PROPICK_HEAD = ITEMS.register("purple_steel_propick_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_AXE = ITEMS.register("purple_steel_axe",
            () -> new AxeItem(ModTiers.PURPLE_STEEL, 3.5F,-3.1F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_AXE_HEAD = ITEMS.register("purple_steel_axe_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_SHOVEL = ITEMS.register("purple_steel_shovel",
            () -> new ShovelItem(ModTiers.PURPLE_STEEL, -2.5F,-3F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_SHOVEL_HEAD = ITEMS.register("purple_steel_shovel_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_HOE = ITEMS.register("purple_steel_hoe",
            () -> new TFCHoeItem(ModTiers.PURPLE_STEEL, (int) -2.5,-2F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_HOE_HEAD = ITEMS.register("purple_steel_hoe_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
