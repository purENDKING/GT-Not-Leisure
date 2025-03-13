package com.science.gtnl.common;

import static gregtech.api.enums.GTValues.NI;

import java.util.Locale;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.science.gtnl.Utils.Utils;
import com.science.gtnl.client.GTNLCreativeTabs;

import gregtech.api.GregTechAPI;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.util.GTLanguageManager;
import gregtech.api.util.GTLog;
import gregtech.api.util.GTUtility;

@SuppressWarnings("unused")
public enum GTNLItemList {

    StargateCoil,
    StargateTier0,
    StargateTier1,
    StargateTier2,
    StargateTier3,
    StargateTier4,
    StargateTier5,
    StargateTier6,
    StargateTier7,
    StargateTier8,
    StargateTier9,
    Stargate_Coil_Compressed,
    LaserBeacon,
    ArtificialStarRender,
    SoulFarmland,
    NeutronCollector,
    DenseNeutronCollector,
    DenserNeutronCollector,
    DensestNeutronCollector,
    ExtremeAnvil,
    PlayerDoll,

    BronzeBrickCasing,
    SteelBrickCasing,
    CrushingWheels,

    TestMetaBlock01_0,
    NewHorizonsCoil,
    BlackLampOff,
    BlackLampOffBorderless,
    PinkLampOff,
    PinkLampOffBorderless,
    RedLampOff,
    RedLampOffBorderless,
    OrangeLampOff,
    OrangeLampOffBorderless,
    YellowLampOff,
    YellowLampOffBorderless,
    GreenLampOff,
    GreenLampOffBorderless,
    LimeLampOff,
    LimeLampOffBorderless,
    BlueLampOff,
    BlueLampOffBorderless,
    LightBlueLampOff,
    LightBlueLampOffBorderless,
    CyanLampOff,
    CyanLampOffBorderless,
    BrownLampOff,
    BrownLampOffBorderless,
    MagentaLampOff,
    MagentaLampOffBorderless,
    PurpleLampOff,
    PurpleLampOffBorderless,
    GrayLampOff,
    GrayLampOffBorderless,
    LightGrayLampOff,
    LightGrayLampOffBorderless,
    WhiteLampOff,
    WhiteLampOffBorderless,
    BlazeCubeBlock,

    TestCasing,
    SteamAssemblyCasing,
    HeatVent,
    SlicingBlades,
    NeutroniumPipeCasing,
    NeutroniumGearbox,
    MolybdenumDisilicideCoil,
    EnergeticPhotovoltaicBlock,
    AdvancedPhotovoltaicBlock,
    VibrantPhotovoltaicBlock,
    TungstensteelGearbox,
    DimensionallyStableCasing,
    PressureBalancedCasing,
    ABSUltraSolidCasing,
    GravitationalFocusingLensBlock,
    GaiaStabilizedForceFieldCasing,
    HyperCore,
    ChemicallyResistantCasing,
    UltraPoweredCasing,

    TrollFace,
    DepletedExcitedNaquadahFuelRod,
    BlazeCube,
    EnhancementCore,
    StellarConstructionFrameMaterial,
    ActivatedGaiaPylon,
    PrecisionSteamMechanism,
    MeteorMinerSchematic1,
    MeteorMinerSchematic2,
    CircuitResonaticULV,
    CircuitResonaticLV,
    CircuitResonaticMV,
    CircuitResonaticHV,
    CircuitResonaticEV,
    CircuitResonaticIV,
    CircuitResonaticLuV,
    CircuitResonaticZPM,
    CircuitResonaticUV,
    CircuitResonaticUHV,
    CircuitResonaticUEV,
    CircuitResonaticUIV,
    VerySimpleCircuit,
    SimpleCircuit,
    BasicCircuit,
    AdvancedCircuit,
    EliteCircuit,
    StargateSingularity,
    StargateCompressedSingularity,
    BiowareSMDCapacitor,
    BiowareSMDDiode,
    BiowareSMDInductor,
    BiowareSMDResistor,
    BiowareSMDTransistor,
    ExoticSMDCapacitor,
    ExoticSMDDiode,
    ExoticSMDInductor,
    ExoticSMDResistor,
    ExoticSMDTransistor,
    TemporallySMDCapacitor,
    TemporallySMDDiode,
    TemporallySMDInductor,
    TemporallySMDResistor,
    TemporallySMDTransistor,
    CosmicSMDCapacitor,
    CosmicSMDDiode,
    CosmicSMDInductor,
    CosmicSMDResistor,
    CosmicSMDTransistor,
    LVParallelControllerCore,
    MVParallelControllerCore,
    HVParallelControllerCore,
    EVParallelControllerCore,
    IVParallelControllerCore,
    LuVParallelControllerCore,
    ZPMParallelControllerCore,
    UVParallelControllerCore,
    UHVParallelControllerCore,
    UEVParallelControllerCore,
    UIVParallelControllerCore,
    UMVParallelControllerCore,
    UXVParallelControllerCore,
    MAXParallelControllerCore,
    NagaBook,
    TwilightForestBook,
    LichBook,
    MinotaurBook,
    HydraBook,
    KnightPhantomBook,
    UrGhastBook,
    AlphaYetiBook,
    SnowQueenBook,
    FinalBook,
    GiantBook,
    ClayedGlowstone,
    QuantumDisk,
    NeutroniumBoule,
    NeutroniumWafer,
    HighlyAdvancedSocWafer,
    HighlyAdvancedSoc,
    ZnFeAlClCatalyst,
    BlackLight,

    FortifyGlowstone,
    GaiaGlass,
    TerraGlass,
    FusionGlass,
    Laser_Cooling_Casing,
    Antifreeze_Heatproof_Machine_Casing,
    BlackLamp,
    BlackLampBorderless,
    PinkLamp,
    PinkLampBorderless,
    RedLamp,
    RedLampBorderless,
    OrangeLamp,
    OrangeLampBorderless,
    YellowLamp,
    YellowLampBorderless,
    GreenLamp,
    GreenLampBorderless,
    LimeLamp,
    LimeLampBorderless,
    BlueLamp,
    BlueLampBorderless,
    LightBlueLamp,
    LightBlueLampBorderless,
    CyanLamp,
    CyanLampBorderless,
    BrownLamp,
    BrownLampBorderless,
    MagentaLamp,
    MagentaLampBorderless,
    PurpleLamp,
    PurpleLampBorderless,
    GrayLamp,
    GrayLampBorderless,
    LightGrayLamp,
    LightGrayLampBorderless,
    WhiteLamp,
    WhiteLampBorderless,

    MegaAlloyBlastSmelter,
    MegaBlastFurnace,
    BrickedBlastFurnace,
    ColdIceFreezer,
    BlazeBlastFurnace,
    ChemicalPlant,
    VacuumFreezer,
    LargeMacerationTower,
    LargeCutter,
    LargeSiftingFunnel,
    LargeIndustrialLathe,
    LargeMaterialPress,
    LargeWiremill,
    LargeBender,
    ElectricImplosionCompressor,
    LargeExtruder,
    LargeArcSmelter,
    LargeForming,
    LargeElectrolyzer,
    LargeElectromagnet,
    LargeAssembler,
    LargeMixer,
    LargeCentrifuge,
    LargeChemicalBath,
    LargeAutoclave,
    LargeSolidifier,
    LargeExtractor,
    EnergyInfuser,
    LargeCanning,
    Digester,
    AlloyBlastSmelter,
    LargeSteamOreWasher,
    LargeHammer,
    IsaMill,
    FlotationCellRegulator,
    VacuumDryingFurnace,
    LargeDistillery,
    Incubator,
    LargeEngravingLaser,
    FishingGround,
    LargePacker,
    LargeAlloySmelter,
    LargePyrolyseOven,
    PreciseAssembler,
    LuvKuangBiaoOneGiantNuclearFusionReactor,
    ZpmKuangBiaoTwoGiantNuclearFusionReactor,
    UvKuangBiaoThreeGiantNuclearFusionReactor,
    UhvKuangBiaoFourGiantNuclearFusionReactor,
    UevKuangBiaoFiveGiantNuclearFusionReactor,
    LargeSteamCentrifuge,
    LargeSteamHammer,
    LargeSteamCompressor,
    LargeBoilerBronze,
    LargeBoilerSteel,
    LargeBoilerTitanium,
    LargeBoilerTungstenSteel,

    LargeSteamFormingPress,
    LargeSteamSifter,
    ResourceCollectionModule,
    FuelRefiningComplex,
    GrandAssemblyLine,
    DecayHastener,
    LargeSteamExtruder,
    DraconicFusionCrafting,
    LargeNaquadahReactor,
    MolecularTransformer,
    WoodDistillation,
    ElementCopying,
    LargeIncubator,
    LargeSteamExtractor,
    ReactionFurnace,
    LibraryOfRuina,
    MatterFabricator,
    LargeBrewer,
    HandOfJohnDavisonRockefeller,
    EnergeticPhotovoltaicPowerStation,
    AdvancedPhotovoltaicPowerStation,
    VibrantPhotovoltaicPowerStation,
    IndustrialArcaneAssembler,
    NineIndustrialMultiMachine,
    RareEarthCentrifugal,
    ProcessingArray,
    MeteorMiner,
    LargeSteamCrusher,
    NeutroniumWireCutting,
    EdenGarden,
    BloodSoulSacrificialArray,
    TeleportationArrayToAlfheim,
    LapotronChip,
    LargeSteamFurnace,
    LargeSteamThermalCentrifuge,
    SteamCracking,
    LargeSteamChemicalBath,
    PrimitiveDistillationTower,
    LargeSteamAlloySmelter,
    ComponentAssembler,
    RealArtificialStar,
    CheatOreProcessingFactory,
    Desulfurizer,
    LargeCircuitAssembler,
    PetrochemicalPlant,
    SmeltingMixingFurnace,
    WhiteNightGenerator,
    LargeSteamCircuitAssembler,
    GenerationEarthEngine,

    LvSteamTurbine,
    MvSteamTurbine,
    HvSteamTurbine,
    SteamAssemblerBronze,
    SteamAssemblerSteel,

    BigSteamInputHatch,
    SuperDataAccessHatch,
    FluidManaInputHatch,
    FluidIceInputHatch,
    FluidBlazeInputHatch,
    SuperCraftingInputHatchME,
    SuperCraftingInputBusME,
    SuperCraftingInputProxy,
    HumongousSolidifierHatch,
    DebugEnergyHatch,
    NinefoldInputHatchEV,
    NinefoldInputHatchIV,
    NinefoldInputHatchLuV,
    NinefoldInputHatchZPM,
    NinefoldInputHatchUV,
    NinefoldInputHatchUHV,
    NinefoldInputHatchUEV,
    NinefoldInputHatchUIV,
    NinefoldInputHatchUMV,
    NinefoldInputHatchUXV,
    NinefoldInputHatchMAX,
    HumongousNinefoldInputHatch,
    QuadrupleOutputHatchEV,
    NinefoldOutputHatchEV,
    HumongousInputBusULV,
    HumongousInputBusLV,
    HumongousInputBusMV,
    HumongousInputBusHV,
    HumongousInputBusEV,
    HumongousInputBusIV,
    HumongousInputBusLuV,
    HumongousInputBusZPM,
    HumongousInputBusUV,
    HumongousInputBusUHV,
    HumongousInputBusUEV,
    HumongousInputBusUIV,
    HumongousInputBusUMV,
    HumongousInputBusUXV,
    HumongousInputBusMAX,
    DualInputHatchLV,
    DualInputHatchMV,
    DualInputHatchHV,
    DualInputHatchEV,
    DualInputHatchIV,
    DualInputHatchLuV,
    DualInputHatchZPM,
    DualInputHatchUV,
    DualInputHatchUHV,
    DualInputHatchUEV,
    DualInputHatchUIV,
    DualInputHatchUMV,
    DualInputHatchUXV,
    DualInputHatchMAX;

    public boolean mHasNotBeenSet;
    public boolean mDeprecated;
    public boolean mWarned;

    public ItemStack mStack;

    // endregion

    GTNLItemList() {
        mHasNotBeenSet = true;
    }

    GTNLItemList(boolean aDeprecated) {
        if (aDeprecated) {
            mDeprecated = true;
            mHasNotBeenSet = true;
        }
    }

    public Item getItem() {
        sanityCheck();
        if (Utils.isStackInvalid(mStack)) return null;
        return mStack.getItem();
    }

    public Block getBlock() {
        sanityCheck();
        return Block.getBlockFromItem(getItem());
    }

    public ItemStack get(int aAmount, Object... aReplacements) {
        sanityCheck();
        // if invalid, return a replacements
        if (Utils.isStackInvalid(mStack)) {
            GTLog.out.println("Object in the ItemList is null at:");
            new NullPointerException().printStackTrace(GTLog.out);
            return Utils.copyAmount(aAmount, TestMetaBlock01_0.get(1));
        }
        return Utils.copyAmount(aAmount, mStack);
    }

    public int getMeta() {
        return mStack.getItemDamage();
    }

    public GTNLItemList set(Item aItem) {
        if (aItem == null) return this;
        return set(Utils.newItemStack(aItem));
    }

    public GTNLItemList set(ItemStack aStack) {
        if (aStack == null) return this;
        mHasNotBeenSet = false;
        mStack = Utils.copyAmount(1, aStack);
        if (Utils.isClientSide()) {
            Item item = mStack.getItem();
            if (item == null) return this;
            if (Block.getBlockFromItem(item) == GregTechAPI.sBlockMachines) {
                GTNLCreativeTabs.addToMachineList(mStack.copy());
            }
        }
        return this;
    }

    public GTNLItemList set(IMetaTileEntity metaTileEntity) {
        if (metaTileEntity == null) throw new IllegalArgumentException();
        return set(metaTileEntity.getStackForm(1L));
    }

    public boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    /**
     * Returns the internal stack. This method is unsafe. It's here only for quick operations. DON'T CHANGE THE RETURNED
     * VALUE!
     */
    public ItemStack getInternalStack_unsafe() {
        return mStack;
    }

    public void sanityCheck() {
        if (mHasNotBeenSet)
            throw new IllegalAccessError("The Enum '" + name() + "' has not been set to an Item at this time!");
        if (mDeprecated && !mWarned) {
            new Exception(this + " is now deprecated").printStackTrace(GTLog.err);
            // warn only once
            mWarned = true;
        }
    }

    @SuppressWarnings("SizeReplaceableByIsEmpty")
    public ItemStack getWithName(int aAmount, String aDisplayName, Object... aReplacements) {
        ItemStack rStack = get(1, aReplacements);
        if (GTUtility.isStackInvalid(rStack)) return NI;

        // CamelCase alphanumeric words from aDisplayName
        StringBuilder tCamelCasedDisplayNameBuilder = new StringBuilder();
        final String[] tDisplayNameWords = aDisplayName.split("\\W");
        for (String tWord : tDisplayNameWords) {
            if (!tWord.isEmpty()) tCamelCasedDisplayNameBuilder.append(
                tWord.substring(0, 1)
                    .toUpperCase(Locale.US));
            if (tWord.length() > 1) tCamelCasedDisplayNameBuilder.append(
                tWord.substring(1)
                    .toLowerCase(Locale.US));
        }
        if (tCamelCasedDisplayNameBuilder.length() == 0) {
            // CamelCased DisplayName is empty, so use hash of aDisplayName
            tCamelCasedDisplayNameBuilder.append(((Long) (long) aDisplayName.hashCode()));
        }

        // Construct a translation key from UnlocalizedName and CamelCased DisplayName
        final String tKey = rStack.getUnlocalizedName() + ".with." + tCamelCasedDisplayNameBuilder + ".name";

        rStack.setStackDisplayName(GTLanguageManager.addStringLocalization(tKey, aDisplayName));
        return GTUtility.copyAmount(aAmount, rStack);
    }

}
