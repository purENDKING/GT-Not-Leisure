package com.science.gtnl.mixins.early;

import com.science.gtnl.common.item.TimeStopManager;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Set;

@Mixin(value = WorldClient.class,remap = true)
public class WorldClient_Mixin {

    @Shadow private NetHandlerPlayClient sendQueue;
    @Shadow private ChunkProviderClient clientChunkProvider;

    @Inject(method = "tick",at = @At("HEAD"),cancellable = true)
    public void mixin$tick(CallbackInfo ci) {
        boolean isStop = TimeStopManager.isTimeStopped();
        if (isStop) {
            ci.cancel();
        }
        else return;

        ((WorldClient)((Object)this)).theProfiler.startSection("reEntryProcessing");

        /*for (int i = 0; i < 10 && !this.entitySpawnQueue.isEmpty(); ++i)
        {
            Entity entity = (Entity)this.entitySpawnQueue.iterator().next();
            this.entitySpawnQueue.remove(entity);

            if (!this.loadedEntityList.contains(entity))
            {
                ((WorldClient)((Object)this)).spawnEntityInWorld(entity);
            }
        }*/

        ((WorldClient)((Object)this)).theProfiler.endStartSection("connection");
        sendQueue.onNetworkTick();
        ((WorldClient)((Object)this)).theProfiler.endStartSection("chunkCache");
        clientChunkProvider.unloadQueuedChunks();
        ((WorldClient)((Object)this)).theProfiler.endStartSection("blocks");
        ((WorldClient)((Object)this)).func_147456_g();
        ((WorldClient)((Object)this)).theProfiler.endSection();
    }
}
