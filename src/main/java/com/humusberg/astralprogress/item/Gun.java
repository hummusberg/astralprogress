package com.humusberg.astralprogress.item;

import org.joml.Vector3f;

import com.humusberg.astralprogress.sound.ModSounds;
import com.humusberg.astralprogress.util.Raycasts;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.Tags;

public class Gun extends Item {
    private int dps;
    private int cooldown;
    private int range;
    private ParticleOptions plume;
    private int plumeLength;
    public Gun(int dps, int cooldown, int range, ParticleOptions plume, int plumeLength, Properties pProperties) {
        super(pProperties);
        this.dps = dps;
        this.cooldown = cooldown;
        this.range = range;
        this.plume = plume;
        this.plumeLength = plumeLength;
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        for (ItemStack stack : player.getInventory().items) {
            if (stack.is(Items.IRON_NUGGET.asItem())) {
                stack.shrink(1);
                level.playSound(
                    player,
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    ModSounds.GUN.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F
                );
                if (!level.isClientSide) {
                    HitResult result = Raycasts.RaycastEntity(level, player, range);
                    HitResult missResult = Raycasts.RaycastBlock(player, range, false);
                    if (plumeLength >= 100) {
                        Raycasts.LaserParticle(level, player.getPosition(1.0f).add(0, 1, 0), missResult.getLocation(), plume, plumeLength);
                    } else {
                        Raycasts.LaserParticle(level, missResult.getLocation(), player.getPosition(1.0f).add(0, 1, 0), plume, plumeLength);
                    }
                        if (result != null) {
                        if (result.getType() == HitResult.Type.ENTITY) {
                            EntityHitResult entityHit = (EntityHitResult) result;
                            Entity entity = entityHit.getEntity();
                            entity.hurt(level.damageSources().playerAttack(player), dps);
                            Raycasts.LaserParticle(level, player.getPosition(1.0f), result.getLocation(), new DustParticleOptions(new Vector3f(0.631f, 0.192f, 0.196f), 1.0F), 20);
                        }
                    } else if (missResult.getType() != Type.MISS) {
                        Raycasts.LaserParticle(level, player.getPosition(1.0f), missResult.getLocation(), ParticleTypes.CRIT, 5);
                        BlockHitResult blockHitResult = (BlockHitResult) missResult;
                        BlockState state = level.getBlockState(blockHitResult.getBlockPos());
                        if (state.getBlock() == Blocks.ICE || state.getBlock() == Blocks.IRON_BARS || state.is(Tags.Blocks.GLASS) || state.is(BlockTags.LEAVES) || state.is(Tags.Blocks.GLASS_PANES)) {
                            level.destroyBlock(blockHitResult.getBlockPos(), false);
                        }
                    }
                        player.getCooldowns().addCooldown(this, cooldown);
                    }
                break;
            }
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }
}
