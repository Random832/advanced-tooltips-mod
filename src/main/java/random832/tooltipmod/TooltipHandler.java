package random832.tooltipmod;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT_SHIFT;

@Mod.EventBusSubscriber(modid = TooltipsMod.MODID, value = Dist.CLIENT)
public class TooltipHandler {
    @SubscribeEvent
    static void onTooltip(ItemTooltipEvent e) {
        boolean isAdvanced = e.getFlags().isAdvanced();
        // yes, this is correct, see AbstractContainerScreen#mouseClicked
        long hWnd = Minecraft.getInstance().getWindow().getWindow();
        boolean shift = InputConstants.isKeyDown(hWnd, GLFW_KEY_LEFT_SHIFT) || InputConstants.isKeyDown(hWnd, GLFW_KEY_RIGHT_SHIFT);
        List<Component> tooltip = e.getToolTip();

        final ItemStack itemStack = e.getItemStack();
        if(Config.ITEM_NBT.get().check(isAdvanced, shift)) {
            CompoundTag tag = itemStack.getTag();
            if (tag != null) {
                // remove the vanilla: "NBT: 1 tag(s)" tooltip
                tooltip.removeIf(component -> component instanceof TranslatableComponent translatable && translatable.getKey().equals("item.nbt_tags"));
                putNbtTag(tooltip, tag, "info.random832tooltips.nbt");
            }
        }
        if(Config.ITEM_TAGS.get().check(isAdvanced, shift))
            putTagList(tooltip, itemStack.getItem().getTags(), "info.random832tooltips.tags");
        if(itemStack.getItem() instanceof BlockItem blockItem) {
            if (Config.ITEM_BLOCK.get().check(isAdvanced, shift))
                tooltip.add(new TranslatableComponent("info.random832tooltips.block_with_id",
                        blockItem.getBlock().getName(),
                        displayRegistryName(blockItem.getBlock())));
            if (Config.BLOCK_TAGS.get().check(isAdvanced, shift))
                putTagList(tooltip, blockItem.getBlock().getTags(), "info.random832tooltips.block_tags");
        }
        if(Config.ITEM_FLUID.get().check(isAdvanced, shift)) {
            LazyOptional<IFluidHandlerItem> cap = itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
            cap.ifPresent(handler -> {
                for (int i=0; i<handler.getTanks(); i++) {
                    FluidStack fluid = handler.getFluidInTank(i);
                    int capacity = handler.getTankCapacity(i);
                    if(Config.FLUID_ID.get().check(isAdvanced, shift))
                        tooltip.add(new TranslatableComponent("info.random832tooltips.fluid_with_id",
                                formatFluidCapacity(fluid.getAmount(), capacity, Config.FLUID_CAPACITY.get().check(isAdvanced, shift)),
                                fluid.getDisplayName(),
                                displayRegistryName(fluid.getFluid())));
                    else
                        tooltip.add(new TranslatableComponent("info.random832tooltips.fluid_info",
                            formatFluidCapacity(fluid.getAmount(), capacity, Config.FLUID_CAPACITY.get().check(isAdvanced, shift)),
                            fluid.getDisplayName()));
                    if(Config.FLUID_NBT.get().check(isAdvanced, shift)) {
                        CompoundTag nbt = fluid.getTag();
                        if(nbt != null) putNbtTag(tooltip, nbt, "info.random832tooltips.fluid_nbt");
                    }
                    if(Config.FLUID_TAGS.get().check(isAdvanced, shift))
                        putTagList(tooltip, fluid.getFluid().getTags(), "info.random832tooltips.fluid_tags");
                }
            });
        }
    }

    private static Component formatFluidCapacity(int amount, int capacity, boolean includeCapacity) {
        final String str = includeCapacity ? amount + "/" + capacity + " mB" : amount + " mB";
        return new TextComponent(str);
    }

    private static void putNbtTag(List<Component> tooltip, CompoundTag tag, String translationKey) {
        tooltip.add(new TranslatableComponent(translationKey,
                new TextComponent("").withStyle(ChatFormatting.GRAY).append(NbtUtils.toPrettyComponent(tag))
        ).withStyle(ChatFormatting.DARK_GRAY));
    }

    private static void putTagList(List<Component> tooltip, Collection<ResourceLocation> tags, String translationKey) {
        if (!tags.isEmpty()) {
            tooltip.add(new TranslatableComponent(translationKey,
                    new TextComponent(tags.stream().map(ResourceLocation::toString).collect(Collectors.joining(", ")))
            ).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    @NotNull
    private static Component displayRegistryName(IForgeRegistryEntry<?> object) {
        ResourceLocation id = object.getRegistryName();
        if(id == null)
            return new TextComponent("unregistered!").withStyle(ChatFormatting.RED);
        else
            return new TextComponent(id.toString());
    }
}