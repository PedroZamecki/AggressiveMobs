package org.zamecki.agressive_mobs.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.zamecki.agressive_mobs.interfaces.IPublicGoalsSelectors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.zamecki.agressive_mobs.Globals.LOGGER;

@Mixin(Animal.class)
public abstract class AnimalMixin extends Mob {

    protected AnimalMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(EntityType<? extends Mob> entityType, Level level, CallbackInfo ci) {
        Animal animal = (Animal) (Object) this;

        if (animal instanceof IPublicGoalsSelectors iMob) {
            LOGGER.debug("AnimalMixin: Modifying goals for aggression.");

            iMob.aggressive_mobs$getTargetSelector().addGoal(1, new NearestAttackableTargetGoal<>(animal, Player.class, true));
            iMob.aggressive_mobs$getGoalSelector().addGoal(0, new MeleeAttackGoal(animal, 1.5D, true));
        } else {
            LOGGER.warn("AnimalMixin: 'this' (as Mob) is NOT an instance of IPublicGoalsSelectors! Cannot make sheep aggressive.");
            LOGGER.warn("AnimalMixin: Mob class: {}", animal.getClass().getName());
        }
    }

    @Inject(method = "createAnimalAttributes", at = @At("RETURN"), cancellable = true)
    private static void createAnimalAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue().add(Attributes.ATTACK_DAMAGE, 2.0D).add(Attributes.ATTACK_KNOCKBACK, 0.75D));
    }
}
