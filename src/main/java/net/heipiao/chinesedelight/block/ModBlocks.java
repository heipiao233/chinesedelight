package net.heipiao.chinesedelight.block;

import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.heipiao.chinesedelight.ChineseDelight;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public enum ModBlocks {
    WOK(() -> new WokBlock(Settings.of(Material.METAL)), true);

    private final Supplier<Block> blockSupplier;
    private final boolean cutout;
    public Block block;

    ModBlocks(Supplier<Block> block) {
        this.blockSupplier = block;
        this.cutout = false;
    }

    ModBlocks(Supplier<Block> block, boolean cutout) {
        this.blockSupplier = block;
        this.cutout = cutout;
    }

    public static void register() {
        for (var value : values()) {
            value.block = value.blockSupplier.get();
            Registry.register(Registry.BLOCK, new Identifier(ChineseDelight.MODID, value.name().toLowerCase()),
                    value.block);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {

        for (var value : values()) {
            if (value.cutout) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.block, RenderLayer.getCutout());
            }
        }
    }
}
