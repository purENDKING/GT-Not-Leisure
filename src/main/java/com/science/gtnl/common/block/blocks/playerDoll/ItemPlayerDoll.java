package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import com.gtnewhorizons.modularui.api.ModularUITextures;
import com.gtnewhorizons.modularui.api.math.Alignment;
import com.gtnewhorizons.modularui.api.math.Color;
import com.gtnewhorizons.modularui.api.screen.IItemWithModularUI;
import com.gtnewhorizons.modularui.api.screen.ModularWindow;
import com.gtnewhorizons.modularui.api.screen.UIBuildContext;
import com.gtnewhorizons.modularui.common.widget.TextWidget;
import com.gtnewhorizons.modularui.common.widget.VanillaButtonWidget;
import com.gtnewhorizons.modularui.common.widget.textfield.TextFieldWidget;

import gregtech.api.gui.modularui.GTUIInfos;
import gregtech.api.gui.modularui.GTUITextures;

public class ItemPlayerDoll extends ItemBlock implements IItemWithModularUI {

    public ItemPlayerDoll(Block block) {
        super(block);
        MinecraftForge.EVENT_BUS.register(this); // 注册事件监听器
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        return super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        // 仅在服务端处理
        if (!world.isRemote) {
            // 打开 GUI
            GTUIInfos.openPlayerHeldItemUI(player);
        }
        return super.onItemRightClick(stack, world, player);
    }

    /**
     * 创建 GUI 窗口
     */
    @Override
    public ModularWindow createWindow(UIBuildContext buildContext, ItemStack stack) {
        if (!(stack.getItem() instanceof ItemPlayerDoll)) return null;
        return new PlayerDollUIFactory(buildContext, stack).createWindow();
    }

    /**
     * GUI 工厂类
     */
    private class PlayerDollUIFactory {

        private final UIBuildContext buildContext;
        private final ItemStack stack;

        public PlayerDollUIFactory(UIBuildContext buildContext, ItemStack stack) {
            this.buildContext = buildContext;
            this.stack = stack;
        }

        public ModularWindow createWindow() {
            ModularWindow.Builder builder = ModularWindow.builder(150, 54);
            builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);

            // 输入框：用于输入玩家名
            TextFieldWidget textField = new TextFieldWidget();
            builder.widget(
                textField.setGetter(() -> getSkullOwner(getCurrentItem()))
                    .setSetter(value -> setSkullOwner(getCurrentItem(), value))
                    .setTextColor(Color.WHITE.dark(1))
                    .setTextAlignment(Alignment.CenterLeft)
                    .setFocusOnGuiOpen(true)
                    .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD.withOffset(-1, -1, 2, 2))
                    .setPos(8, 8)
                    .setSize(77, 12))
                .widget(new TextWidget("Player Name").setPos(88, 10))
                .widget(
                    new VanillaButtonWidget().setDisplayString("Confirm")
                        .setOnClick((clickData, widget) -> {
                            textField.onRemoveFocus();
                            widget.getWindow()
                                .tryClose();
                        })
                        .setSynced(false, false)
                        .setPos(8, 26)
                        .setSize(48, 20));

            return builder.build();
        }

        /**
         * 获取 SkullOwner
         */
        private String getSkullOwner(ItemStack stack) {
            if (stack.hasTagCompound()) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt.hasKey("SkullOwner", 8)) { // 8 表示 String 类型
                    return nbt.getString("SkullOwner");
                }
            }
            return "";
        }

        /**
         * 设置 SkullOwner
         */
        private void setSkullOwner(ItemStack stack, String playerName) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                stack.setTagCompound(nbt = new NBTTagCompound());
            }
            nbt.setString("SkullOwner", playerName);
        }

        /**
         * 获取当前手持物品
         */
        private ItemStack getCurrentItem() {
            return buildContext.getPlayer().inventory.getCurrentItem();
        }
    }
}
