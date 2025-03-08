package com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class ContainerExtremeAnvil extends Container {

    public final IInventory inputSlots; // 输入槽位的容器
    public final IInventory outputSlot; // 输出槽位的容器

    public final Slot inputSlot1; // 第一个输入槽
    public final Slot inputSlot2; // 第二个输入槽
    public final Slot outputSlotInstance; // 输出槽

    public String repairedItemName = "";

    public ContainerExtremeAnvil(InventoryPlayer playerInv, IInventory inputSlots, IInventory outputSlot) {
        this.inputSlots = inputSlots;
        this.outputSlot = outputSlot;

        // 添加输入槽位
        this.inputSlot1 = new Slot(inputSlots, 0, 27, 47); // 第一个输入槽
        this.inputSlot2 = new Slot(inputSlots, 1, 76, 47); // 第二个输入槽
        this.addSlotToContainer(inputSlot1);
        this.addSlotToContainer(inputSlot2);

        // 添加输出槽位
        this.outputSlotInstance = new Slot(outputSlot, 2, 134, 47); // 输出槽
        this.addSlotToContainer(outputSlotInstance);

        // 添加玩家物品栏
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // 添加玩家快捷栏
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }

    public String getRepairedItemName() {
        return this.repairedItemName;
    }

    public void setRepairedItemName(String name) {
        this.repairedItemName = name;
    }

    public Slot getInputSlot1() {
        return this.inputSlot1;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true; // 允许玩家与容器交互
    }

    @Override
    public void onCraftMatrixChanged(IInventory inventory) {
        super.onCraftMatrixChanged(inventory);
        this.updateRepairOutput(); // 当输入槽发生变化时更新输出
    }

    public void updateRepairOutput() {
        ItemStack input = inputSlots.getStackInSlot(0); // 获取第一个输入槽中的物品
        ItemStack material = inputSlots.getStackInSlot(1); // 获取第二个输入槽中的物品
        ItemStack output = null;

        // 检查输入物品和材料的有效性
        if (input != null && material != null) {
            if (material.getItem() instanceof ItemEnchantedBook) {
                // 如果材料是附魔书，则允许合成
                output = input.copy();
            } else if (input.getItem() == material.getItem()) {
                // 如果输入物品和材料是相同类型，则检查是否有耐久度
                if (input.getItem()
                    .isDamageable()) {
                    // 如果有耐久度，则允许合成并修复耐久
                    output = input.copy();

                    // 计算修补后的耐久
                    int inputDurability = input.getMaxDamage() - input.getItemDamage();
                    int materialDurability = material.getMaxDamage() - material.getItemDamage();
                    int repairedDurability = (int) (inputDurability + materialDurability * 1.1);

                    // 设置修补后的耐久
                    output.setItemDamage(Math.max(0, output.getMaxDamage() - repairedDurability));
                } else {
                    // 如果没有耐久度，则禁止合成
                    output = null;
                }
            } else {
                // 如果输入物品和材料既不是附魔书，也不是相同类型的物品，则清空输出槽
                output = null;
            }
        } else {
            // 如果输入槽或材料槽为空，清空输出槽
            output = null;
        }

        // 如果输出物品不为空，处理附魔合并和其他 NBT 数据
        if (output != null) {
            // 合并附魔
            Map<Integer, Integer> enchantments = new HashMap<>();
            this.mergeEnchantments(enchantments, input); // 合并输入物品的附魔
            this.mergeEnchantments(enchantments, material); // 合并材料的附魔

            NBTTagList enchList = new NBTTagList();
            for (Map.Entry<Integer, Integer> entry : enchantments.entrySet()) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setShort(
                    "id",
                    (short) entry.getKey()
                        .intValue());
                tag.setShort(
                    "lvl",
                    (short) entry.getValue()
                        .intValue());
                enchList.appendTag(tag);
            }

            // 如果合并后的附魔列表不为空，设置到输出物品中
            if (enchList.tagCount() > 0) {
                if (!output.hasTagCompound()) {
                    output.setTagCompound(new NBTTagCompound());
                }
                output.getTagCompound()
                    .setTag("ench", enchList);
            }

            // 保留输入物品的其他 NBT 数据（如重命名、附魔等）
            if (input.hasTagCompound()) {
                NBTTagCompound inputTag = input.getTagCompound();
                if (!output.hasTagCompound()) {
                    output.setTagCompound(new NBTTagCompound());
                }
                NBTTagCompound outputTag = output.getTagCompound();

                // 复制所有非附魔相关的 NBT 数据
                for (String key : inputTag.func_150296_c()) {
                    if (!key.equals("ench") && !key.equals("RepairCost")) {
                        outputTag.setTag(
                            key,
                            inputTag.getTag(key)
                                .copy());
                    }
                }
            }
        }

        // 设置输出槽位中的物品
        outputSlot.setInventorySlotContents(0, output);
        detectAndSendChanges();
    }

    // 合并附魔并确保不超过最大等级
    public void mergeEnchantments(Map<Integer, Integer> map, ItemStack stack) {
        if (stack == null) return;

        NBTTagList list = null;
        if (stack.isItemEnchanted()) {
            list = stack.getEnchantmentTagList();
        } else if (stack.getItem() instanceof ItemEnchantedBook) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag != null && tag.hasKey("StoredEnchantments")) {
                list = tag.getTagList("StoredEnchantments", 10);
            }
        }

        if (list != null) {
            for (int i = 0; i < list.tagCount(); ++i) {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                int id = tag.getShort("id");
                int lvl = tag.getShort("lvl");

                Enchantment ench = Enchantment.enchantmentsList[id];
                if (ench != null) {
                    int max = ench.getMaxLevel();
                    int currentLevel = map.getOrDefault(id, 0);
                    map.put(id, Math.min(currentLevel + lvl, max)); // 确保不超过最大等级
                }
            }
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack stack = null;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();

            // 如果点击的是输出槽
            if (index == 2) {
                // 调用 onTakeOutput 方法
                this.onTakeOutput(player, stackInSlot);

                // 尝试将物品移动到玩家背包
                if (!this.mergeItemStack(stackInSlot, 3, 39, true)) {
                    return null;
                }

                slot.onSlotChange(stackInSlot, stack);
            }
            // 如果点击的是玩家背包或快捷栏
            else if (index >= 3) {
                // 尝试将物品移动到输入槽
                if (!this.mergeItemStack(stackInSlot, 0, 2, false)) {
                    return null;
                }
            }
            // 如果点击的是输入槽
            else if (!this.mergeItemStack(stackInSlot, 3, 39, false)) {
                return null;
            }

            // 处理物品堆叠大小
            if (stackInSlot.stackSize == 0) {
                slot.putStack(null); // 清空槽位
            } else {
                slot.onSlotChanged(); // 更新槽位
            }

            // 如果物品堆叠大小未改变，返回 null
            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }

            // 触发槽位的拾取逻辑
            slot.onPickupFromSlot(player, stackInSlot);
        }

        return stack;
    }

    public void onTakeOutput(EntityPlayer player, ItemStack stack) {
        // 将重命名应用到实际物品
        if (!this.repairedItemName.isEmpty()) {
            stack.setStackDisplayName(this.repairedItemName);
        }

        // 重置名称状态
        this.repairedItemName = "";
        inputSlots.setInventorySlotContents(0, null); // 清空第一个输入槽
        inputSlots.decrStackSize(1, 1); // 消耗第二个输入槽中的物品
        updateRepairOutput(); // 更新输出槽
    }
}
