/*
 * The Veil Mod
 * File: VeilTeleporter.java
 * Description: Teleporter Logic for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.world;
import com.trist79.veil.TheVeilMod;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class VeilTeleporter {
    private final ServerLevel destinationWorld;
    public VeilTeleporter(ServerLevel destinationWorld) {
        this.destinationWorld = destinationWorld;
    }

    public static void VeilTeleportAnimation(ServerLevel targetDim, Vec3 targetPos, Player player){
        TheVeilMod.LOGGER.info("VeilTeleportAnimation method Fired");
        player.level().playSound(player, player.getX(), player.getY(), player.getZ(),
            SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
            targetDim.sendParticles(
                ParticleTypes.PORTAL,
                player.getX(), player.getY() + 0.5, player.getZ(),
                128, 0.5, 1.0, 0.5, 0.0
            );
    }

    //Teleports a player from their current dimension to the veil, or from the veil to their previous dimension
    public Entity placeEntity(Entity entity, ServerLevel world, float yaw, Vec3 targetPos) {

        CompoundTag data = entity.getPersistentData();
        ServerLevel veilWorld = entity.getServer().getLevel(VeilDimensionRegistry.VEIL_DIM);

        ServerLevel destinationWorld;
        Vec3 finalPos;
        if (!world.dimension().location().equals(veilWorld.dimension().location())) {
            // Entering the Veil: save current position and dimension
            CompoundTag prevPos = new CompoundTag();
            prevPos.putDouble("x", entity.getX());
            prevPos.putDouble("y", entity.getY());
            prevPos.putDouble("z", entity.getZ());
            prevPos.putString("dimension", world.dimension().location().toString());
            data.put("veil_previous_pos", prevPos);

            destinationWorld = veilWorld;
            BlockPos spawn = veilWorld.getSharedSpawnPos();
            finalPos = new Vec3(spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5);

        } else {
            // Exiting the Veil: teleport back to saved position
            if (!data.contains("veil_previous_pos")) return entity;

            CompoundTag prevPos = data.getCompound("veil_previous_pos");
            String location = prevPos.getString("dimension");
            String[] locationSplit = location.split(":");
            TheVeilMod.LOGGER.atInfo().log(locationSplit.toString());;
            ResourceKey<Level> prevDimKey = ResourceKey.create(
                Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(locationSplit[0], locationSplit[1])
            );
            destinationWorld = entity.getServer().getLevel(prevDimKey);
            if (destinationWorld == null) return entity;

            finalPos = new Vec3(prevPos.getDouble("x"), prevPos.getDouble("y"), prevPos.getDouble("z"));
        }
        DimensionTransition.PostDimensionTransition postTransition = (newPlayer) -> {
            // This code runs right after the entity changes dimension
            if (entity instanceof Player player) {
                // Example: reset velocity or play sound
                player.setDeltaMovement(0, 0, 0);
                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);
            }
        };
        if (entity instanceof ServerPlayer player) {
            player.changeDimension(new DimensionTransition(destinationWorld, targetPos, finalPos, yaw, 0, false, postTransition));
            player.setYRot(yaw);
            player.setXRot(0);
            player.setDeltaMovement(0, 0, 0);
            VeilTeleportAnimation(destinationWorld, finalPos, player);
        }
        return entity;
    }
}