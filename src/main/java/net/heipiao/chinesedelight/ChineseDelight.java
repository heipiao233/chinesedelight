package net.heipiao.chinesedelight;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.heipiao.chinesedelight.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChineseDelight implements ModInitializer {
	public static final String MODID = "chinesedelight";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);
    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(ChineseDelight.MODID, "mod_item_group"), ModItems.IRON_SPATULA.item::getDefaultStack);

	@Override
	public void onInitialize() {
		ModItems.register();
	}
}
