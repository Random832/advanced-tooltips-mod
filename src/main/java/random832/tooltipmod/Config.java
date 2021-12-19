package random832.tooltipmod;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = TooltipsMod.MODID)
public class Config {
    public static final ForgeConfigSpec CLIENT_CONFIG;

    public static final ForgeConfigSpec.ConfigValue<DisplayMode> ITEM_NBT;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> ITEM_TAGS;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> ITEM_FLUID;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> ITEM_BLOCK;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> BLOCK_TAGS;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> FLUID_ID;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> FLUID_CAPACITY;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> FLUID_TAGS;
    public static final ForgeConfigSpec.ConfigValue<DisplayMode> FLUID_NBT;

    static {
        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

        CLIENT_BUILDER.push("tooltips");
        ITEM_TAGS = CLIENT_BUILDER.comment("Show item tags in tooltips").define("item_tags", DisplayMode.ADVANCED);
        ITEM_NBT = CLIENT_BUILDER.comment("Show item NBT in tooltips").define("item_nbt", DisplayMode.SHIFT_ADVANCED);
        ITEM_BLOCK = CLIENT_BUILDER.comment("Show block info in tooltips").define("item_block", DisplayMode.SHIFT_ADVANCED);
        BLOCK_TAGS = CLIENT_BUILDER.comment("Show block tags in tooltips").define("block_tags", DisplayMode.SHIFT_ADVANCED);
        ITEM_FLUID = CLIENT_BUILDER.comment("Show fluid info in tooltips").define("item_fluid", DisplayMode.ALWAYS);
        FLUID_ID = CLIENT_BUILDER.comment("Show fluid id when fluid info is shown").define("fluid_id", DisplayMode.ADVANCED);
        FLUID_CAPACITY = CLIENT_BUILDER.comment("Show fluid id when fluid info is shown").define("fluid_capacity", DisplayMode.SHIFT);
        FLUID_TAGS = CLIENT_BUILDER.comment("Show fluid tags when fluid info is shown").define("fluid_tags", DisplayMode.ADVANCED);
        FLUID_NBT = CLIENT_BUILDER.comment("Show fluid NBT when fluid info is shown").define("fluid_nbt", DisplayMode.SHIFT_ADVANCED);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading configEvent) {
    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
    }
}