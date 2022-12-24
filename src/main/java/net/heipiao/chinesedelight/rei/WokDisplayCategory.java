package net.heipiao.chinesedelight.rei;

import java.util.List;

import com.google.common.collect.Lists;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.heipiao.chinesedelight.ChineseDelight;
import net.heipiao.chinesedelight.item.ModItems;
import net.minecraft.text.Text;

public class WokDisplayCategory implements DisplayCategory<WokDisplay> {

    @Override
    public CategoryIdentifier<? extends WokDisplay> getCategoryIdentifier() {
        return ChineseDelightClientPlugin.WOK_DISPLAY;
    }

    @Override
    public Text getTitle() {
        return ChineseDelight.getText("rei.wok");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModItems.WOK.item);
    }

    @Override
    public List<Widget> setupDisplay(WokDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getX() + 10, bounds.getY() + 10);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        int i = 0;
        for (EntryIngredient input: display.getInputEntries()) {
            widgets.add(Widgets.createSlot(new Point(startPoint.x + i * 18, startPoint.y))
                .entries(input)
                .markInput());
            i++;
        }
        Point arrowPoint = new Point(bounds.getMaxX() - 64, bounds.getMaxY() - 25);
        widgets.add(Widgets.createArrow(arrowPoint));
        Point resultPoint = new Point(bounds.getMaxX() - 28, bounds.getMaxY() - 25);
        widgets.add(Widgets.createResultSlotBackground(resultPoint));
        widgets.add(
            Widgets.createSlot(resultPoint)
                .entries(display.getOutputEntries().get(0))
                .markOutput()
                .disableBackground());
        return widgets;
    }
}
