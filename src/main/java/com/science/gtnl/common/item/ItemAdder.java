package com.science.gtnl.common.item;

import static com.science.gtnl.ScienceNotLeisure.RESOURCE_ROOT_ID;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.science.gtnl.Utils.item.MetaItemStackUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.render.IHaloRenderItem;

/**
 * An ItemStack Generator used Meta Item System.
 * <li>Use {@link ItemAdder#initItem(String, int)} to create your Item at ItemList01.
 *
 */
public class ItemAdder extends ItemAdder_Basic implements IHaloRenderItem {

    /**
     * An Item Map for managing basic items
     */
    // public static Map<String, ItemAdder> Item01Map = new HashMap<>();

    /**
     * A Set contains the meta value that has been used.
     */
    public static final Set<Integer> MetaSet = new HashSet<>();
    public static final Map<Integer, String[]> MetaItemTooltipsMap = new HashMap<>();

    public final String unlocalizedName;
    public IIcon[] halo;

    /**
     * Create the basic item MetaItem.
     */
    public ItemAdder(String aName, String aMetaName, CreativeTabs aCreativeTabs) {
        super(aName, aMetaName, aCreativeTabs);
        this.unlocalizedName = aMetaName;
    }

    /**
     * The method about creating Items with ItemStack form by Meta Item System.
     * Use this method to create Items at ReAvaItemList.
     *
     * @param aName The name of your creating item.
     * @param aMeta The MetaValue of your creating item.
     * @return Return the Item with ItemStack form you create.
     */
    public static ItemStack initItem(String aName, int aMeta) {

        return MetaItemStackUtils.initMetaItemStack(aName, aMeta, BasicItems.MetaItem, MetaSet);

    }

    public static ItemStack initItem(String aName, int aMeta, String[] tooltips) {

        if (tooltips != null) {
            MetaItemStackUtils.metaItemStackTooltipsAdd(MetaItemTooltipsMap, aMeta, tooltips);
        }

        return initItem(aName, aMeta);

    }

    /**
     * Init the basic items at the game pre init.
     */
    // public static void init() {
    // for (String MetaName : Item01Map.keySet()) {
    // GameRegistry.registerItem(Item01Map.get(MetaName), MetaName);
    // }
    // }

    // region Overrides

    @Override
    public String getUnlocalizedName(ItemStack aItemStack) {
        return this.unlocalizedName + "." + aItemStack.getItemDamage();
    }

    @Override
    public String getUnlocalizedName() {
        return this.unlocalizedName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
        this.itemIcon = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "MetaItem/0");
        for (int meta : MetaSet) {
            ItemStaticDataClientOnly.iconsMap01
                .put(meta, iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "MetaItem/" + meta));
        }
        halo = new IIcon[4];
        halo[0] = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "CompressionHalo");
        halo[1] = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "CompressionHaloCyan");
        halo[2] = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "CompressionHaloCyanFaded");
        halo[3] = iconRegister.registerIcon(RESOURCE_ROOT_ID + ":" + "CompressionHaloSol");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int aMetaData) {
        return ItemStaticDataClientOnly.iconsMap01.containsKey(aMetaData)
            ? ItemStaticDataClientOnly.iconsMap01.get(aMetaData)
            : ItemStaticDataClientOnly.iconsMap01.get(0);
    }

    /**
     * Handle the tooltips.
     *
     * @param aItemStack
     * @param theTooltipsList
     */
    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings({ "unchecked" })
    public void addInformation(ItemStack aItemStack, EntityPlayer p_77624_2_, List theTooltipsList,
        boolean p_77624_4_) {
        int meta = aItemStack.getItemDamage();
        if (null != MetaItemTooltipsMap.get(meta)) {
            String[] tooltips = MetaItemTooltipsMap.get(meta);
            theTooltipsList.addAll(Arrays.asList(tooltips));
        }
    }

    /**
     * Override this method to show all ItemStack of MetaItem.
     *
     * @param aItem
     * @param aCreativeTabs
     * @param aList
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item aItem, CreativeTabs aCreativeTabs, List aList) {
        for (int Meta : MetaSet) {
            aList.add(new ItemStack(BasicItems.MetaItem, 1, Meta));
        }
    }

    @Override
    public boolean drawHalo(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 26, 27 -> true;
            default -> false;
        };
    }

    @Override
    public IIcon getHaloTexture(ItemStack stack) {
        return switch (stack.getItemDamage()) {
            case 26 -> halo[0];
            case 27 -> halo[3];
            default -> halo[0];
        };
    }

    @Override
    public int getHaloSize(ItemStack stack) {
        return 10;
    }

    @Override
    public boolean drawPulseEffect(ItemStack stack) {
        return false;
    }

    @Override
    public int getHaloColour(ItemStack stack) {
        return 0xE6FFFFFF;
    }
}
