package com.htp.entitycramming.mixin;


import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {

    @Inject(method = "pushEntities", at = @At("HEAD"))
    private void pushEntities(CallbackInfo ci) {
        LivingEntity mob = (LivingEntity) (Object) this;
        List<Entity> list = mob.level.getEntities(mob, mob.getBoundingBox(), EntitySelector.pushableBy(mob));
        if (!list.isEmpty()) {
            int i = mob.level.getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
            if (i > 0 && list.size() > i - 1 && mob.getRandom().nextInt(4) == 0) {
                int j = 0;
                for (Entity entity : list) {
                    if (!entity.isPassenger()) {
                        ++j;
                    }
                }
                if (j > i - 1) {
                    mob.kill(); // Kill the Entity instead of inflicting suffocation damage as asked by the commissioner.
                }
            }
        }
    }
}
