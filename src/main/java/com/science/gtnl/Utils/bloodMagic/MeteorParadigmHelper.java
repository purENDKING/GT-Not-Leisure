package com.science.gtnl.Utils.bloodMagic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.science.gtnl.config.MainConfig;

import WayofTime.alchemicalWizardry.AlchemicalWizardry;
import WayofTime.alchemicalWizardry.common.summoning.meteor.MeteorParadigmComponent;
import cpw.mods.fml.common.Optional;
import gregtech.common.blocks.TileEntityOres;

public class MeteorParadigmHelper {

    public static final int CORE_POOL_SIZE = Runtime.getRuntime()
        .availableProcessors() * 4;
    public static final ExecutorService METEOR_PLACEMENT_POOL = Executors.newWorkStealingPool(CORE_POOL_SIZE);

    public static final int CHUNK_SIZE = MainConfig.meteorParadigmChunkSize;
    public static final int BATCH_UPDATE_INTERVAL = MainConfig.meteorParadigmBatchUpdateInterval;

    public static class SphereData {

        public final List<int[]> positions;
        public final int radius;

        public SphereData(List<int[]> positions, int radius) {
            this.positions = Collections.unmodifiableList(positions);
            this.radius = radius;
        }
    }

    public static class WeightData {

        final List<MeteorParadigmComponent> components;
        final int[] prefixSum;
        final int total;

        WeightData(List<MeteorParadigmComponent> components, int[] prefixSum, int total) {
            this.components = components;
            this.prefixSum = prefixSum;
            this.total = total;
        }

        int select(Random rand) {
            if (total <= 0 || components.isEmpty()) return -1;
            int target = rand.nextInt(total);
            int index = Arrays.binarySearch(prefixSum, target);
            return index >= 0 ? index : -index - 1;
        }
    }

    public static void processChunk(World world, SphereData sphereData, WeightData components, WeightData fillers,
        int fillerChance, int start, int end, AtomicInteger counter) {
        final Random rand = new Random();
        final int[] batchRandoms = new int[end - start];
        for (int i = 0; i < batchRandoms.length; i++) {
            batchRandoms[i] = rand.nextInt(100);
        }

        for (int idx = start; idx < end; idx++) {
            final int[] pos = sphereData.positions.get(idx);
            final int worldX = pos[0], worldY = pos[1], worldZ = pos[2];

            boolean useFiller = (fillerChance > 0) && (batchRandoms[idx - start] < fillerChance);
            WeightData targetData = useFiller ? fillers : components;

            int selectedIndex = targetData.select(rand);
            if (selectedIndex == -1) continue;

            MeteorParadigmComponent comp = useFiller ? fillers.components.get(selectedIndex)
                : components.components.get(selectedIndex);

            try {
                ItemStack stack = comp.getValidBlockParadigm();
                if (stack != null && stack.getItem() instanceof ItemBlock) {
                    placeBlockFast(world, worldX, worldY, worldZ, (ItemBlock) stack.getItem(), stack.getItemDamage());

                    if (AlchemicalWizardry.isGregTechLoaded) {
                        handleGTOres(world, worldX, worldY, worldZ);
                    }

                    if (counter.incrementAndGet() % BATCH_UPDATE_INTERVAL == 0) {
                        world.markBlockRangeForRenderUpdate(
                            worldX - 8,
                            worldY - 8,
                            worldZ - 8,
                            worldX + 8,
                            worldY + 8,
                            worldZ + 8);
                    }
                }
            } catch (Exception e) {
                AlchemicalWizardry.logger.error("Async placement error at [{},{},{}]", worldX, worldY, worldZ, e);
            }
        }
    }

    public static int calculateRadius(int base, boolean hasOrbis, boolean hasTerrae) {
        int radius = base;
        if (hasOrbis) radius += 2;
        else if (hasTerrae) radius += 1;
        return radius;
    }

    public static int calculateFillerChance(int base, boolean hasOrbis, boolean hasTerrae) {
        double multiplier = 1.0;
        if (hasOrbis) multiplier = 1.12;
        else if (hasTerrae) multiplier = 1.06;
        return Math.min((int) (base * multiplier), 100);
    }

    public static List<MeteorParadigmComponent> buildFillerList(boolean hasCrystallos, boolean hasIncendium,
        boolean hasTennebrae, List<MeteorParadigmComponent> original) {
        if (!hasCrystallos && !hasIncendium && !hasTennebrae) {
            return new ArrayList<>(original);
        }

        List<MeteorParadigmComponent> list = new ArrayList<>();
        if (hasCrystallos) list.add(new MeteorParadigmComponent(new ItemStack(Blocks.ice), 180));
        if (hasIncendium) list.add(new MeteorParadigmComponent(new ItemStack(Blocks.netherrack), 150));
        if (hasTennebrae) list.add(new MeteorParadigmComponent(new ItemStack(Blocks.obsidian), 120));
        list.addAll(original);
        return list;
    }

    @Optional.Method(modid = "gregtech")
    private static void handleGTOres(World world, int x, int y, int z) {
        try {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityOres gtOre) {
                gtOre.mNatural = true;
            }
        } catch (Exception e) {
            AlchemicalWizardry.logger.error("GTNL: Meteor Mixin: GT Ore Error [{},{},{}]", x, y, z, e);
        }
    }

    public static WeightData precomputeWeights(List<MeteorParadigmComponent> components) {
        int total = components.stream()
            .mapToInt(c -> c.getWeight())
            .sum();
        int[] prefixSum = new int[components.size()];
        int sum = 0;
        for (int i = 0; i < components.size(); i++) {
            sum += components.get(i)
                .getWeight();
            prefixSum[i] = sum;
        }
        return new WeightData(components, prefixSum, total);
    }

    public static SphereData precomputeSphereData(World world, int x, int y, int z, int radius) {
        List<int[]> positions = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    if (dx * dx + dy * dy + dz * dz <= radius * radius) {
                        positions.add(new int[] { x + dx, y + dy, z + dz });
                    }
                }
            }
        }
        return new SphereData(positions, radius);
    }

    private static void placeBlockFast(World world, int x, int y, int z, ItemBlock block, int meta) {
        world.setBlock(x, y, z, Block.getBlockFromItem(block), meta, 3);
    }
}
