package com.jewey.rosia.common.capabilities.food;

import net.dries007.tfc.common.capabilities.food.FoodTrait;
import net.dries007.tfc.util.Helpers;

public class RosiaFoodTraits {
    public static final FoodTrait BOUND = register("bound", 0.7F);

    public RosiaFoodTraits() {
    }

    public static void registerFoodTraits() {
    }

    private static FoodTrait register(String name, float decayModifier) {
        return FoodTrait.register(Helpers.identifier(name), new FoodTrait(decayModifier, "tfc.tooltip.food_trait." + name));
    }
}
