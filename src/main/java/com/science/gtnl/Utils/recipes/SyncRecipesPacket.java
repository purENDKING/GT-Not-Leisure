package com.science.gtnl.Utils.recipes;

import com.science.gtnl.common.recipe.Special.RemoveRecipes;
import com.science.gtnl.config.MainConfig;
import com.science.gtnl.loader.RecipeLoaderServerStart;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

public class SyncRecipesPacket implements IMessage {

    public SyncRecipesPacket() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<SyncRecipesPacket, IMessage> {

        @Override
        public IMessage onMessage(SyncRecipesPacket message, MessageContext ctx) {
            if (MainConfig.needSeedPacket) {
                RemoveRecipes.removeRecipes();
                RecipeLoaderServerStart.loadRecipesServerStart();
                MainConfig.needSeedPacket = false;
                return null;
            }
            return null;
        }
    }
}
