package org.zamecki.agressive_mobs.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import org.zamecki.agressive_mobs.interfaces.IPublicGoalsSelectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Mob.class)
public abstract class MobMixin implements IPublicGoalsSelectors {
    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Shadow
    @Final
    protected GoalSelector targetSelector;

    @Override
    public GoalSelector aggressive_mobs$getGoalSelector() {
        return goalSelector;
    }

    @Override
    public GoalSelector aggressive_mobs$getTargetSelector() {
        return targetSelector;
    }
}
