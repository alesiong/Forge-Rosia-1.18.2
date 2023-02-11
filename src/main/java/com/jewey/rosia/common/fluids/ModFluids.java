package com.jewey.rosia.common.fluids;


import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;




public class ModFluids {

    public static final ResourceLocation LAVA_STILL_RL = new ResourceLocation("block/lava_still");
    public static final ResourceLocation LAVA_FLOW_RL = new ResourceLocation("block/lava_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");


    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, Rosia.MOD_ID);

    public static final RegistryObject<FlowingFluid> NICHROME_FLUID
            = FLUIDS.register("nichrome_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.NICHROME_PROPERTIES));

    public static final RegistryObject<FlowingFluid> NICHROME_FLOWING
            = FLUIDS.register("nichrome_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.NICHROME_PROPERTIES));


    public static final ForgeFlowingFluid.Properties NICHROME_PROPERTIES = new ForgeFlowingFluid.Properties(
            () -> NICHROME_FLUID.get(), () -> NICHROME_FLOWING.get(), FluidAttributes.builder(LAVA_STILL_RL, LAVA_FLOW_RL)
            .color(0xff364236).density(3000).luminosity(15).viscosity(6000).temperature(1300).sound(SoundEvents.BUCKET_EMPTY_LAVA).overlay(WATER_OVERLAY_RL))
            .slopeFindDistance(3).levelDecreasePerBlock(3).block(() -> ModFluids.NICHROME_BLOCK.get());

    public static final RegistryObject<LiquidBlock> NICHROME_BLOCK = ModBlocks.BLOCKS.register("nichrome_fluid",
            () -> new LiquidBlock(() -> ModFluids.NICHROME_FLUID.get(), BlockBehaviour.Properties.of(Material.LAVA)
                    .noCollission().strength(100f).noDrops()));


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
