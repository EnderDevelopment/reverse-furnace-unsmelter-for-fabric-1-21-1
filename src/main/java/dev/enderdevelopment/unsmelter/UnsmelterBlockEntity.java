package dev.enderdevelopment.unsmelter;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public final class UnsmelterBlockEntity extends BlockEntity implements Inventory, NamedScreenHandlerFactory {
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);

    public UnsmelterBlockEntity(BlockPos pos, BlockState state) { super(UnsmelterMod.BLOCK_ENTITY, pos, state); }
    @Override public int size() { return items.size(); }
    @Override public boolean isEmpty() { return items.stream().allMatch(ItemStack::isEmpty); }
    @Override public ItemStack getStack(int slot) { return items.get(slot); }
    @Override public ItemStack removeStack(int slot, int amount) { ItemStack result = Inventories.splitStack(items, slot, amount); if (!result.isEmpty()) markDirty(); return result; }
    @Override public ItemStack removeStack(int slot) { ItemStack result = Inventories.removeStack(items, slot); if (!result.isEmpty()) markDirty(); return result; }
    @Override public void setStack(int slot, ItemStack stack) { items.set(slot, stack); if (stack.getCount() > getMaxCountPerStack()) stack.setCount(getMaxCountPerStack()); markDirty(); }
    @Override public boolean canPlayerUse(PlayerEntity player) { return Inventory.canPlayerUse(this, player); }
    @Override public void clear() { items.clear(); markDirty(); }
    @Override public Text getDisplayName() { return Text.translatable("container.unsmelter.unsmelter"); }
    @Override public ScreenHandler createMenu(int syncId, net.minecraft.entity.player.PlayerInventory playerInventory, PlayerEntity player) { return new UnsmelterScreenHandler(syncId, playerInventory, this); }
    @Override protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) { super.writeNbt(nbt, registries); Inventories.writeNbt(nbt, items, registries); }
    @Override protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) { super.readNbt(nbt, registries); Inventories.readNbt(nbt, items, registries); }
}
