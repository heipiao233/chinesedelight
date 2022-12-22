package net.heipiao.chinesedelight.item;

import java.util.function.Supplier;

import com.nhoryzon.mc.farmersdelight.item.ModItemSettings;
import net.heipiao.chinesedelight.ChineseDelight;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ModItems {
    FLINT_SPATULA(() -> new SpatulaItem(com.nhoryzon.mc.farmersdelight.item.enumeration.ToolMaterials.FLINT, ModItemSettings.noStack().group(ChineseDelight.GROUP))),
    IRON_SPATULA(() -> new SpatulaItem(ToolMaterials.IRON, ModItemSettings.noStack().group(ChineseDelight.GROUP))),
    GOLD_SPATULA(() -> new SpatulaItem(ToolMaterials.GOLD, ModItemSettings.noStack().group(ChineseDelight.GROUP))),
    DIAMOND_SPATULA(() -> new SpatulaItem(ToolMaterials.DIAMOND, ModItemSettings.noStack().group(ChineseDelight.GROUP))),
    NETHERITE_SPATULA(() -> new SpatulaItem(ToolMaterials.NETHERITE, ModItemSettings.noStack().group(ChineseDelight.GROUP)));
    private final Supplier<Item> itemSupplier;
    public Item item;
    ModItems(Supplier<Item> item) {
        this.itemSupplier = item;
    }
    public static void register() {
        for (var value: values()) {
            value.item = value.itemSupplier.get();
            Registry.register(Registry.ITEM, new Identifier(ChineseDelight.MODID, value.name().toLowerCase()), value.item);
        }
    }
}
