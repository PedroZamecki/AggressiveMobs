package org.zamecki.agressive_mobs.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.zamecki.agressive_mobs.interfaces.IAggressiveDamageDealer;
import org.zamecki.agressive_mobs.interfaces.IPublicGoalsSelectors;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(Mob.class)
public abstract class MobMixin implements IPublicGoalsSelectors, IAggressiveDamageDealer {
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

    @Override
    public void aggressive_mobs$doHurtTarget(ServerLevel serverLevel, Entity entity) {
        Mob mob = (Mob) (Object) this;
        ItemStack itemStack = mob.getWeaponItem();
        DamageSource damageSource = Optional.ofNullable(itemStack.getItem().getDamageSource(mob)).orElse(mob.damageSources().mobAttack(mob));
        float f = EnchantmentHelper.modifyDamage(serverLevel, itemStack, entity, damageSource, 2);
        f += itemStack.getItem().getAttackDamageBonus(entity, f, damageSource);
        boolean bl = entity.hurtServer(serverLevel, damageSource, f);
        if (bl) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.knockback(0.75F, Mth.sin(mob.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(mob.getYRot() * (float) (Math.PI / 180.0)));
                mob.setDeltaMovement(mob.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            if (entity instanceof LivingEntity livingEntity) {
                itemStack.hurtEnemy(livingEntity, mob);
            }

            EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
            mob.setLastHurtMob(entity);
        }

    }
}
