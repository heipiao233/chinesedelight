package net.heipiao.chinesedelight.rei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.heipiao.chinesedelight.ChineseDelight;
import net.heipiao.chinesedelight.recipe.WokRecipe;
import net.heipiao.chinesedelight.utils.Three;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

public class WokDisplay extends BasicDisplay {

    public WokDisplay(WokRecipe recipe) {
        this(fromItems(recipe.getItems()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.of(recipe.getId()));
    }

    public WokDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, Optional<Identifier> location) {
        super(inputs, outputs, location);
    }

    private static List<EntryIngredient> fromItems(List<Three<Ingredient, Integer, Integer>> items) {
        if (items.size() == 0) return Collections.emptyList();
        if (items.size() == 1) {
            var item = items.get(0);
            Ingredient ingredient = item.a();
            if (ingredient.isEmpty()) return Collections.emptyList();
            return Collections.singletonList(EntryIngredients.ofIngredient(ingredient).map((stack) -> {
                return stack.tooltip(
                    ChineseDelight.getText("rei.after_max_time", item.b()),
                    ChineseDelight.getText("rei.after_min_fry", item.c()));
            }));
        }
        List<EntryIngredient> result = new ArrayList<>(items.size());
        for (int i = items.size() - 1; i >= 0; i--) {
            var item = items.get(i);
            Ingredient ingredient = item.a();
            result.add(0, EntryIngredients.ofIngredient(ingredient).map((stack) -> {
                return stack.tooltip(
                    ChineseDelight.getText("rei.after_max_time", item.b()),
                    ChineseDelight.getText("rei.after_min_fry", item.c()));
            }));
        }
        return ImmutableList.copyOf(result);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return ChineseDelightClientPlugin.WOK_DISPLAY;
    }
}
