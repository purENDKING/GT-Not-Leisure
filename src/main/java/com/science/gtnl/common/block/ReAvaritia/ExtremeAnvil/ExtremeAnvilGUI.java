package com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExtremeAnvilGUI extends GuiContainer {

    private static final ResourceLocation ANVIL_TEXTURE = new ResourceLocation(
        "reavaritia:textures/gui/extreme_anvil.png");
    private GuiTextField nameField;
    private final ContainerExtremeAnvil containerAnvil;
    private boolean isTextFieldManuallyEdited = false; // 标记文本框是否被手动编辑

    public ExtremeAnvilGUI(InventoryPlayer playerInv, IInventory inputSlots, IInventory outputSlot) {
        super(new ContainerExtremeAnvil(playerInv, inputSlots, outputSlot));
        this.containerAnvil = (ContainerExtremeAnvil) this.inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();

        // 初始化 nameField
        this.nameField = new GuiTextField(this.fontRendererObj, this.guiLeft + 62, this.guiTop + 24, 103, 12);
        this.nameField.setMaxStringLength(256); // 设置最大字符数
        this.nameField.setEnableBackgroundDrawing(false); // 禁用背景绘制
        this.nameField.setTextColor(-1); // 设置文本颜色为白色
        this.nameField.setFocused(true); // 设置焦点

        // 获取第一个输入槽的物品
        ItemStack inputStack = containerAnvil.getInputSlot1()
            .getStack();

        // 设置初始文本
        if (inputStack != null && inputStack.hasDisplayName()) {
            this.nameField.setText(inputStack.getDisplayName());
        } else {
            this.nameField.setText(containerAnvil.getRepairedItemName());
        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        // 获取第一个输入槽的物品
        ItemStack inputStack = containerAnvil.getInputSlot1()
            .getStack();

        // 如果第一个输入槽的物品被拿走，清空文本框
        if (inputStack == null) {
            this.nameField.setText("");
            this.isTextFieldManuallyEdited = false;
            return;
        }

        // 如果文本框未被手动编辑，则同步修复后的物品名称
        if (!isTextFieldManuallyEdited) {
            String repairedName = containerAnvil.getRepairedItemName();
            if (!repairedName.equals(this.nameField.getText())) {
                this.nameField.setText(repairedName);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        // 处理特殊按键
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.thePlayer.closeScreen();
            return;
        }

        // 名称字段处理
        if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
            this.isTextFieldManuallyEdited = true; // 标记文本框被手动编辑
            this.updateRepairedName();
        } else {
            super.keyTyped(typedChar, keyCode);
        }
    }

    private void updateRepairedName() {
        String newName = this.nameField.getText();
        this.containerAnvil.setRepairedItemName(newName.trim());
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.nameField.mouseClicked(mouseX, mouseY, mouseButton);

        // 如果点击了输出槽，同步输出物品的名称
        if (this.isPointInRegion(134, 47, 110, 16, mouseX, mouseY)) {
            ItemStack output = containerAnvil.outputSlot.getStackInSlot(0);
            if (output != null && output.hasDisplayName()) {
                this.nameField.setText(output.getDisplayName());
                this.updateRepairedName();
            }
        }

        // 如果点击了文本框以外的区域，取消焦点
        if (!this.isPointInRegion(59, 23, 110, 16, mouseX, mouseY)) {
            this.nameField.setFocused(false);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        // 清空名称字段
        this.nameField.setText("");
    }

    private boolean isPointInRegion(int x, int y, int width, int height, int mouseX, int mouseY) {
        int guiLeft = (this.width - this.xSize) / 2;
        int guiTop = (this.height - this.ySize) / 2;
        mouseX -= guiLeft;
        mouseY -= guiTop;
        return mouseX >= x - 1 && mouseX < x + width + 1 && mouseY >= y - 1 && mouseY < y + height + 1;
    }

    private String getLocalizedName() {
        return StatCollector.translateToLocal("container.ExtremeAnvil");
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        // 绘制标题
        String title = this.getLocalizedName();
        this.fontRendererObj
            .drawString(title, (this.xSize - this.fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);

        // 绘制背包标签
        this.fontRendererObj
            .drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 0x404040);

        // 绘制名称输入框
        this.nameField.drawTextBox();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager()
            .bindTexture(ANVIL_TEXTURE);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

        int textFieldState = this.nameField.isFocused() ? 0 : 1;
        this.drawTexturedModalRect(x + 59, y + 23, 0, this.ySize + textFieldState * 16, 110, 16);

        ItemStack input1 = containerAnvil.inputSlots.getStackInSlot(0);
        ItemStack input2 = containerAnvil.inputSlots.getStackInSlot(1);

        boolean showError = false;

        if (input1 != null) {
            if (input2 == null && nameField.getText()
                .isEmpty()) {
                showError = true;
            } else if (input2 != null) {
                if (input2.getItem() instanceof ItemEnchantedBook) {
                    showError = false;
                } else if (input1.getItem() == input2.getItem()) {
                    if (!input1.getItem()
                        .isDamageable()) {
                        showError = true;
                    }
                } else {
                    showError = true;
                }
            }
        }

        if (showError) {
            this.drawTexturedModalRect(x + 99, y + 47, this.xSize, 0, 28, 21);
        }
    }
}
