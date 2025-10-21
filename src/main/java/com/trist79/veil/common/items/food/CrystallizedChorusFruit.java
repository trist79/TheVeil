/*
 * The Veil Mod
 * File: CrystallizedChorusFruit.java
 * Description: Logic for Teleportation using Crystallized Chorus Fruit
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */

package com.trist79.veil.common.items.food;
import com.trist79.veil.common.world.VeilDimensionRegistry;
import com.trist79.veil.common.world.VeilTeleporter;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class CrystallizedChorusFruit extends Item {

    public CrystallizedChorusFruit(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide) {
            if (entity instanceof Player player) {
                ResourceKey<Level> dimensionKey = player.level().dimension();
                ServerLevel veilWorld = player.getServer().getLevel(VeilDimensionRegistry.VEIL_DIM);
                ServerLevel currentDimension = player.getServer().getLevel(dimensionKey);
                if (veilWorld != null) {
                    BlockPos spawnPos = veilWorld.getSharedSpawnPos();
                    Vec3 targetPos = new Vec3(
                        spawnPos.getX() + 0.5, // center of the block
                        spawnPos.getY(),
                        spawnPos.getZ() + 0.5
                    );
                    new VeilTeleporter(currentDimension).placeEntity(player, currentDimension, player.getYRot(), targetPos);
                    VeilTeleporter.VeilTeleportAnimation(currentDimension, targetPos, player);
                }
            }
        }
        return super.finishUsingItem(stack, world, entity);
    }
}