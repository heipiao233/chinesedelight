package net.heipiao.chinesedelight.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.heipiao.chinesedelight.ChineseDelight;
import net.heipiao.chinesedelight.block.ModBlocks;
import net.heipiao.chinesedelight.utils.LazySupplier;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntityTypes {
    public static final LazySupplier<BlockEntityType<WokBlockEntity>> WOK = new LazySupplier<>(() -> FabricBlockEntityTypeBuilder.create(WokBlockEntity::new, ModBlocks.WOK.block).build());
    public static void register() {
        register("wok", WOK.get());
    }
    private static void register(String name, BlockEntityType<WokBlockEntity> type) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(ChineseDelight.MODID, name), type);
    }
}
