package com.jewey.rosia.common.fluids;


import com.jewey.rosia.Rosia;
import com.jewey.rosia.common.blocks.ModBlocks;
import net.dries007.tfc.util.Helpers;
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

    public static final ResourceLocation MOLTEN_STILL = Helpers.identifier("block/molten_still");
    public static final ResourceLocation MOLTEN_FLOW = Helpers.identifier("block/molten_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");


    public static final DeferredRegister<Fluid> FLUIDS
            = DeferredRegister.create(ForgeRegistries.FLUIDS, Rosia.MOD_ID);

    public static final RegistryObject<FlowingFluid> INVAR_FLUID
            = FLUIDS.register("invar_fluid", () -> new ForgeFlowingFluid.Source(ModFluids.INVAR_PROPERTIES));

    public static final RegistryObject<FlowingFluid> INVAR_FLOWING
            = FLUIDS.register("invar_flowing", () -> new ForgeFlowingFluid.Flowing(ModFluids.INVAR_PROPERTIES));


    public static final ForgeFlowingFluid.Properties INVAR_PROPERTIES = new ForgeFlowingFluid.Properties(
            INVAR_FLUID, INVAR_FLOWING, FluidAttributes.builder(MOLTEN_STILL, MOLTEN_FLOW)
            .color(0xff695b43).density(3000).luminosity(15).viscosity(6000).temperature(1300).sound(SoundEvents.BUCKET_EMPTY_LAVA).overlay(WATER_OVERLAY_RL))
            .slopeFindDistance(3).levelDecreasePerBlock(3).block(ModFluids.INVAR_BLOCK);

    public static final RegistryObject<LiquidBlock> INVAR_BLOCK = ModBlocks.BLOCKS.register("invar_fluid",
            () -> new LiquidBlock(ModFluids.INVAR_FLUID, BlockBehaviour.Properties.of(Material.LAVA)
                    .noCollission().strength(100f).noDrops()));


    public static void register(IEventBus eventBus) {
        FLUIDS.register(eventBus);
    }
}
