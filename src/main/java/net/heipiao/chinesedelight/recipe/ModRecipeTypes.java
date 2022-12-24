package net.heipiao.chinesedelight.recipe;

import java.util.LinkedHashMap;
import java.util.Map;

import net.heipiao.chinesedelight.ChineseDelight;
import net.heipiao.chinesedelight.utils.LazySupplier;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRecipeTypes {
    private static final Map<Identifier, LazySupplier<RecipeType<Recipe<Inventory>>>> types = new LinkedHashMap<>();
    public static LazySupplier<RecipeType<WokRecipe>> WOK = register("wok");

    @SuppressWarnings("unchecked")
    private static <I extends Inventory, T extends Recipe<I>> LazySupplier<RecipeType<T>> register(String name) {
        var id = new Identifier(ChineseDelight.MODID, name);
        LazySupplier<RecipeType<Recipe<Inventory>>> type = new LazySupplier<>(() -> new RecipeType<Recipe<Inventory>> () {
            @Override
            public String toString() {
                return id.toString();
            }
        });
        types.put(id, type);
        return (LazySupplier<RecipeType<T>>)(Object)type;
    }

    public static void register() {
        for (var type: types.entrySet()) {
            Registry.register(Registry.RECIPE_TYPE, type.getKey(), type.getValue().get());
        }
    }

}
