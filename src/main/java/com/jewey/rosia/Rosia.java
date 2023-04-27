package com.jewey.rosia;

import com.jewey.rosia.common.blocks.ModBlocks;
import com.jewey.rosia.common.blocks.entity.ModBlockEntities;
import com.jewey.rosia.common.capabilities.food.RosiaFoodTraits;
import com.jewey.rosia.common.container.ModContainerTypes;
import com.jewey.rosia.common.fluids.ModFluids;
import com.jewey.rosia.common.items.ModItems;
import com.jewey.rosia.effect.ModEffects;
import com.jewey.rosia.event.ModEvents;
import com.jewey.rosia.networking.ModMessages;
import com.jewey.rosia.recipe.ModRecipes;
import com.jewey.rosia.screen.*;
import com.mojang.logging.LogUtils;
import net.dries007.tfc.network.PacketHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(Rosia.MOD_ID)
public class Rosia
{
    public static final String MOD_ID = "rosia";

    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();


    public Rosia()
    {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModFluids.register(eventBus);

        ModBlockEntities.register(eventBus);
        ModContainerTypes.CONTAINERS.register(eventBus);
        ModMenuTypes.register(eventBus);

        RosiaFoodTraits.registerFoodTraits();

        ModEffects.EFFECTS.register(eventBus);

        ModRecipes.register(eventBus);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        PacketHandler.init();
        ModEvents.init();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(ModMenuTypes.AUTO_QUERN_MENU.get(), AutoQuernScreen::new);
        MenuScreens.register(ModContainerTypes.FIRE_BOX.get(), FireBoxScreenFinal::new);
        MenuScreens.register(ModContainerTypes.STEAM_GENERATOR.get(), SteamGeneratorScreen::new);
        MenuScreens.register(ModMenuTypes.NICKEL_IRON_BATTERY_MENU.get(), NickelIronBatteryScreen::new);
        MenuScreens.register(ModContainerTypes.WATER_PUMP.get(), WaterPumpScreen::new);
        MenuScreens.register(ModMenuTypes.EXTRUDING_MACHINE_MENU.get(), ExtrudingMachineScreen::new);
        MenuScreens.register(ModContainerTypes.LEATHER_SATCHEL.get(), LeatherSatchelScreen::new);


        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AUTO_QUERN.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIRE_BOX.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STEAM_GENERATOR.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NICKEL_IRON_BATTERY.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WATER_PUMP.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EXTRUDING_MACHINE.get(), RenderType.solid());
    }




    private void setup(final FMLCommonSetupEvent event) {

        ItemBlockRenderTypes.setRenderLayer(ModFluids.INVAR_BLOCK.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.INVAR_FLUID.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.INVAR_FLOWING.get(), RenderType.solid());

        ModMessages.register();
    }


}
