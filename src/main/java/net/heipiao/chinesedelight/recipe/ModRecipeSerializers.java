package net.heipiao.chinesedelight.recipe;

import java.util.function.Supplier;

import net.heipiao.chinesedelight.ChineseDelight;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ModRecipeSerializers {
    WOK(() -> new WokRecipe.WokRecipeSerializer());

    private final Supplier<RecipeSerializer<?>> serializerSupplier;
    public RecipeSerializer<?> serializer;

    ModRecipeSerializers(Supplier<RecipeSerializer<?>> serializer) {
        this.serializerSupplier = serializer;
    }

    public static void register() {
        for (var value : values()) {
            value.serializer = value.serializerSupplier.get();
            Registry.register(Registry.RECIPE_SERIALIZER, new Identifier(ChineseDelight.MODID, value.name().toLowerCase()),
                    value.serializer);
        }
    }
}
