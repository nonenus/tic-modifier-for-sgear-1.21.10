package com.modifierworkbench.registry;

import com.modifierworkbench.ModifierWorkbenchMod;
import com.modifierworkbench.menu.ModifierWorkbenchMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.MENU, ModifierWorkbenchMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<ModifierWorkbenchMenu>> MODIFIER_WORKBENCH_MENU =
            MENU_TYPES.register("modifier_workbench", () ->
                    IMenuTypeExtension.create(ModifierWorkbenchMenu::new));
}
