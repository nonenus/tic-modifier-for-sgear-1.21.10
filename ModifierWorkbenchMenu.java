package com.modifierworkbench;

import com.modifierworkbench.handler.ModEvents;
import com.modifierworkbench.handler.TipRemovalHandler;
import com.modifierworkbench.network.ApplyModifierPacket;
import com.modifierworkbench.registry.ModBlockEntities;
import com.modifierworkbench.registry.ModBlocks;
import com.modifierworkbench.registry.ModMenuTypes;
import com.modifierworkbench.screen.ModifierWorkbenchScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModifierWorkbenchMod.MOD_ID)
public class ModifierWorkbenchMod {

    public static final String MOD_ID = "modifierworkbench";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public ModifierWorkbenchMod(IEventBus modEventBus) {
        // Register deferred registers
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.ITEMS.register(modEventBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        ModMenuTypes.MENU_TYPES.register(modEventBus);

        // Register lifecycle events
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
        modEventBus.addListener(this::registerPackets);
        modEventBus.addListener(ModifierWorkbenchScreen::onRegisterMenuScreens);

        // Register NeoForge game events (enchanting prevention, etc.)
        NeoForge.EVENT_BUS.register(ModEvents.class);

        // Register tip removal handler on the mod event bus
        modEventBus.addListener(TipRemovalHandler::onCommonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("[ModifierWorkbench] Common setup complete.");
    }

    private void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info("[ModifierWorkbench] Client setup complete.");
    }

    private void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MOD_ID).versioned("1.0");
        registrar.playToServer(
                ApplyModifierPacket.TYPE,
                ApplyModifierPacket.CODEC,
                ApplyModifierPacket::handle
        );
    }
}
