package net.heipiao.chinesedelight;

import net.fabricmc.api.ClientModInitializer;
import net.heipiao.chinesedelight.block.ModBlocks;

public class ChineseDelightClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModBlocks.registerRenderLayer();
    }
    
}
