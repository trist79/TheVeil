/*
 * The Veil Mod
 * File: BryophyteCoveredPeat.java
 * Description: Bryophyte Covered Peat Block Logic
 * Copyright (c) 2025 Tristan Anderson
 * Licensed under the MIT License
 */


package com.trist79.veil.common.blocks;

import java.util.Collections;
import java.util.List;
import com.mojang.serialization.MapCodec;
import com.trist79.veil.common.data.blocks.VeilBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class BryophyteCoveredPeat extends SpreadingSnowyDirtBlock implements BonemealableBlock{

    public BryophyteCoveredPeat(Properties properties) {
        super(properties);
    }

    // Drop with optional entity and tool
    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        ItemStack tool = builder.getParameter(LootContextParams.TOOL);

        Holder<Enchantment> silkTouchHolder = builder.getLevel().registryAccess()
            .registryOrThrow(Registries.ENCHANTMENT)
            .getHolder(Enchantments.SILK_TOUCH)
            .orElseThrow();
        if (tool != null && EnchantmentHelper.getTagEnchantmentLevel(silkTouchHolder, tool) > 0) {
            return Collections.singletonList(new ItemStack(this)); // drop itself
        } else {
            return Collections.singletonList(new ItemStack(VeilBlocks.PEAT.get())); // drop regular peat
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockState blockState = this.defaultBlockState();
        BlockState above = level.getBlockState(pos.above());
        int light = level.getMaxLocalRawBrightness(pos.above());

        if (light == 0 || light >= 14) {
            level.setBlock(pos, VeilBlocks.PEAT.get().defaultBlockState(), 2);
            return;
        }

        for (int i = 0; i < 4; i++) { // attempt 4 random nearby positions per tick
            // 3x5x3 random offset
            BlockPos targetPos = pos.offset(
                random.nextInt(3) - 1,
                random.nextInt(5) - 3,
                random.nextInt(3) - 1
            );
            BlockState targetState = level.getBlockState(targetPos);
            above = level.getBlockState(targetPos.above());

            light = level.getMaxLocalRawBrightness(targetPos.above());
            if (targetState.is(VeilBlocks.PEAT.get()) &&
                above.isAir() &&
                light >= 9 &&
                light < 14)
            {
                level.setBlock(targetPos, blockState, 2);
            }
        }
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState blockState) {
        return true;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState blockState) {
        return level.getBlockState(pos.above()).isAir();
    }


    //Uninplemented performBoneMeal method snagged from Vanilla GrassBlock.class
    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState blockState) {
    /*     BlockPos targetPos = pos.above();
        // Optional placed feature for your vegetation (replace with your actual feature key)
        Optional<Holder.Reference<PlacedFeature>> optionalFeature = level.registryAccess()
                .registryOrThrow(Registries.PLACED_FEATURE)
                .getHolder(VegetationPlacements.GRASS_BONEMEAL); // replace with your Bryophyte feature if desired
        for (int i = 0; i < 128; ++i) {
            BlockPos spawnPos = targetPos;
            // Random walk offsets
            for (int j = 0; j < i / 16; ++j) {
                spawnPos = spawnPos.offset(
                        random.nextInt(3) - 1,
                        (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1
                );
                if (!level.getBlockState(spawnPos.below()).is(this)
                    || level.getBlockState(spawnPos).isCollisionShapeFullBlock(level, spawnPos)) {
                    continue;
                }
            }
            BlockState blockAtPos = level.getBlockState(spawnPos);
            // Small chance to recursively bonemeal an existing small vegetation
            if (blockAtPos.is(Blocks.SHORT_GRASS) && random.nextInt(10) == 0) {
                if (blockAtPos.getBlock() instanceof BonemealableBlock bonemealable) {
                    bonemealable.performBonemeal(level, random, spawnPos, blockAtPos);
                }
            }
            // Spawn new plant if air
            if (blockAtPos.isAir()) {
                Holder<PlacedFeature> featureHolder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> flowers = level.getBiome(spawnPos).value()
                            .getGenerationSettings()
                            .getFlowerFeatures();
                    if (flowers.isEmpty()) continue;
                    featureHolder = ((RandomPatchConfiguration) flowers.get(0).config()).feature();
                } else {
                    if (!optionalFeature.isPresent()) continue;
                    featureHolder = optionalFeature.get();
                }
                featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, spawnPos);
            }
        } */
    }

    public static final MapCodec<GrassBlock> CODEC = simpleCodec(GrassBlock::new);
    public MapCodec<? extends SpreadingSnowyDirtBlock> codec() {
        return CODEC;
    }
}