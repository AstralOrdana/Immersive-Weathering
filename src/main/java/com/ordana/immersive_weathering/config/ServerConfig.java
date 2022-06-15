package com.ordana.immersive_weathering.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@SuppressWarnings("ALL")
@Config(name = "immersive_weathering")
@Config.Gui.Background("immersive_weathering:textures/block/cracked_bricks.png")
public final class ServerConfig extends PartitioningSerializer.GlobalData {

    @ConfigEntry.Category("block_growth_config")
    @ConfigEntry.Gui.TransitiveObject
    public BlockGrowthConfig blockGrowthConfig = new BlockGrowthConfig();

    @ConfigEntry.Category("leaves_config")
    @ConfigEntry.Gui.TransitiveObject
    public LeavesConfig leavesConfig = new LeavesConfig();

    @ConfigEntry.Category("fire_and_ice_config")
    @ConfigEntry.Gui.TransitiveObject
    public FireAndIceConfig fireAndIceConfig = new FireAndIceConfig();

    @ConfigEntry.Category("generators_config")
    @ConfigEntry.Gui.TransitiveObject
    public GeneratorsConfig generatorsConfig = new GeneratorsConfig();

    @ConfigEntry.Category("item_uses_config")
    @ConfigEntry.Gui.TransitiveObject
    public ItemUsesConfig itemUsesConfig = new ItemUsesConfig();

    @ConfigEntry.Category("worldgen_config")
    @ConfigEntry.Gui.TransitiveObject
    public WorldgenConfig worldgenConfig = new WorldgenConfig();

    private ServerConfig() {
    }

    @Config(name = "leaves_config")
    public static final class LeavesConfig implements ConfigData {

        public boolean fallingLeafParticles = true;
        public boolean leafDecayPiles = true;
        public boolean leafDecayParticles = true;
        public boolean leafDecaySound = true;
        public boolean leafPilesForm = true;
        public boolean branchesForm = true;
        public boolean leafPilesConvertBlockBelow = true;
        public boolean leggingsPreventThornDamage = true;

        public boolean composterDropsDirt = true;
        public boolean mulchGrowsCrops = true;
        public boolean featherFallingFarmer = true;

        private LeavesConfig() {
        }
    }

    @Config(name = "fire_and_ice_config")
    public static final class FireAndIceConfig implements ConfigData {

        public boolean fireCharsWood = true;
        public boolean campfiresCreateSoot = true;
        public boolean lightningCreateMagma = true;
        public boolean lightningCreateLava = true;
        public boolean lightningCreateVitrifiedSand = true;
        public boolean flammableCobwebs = true;

        public boolean freezingWater = true;
        public int freezingWaterSeverity = 200;
        public boolean permafrostFreezing = true;
        public int freezingPermafrostSeverity = 200;
        public boolean icicleFreezing = true;
        public int freezingIcicleSeverity = 300;
        public boolean thinIceFormation = true;
        public boolean thinIceMelting = false;
        public boolean glassFrosting = true;
        public boolean grassFrosting = true;
        public boolean naturalIceMelt = true;
        public boolean iciclePlacement = true;

        private FireAndIceConfig() {
        }
    }
    

    @Config(name = "block_growth_config")
    public static final class BlockGrowthConfig implements ConfigData {

        public boolean blockGrowth = true;
        public boolean blockCracking = true;
        public boolean blockMossing = true;
        public boolean blockRusting = true;
        public boolean woodWeathering = false;

        private BlockGrowthConfig() {
        }
    }

    @Config(name = "generators_config")
    public static final class GeneratorsConfig implements ConfigData {

        public boolean allGenerators = true;
        public boolean mossBurning = true;
        public boolean basaltGenerator = true;
        public boolean deepslateGenerator = true;
        public boolean graniteGenerator = true;
        public boolean andesiteGenerator = true;
        public boolean dioriteGenerator = true;
        public boolean tuffGenerator = true;
        public boolean blackstoneGenerator = true;
        public boolean magmaGenerator = true;
        public boolean terracottaGenerator = true;
        public boolean crackedMudGenerator = true;
        public boolean vitrifiedSandGenerator = true;
        public boolean cryingObsidianGenerator = true;
        public boolean iceGenerator = true;

        private GeneratorsConfig() {
        }
    }

    @Config(name = "item_uses_config")
    public static final class ItemUsesConfig implements ConfigData {

        public boolean cauldronWashing = false;
        public boolean pistonSliming = true;
        public boolean soilShearing = true;
        public boolean azaleaShearing = true;
        public boolean mossShearing = true;
        public boolean mossBurning = true;
        public boolean charredBlockIgniting = true;
        public boolean shovelExtinguishing = true;
        public boolean spongeRusting = true;
        public boolean pickaxeCracking = true;
        public boolean axeStripping = true;
        public boolean axeScraping = true;

        private ItemUsesConfig() {
        }
    }

    @Config(name = "worldgen_config")
    public static final class WorldgenConfig implements ConfigData {

        public boolean icicleFeature = true;
        public boolean cryosolFeature = true;
        public boolean humusFeature = true;
        public boolean rootsFeature = true;
        public boolean fluvisolFeature = true;
        public boolean siltFeature = true;
        public boolean lakebedFeature = true;
        public boolean vertisolFeature = true;
        public boolean oakLeavesFeature = true;
        public boolean darkLeavesFeature = true;
        public boolean birchLeavesFeature = true;
        public boolean spruceLeavesFeature = true;
        public boolean ivyFeature = true;
        public boolean quicksandFeature = true;

        private WorldgenConfig() {
        }
    }
}
