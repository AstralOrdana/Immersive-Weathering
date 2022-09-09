package net.mehvahdjukaar.moonlight.api.platform.fabric;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class EventPlatformHelperImpl {
    public static boolean onProjectileImpact(Projectile improvedProjectileEntity, HitResult blockHitResult) {
        return false;
    }
}
