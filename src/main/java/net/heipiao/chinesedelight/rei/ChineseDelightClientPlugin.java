package net.heipiao.chinesedelight.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.heipiao.chinesedelight.ChineseDelight;
import net.heipiao.chinesedelight.ModTags;
import net.heipiao.chinesedelight.item.ModItems;
import net.heipiao.chinesedelight.recipe.WokRecipe;

public class ChineseDelightClientPlugin implements REIClientPlugin {
    public static final CategoryIdentifier<WokDisplay> WOK_DISPLAY = CategoryIdentifier.of(ChineseDelight.MODID, "wok_display");
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new WokDisplayCategory());
        registry.addWorkstations(WOK_DISPLAY, EntryIngredients.of(ModItems.WOK.item), EntryIngredients.ofItemTag(ModTags.SPATULA));
    }
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerFiller(WokRecipe.class, WokDisplay::new);
    }
}
