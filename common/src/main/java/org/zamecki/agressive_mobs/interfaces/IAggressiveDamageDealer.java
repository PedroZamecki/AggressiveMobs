package org.zamecki.agressive_mobs.interfaces;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

public interface IAggressiveDamageDealer {
    void aggressive_mobs$doHurtTarget(ServerLevel serverLevel, Entity entity);
}
