package net.heipiao.chinesedelight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.heipiao.chinesedelight.block.ModBlocks;
import net.heipiao.chinesedelight.block.entity.ModBlockEntityTypes;
import net.heipiao.chinesedelight.item.ModItems;
import net.heipiao.chinesedelight.recipe.ModRecipeSerializers;
import net.heipiao.chinesedelight.recipe.ModRecipeTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class ChineseDelight implements ModInitializer {
	public static final String MODID = "chinesedelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(ChineseDelight.MODID, "mod_item_group"), () -> ModItems.IRON_SPATULA.item.getDefaultStack());

	@Override
	public void onInitialize() {
		ModBlocks.register();
		ModItems.register();
		ModBlockEntityTypes.register();
		ModRecipeSerializers.register();
		ModRecipeTypes.register();
	}

    public static Text getText(String string, Object... args) {
        return new TranslatableText(MODID + "." + string, args);
    }
}
