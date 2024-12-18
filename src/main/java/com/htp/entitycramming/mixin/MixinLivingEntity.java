package com.htp.entitycramming.mixin;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "pushEntities", at = @At("HEAD"), cancellable = true)
    private void pushEntities(CallbackInfo ci) {
        LivingEntity mob = (LivingEntity) (Object) this;

        if (mob instanceof Player) {
            return;
        }

        List<Entity> list = mob.level.getEntities(mob, mob.getBoundingBox(), EntitySelector.pushableBy(mob));

        if (!list.isEmpty()) {
            int maxCramming = mob.level.getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
            if (maxCramming > 0 && list.size() > maxCramming - 1 && mob.getRandom().nextInt(4) == 0) {
                long crammedEntities = list.stream().filter(entity -> !entity.isPassenger()).count();
                if (crammedEntities > maxCramming - 1) {
                    mob.kill(); // Kill the Mob instantly rather than suffocation damage as requested by the Commissioner.
                }
            }
        }
        ci.cancel();
    }
}


