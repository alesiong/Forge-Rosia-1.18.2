package com.jewey.rosia.common.items;

import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.ModBlocks;
import net.dries007.tfc.common.TFCTags;
import net.dries007.tfc.common.capabilities.food.Nutrient;
import net.dries007.tfc.common.items.*;
import net.dries007.tfc.util.Helpers;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;
import java.util.Map;
import java.util.function.Supplier;

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

    public static final RegistryObject<Item> PURPLE_STEEL_CHISEL = ITEMS.register("purple_steel_chisel",
            () -> new ChiselItem(ModTiers.PURPLE_STEEL, (int) -8.5,-1.5F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_CHISEL_HEAD = ITEMS.register("purple_steel_chisel_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_HAMMER = ITEMS.register("purple_steel_hammer",
            () -> new ToolItem(ModTiers.PURPLE_STEEL, -0.5F,-3F,
                    TFCTags.Blocks.MINEABLE_WITH_HAMMER, new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_HAMMER_HEAD = ITEMS.register("purple_steel_hammer_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_SAW = ITEMS.register("purple_steel_saw",
            () -> new AxeItem(ModTiers.PURPLE_STEEL, -5.5F,-3F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_SAW_BLADE = ITEMS.register("purple_steel_saw_blade",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_SWORD = ITEMS.register("purple_steel_sword",
            () -> new SwordItem(ModTiers.PURPLE_STEEL, 0,-2.4F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_SWORD_BLADE = ITEMS.register("purple_steel_sword_blade",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_MACE = ITEMS.register("purple_steel_mace",
            () -> new MaceItem(ModTiers.PURPLE_STEEL, (int) 1F,-3F,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_MACE_BLADE = ITEMS.register("purple_steel_mace_head",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_KNIFE = ITEMS.register("purple_steel_knife",
            () -> new ToolItem(ModTiers.PURPLE_STEEL, -4.5F,-2F,
                    TFCTags.Blocks.MINEABLE_WITH_KNIFE, new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_KNIFE_BLADE = ITEMS.register("purple_steel_knife_blade",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_SCYTHE = ITEMS.register("purple_steel_scythe",
            () -> new ScytheItem(ModTiers.PURPLE_STEEL, -4F,-3.2F,
                    TFCTags.Blocks.MINEABLE_WITH_SCYTHE, new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));
    public static final RegistryObject<Item> PURPLE_STEEL_SCYTHE_BLADE = ITEMS.register("purple_steel_scythe_blade",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_SHEARS = ITEMS.register("purple_steel_shears",
            () -> new ShearsItem(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB).durability(7500)));

    //ARMORS
    public static final RegistryObject<Item> PURPLE_STEEL_UNFINISHED_HELMET = ITEMS.register("purple_steel_unfinished_helmet",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_HELMET = ITEMS.register("purple_steel_helmet",
            () -> new ArmorItem(ModArmorMaterials.PURPLE_STEEL, EquipmentSlot.HEAD,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_UNFINISHED_CHESTPLATE = ITEMS.register("purple_steel_unfinished_chestplate",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_CHESTPLATE = ITEMS.register("purple_steel_chestplate",
            () -> new ArmorItem(ModArmorMaterials.PURPLE_STEEL, EquipmentSlot.CHEST,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_UNFINISHED_GREAVES = ITEMS.register("purple_steel_unfinished_greaves",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_GREAVES = ITEMS.register("purple_steel_greaves",
            () -> new ArmorItem(ModArmorMaterials.PURPLE_STEEL, EquipmentSlot.LEGS,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_UNFINISHED_BOOTS = ITEMS.register("purple_steel_unfinished_boots",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> PURPLE_STEEL_BOOTS = ITEMS.register("purple_steel_boots",
            () -> new ArmorItem(ModArmorMaterials.PURPLE_STEEL, EquipmentSlot.FEET,
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));


    // CANNED FOOD
    public static final Map<Nutrient, RegistryObject<DynamicCanFood>> CANS = Helpers.mapOfKeys(Nutrient.class, nutrient ->
            register("food/" + nutrient.name() + "_can",
                    () -> new DynamicCanFood(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4).saturationMod(0.3f).build()).tab(ModCreativeModeTab.ROSIA_TAB))));

    public static final RegistryObject<DynamicCanFood> SOUP_CAN = register("food/soup_can",
            () -> new DynamicCanFood(new Item.Properties().food(new FoodProperties.Builder()
                            .nutrition(4).saturationMod(0.3f).build()).tab(ModCreativeModeTab.ROSIA_TAB)));

    public static final RegistryObject<Item> TIN_CAN = ITEMS.register("tin_can",
            () -> new Item(new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));



    //DOWN HERE SO IT'S NEAR THE BLOCKS
    public static final RegistryObject<StandingAndWallBlockItem> IRON_SUPPORT = ITEMS.register("iron_support",
            () -> new StandingAndWallBlockItem(ModBlocks.IRON_SUPPORT_VERTICAL.get(), ModBlocks.IRON_SUPPORT_HORIZONTAL.get(),
                    new Item.Properties().tab(ModCreativeModeTab.ROSIA_TAB)));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name.toLowerCase(Locale.ROOT), item);
    }


}
