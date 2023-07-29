package com.jewey.rosia.integration.jei;

import com.jewey.rosia.common.blocks.ModBlocks;
import com.jewey.rosia.recipe.ExtrudingMachineRecipe;
import com.jewey.rosia.recipe.RollingMachineRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import com.jewey.rosia.Rosia;
import com.jewey.rosia.recipe.AutoQuernRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEIRosiaPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Rosia.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new AutoQuernRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new ExtrudingMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new RollingMachineRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<AutoQuernRecipe> autoQuernRecipes = rm.getAllRecipesFor(AutoQuernRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(AutoQuernRecipeCategory.UID, AutoQuernRecipe.class), autoQuernRecipes);

        List<ExtrudingMachineRecipe> extrudingMachineRecipes = rm.getAllRecipesFor(ExtrudingMachineRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(ExtrudingMachineRecipeCategory.UID, ExtrudingMachineRecipe.class), extrudingMachineRecipes);

        List<RollingMachineRecipe> rollingMachineRecipes = rm.getAllRecipesFor(RollingMachineRecipe.Type.INSTANCE);
        registration.addRecipes(new RecipeType<>(RollingMachineRecipeCategory.UID, RollingMachineRecipe.class), rollingMachineRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration)
    {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.AUTO_QUERN.get()), AutoQuernRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.EXTRUDING_MACHINE.get()), ExtrudingMachineRecipeCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ROLLING_MACHINE.get()), RollingMachineRecipeCategory.UID);
    }
}
