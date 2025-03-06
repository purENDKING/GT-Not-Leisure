package com.science.gtnl.common.block.ReAvaritia;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil.ContainerExtremeAnvil;
import com.science.gtnl.common.block.ReAvaritia.ExtremeAnvil.ExtremeAnvilGUI;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.ContainerNeutronItem;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.NeutronCollectorGUI;
import com.science.gtnl.common.block.ReAvaritia.NeutronCollector.TileEntityNeutronCollector;

import cpw.mods.fml.common.network.IGuiHandler;

public class GooeyHandler implements IGuiHandler {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0)
            return new NeutronCollectorGUI(player.inventory, (TileEntityNeutronCollector) world.getTileEntity(x, y, z));
        if (ID == 1) return new ExtremeAnvilGUI(player.inventory, world, x, y, z);
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new ContainerNeutronItem(
            player.inventory,
            (TileEntityNeutronCollector) world.getTileEntity(x, y, z));
        if (ID == 1) return new ContainerExtremeAnvil(player.inventory, world, x, y, z, player);
        return null;
    }
}
