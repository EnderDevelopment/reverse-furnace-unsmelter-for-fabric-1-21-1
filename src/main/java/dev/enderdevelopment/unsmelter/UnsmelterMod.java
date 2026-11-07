package dev.enderdevelopment.unsmelter;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public final class UnsmelterMod implements ModInitializer {
    public static final String MOD_ID = "unsmelter";
    public static final Identifier BLOCK_ID = Identifier.of(MOD_ID, "unsmelter");
    public static final Block UNSMELTER = new UnsmelterBlock(FabricBlockSettings.create()
            .mapColor(MapColor.STONE_GRAY).instrument(Instrument.BASEDRUM).strength(3.5F).requiresTool());
    public static final BlockEntityType<UnsmelterBlockEntity> BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, BLOCK_ID,
            BlockEntityType.Builder.create(UnsmelterBlockEntity::new, UNSMELTER).build()
    );
    public static final ScreenHandlerType<UnsmelterScreenHandler> SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER, BLOCK_ID,
            new ScreenHandlerType<>(UnsmelterScreenHandler::new, null)
    );

    @Override
    public void onInitialize() {
        Registry.register(Registries.BLOCK, BLOCK_ID, UNSMELTER);
        Registry.register(Registries.ITEM, BLOCK_ID, new BlockItem(UNSMELTER, new Item.Settings()));
    }
}
