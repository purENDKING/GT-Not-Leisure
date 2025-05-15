package com.science.gtnl.mixins.late.BloodMagic;

import static com.science.gtnl.Utils.bloodMagic.MeteorParadigmHelper.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorParadigm;
import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorParadigmComponent;

@SuppressWarnings("UnusedMixin")
@Mixin(value = MeteorParadigm.class, remap = false)
public abstract class MeteorParadigm_Mixin {

    @Inject(
        method = "createMeteorImpact",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFZ)Lnet/minecraft/world/Explosion;",
            shift = At.Shift.AFTER),
        cancellable = true)
    private void onCreateMeteorImpact(World world, int x, int y, int z, boolean[] flags, CallbackInfo ci) {
        MeteorParadigm self = (MeteorParadigm) (Object) this;

        final int originalRadius = self.radius;
        final int originalFillerChance = self.fillerChance;
        final List<MeteorParadigmComponent> componentList = new ArrayList<>(self.componentList);
        final List<MeteorParadigmComponent> originalFillerList = new ArrayList<>(self.fillerList);

        boolean hasTerrae = false, hasOrbisTerrae = false, hasCrystallos = false, hasIncendium = false,
            hasTennebrae = false;
        if (flags != null && flags.length >= 5) {
            hasTerrae = flags[0];
            hasOrbisTerrae = flags[1];
            hasCrystallos = flags[2];
            hasIncendium = flags[3];
            hasTennebrae = flags[4];
        }

        int newRadius = calculateRadius(originalRadius, hasOrbisTerrae, hasTerrae);
        int fillerChance = calculateFillerChance(originalFillerChance, hasOrbisTerrae, hasTerrae);
        List<MeteorParadigmComponent> currentFillerList = buildFillerList(
            hasCrystallos,
            hasIncendium,
            hasTennebrae,
            originalFillerList);

        final SphereData sphereData = precomputeSphereData(world, x, y, z, newRadius);
        final WeightData componentWeights = precomputeWeights(componentList);
        final WeightData fillerWeights = precomputeWeights(currentFillerList);
        final int finalFillerChance = fillerChance;

        AtomicInteger counter = new AtomicInteger(0);
        List<Future<?>> futures = new ArrayList<>();

        for (int chunkStart = 0; chunkStart < sphereData.positions.size(); chunkStart += CHUNK_SIZE) {
            final int start = chunkStart;
            final int end = Math.min(start + CHUNK_SIZE, sphereData.positions.size());

            futures.add(
                METEOR_PLACEMENT_POOL.submit(
                    () -> processChunk(
                        world,
                        sphereData,
                        componentWeights,
                        fillerWeights,
                        finalFillerChance,
                        start,
                        end,
                        counter)));
        }

        METEOR_PLACEMENT_POOL.submit(
            () -> world.markBlockRangeForRenderUpdate(
                x - newRadius,
                y - newRadius,
                z - newRadius,
                x + newRadius,
                y + newRadius,
                z + newRadius));

        METEOR_PLACEMENT_POOL.submit(() -> {
            try {
                for (Future<?> future : futures) {
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                futures.clear();
            }
        });

        ci.cancel();
    }
}
