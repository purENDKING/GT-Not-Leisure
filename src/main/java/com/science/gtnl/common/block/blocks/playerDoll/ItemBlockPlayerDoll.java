package com.science.gtnl.common.block.blocks.playerDoll;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
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

public class ItemBlockPlayerDoll extends ItemBlock implements IItemWithModularUI {

    public ItemBlockPlayerDoll(Block block) {
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
        if (!(stack.getItem() instanceof ItemBlockPlayerDoll)) return null;
        return new PlayerDollUIFactory(buildContext, stack).createWindow();
    }

    /**
     * GUI 工厂类
     */
    private static class PlayerDollUIFactory {

        private final UIBuildContext buildContext;
        private final ItemStack stack;

        public PlayerDollUIFactory(UIBuildContext buildContext, ItemStack stack) {
            this.buildContext = buildContext;
            this.stack = stack;
        }

        public ModularWindow createWindow() {
            ModularWindow.Builder builder = ModularWindow.builder(300, 97); // 增加窗口高度以容纳新文本框
            builder.setBackground(ModularUITextures.VANILLA_BACKGROUND);

            // 输入框：用于输入玩家名
            TextFieldWidget playerNameField = new TextFieldWidget();
            builder.widget(
                playerNameField.setGetter(() -> getSkullOwner(getCurrentItem()))
                    .setSetter(value -> setSkullOwner(getCurrentItem(), value))
                    .setTextColor(Color.WHITE.dark(1))
                    .setTextAlignment(Alignment.CenterLeft)
                    .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD.withOffset(-1, -1, 2, 2))
                    .setPos(8, 8)
                    .setSize(77, 12))
                .widget(new TextWidget(StatCollector.translateToLocal("Tooltip_PlayerDoll_00")).setPos(88, 10));

            // 输入框：用于输入 HTTP 链接
            TextFieldWidget skinHttpField = new TextFieldWidget();
            builder.widget(
                skinHttpField.setGetter(() -> getSkinHttp(getCurrentItem()))
                    .setSetter(value -> setSkinHttp(getCurrentItem(), value))
                    .setTextColor(Color.WHITE.dark(1))
                    .setTextAlignment(Alignment.CenterLeft)
                    .setScrollBar()
                    .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD.withOffset(-1, -1, 2, 2))
                    .setPos(8, 26)
                    .setSize(197, 12))
                .widget(new TextWidget(StatCollector.translateToLocal("Tooltip_PlayerDoll_02")).setPos(208, 28)); // 新标签

            // 输入框：用于输入 HTTP 链接
            TextFieldWidget capeHttpField = new TextFieldWidget();
            builder.widget(
                capeHttpField.setGetter(() -> getCapeHttp(getCurrentItem()))
                    .setSetter(value -> setCapeHttp(getCurrentItem(), value))
                    .setTextColor(Color.WHITE.dark(1))
                    .setTextAlignment(Alignment.CenterLeft)
                    .setScrollBar()
                    .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD.withOffset(-1, -1, 2, 2))
                    .setPos(8, 44)
                    .setSize(197, 12))
                .widget(new TextWidget(StatCollector.translateToLocal("Tooltip_PlayerDoll_04")).setPos(208, 46)); // 新标签

            // 输入框：用于输入鞘翅渲染状态（0 或 1）
            TextFieldWidget elytraStateField = new TextFieldWidget();
            builder.widget(
                elytraStateField.setGetter(() -> getEnableElytra(getCurrentItem()) ? "1" : "0")
                    .setSetter(value -> {
                        // 将输入值转换为 boolean
                        boolean enableElytra = "1".equals(value);
                        setEnableElytra(getCurrentItem(), enableElytra);
                    })
                    .setTextColor(Color.WHITE.dark(1))
                    .setTextAlignment(Alignment.CenterLeft)
                    .setBackground(GTUITextures.BACKGROUND_TEXT_FIELD.withOffset(-1, -1, 2, 2))
                    .setPos(64, 66)
                    .setSize(36, 12))
                .widget(new TextWidget(StatCollector.translateToLocal("Tooltip_PlayerDoll_03")).setPos(105, 68));

            // 确认按钮
            builder.widget(
                new VanillaButtonWidget().setDisplayString(StatCollector.translateToLocal("Tooltip_PlayerDoll_01"))
                    .setOnClick((clickData, widget) -> {
                        playerNameField.onRemoveFocus();
                        skinHttpField.onRemoveFocus();
                        capeHttpField.onRemoveFocus();
                        widget.getWindow()
                            .tryClose();
                    })
                    .setSynced(false, false)
                    .setPos(8, 62)
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
         * 获取 SkinHttp
         */
        private String getSkinHttp(ItemStack stack) {
            if (stack.hasTagCompound()) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt.hasKey("SkinHttp", 8)) { // 8 表示 String 类型
                    return nbt.getString("SkinHttp");
                }
            }
            return "";
        }

        /**
         * 设置 SkinHttp
         */
        private void setSkinHttp(ItemStack stack, String skinHttp) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                stack.setTagCompound(nbt = new NBTTagCompound());
            }
            nbt.setString("SkinHttp", skinHttp);
        }

        /**
         * 获取 CapeHttp
         */
        private String getCapeHttp(ItemStack stack) {
            if (stack.hasTagCompound()) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt.hasKey("CapeHttp", 8)) { // 8 表示 String 类型
                    return nbt.getString("CapeHttp");
                }
            }
            return "";
        }

        /**
         * 设置 CapeHttp
         */
        private void setCapeHttp(ItemStack stack, String skinHttp) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                stack.setTagCompound(nbt = new NBTTagCompound());
            }
            nbt.setString("CapeHttp", skinHttp);
        }

        /**
         * 获取 enableElytra
         */
        private boolean getEnableElytra(ItemStack stack) {
            if (stack.hasTagCompound()) {
                NBTTagCompound nbt = stack.getTagCompound();
                if (nbt.hasKey("enableElytra", 1)) { // 1 表示 Boolean 类型
                    return nbt.getBoolean("enableElytra");
                }
            }
            return false; // 默认值
        }

        /**
         * 设置 enableElytra
         */
        private void setEnableElytra(ItemStack stack, boolean enableElytra) {
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null) {
                stack.setTagCompound(nbt = new NBTTagCompound());
            }
            nbt.setBoolean("enableElytra", enableElytra);
        }

        /**
         * 获取当前手持物品
         */
        private ItemStack getCurrentItem() {
            return buildContext.getPlayer().inventory.getCurrentItem();
        }
    }
}
