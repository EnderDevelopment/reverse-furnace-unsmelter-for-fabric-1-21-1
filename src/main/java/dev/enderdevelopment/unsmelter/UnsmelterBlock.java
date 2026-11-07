package dev.enderdevelopment.unsmelter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.BlockRenderType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public final class UnsmelterBlock extends BlockWithEntity {
    public UnsmelterBlock(Settings settings) { super(settings); }
    @Override protected BlockEntity createBlockEntity(BlockPos pos, BlockState state) { return new UnsmelterBlockEntity(pos, state); }
    @Override protected BlockRenderType getRenderType(BlockState state) { return BlockRenderType.MODEL; }
    @Override @Nullable protected <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return type == UnsmelterMod.BLOCK_ENTITY ? (w, p, s, entity) -> ((UnsmelterBlockEntity) entity).tick() : null;
    }
    @Override protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && world.getBlockEntity(pos) instanceof NamedScreenHandlerFactory factory) player.openHandledScreen(factory);
        return ActionResult.success(world.isClient);
    }
}
