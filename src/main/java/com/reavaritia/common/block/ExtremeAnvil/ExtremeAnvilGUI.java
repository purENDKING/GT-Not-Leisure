package com.reavaritia.common.block.ExtremeAnvil;

import static com.reavaritia.ReAvaritia.RESOURCE_ROOT_ID;

import java.util.List;

import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ExtremeAnvilGUI extends GuiContainer implements ICrafting {

    private static final ResourceLocation ANVIL_TEXTURE = new ResourceLocation(
        RESOURCE_ROOT_ID + ":" + "textures/gui/extreme_anvil.png");

    private GuiTextField nameField;
    private final ContainerExtremeAnvil containerAnvil;
    private boolean isTextFieldManuallyEdited = false;
    private ItemStack lastInputStack = null;
    private ItemStack lastMaterialStack = null;

    private int clientCanTake = 0;

    public ExtremeAnvilGUI(InventoryPlayer playerInv, IInventory inputSlots, IInventory outputSlot) {
        super(new ContainerExtremeAnvil(playerInv, inputSlots, outputSlot));
        this.containerAnvil = (ContainerExtremeAnvil) this.inventorySlots;
    }

    @Override
    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);

        this.nameField = new GuiTextField(this.fontRendererObj, this.guiLeft + 62, this.guiTop + 24, 103, 12);
        this.nameField.setMaxStringLength(256);
        this.nameField.setEnableBackgroundDrawing(false);
        this.nameField.setTextColor(-1);
        this.nameField.setFocused(true);

        ItemStack inputStack = containerAnvil.inputSlots.getStackInSlot(0);
        ItemStack materialStack = containerAnvil.inputSlots.getStackInSlot(1);
        this.lastInputStack = inputStack != null ? inputStack.copy() : null;
        this.lastMaterialStack = materialStack != null ? materialStack.copy() : null;

        if (inputStack != null) {
            this.nameField.setText(inputStack.getDisplayName());
            this.containerAnvil.setRepairedItemName(
                this.nameField.getText()
                    .trim());
        } else {
            this.nameField.setText("");
            this.containerAnvil.setRepairedItemName("");
        }
        this.isTextFieldManuallyEdited = false;

        this.containerAnvil.addCraftingToCrafters(this);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        ItemStack currentInputStack = containerAnvil.inputSlots.getStackInSlot(0);
        ItemStack currentMaterialStack = containerAnvil.inputSlots.getStackInSlot(1);

        if (!ItemStack.areItemStacksEqual(currentInputStack, this.lastInputStack)
            || !ItemStack.areItemStacksEqual(currentMaterialStack, this.lastMaterialStack)) {

            this.lastInputStack = currentInputStack != null ? currentInputStack.copy() : null;
            this.lastMaterialStack = currentMaterialStack != null ? currentMaterialStack.copy() : null;

            this.isTextFieldManuallyEdited = false;

            if (currentInputStack != null) {
                String displayName = currentInputStack.getDisplayName();
                this.nameField.setText(displayName);
                this.updateRepairedName();
            } else {
                this.nameField.setText("");
                this.updateRepairedName();
            }
        } else {
            if (!isTextFieldManuallyEdited) {
                String repairedName = containerAnvil.getRepairedItemName();
                if (!repairedName.equals(this.nameField.getText())) {
                    this.nameField.setText(repairedName);
                }
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.thePlayer.closeScreen();
            return;
        }

        if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {
            this.isTextFieldManuallyEdited = true;
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

        if (!this.isPointInRegion(62, 24, 103, 12, mouseX, mouseY)) {
            this.nameField.setFocused(false);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        this.nameField.setText("");
        this.containerAnvil.removeCraftingFromCrafters(this);
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
        String title = this.getLocalizedName();
        this.fontRendererObj
            .drawString(title, (this.xSize - this.fontRendererObj.getStringWidth(title)) / 2, 6, 0x404040);
        this.fontRendererObj
            .drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 3, 0x404040);
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

        boolean showError = (this.clientCanTake == 0);

        if (showError) {
            this.drawTexturedModalRect(x + 99, y + 47, this.xSize, 0, 28, 21);
        }
    }

    @Override
    public void sendContainerAndContentsToPlayer(Container container, List<ItemStack> itemStacks) {}

    @Override
    public void sendSlotContents(Container container, int slotID, ItemStack stack) {}

    @Override
    public void sendProgressBarUpdate(Container container, int id, int data) {
        if (id == 0) {
            this.clientCanTake = data;
        }
    }
}
