package com.humusberg.astralprogress.item;

import com.humusberg.astralprogress.sound.ModSounds;
import com.humusberg.astralprogress.util.Raycasts;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class GrapplingHook extends Item{
    public GrapplingHook(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int pSlotId, boolean pIsSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            if (!player.getCooldowns().isOnCooldown(this)) {
                stack.getOrCreateTag().putBoolean("fired", false);
            }
        }
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        HitResult result = Raycasts.RaycastBlock(player, 50, false);
        level.playSound(
            player,
            player.getX(),
            player.getY(),
            player.getZ(),
            ModSounds.GRAPPLING_HOOK.get(),
            SoundSource.PLAYERS,
            1.0F,
            1.0F
        );
        if (result.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) result;
            Vec3 blockPos = blockHit.getLocation();
            Vec3 playerPos = player.getPosition(1.0f);
            Vec3 direction = blockPos.subtract(playerPos);
            if (!level.isClientSide) {
                Raycasts.LaserParticle(level, playerPos, blockPos, ParticleTypes.CRIT, 1000);
            }
            player.lerpMotion(Math.min(direction.x / 12, 2), Math.min(direction.y / 12, 2), Math.min(direction.z / 12, 2));
        }
        player.getItemInHand(hand).getOrCreateTag().putBoolean("fired", true);
        player.getCooldowns().addCooldown(this, 10);
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }
}

