package dev.enderdevelopment.unsmelter;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;

public final class UnsmelterScreenHandler extends ScreenHandler {
    private final Inventory inventory;
    private final ScreenHandlerContext context;
    private int selected;
    private List<RecipeEntry<AbstractCookingRecipe>> recipes = List.of();

    public UnsmelterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new UnsmelterBlockEntity(BlockPos.ORIGIN, UnsmelterMod.UNSMELTER.getDefaultState()));
    }

    public UnsmelterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(UnsmelterMod.SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.context = ScreenHandlerContext.create(playerInventory.player.getWorld(),
                inventory instanceof UnsmelterBlockEntity entity ? entity.getPos() : BlockPos.ORIGIN);
        addSlot(new Slot(inventory, 0, 44, 35) {
            @Override public void markDirty() { super.markDirty(); updateRecipes(); }
        });
        addSlot(new Slot(inventory, 1, 116, 35) {
            @Override public boolean canInsert(ItemStack stack) { return false; }
            @Override public boolean canTakeItems(PlayerEntity playerEntity) { return true; }
            @Override public void onTakeItem(PlayerEntity player, ItemStack stack) {
                inventory.setStack(1, ItemStack.EMPTY);
                inventory.getStack(0).decrement(1);
                updateRecipes();
            }
        });
        addPlayerSlots(playerInventory);
        updateRecipes();
    }

    private void addPlayerSlots(PlayerInventory inv) {
        for (int row = 0; row < 3; row++) for (int col = 0; col < 9; col++)
            addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
        for (int col = 0; col < 9; col++) addSlot(new Slot(inv, col, 8 + col * 18, 142));
    }

    private void updateRecipes() {
        if (inventory.getStack(0).isEmpty()) {
            recipes = List.of();
            inventory.setStack(1, ItemStack.EMPTY);
            return;
        }
        context.run((world, pos) -> recipes = world.getRecipeManager().values().stream()
                .filter(entry -> entry.value() instanceof AbstractCookingRecipe cooking && cooking.getType() == RecipeType.SMELTING)
                .map(entry -> (RecipeEntry<AbstractCookingRecipe>) (RecipeEntry<?>) entry)
                .filter(entry -> entry.value().getResult(world.getRegistryManager()).isOf(inventory.getStack(0).getItem()))
                .toList());
        if (recipes.isEmpty()) inventory.setStack(1, ItemStack.EMPTY);
        else {
            selected = Math.floorMod(selected, recipes.size());
            var ingredient = recipes.get(selected).value().getIngredients().stream()
                    .flatMap(value -> java.util.Arrays.stream(value.getMatchingStacks())).findFirst().orElse(ItemStack.EMPTY);
            inventory.setStack(1, ingredient.copyWithCount(1));
        }
    }

    @Override public boolean onButtonClick(PlayerEntity player, int id) {
        if (recipes.isEmpty()) return false;
        selected = Math.floorMod(selected + (id == 0 ? -1 : 1), recipes.size());
        updateRecipes();
        sendContentUpdates();
        return true;
    }

    @Override public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack result = ItemStack.EMPTY;
        Slot source = slots.get(slot);
        if (source == null || !source.hasStack()) return result;
        ItemStack stack = source.getStack();
        result = stack.copy();
        if (slot < 2) {
            if (!insertItem(stack, 2, slots.size(), true)) return ItemStack.EMPTY;
        } else if (!insertItem(stack, 0, 1, false)) return ItemStack.EMPTY;
        if (stack.isEmpty()) source.setStack(ItemStack.EMPTY); else source.markDirty();
        return result;
    }

    @Override public boolean canUse(PlayerEntity player) { return inventory.canPlayerUse(player); }
}
