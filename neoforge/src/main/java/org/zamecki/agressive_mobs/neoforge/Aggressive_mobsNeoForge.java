package org.zamecki.agressive_mobs.neoforge;

import org.zamecki.agressive_mobs.Aggressive_mobs;
import net.neoforged.fml.common.Mod;

import static org.zamecki.agressive_mobs.Globals.MOD_ID;

@Mod(MOD_ID)
public final class Aggressive_mobsNeoForge {
    public Aggressive_mobsNeoForge() {
        // Run our common setup.
        Aggressive_mobs.init();
    }
}
