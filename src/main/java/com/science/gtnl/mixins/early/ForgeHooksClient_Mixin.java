package com.science.gtnl.mixins.early;

import static com.science.gtnl.ScienceNotLeisure.network;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.science.gtnl.Utils.message.PacketGetTileEntityNBTRequest;

@SuppressWarnings("UnusedMixin")
@Mixin(value = ForgeHooks.class, remap = false)
public class ForgeHooksClient_Mixin {

    @Inject(method = "onPickBlock", at = @At("HEAD"), cancellable = true)
    private static void preOnPickBlockSendRequest(MovingObjectPosition target, EntityPlayer player, World world,
        CallbackInfoReturnable<Boolean> cir) {
        boolean isCtrlKeyDown = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);

        if (isCtrlKeyDown && player.capabilities.isCreativeMode) {
            ItemStack result;

            if (target != null && target.typeOfHit == MovingObjectType.BLOCK) {
                int x = target.blockX;
                int y = target.blockY;
                int z = target.blockZ;
                Block block = world.getBlock(x, y, z);

                if (!block.isAir(world, x, y, z)) {
                    result = block.getPickBlock(target, world, x, y, z, player);
                    Item item = result.getItem();
                    int blockID = Item.getIdFromItem(item);
                    int blockMeta = result.getItemDamage();
                    TileEntity tileentity = world.getTileEntity(x, y, z);
                    if (tileentity != null) {
                        network.sendToServer(new PacketGetTileEntityNBTRequest(x, y, z, blockID, blockMeta));
                        cir.setReturnValue(true);
                        cir.cancel();
                    }
                }
            }
        }

    }
}
