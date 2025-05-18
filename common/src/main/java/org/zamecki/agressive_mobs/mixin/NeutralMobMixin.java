package org.zamecki.agressive_mobs.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.NeutralMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NeutralMob.class)
public interface NeutralMobMixin {
    @Inject(method = "isAngryAtAllPlayers", at = @At("RETURN"), cancellable = true)
    private void isAngryAtAllPlayers(ServerLevel serverLevel, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
