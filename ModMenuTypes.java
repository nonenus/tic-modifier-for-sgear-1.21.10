package com.modifierworkbench.blockentity;

import com.modifierworkbench.menu.ModifierWorkbenchMenu;
import com.modifierworkbench.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ModifierWorkbenchBlockEntity extends BaseContainerBlockEntity {

    // Slot layout:
    // 0 = Silent Gear item
    // 1-5 = Material (lapis / redstone / quartz)
    // 6 = Output preview (read-only display)
    public static final int TOTAL_SLOTS = 7;
    public static final int GEAR_SLOT = 0;
    public static final int MAT_SLOT_START = 1;
    public static final int MAT_SLOT_END = 5;
    public static final int OUTPUT_SLOT = 6;

    private final NonNullList<ItemStack> items = NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);

    public ModifierWorkbenchBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.MODIFIER_WORKBENCH_BE.get(), pos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.modifierworkbench.modifier_workbench");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItems) {
        items.clear();
        for (int i = 0; i < Math.min(pItems.size(), items.size()); i++) {
            items.set(i, pItems.get(i));
        }
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new ModifierWorkbenchMenu(id, inv, this, getBlockPos());
    }

    @Override
    public int getContainerSize() {
        return TOTAL_SLOTS;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        ContainerHelper.saveAllItems(tag, items, registries);
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, @NotNull HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        ContainerHelper.loadAllItems(tag, items, registries);
    }

    public void dropContents(Level level, BlockPos pos) {
        for (ItemStack stack : items) {
            if (!stack.isEmpty()) {
                net.minecraft.world.Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }
        items.clear();
    }

    @Override
    public boolean stillValid(Player player) {
        return BaseContainerBlockEntity.stillValid(this, player);
    }
}
