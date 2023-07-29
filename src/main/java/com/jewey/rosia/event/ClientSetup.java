package com.jewey.rosia.event;

import com.jewey.rosia.common.blocks.ModBlocks;
import com.jewey.rosia.common.container.ModContainerTypes;
import com.jewey.rosia.common.fluids.ModFluids;
import com.jewey.rosia.screen.*;
import com.jewey.rosia.util.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ClientSetup {
    public static void register() {
        //SCREEN REGISTER
            //BLOCK INVENTORY
        MenuScreens.register(ModMenuTypes.AUTO_QUERN_MENU.get(), AutoQuernScreen::new);
        MenuScreens.register(ModContainerTypes.FIRE_BOX.get(), FireBoxScreenFinal::new);
        MenuScreens.register(ModContainerTypes.STEAM_GENERATOR.get(), SteamGeneratorScreen::new);
        MenuScreens.register(ModContainerTypes.NICKEL_IRON_BATTERY.get(), NickelIronBatteryScreen::new);
        MenuScreens.register(ModContainerTypes.WATER_PUMP.get(), WaterPumpScreen::new);
        MenuScreens.register(ModMenuTypes.EXTRUDING_MACHINE_MENU.get(), ExtrudingMachineScreen::new);
        MenuScreens.register(ModMenuTypes.ROLLING_MACHINE_MENU.get(), RollingMachineScreen::new);
        MenuScreens.register(ModContainerTypes.ELECTRIC_FORGE.get(), ElectricForgeScreen::new);
        MenuScreens.register(ModContainerTypes.ELECTRIC_GRILL.get(), ElectricGrillScreen::new);
        MenuScreens.register(ModContainerTypes.FRIDGE.get(), FridgeScreen::new);
        MenuScreens.register(ModContainerTypes.CHARCOAL_KILN.get(), CharcoalKilnScreen::new);
        MenuScreens.register(ModMenuTypes.CANNING_PRESS_MENU.get(), CanningPressScreen::new);
            //ITEM INVENTORY
        MenuScreens.register(ModContainerTypes.LEATHER_SATCHEL.get(), LeatherSatchelScreen::new);
        MenuScreens.register(ModContainerTypes.TOOL_BELT.get(), ToolBeltScreen::new);

        //CUSTOM ITEM PROPERTIES
        ModItemProperties.addCustomItemProperties();

        //BLOCK RENDER
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.AUTO_QUERN.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FIRE_BOX.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.STEAM_GENERATOR.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.NICKEL_IRON_BATTERY.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.WATER_PUMP.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.EXTRUDING_MACHINE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ROLLING_MACHINE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELECTRIC_FORGE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELECTRIC_GRILL.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.FRIDGE.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.ELECTRIC_LANTERN.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHARCOAL_KILN.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModBlocks.CANNING_PRESS.get(), RenderType.solid());

        //FLUID RENDER
        ItemBlockRenderTypes.setRenderLayer(ModFluids.INVAR_BLOCK.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.INVAR_FLUID.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.INVAR_FLOWING.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PURPLE_STEEL_BLOCK.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PURPLE_STEEL_FLUID.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.PURPLE_STEEL_FLOWING.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.WEAK_PURPLE_STEEL_BLOCK.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.WEAK_PURPLE_STEEL_FLUID.get(), RenderType.solid());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.WEAK_PURPLE_STEEL_FLOWING.get(), RenderType.solid());
    }
}
