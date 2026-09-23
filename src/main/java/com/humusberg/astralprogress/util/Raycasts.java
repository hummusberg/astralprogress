package com.humusberg.astralprogress.util;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class Raycasts {
    
    public static HitResult RaycastEntity(Level level, Player player, double range) {
        Vec3 start = player.getEyePosition(1.0f);
        Vec3 look = player.getLookAngle();
        Vec3 end = start.add(look.x * range, look.y * range, look.z * range);
        HitResult result = ProjectileUtil.getEntityHitResult(
            level, 
            player, 
            start, 
            end, 
            player.getBoundingBox().expandTowards(look.scale(range)).inflate(1.0, 1.0, 1.0), 
            entity -> !entity.isSpectator() && entity.isPickable()
        );
        return result;
    }

    public static HitResult RaycastBlock(Player player, double range , Boolean hitFluids) {
        HitResult result = player.pick(range, 0.0F, hitFluids);
        return result;
    }
    //particleType variable should be particleTypes.PARTICLE , eg: particleTypes.CRIT
    public static void LaserParticle(Level level, Vec3 startPos, Vec3 endPos, ParticleOptions particleType, int particleAmount) {
        Vec3 direction = startPos.subtract(endPos);
        for (double i = 0; i <= particleAmount; i++) {
            Vec3 resultVec3 = endPos.add(direction.scale(i / 100.0));
            ((ServerLevel) level).sendParticles(
                particleType,
                resultVec3.x,
                resultVec3.y,
                resultVec3.z,
                1,
                0,
                0,
                0,
                0.01
            );
        }
    }
}
