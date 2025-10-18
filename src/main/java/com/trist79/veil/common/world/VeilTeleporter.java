/*
 * The Veil Mod
 * File: VeilTeleporter.java
 * Description: Teleporter Logic for Veil Dimension
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.world;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class VeilTeleporter {

    private final ServerLevel destinationWorld;

    public VeilTeleporter(ServerLevel destinationWorld) {
        this.destinationWorld = destinationWorld;
    }

    /**
     * Teleports an entity to the Veil dimension.
     * @param entity The entity to teleport
     * @param currentWorld The world the entity is currently in
     * @param yaw The yaw rotation to apply
     * @param targetPos Optional target position. If null, uses the destination world's spawn
     * @return The teleported entity
     */
    public Entity placeEntity(Entity entity, ServerLevel currentWorld, float yaw, Vec3 targetPos) {
        // Determine destination

        // Remove entity from any vehicle
        entity.unRide();

        // Teleport entity
        entity.teleportTo(targetPos.x, targetPos.y, targetPos.z);

        // Set rotation
        entity.setYRot(yaw);
        entity.setXRot(0);

        // Reset velocity
        entity.setDeltaMovement(0, 0, 0);

        // Add entity to the destination world safely
        if (!destinationWorld.addFreshEntity(entity)) {
            destinationWorld.addDuringTeleport(entity);
        }

        return entity;
    }
}