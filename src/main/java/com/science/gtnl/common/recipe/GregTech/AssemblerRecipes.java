package com.science.gtnl.common.recipe.GregTech;

import static com.science.gtnl.Mods.ScienceNotLeisure;
import static com.science.gtnl.loader.IScriptLoader.missing;
import static gregtech.api.enums.Mods.*;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.dreammaster.gthandler.CustomItemList;
import com.science.gtnl.common.GTNLItemList;
import com.science.gtnl.common.block.Casings.BasicBlocks;
import com.science.gtnl.common.materials.MaterialPool;
import com.science.gtnl.common.recipe.IRecipePool;

import bartworks.common.loaders.ItemRegistry;
import bartworks.system.material.WerkstoffLoader;
import goodgenerator.util.ItemRefer;
import gregtech.api.GregTechAPI;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.MaterialsBotania;
import gregtech.api.enums.MaterialsUEVplus;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;
import gtPlusPlus.core.item.ModItems;
import gtPlusPlus.core.item.crafting.ItemDummyResearch;
import gtPlusPlus.core.material.MaterialMisc;
import gtPlusPlus.core.material.MaterialsAlloy;
import gtPlusPlus.core.material.MaterialsElements;
import gtPlusPlus.core.util.minecraft.ItemUtils;
import gtPlusPlus.xmod.gregtech.api.enums.GregtechItemList;

public class AssemblerRecipes implements IRecipePool {

    final RecipeMap<?> As = RecipeMaps.assemblerRecipes;

    @Override
    public void loadRecipes() {

        ItemStack MEoutputBus = ItemList.Hatch_Output_Bus_ME.get(1L);
        NBTTagCompound MEoutputBusType = MEoutputBus.getTagCompound();
        if (MEoutputBusType != null) {
            MEoutputBusType.setLong("baseCapacity", 9223372036854775807L);
        } else {
            MEoutputBusType = new NBTTagCompound();
            MEoutputBusType.setLong("baseCapacity", 9223372036854775807L);
            MEoutputBus.setTagCompound(MEoutputBusType);
        }

        ItemStack MEoutputHatch = ItemList.Hatch_Output_ME.get(1L);
        NBTTagCompound MEoutputHatchType = MEoutputHatch.getTagCompound();
        if (MEoutputHatchType != null) {
            MEoutputHatchType.setLong("baseCapacity", 9223372036854775807L);
        } else {
            MEoutputHatchType = new NBTTagCompound();
            MEoutputHatchType.setLong("baseCapacity", 9223372036854775807L);
            MEoutputHatch.setTagCompound(MEoutputHatchType);
        }

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Battery_RE_ULV_Tantalum.get(4L),
                ItemList.Circuit_Parts_Wiring_Basic.get(4L),
                ItemList.Circuit_Parts_Coil.get(4L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 1, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gem, 1),
                ItemList.Circuit_Primitive.get(1L))
            .fluidInputs(Materials.Tin.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 9))
            .specialValue(0)
            .noOptimize()
            .duration(50)
            .eut(30)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_Advanced.get(4L),
                ItemList.Circuit_Parts_Wiring_Elite.get(4L),
                ItemList.Circuit_Parts_Wiring_Advanced.get(4L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 1, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gem, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 9))
            .fluidInputs(Materials.Tin.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 10))
            .specialValue(0)
            .noOptimize()
            .duration(90)
            .eut(120)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_Advanced.get(8L),
                ItemList.Circuit_Parts_Wiring_Elite.get(8L),
                ItemList.Circuit_Parts_Wiring_Advanced.get(8L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 1, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gem, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 10))
            .fluidInputs(Materials.Tin.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 11))
            .specialValue(0)
            .noOptimize()
            .duration(150)
            .eut(480)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_DiodeSMD.get(4L),
                ItemList.Circuit_Parts_TransistorSMD.get(4L),
                ItemList.Circuit_Parts_CapacitorSMD.get(4L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 2, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemFlawless, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 11))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 12))
            .specialValue(0)
            .noOptimize()
            .duration(230)
            .eut(1920)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_DiodeSMD.get(8L),
                ItemList.Circuit_Parts_TransistorSMD.get(8L),
                ItemList.Circuit_Parts_CapacitorSMD.get(8L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 4, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemFlawless, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 12))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 13))
            .specialValue(0)
            .noOptimize()
            .duration(330)
            .eut(7680)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_DiodeASMD.get(4L),
                ItemList.Circuit_Parts_TransistorASMD.get(4L),
                ItemList.Circuit_Parts_CapacitorASMD.get(4L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 4, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemFlawless, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 13))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 14))
            .specialValue(0)
            .noOptimize()
            .duration(450)
            .eut(30720)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_DiodeASMD.get(8L),
                ItemList.Circuit_Parts_TransistorASMD.get(8L),
                ItemList.Circuit_Parts_CapacitorASMD.get(8L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 4, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemFlawless, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 14))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 15))
            .specialValue(0)
            .noOptimize()
            .duration(570)
            .eut(122880)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.BiowareSMDDiode.get(16),
                GTNLItemList.BiowareSMDCapacitor.get(16),
                GTNLItemList.BiowareSMDTransistor.get(16),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 8, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemExquisite, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 15))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(144))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 16))
            .specialValue(0)
            .noOptimize()
            .duration(710)
            .eut(491520)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Parts_DiodeXSMD.get(16L),
                ItemList.Circuit_Parts_TransistorXSMD.get(16L),
                ItemList.Circuit_Parts_CapacitorXSMD.get(16L),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 8, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemExquisite, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 16))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(288))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 17))
            .specialValue(0)
            .noOptimize()
            .duration(730)
            .eut(1966080)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.ExoticSMDDiode.get(16),
                GTNLItemList.ExoticSMDCapacitor.get(16),
                GTNLItemList.ExoticSMDTransistor.get(16),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 8, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemExquisite, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 17))
            .fluidInputs(MaterialMisc.MUTATED_LIVING_SOLDER.getFluidStack(432))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 18))
            .specialValue(0)
            .noOptimize()
            .duration(750)
            .eut(7864320)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.CosmicSMDDiode.get(16),
                GTNLItemList.CosmicSMDCapacitor.get(16),
                GTNLItemList.CosmicSMDTransistor.get(16),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 8, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemExquisite, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 18))
            .fluidInputs(MaterialPool.SuperMutatedLivingSolder.getFluidOrGas(288))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 19))
            .specialValue(0)
            .noOptimize()
            .duration(770)
            .eut(31457280)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.TemporallySMDDiode.get(16),
                GTNLItemList.TemporallySMDCapacitor.get(16),
                GTNLItemList.TemporallySMDTransistor.get(16),
                GTModHandler.getModItem(BartWorks.ID, "gt.bwMetaGeneratedItem0", 8, 3),
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.gemExquisite, 1),
                GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 1, 19))
            .fluidInputs(MaterialPool.SuperMutatedLivingSolder.getFluidOrGas(432))
            .itemOutputs(GTModHandler.getModItem(ScienceNotLeisure.ID, "MetaItem", 4, 20))
            .specialValue(0)
            .noOptimize()
            .duration(790)
            .eut(125829120)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 8),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Silicon, 8),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.Naquadah, 4))
            .fluidInputs(MaterialPool.Polyimide.getFluidOrGas(288))
            .itemOutputs(GTNLItemList.BiowareSMDCapacitor.get(16))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(491520)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 8),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Lutetium, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.Tritanium, 1))
            .fluidInputs(MaterialPool.Polyimide.getFluidOrGas(288))
            .itemOutputs(GTNLItemList.BiowareSMDDiode.get(16))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(491520)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 8),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Naquadria, 1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Tritanium, 1))
            .fluidInputs(MaterialPool.Polyimide.getFluidOrGas(288))
            .itemOutputs(GTNLItemList.BiowareSMDResistor.get(16))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(491520)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 8),
                MaterialPool.Germaniumtungstennitride.get(OrePrefixes.plate, 2),
                MaterialsAlloy.SILICON_CARBIDE.getPlate(2))
            .fluidInputs(MaterialPool.Polyimide.getFluidOrGas(288))
            .itemOutputs(GTNLItemList.BiowareSMDTransistor.get(16))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(491520)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.NaquadahAlloy, 8),
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.NaquadahAlloy, 1))
            .fluidInputs(MaterialPool.Polyimide.getFluidOrGas(288))
            .itemOutputs(GTNLItemList.BiowareSMDInductor.get(16))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(491520)
            .requiresCleanRoom()
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, ItemList.Circuit_Primitive.get(1L)),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                GTModHandler.getModItem(Minecraft.ID, "redstone", 1, 0))
            .fluidInputs(FluidRegistry.getFluidStack("refinedglue", 20))
            .itemOutputs(GTNLItemList.VerySimpleCircuit.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(40)
            .eut(7)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuit", 1)),
                GTNLItemList.VerySimpleCircuit.get(1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Iron, 1),
                GTOreDictUnificator.get(OrePrefixes.dust, Materials.RedAlloy, 1))
            .fluidInputs(FluidRegistry.getFluidStack("refinedglue", 20))
            .itemOutputs(GTNLItemList.SimpleCircuit.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(80)
            .eut(16)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, ItemList.Circuit_Good.get(1L)),
                GTModHandler.getModItem(Minecraft.ID, "paper", 1, 0),
                GTNLItemList.SimpleCircuit.get(2),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 1),
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Diamond, 1))
            .fluidInputs(FluidRegistry.getFluidStack("refinedglue", 20))
            .itemOutputs(GTNLItemList.BasicCircuit.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(160)
            .eut(30)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.copyAmount(0, GTModHandler.getModItem(IndustrialCraft2.ID, "itemPartCircuitAdv", 1)),
                ItemList.Circuit_Board_Coated_Basic.get(1L),
                GTNLItemList.BasicCircuit.get(1),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 1),
                GTOreDictUnificator.get(OrePrefixes.dustSmall, Materials.Obsidian, 1),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.RedAlloy, 1))
            .fluidInputs(FluidRegistry.getFluidStack("refinedglue", 20))
            .itemOutputs(GTNLItemList.AdvancedCircuit.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(80)
            .eut(120)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Circuit_Board_Phenolic_Good.get(1L),
                GTNLItemList.AdvancedCircuit.get(1),
                GTOreDictUnificator.get(OrePrefixes.foil, Materials.RedAlloy, 8))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .itemOutputs(GTNLItemList.EliteCircuit.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(GTPlusPlus.ID, "blockCompressedObsidian", 1, 6),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 4),
                GTModHandler.getModItem(ExtraUtilities.ID, "decorativeBlock2", 1, 7))
            .itemOutputs(GTNLItemList.FortifyGlowstone.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(Botania.ID, "pylon", 1, 2),
                GTModHandler.getModItem(Botania.ID, "pylon", 2, 1),
                GTModHandler.getModItem(Botania.ID, "pylon", 4, 0),
                GTModHandler.getModItem(Botania.ID, "manaResource", 16, 7),
                GTModHandler.getModItem(Botania.ID, "manaResource", 16, 8))
            .itemOutputs(GTNLItemList.ActivatedGaiaPylon.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                ItemList.Hatch_Input_LuV.get(1L),
                GTOreDictUnificator.get(OrePrefixes.plate, MaterialsBotania.Terrasteel, 8),
                GTModHandler.getModItem(Botania.ID, "pylon", 4, 1),
                GTModHandler.getModItem(Botania.ID, "pool", 1, 3),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2L))
            .itemOutputs(GTNLItemList.FluidManaInputHatch.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(30720)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(2),
                ItemList.OreDrill2.get(1L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 8),
                new ItemStack(GregTechAPI.sLaserRender, 4, 0),
                ItemList.Emitter_EV.get(2L),
                ItemList.Sensor_EV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.gear, Materials.TungstenSteel, 4),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 4),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2L))
            .fluidInputs(Materials.SolderingAlloy.getMolten(9216))
            .itemOutputs(GTNLItemList.MeteorMiner.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(1200)
            .eut(30720)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Casing_Coil_Superconductor.get(1L),
                com.dreammaster.item.ItemList.LaserEmitter.getIS(4),
                GTOreDictUnificator.get(OrePrefixes.lens, Materials.Diamond, 4),
                new ItemStack(ModItems.itemDehydratorCoilWire, 8, 2),
                ItemList.LuV_Coil.get(4L),
                ItemList.Emitter_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 16))
            .fluidInputs(Materials.SolderingAlloy.getMolten(9216))
            .itemOutputs(GTNLItemList.LaserBeacon.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(30720)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_IV_Assembler.get(4L),
                ItemRefer.Precise_Assembler.get(2),
                ItemList.Machine_Multi_Assemblyline.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 16),
                ItemList.Robot_Arm_IV.get(8L),
                ItemList.Casing_Gearbox_TungstenSteel.get(8L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 8L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 10L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 12L))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(256000))
            .itemOutputs(GTNLItemList.ComponentAssembler.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(600)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_IV.get(4L),
                ItemList.Casing_Autoclave.get(16L),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Desh, 32),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 8L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4L),
                ItemList.Super_Tank_EV.get(4L),
                ItemList.Electric_Motor_IV.get(4L),
                ItemList.Electric_Pump_IV.get(8L),
                ItemList.Steam_Valve_IV.get(16L))
            .fluidInputs(MaterialsAlloy.INDALLOY_140.getFluidStack(64000))
            .itemOutputs(GTNLItemList.PetrochemicalPlant.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(1200)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_EV.get(1L),
                ItemList.Robot_Arm_EV.get(16L),
                ItemList.Conveyor_Module_EV.get(4L),
                ItemList.Electric_Motor_EV.get(4L),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 32),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 8L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L),
                ItemList.Casing_StableTitanium.get(4L),
                GregtechItemList.GTPP_Casing_EV.get(8L))
            .fluidInputs(Materials.SolderingAlloy.getMolten(18432))
            .itemOutputs(GTNLItemList.ProcessingArray.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(600)
            .eut(1920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Bronze, 4L),
                GTNLItemList.PrecisionSteamMechanism.get(4),
                ItemList.Machine_Bricked_BlastFurnace.get(1L))
            .itemOutputs(GTNLItemList.BrickedBlastFurnace.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(600)
            .eut(16)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_EV_CircuitAssembler.get(2),
                ItemList.Robot_Arm_EV.get(2),
                GTOreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2L),
                ItemList.Conveyor_Module_EV.get(2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L))
            .itemOutputs(GTNLItemList.LargeCircuitAssembler.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_HV.get(1),
                CustomItemList.AdsorptionFilter.get(1),
                ItemList.Electric_Pump_HV.get(2),
                ItemList.Electric_Motor_HV.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                GTOreDictUnificator.get(OrePrefixes.wireGt08, Materials.Electrum, 2L))
            .itemOutputs(GTNLItemList.Desulfurizer.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.LEAGRISIUM.getPlateDouble(2),
                GregtechItemList.Casing_AdvancedVacuum.get(1),
                MaterialsAlloy.INCOLOY_MA956.getGear(2),
                ItemList.Electric_Piston_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1L),
                GregtechItemList.Gregtech_Computer_Cube.get(1L))
            .itemOutputs(GregtechItemList.Industrial_Cryogenic_Freezer.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(32070)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialsAlloy.HASTELLOY_N.getPlateDouble(2),
                GregtechItemList.Casing_Adv_BlastFurnace.get(1),
                MaterialsAlloy.HASTELLOY_W.getGear(2),
                ItemList.Robot_Arm_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 1L),
                GregtechItemList.Gregtech_Computer_Cube.get(1L))
            .itemOutputs(GregtechItemList.Machine_Adv_BlastFurnace.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(32070)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Cryogenic_Freezer.get(4),
                GregtechItemList.Casing_AdvancedVacuum.get(4),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.HSSE, 16L),
                ItemList.Electric_Motor_IV.get(16L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2L),
                MaterialsAlloy.INCOLOY_MA956.getGear(8),
                MaterialsAlloy.LEAGRISIUM.getPlateDouble(16),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 8L),
                GregtechItemList.Gregtech_Computer_Cube.get(1L))
            .itemOutputs(GTNLItemList.ColdIceFreezer.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(4608))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(32070)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Machine_Adv_BlastFurnace.get(4),
                GregtechItemList.Casing_Adv_BlastFurnace.get(4),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorLuV, 16L),
                ItemList.Robot_Arm_IV.get(4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 2L),
                MaterialsAlloy.INCOLOY_MA956.getGear(8),
                MaterialsAlloy.HASTELLOY_N.getPlateDouble(16),
                GregtechItemList.Gregtech_Computer_Cube.get(1L))
            .itemOutputs(GTNLItemList.BlazeBlastFurnace.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(4608))
            .specialValue(0)
            .noOptimize()
            .duration(120)
            .eut(32070)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_Multi_LargeChemicalReactor.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 4L),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.SuperconductorEV, 4L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Polytetrafluoroethylene, 4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                ItemList.Electric_Motor_EV.get(4L),
                ItemList.Electric_Pump_EV.get(4L),
                WerkstoffLoader.Ruridit.get(OrePrefixes.rotor, 4))
            .itemOutputs(GTNLItemList.ChemicalPlant.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(1920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Extruder.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TPV, 4L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Polytetrafluoroethylene, 4L),
                MaterialsAlloy.INCONEL_625.getPlateDouble(4),
                ItemList.Electric_Piston_IV.get(2L),
                ItemList.Conveyor_Module_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 1L))
            .itemOutputs(GTNLItemList.LargeExtruder.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_CuttingFactoryController.get(1L),
                GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 4, 0, missing),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Platinum, 4L),
                WerkstoffLoader.Ruridit.get(OrePrefixes.plateDouble, 4),
                ItemList.Electric_Motor_IV.get(2L),
                ItemList.Conveyor_Module_IV.get(2L),
                ItemList.Component_Sawblade_Diamond.get(2L))
            .itemOutputs(GTNLItemList.LargeCutter.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_MacerationStack.get(1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Platinum, 4L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 4L),
                ItemList.Electric_Motor_IV.get(2L),
                ItemList.Electric_Piston_IV.get(2L),
                ItemList.Component_Grinder_Tungsten.get(4L))
            .itemOutputs(GTNLItemList.LargeMacerationTower.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Arc_Furnace.get(1L),
                GTModHandler.getModItem(GTPlusPlus.ID, "item.itemBufferCore6", 2),
                ItemList.Field_Generator_IV.get(1L),
                ItemList.Emitter_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                MaterialsAlloy.ZERON_100.getPlateDense(2),
                GTOreDictUnificator.get(OrePrefixes.cableGt08, Materials.HSSG, 4L))
            .itemOutputs(GTNLItemList.LargeArcSmelter.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Sifter.get(1L),
                MaterialsAlloy.INCONEL_792.getFrameBox(4),
                GregtechItemList.Casing_SifterGrate.get(4),
                ItemList.Electric_Piston_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Osmiridium, 4L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Osmium, 4L))
            .itemOutputs(GTNLItemList.LargeSiftingFunnel.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Casing_CokeOven.get(4L),
                ItemList.Machine_IV_Brewery.get(1L),
                ItemList.Machine_IV_Fermenter.get(1L),
                ItemList.Machine_IV_FluidHeater.get(1L),
                GTNLItemList.FusionGlass.get(8),
                ItemList.Electric_Pump_IV.get(2L),
                MaterialsAlloy.TANTALLOY_61.getPlateDouble(16),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 8L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L))
            .itemOutputs(GTNLItemList.LargeBrewer.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_Multi_Lathe.get(1L),
                MaterialsAlloy.AQUATIC_STEEL.getFrameBox(4),
                ItemList.Electric_Motor_IV.get(2L),
                ItemList.Electric_Piston_IV.get(2L),
                WerkstoffLoader.AdemicSteel.get(OrePrefixes.plateDouble, 4),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Platinum, 4L))
            .itemOutputs(GTNLItemList.LargeIndustrialLathe.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_Multi_IndustrialCompressor.get(1L),
                MaterialsAlloy.MARAGING350.getFrameBox(4),
                ItemList.Electric_Piston_IV.get(4L),
                ItemList.Robot_Arm_IV.get(2L),
                WerkstoffLoader.AdemicSteel.get(OrePrefixes.plateDouble, 4),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Osmiridium, 4L))
            .itemOutputs(GTNLItemList.LargeMaterialPress.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_WireFactory.get(1L),
                MaterialsAlloy.MARAGING350.getFrameBox(4),
                ItemList.Electric_Motor_IV.get(4L),
                ItemList.Electric_Piston_IV.get(4L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.BlueSteel, 4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Platinum, 4L))
            .itemOutputs(GTNLItemList.LargeWiremill.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_PlatePress.get(1L),
                MaterialsAlloy.MARAGING300.getFrameBox(4),
                ItemList.Electric_Motor_IV.get(4L),
                ItemList.Electric_Piston_IV.get(4L),
                ItemList.Conveyor_Module_IV.get(4L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.RedSteel, 4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Tungsten, 4L))
            .itemOutputs(GTNLItemList.LargeBender.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_PlatePress.get(1L),
                MaterialsAlloy.MARAGING250.getFrameBox(4),
                ItemList.Electric_Motor_IV.get(4L),
                ItemList.Electric_Piston_IV.get(4L),
                ItemList.Robot_Arm_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Invar, 4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.TungstenSteel, 4L))
            .itemOutputs(GTNLItemList.LargeForming.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Electrolyzer.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 4, 0, missing),
                ItemList.Electric_Pump_IV.get(2L),
                MaterialsAlloy.STELLITE.getPlateDouble(4),
                MaterialsAlloy.STELLITE.getRotor(8),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Osmium, 4L))
            .itemOutputs(GTNLItemList.LargeElectrolyzer.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_Multi_IndustrialElectromagneticSeparator.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                ItemList.Conveyor_Module_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.VanadiumGallium, 8L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Osmium, 4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                ItemList.Electromagnet_Iron.get(1),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Platinum, 4L))
            .itemOutputs(GTNLItemList.LargeElectromagnet.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_IV_Assembler.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                ItemUtils.getSimpleStack(ModItems.itemCircuitLFTR, 1),
                GregtechItemList.TransmissionComponent_IV.get(4),
                ItemList.Electric_Motor_IV.get(8L),
                ItemList.Conveyor_Module_IV.get(8L),
                ItemList.Robot_Arm_IV.get(8L),
                GregtechItemList.Gregtech_Computer_Cube.get(2),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L))
            .itemOutputs(GTNLItemList.LargeAssembler.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Mixer.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Naquadah, 4L),
                GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 4, 0, missing),
                ItemList.Electric_Motor_IV.get(4L),
                MaterialsAlloy.ZIRCONIUM_CARBIDE.getPlateDouble(4),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 8L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.Osmium, 4L))
            .itemOutputs(GTNLItemList.LargeMixer.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_Centrifuge.get(1L),
                GregtechItemList.Industrial_ThermalCentrifuge.get(1L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Tungsten, 4L),
                GTOreDictUnificator.get(OrePrefixes.pipeHuge, Materials.Platinum, 2L),
                ItemList.Electric_Motor_IV.get(4L),
                MaterialsAlloy.INCOLOY_DS.getPlateDouble(8),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.Naquadah, 4L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 2L),
                GTOreDictUnificator.get(OrePrefixes.wireGt04, Materials.Platinum, 4L))
            .itemOutputs(GTNLItemList.LargeCentrifuge.get(1))
            .fluidInputs(Materials.Polybenzimidazole.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Neutronium, 4L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Neutronium, 4L),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                GTUtility.getIntegratedCircuit(12))
            .itemOutputs(GTNLItemList.NeutroniumPipeCasing.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(30)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Neutronium, 2L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Neutronium, 4L),
                GTUtility.getIntegratedCircuit(3))
            .itemOutputs(GTNLItemList.NeutroniumGearbox.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(122880)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Europium, 8L),
                ItemList.Electric_Motor_UHV.get(16L),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.HSSS, 16L),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.Infinity, 8L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSE, 16L),
                GTOreDictUnificator.get(OrePrefixes.wireFine, Materials.Naquadria, 64L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UHV, 4L),
                MaterialsAlloy.STELLITE.getPlate(32))
            .fluidInputs(Materials.SolderingAlloy.getMolten(1296))
            .itemOutputs(GTNLItemList.RareEarthCentrifugal.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(1000)
            .eut(7864320)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(6),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.StainlessSteel, 2L),
                ItemList.Casing_SolidSteel.get(1L),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1L),
                ItemList.Electric_Motor_HV.get(2L))
            .fluidInputs(Materials.Polytetrafluoroethylene.getMolten(576))
            .itemOutputs(GTNLItemList.HeatVent.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Machine_Multi_VacuumFreezer.get(1L),
                ItemList.Casing_FrostProof.get(2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Gold, 4L),
                ItemList.Electric_Motor_HV.get(2L),
                ItemList.Electric_Pump_HV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 2L))
            .itemOutputs(GTNLItemList.VacuumFreezer.get(1))
            .fluidInputs(Materials.Aluminium.getMolten(1152))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Casing_ZPM.get(1L),
                MaterialsAlloy.ABYSSAL.getPlateDouble(16),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 4),
                ItemList.Field_Generator_UV.get(1L),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 64),
                GTOreDictUnificator.get(OrePrefixes.stickLong, Materials.BlackPlutonium, 32),
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Infinity, 2))
            .fluidInputs(new FluidStack(MaterialsElements.STANDALONE.CELESTIAL_TUNGSTEN.getPlasma(), 16000))
            .itemOutputs(GTNLItemList.Antifreeze_Heatproof_Machine_Casing.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(1966080)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.block, Materials.BlackPlutonium, 4),
                GTOreDictUnificator.get(OrePrefixes.block, Materials.CosmicNeutronium, 12),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Bedrockium, 8),
                GTModHandler.getModItem(Avaritia.ID, "Crystal_Matrix", 4),
                ItemList.Electric_Motor_UV.get(2L),
                ItemList.Sensor_UV.get(4L),
                ItemList.Emitter_UV.get(4L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Infinity, 4),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.UV, 4))
            .itemOutputs(new ItemStack(Item.getItemFromBlock(BasicBlocks.NeutronCollector), 1))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(491520)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                MaterialPool.MolybdenumDisilicide.get(OrePrefixes.ring, 32),
                CustomItemList.MicaInsulatorFoil.get(16L))
            .fluidInputs(MaterialPool.HSLASteel.getMolten(144))
            .itemOutputs(GTNLItemList.MolybdenumDisilicideCoil.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(500)
            .eut(1920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                new ItemStack(Item.getItemFromBlock(ItemRegistry.bw_realglas), 1, 4),
                ItemList.Casing_Fusion.get(1),
                GTOreDictUnificator.get(OrePrefixes.plateDense, Materials.Naquadah, 1))
            .fluidInputs(Materials.NiobiumTitanium.getMolten(1152))
            .itemOutputs(GTNLItemList.FusionGlass.get(4))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(30720)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(EnderIO.ID, "blockSolarPanel", 1, 0),
                GTModHandler.getModItem(EnderIO.ID, "itemPowerConduit", 4, 0),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 4L),
                GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.RedAlloy, 4L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 2L))
            .itemOutputs(GTNLItemList.EnergeticPhotovoltaicBlock.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(30)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(EnderIO.ID, "blockSolarPanel", 1, 1),
                GTModHandler.getModItem(EnderIO.ID, "itemPowerConduit", 8, 0),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 8L),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.ElectricalSteel, 4L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 2L))
            .itemOutputs(GTNLItemList.AdvancedPhotovoltaicBlock.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(120)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTModHandler.getModItem(EnderIO.ID, "blockSolarPanel", 1, 2),
                GTModHandler.getModItem(EnderIO.ID, "itemPowerConduit", 16, 0),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.ULV, 16L),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.VibrantAlloy, 4L),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 2L))
            .itemOutputs(GTNLItemList.VibrantPhotovoltaicBlock.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenCarbide, 3L),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Ultimet, 4L),
                ItemList.Casing_MiningOsmiridium.get(1L),
                ItemList.Electric_Motor_IV.get(1L))
            .itemOutputs(GTNLItemList.CrushingWheels.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(50)
            .eut(16)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenCarbide, 3L),
                GTOreDictUnificator.get(OrePrefixes.gearGt, Materials.Ultimet, 4L),
                GregtechItemList.Casing_CuttingFactoryFrame.get(1L),
                ItemList.Electric_Motor_IV.get(1L))
            .itemOutputs(GTNLItemList.SlicingBlades.get(2))
            .specialValue(0)
            .noOptimize()
            .duration(50)
            .eut(16)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hatch_Output_Bus_EV.get(1L),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 30))
            .itemOutputs(MEoutputBus)
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hatch_Output_EV.get(1L),
                GTModHandler.getModItem(AE2FluidCraft.ID, "part_fluid_interface", 1),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 2, 30))
            .itemOutputs(MEoutputHatch)
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hatch_Input_IV.get(1L),
                GTModHandler.getModItem(AE2FluidCraft.ID, "part_fluid_interface", 1),
                ItemList.Electric_Pump_IV.get(1),
                ItemList.Sensor_IV.get(1),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 30))
            .itemOutputs(ItemList.Hatch_Input_ME_Advanced.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hatch_Input_Bus_IV.get(1L),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiPart", 1, 440),
                ItemList.Conveyor_Module_IV.get(1),
                ItemList.Sensor_IV.get(1),
                GTModHandler.getModItem(AppliedEnergistics2.ID, "item.ItemMultiMaterial", 4, 30))
            .itemOutputs(ItemList.Hatch_Input_Bus_ME_Advanced.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Steel, 1L),
                ItemList.Hatch_Input_Bus_LV.get(1L),
                ItemList.Hatch_Input_LV.get(1L),
                ItemList.Automation_ChestBuffer_LV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Bronze, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchLV.get(1))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(30)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Aluminium, 1L),
                ItemList.Hatch_Input_Bus_MV.get(1L),
                ItemList.Hatch_Input_MV.get(1L),
                ItemList.Automation_ChestBuffer_MV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Steel, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchMV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(120)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1L),
                ItemList.Hatch_Input_Bus_HV.get(1L),
                ItemList.Hatch_Input_HV.get(1L),
                ItemList.Automation_ChestBuffer_HV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.StainlessSteel, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchHV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Titanium, 1L),
                ItemList.Hatch_Input_Bus_EV.get(1L),
                ItemList.Hatch_Input_EV.get(1L),
                ItemList.Automation_ChestBuffer_EV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Titanium, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchEV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(1920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1L),
                ItemList.Hatch_Input_Bus_IV.get(1L),
                ItemList.Hatch_Input_IV.get(1L),
                ItemList.Automation_ChestBuffer_IV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.TungstenSteel, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchIV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Iridium, 1L),
                ItemList.Hatch_Input_Bus_LuV.get(1L),
                ItemList.Hatch_Input_LuV.get(1L),
                ItemList.Automation_ChestBuffer_LuV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.NiobiumTitanium, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchLuV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(30720)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1L),
                ItemList.Hatch_Input_Bus_ZPM.get(1L),
                ItemList.Hatch_Input_ZPM.get(1L),
                ItemList.Automation_ChestBuffer_ZPM.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Naquadah, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchZPM.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(122880)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Osmium, 1L),
                ItemList.Hatch_Input_Bus_UV.get(1L),
                ItemList.Hatch_Input_UV.get(1L),
                ItemList.Automation_ChestBuffer_UV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.MysteriousCrystal, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchUV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(491520)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Neutronium, 1L),
                GregtechItemList.Hatch_SuperBus_Input_EV.get(1L),
                ItemList.Hatch_Input_UHV.get(1L),
                ItemList.Automation_ChestBuffer_UHV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Neutronium, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchUHV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(1966080)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Bedrockium, 1L),
                GregtechItemList.Hatch_SuperBus_Input_IV.get(1L),
                ItemList.Hatch_Input_UEV.get(1L),
                ItemList.Automation_ChestBuffer_UEV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, Materials.Infinity, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchUEV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(7864320)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.BlackPlutonium, 1L),
                GregtechItemList.Hatch_SuperBus_Input_LuV.get(1L),
                ItemList.Hatch_Input_UIV.get(1L),
                ItemList.Automation_ChestBuffer_UIV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, MaterialsUEVplus.TranscendentMetal, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchUIV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(31457280)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, MaterialsUEVplus.SpaceTime, 1L),
                GregtechItemList.Hatch_SuperBus_Input_ZPM.get(1L),
                ItemList.Hatch_Input_UMV.get(1L),
                ItemList.Automation_ChestBuffer_UMV.get(1),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, MaterialsUEVplus.SpaceTime, 1L))
            .itemOutputs(GTNLItemList.DualInputHatchUMV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(125829120)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, MaterialsUEVplus.MagMatter, 1L),
                GregtechItemList.Hatch_SuperBus_Input_UV.get(1L),
                ItemList.Hatch_Input_UXV.get(1L),
                ItemList.Automation_ChestBuffer_UMV.get(2),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, MaterialsUEVplus.SpaceTime, 2L))
            .itemOutputs(GTNLItemList.DualInputHatchUXV.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(503316480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator
                    .get(OrePrefixes.frameGt, MaterialsUEVplus.MagnetohydrodynamicallyConstrainedStarMatter, 1L),
                GregtechItemList.Hatch_SuperBus_Input_UV.get(1L),
                ItemList.Hatch_Input_MAX.get(1L),
                ItemList.Automation_ChestBuffer_UMV.get(4),
                GTOreDictUnificator.get(OrePrefixes.pipeNonuple, MaterialsUEVplus.SpaceTime, 4L))
            .itemOutputs(GTNLItemList.DualInputHatchMAX.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(144))
            .specialValue(0)
            .noOptimize()
            .duration(300)
            .eut(2013265920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTNLItemList.LargePyrolyseOven.get(2),
                GTNLItemList.LargeDistillery.get(4),
                ItemList.Emitter_IV.get(4L),
                GTOreDictUnificator.get(OrePrefixes.pipeHuge, Materials.StainlessSteel, 8L),
                ItemList.Electric_Pump_IV.get(8L),
                MaterialsAlloy.AQUATIC_STEEL.getPlate(16),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 32L))
            .itemOutputs(GTNLItemList.WoodDistillation.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemDummyResearch
                    .getResearchStack(ItemDummyResearch.ASSEMBLY_LINE_RESEARCH.RESEARCH_11_MOLECULAR_TRANSFORMER, 1),
                MaterialsAlloy.ZERON_100.getFrameBox(8),
                GTOreDictUnificator.get(OrePrefixes.cableGt16, Materials.Platinum, 8L),
                MaterialsAlloy.BABBIT_ALLOY.getPlate(16),
                MaterialsAlloy.HG1223.getFineWire(32),
                ItemList.Emitter_LuV.get(8L),
                ItemList.Energy_LapotronicOrb.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 8L),
                ItemList.Field_Generator_EV.get(8L))
            .itemOutputs(GTNLItemList.MolecularTransformer.get(1))
            .fluidInputs(MaterialsAlloy.NITINOL_60.getFluidStack(2304))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.PyrolyseOven.get(1),
                ItemList.Field_Generator_HV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.pipeHuge, Materials.StainlessSteel, 2L),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 4L))
            .itemOutputs(GTNLItemList.LargePyrolyseOven.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(576))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                ItemList.Hull_LV.get(1),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Steel, 2L),
                ItemList.Robot_Arm_LV.get(1L),
                ItemList.Emitter_LV.get(1L),
                ItemList.Sensor_LV.get(1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.MV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tin, 2L))
            .itemOutputs(GTNLItemList.LVParallelControllerCore.get(1))
            .fluidInputs(Materials.Cupronickel.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(30)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                ItemList.Hull_MV.get(1),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Aluminium, 2L),
                ItemList.Robot_Arm_MV.get(1L),
                ItemList.Emitter_MV.get(1L),
                ItemList.Sensor_MV.get(1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.HV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.AnnealedCopper, 2L))
            .itemOutputs(GTNLItemList.MVParallelControllerCore.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(120)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                ItemList.Hull_HV.get(1),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.StainlessSteel, 4L),
                ItemList.Electric_Motor_HV.get(1L),
                ItemList.Robot_Arm_HV.get(1L),
                ItemList.Emitter_HV.get(1L),
                ItemList.Sensor_HV.get(1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.EV, 2L),
                GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.BlueAlloy, 2L))
            .itemOutputs(GTNLItemList.HVParallelControllerCore.get(1))
            .fluidInputs(Materials.EnergeticAlloy.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(480)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                ItemList.Hull_EV.get(1),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.Titanium, 4L),
                ItemList.Electric_Motor_EV.get(2L),
                ItemList.Robot_Arm_EV.get(1L),
                ItemList.Emitter_EV.get(1L),
                ItemList.Sensor_EV.get(1L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L),
                GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Aluminium, 2L))
            .itemOutputs(GTNLItemList.EVParallelControllerCore.get(1))
            .fluidInputs(Materials.HSSG.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(1920)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(4),
                ItemList.Hull_IV.get(2),
                GTOreDictUnificator.get(OrePrefixes.plateDouble, Materials.TungstenSteel, 8L),
                ItemList.Electric_Motor_IV.get(2L),
                ItemList.Robot_Arm_IV.get(2L),
                ItemList.Emitter_IV.get(2L),
                ItemList.Sensor_IV.get(2L),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.LuV, 4L),
                GTOreDictUnificator.get(OrePrefixes.cableGt02, Materials.Tungsten, 4L))
            .itemOutputs(GTNLItemList.IVParallelControllerCore.get(1))
            .fluidInputs(Materials.TPV.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(400)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 4L),
                GregtechItemList.GT_MetaTileEntity_Solidifier_I.get(1),
                GTNLItemList.NinefoldInputHatchIV.get(1),
                ItemList.Automation_ChestBuffer_IV.get(4),
                GTOreDictUnificator.get(OrePrefixes.pipeLarge, Materials.TungstenSteel, 4L),
                GTModHandler.getModItem(IndustrialCraft2.ID, "blockAlloyGlass", 1, 0, missing))
            .itemOutputs(GTNLItemList.HumongousSolidifierHatch.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(576))
            .specialValue(0)
            .noOptimize()
            .duration(600)
            .eut(30720)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_IV.get(1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 2L),
                GregtechItemList.Hatch_Input_Cryotheum.get(1),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 2L),
                GregtechItemList.GTFluidTank_HV.get(1),
                MaterialsAlloy.MARAGING250.getPlateDouble(4))
            .itemOutputs(GTNLItemList.FluidIceInputHatch.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                ItemList.Hull_IV.get(1),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 2L),
                GregtechItemList.Hatch_Input_Pyrotheum.get(1),
                GTOreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 2L),
                GregtechItemList.GTFluidTank_HV.get(1),
                MaterialsAlloy.MARAGING350.getPlateDouble(4))
            .itemOutputs(GTNLItemList.FluidBlazeInputHatch.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(1296))
            .specialValue(0)
            .noOptimize()
            .duration(200)
            .eut(7680)
            .addTo(As);

        GTValues.RA.stdBuilder()
            .itemInputs(
                GregtechItemList.Industrial_AlloyBlastSmelter.get(1),
                GTOreDictUnificator.get(OrePrefixes.circuit, Materials.IV, 4L),
                MaterialsAlloy.ZIRCONIUM_CARBIDE.getPlateDouble(8),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSG, 8L),
                GTOreDictUnificator.get(OrePrefixes.cableGt04, Materials.Tungsten, 8L),
                ItemList.IV_Coil.get(2))
            .itemOutputs(GTNLItemList.AlloyBlastSmelter.get(1))
            .fluidInputs(Materials.SolderingAlloy.getMolten(576))
            .specialValue(0)
            .noOptimize()
            .duration(100)
            .eut(7680)
            .addTo(As);
    }
}
