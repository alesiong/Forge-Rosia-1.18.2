package com.jewey.rosia.common.blocks;

import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.custom.auto_quern;
import com.jewey.rosia.common.blocks.custom.fire_box;
import com.jewey.rosia.common.blocks.entity.ModBlockEntities;
import com.jewey.rosia.common.items.ModCreativeModeTab;
import com.jewey.rosia.common.items.ModItems;
import net.dries007.tfc.common.blocks.ExtendedProperties;
import net.dries007.tfc.util.registry.RegistrationHelpers;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Rosia.MOD_ID);


    public static final RegistryObject<Block> TESTBLOCK = registerBlock("testblock",
            () -> new Block(BlockBehaviour.Properties.of(Material.METAL).strength(1f).requiresCorrectToolForDrops()),
            ModCreativeModeTab.ROSIA_TAB);
    public static final RegistryObject<Block> AUTO_QUERN = registerBlock("auto_quern",
            () -> new auto_quern(BlockBehaviour.Properties.of(Material.METAL).strength(1f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
            ModCreativeModeTab.ROSIA_TAB);

    public static final RegistryObject<Block> FIRE_BOX = registerBlock("fire_box",
            () -> new fire_box(BlockBehaviour.Properties.of(Material.METAL).strength(1f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
            ModCreativeModeTab.ROSIA_TAB);


    /**
    public static final RegistryObject<Block> FIRE_BOX  = register("fire_box",
            () -> new fire_box(ExtendedProperties.of(Material.METAL, MaterialColor.METAL).strength(1F)
                    .sound(SoundType.METAL).randomTicks().lightLevel(state -> state.getValue(fire_box.HEAT) * 2)
                    .pathType(BlockPathTypes.DAMAGE_FIRE).blockEntity(ModBlockEntities.FIRE_BOX_BLOCK_ENTITY)),
            ModCreativeModeTab.ROSIA_TAB);
     */


    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, @Nullable Function<T, ? extends BlockItem> blockItemFactory)
    {
        return RegistrationHelpers.registerBlock(ModBlocks.BLOCKS, ModItems.ITEMS, name, blockSupplier, blockItemFactory);
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
