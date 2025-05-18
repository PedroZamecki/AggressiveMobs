package org.zamecki.agressive_mobs.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.zamecki.agressive_mobs.entity.ai.goal.AggressiveMeleeAttackGoal;
import org.zamecki.agressive_mobs.entity.ai.goal.target.AggressiveNearestAttackableTargetGoal;
import org.zamecki.agressive_mobs.interfaces.IPublicGoalsSelectors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.zamecki.agressive_mobs.Globals.LOGGER;

@Mixin(PathfinderMob.class)
public abstract class PathfinderMobMixin extends Mob {

    protected PathfinderMobMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(EntityType<? extends Mob> entityType, Level level, CallbackInfo ci) {
        PathfinderMob pathfinderMob = (PathfinderMob) (Object) this;
        if (pathfinderMob instanceof Monster) return; // It'll attack, no matter what.

        if (pathfinderMob instanceof IPublicGoalsSelectors iMob) {
            LOGGER.debug("PathfinderMobMixin: Modifying goals for aggression.");

            iMob.aggressive_mobs$getGoalSelector().addGoal(0, new AggressiveMeleeAttackGoal(pathfinderMob, 1.2D, true));
            iMob.aggressive_mobs$getTargetSelector().addGoal(1, new AggressiveNearestAttackableTargetGoal<>(pathfinderMob, Player.class, true));
        } else {
            LOGGER.warn("PathfinderMobMi: 'this' (as Mob) is NOT an instance of IPublicGoalsSelectors! Cannot make sheep aggressive.");
            LOGGER.warn("PathfinderMobMi: Mob class: {}", pathfinderMob.getClass().getName());
        }
    }
}
