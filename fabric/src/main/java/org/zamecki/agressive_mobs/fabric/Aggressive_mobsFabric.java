package org.zamecki.agressive_mobs.fabric;

import org.zamecki.agressive_mobs.Aggressive_mobs;
import net.fabricmc.api.ModInitializer;

public final class Aggressive_mobsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        Aggressive_mobs.init();
    }
}
