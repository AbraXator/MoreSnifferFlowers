package net.abraxator.moresnifferflowers.client;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.ColorableVivicusBlock;
import net.abraxator.moresnifferflowers.components.BlockPattern;
import net.abraxator.moresnifferflowers.components.Colorable;
import net.abraxator.moresnifferflowers.components.Dye;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModDataComponents;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.items.DyespriaItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, value = Dist.CLIENT)
public class ModColorHandler {
    @SubscribeEvent
    public static void onRegisterBlockColorHandlers(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, pTintIndex) -> {
            Colorable colorable = ((Colorable) state.getBlock());
            Dye dye = colorable.getDyeFromBlock(state);
            int color = Dye.colorForDye(colorable, dye.color());
            if(!dye.isEmpty()) {
                if (pTintIndex == 0) {
                    float[] colorHSB = getColorHSB(color);

                    return Color.HSBtoRGB(colorHSB[0], Math.max(colorHSB[1] / 1.7F, 0), Math.max(colorHSB[2], 0));
                }
                if (pTintIndex == 1) {
                    return color;
                }
            }
            return -1;
        }, ModBlocks.CAULORFLOWER.get());
        event.register((state, level, pos, pTintIndex) -> {
            int color = state.getValue(ModStateProperties.BLOCK_PATTERN).getColor();
            if (state.getValue(ModStateProperties.EMPTY)) color = 0xFFFFFF;
            if (pTintIndex == 0) {
                float[] colorHSB = getColorHSB(color);
                return Color.HSBtoRGB(colorHSB[0], Math.max(colorHSB[1] / 1.7F, 0), Math.max(colorHSB[2], 0));
            }
            if (pTintIndex == 1) {
                float[] colorHSB = getColorHSB(color);
                return Color.HSBtoRGB(colorHSB[0], Math.min(colorHSB[1] * 1.1F, 1), Math.min(colorHSB[2] * 1.2F, 1));
            }

            return -1;
        }, ModBlocks.PATTERNFLOWER.get());
        event.register((state, level, pos, pTintIndex) -> {
                    var colorable = ((ColorableVivicusBlock) state.getBlock());
                    if(pTintIndex == 0) {
                        var dyedValue = Dye.colorForDye(colorable, state.getValue(colorable.getColorProperty()));
                        var color = colorable.getDyeFromBlock(state).color();

                        if(state.is(ModBlocks.VIVICUS_LEAVES.get()) || state.is(ModBlocks.VIVICUS_LEAVES_SPROUT.get())) {
                            float[] colorHSB = getColorHSB(dyedValue);

                            assert pos != null;
                            float hue = colorHSB[0] + ((1+ Mth.sin((float)pos.getX() + (float)pos.getY() + (float)pos.getZ())) / 15);

                            if (colorHSB[1] < 0.3 && colorHSB[2] < 0.8){
                                colorHSB[2] = colorHSB[2] - ((1+Mth.sin((float)pos.getX() + (float)pos.getY() + (float)pos.getZ())) / 15);
                            }

                            if (colorHSB[1] < 0.3){
                                colorHSB[1] = colorHSB[1] + ((1+Mth.sin((float)pos.getX() + (float)pos.getY() + (float)pos.getZ())) / 12);
                            }


                            return Color.HSBtoRGB(hue, colorHSB[1], colorHSB[2]);
                        }

                        return dyedValue;
                    }

                    return -1;
                }, ModBlocks.VIVICUS_LOG.get(), ModBlocks.VIVICUS_WOOD.get(), ModBlocks.STRIPPED_VIVICUS_LOG.get(), ModBlocks.STRIPPED_VIVICUS_LOG.get(),
                ModBlocks.STRIPPED_VIVICUS_WOOD.get(), ModBlocks.VIVICUS_PLANKS.get(), ModBlocks.VIVICUS_STAIRS.get(),
                ModBlocks.VIVICUS_SLAB.get(), ModBlocks.VIVICUS_FENCE.get(), ModBlocks.VIVICUS_FENCE_GATE.get(),
                ModBlocks.VIVICUS_DOOR.get(), ModBlocks.VIVICUS_TRAPDOOR.get(), ModBlocks.VIVICUS_PRESSURE_PLATE.get(),
                ModBlocks.VIVICUS_BUTTON.get(), ModBlocks.VIVICUS_LEAVES.get(), ModBlocks.VIVICUS_SAPLING.get(),
                ModBlocks.VIVICUS_LEAVES_SPROUT.get(), ModBlocks.VIVICUS_SIGN.get(), ModBlocks.VIVICUS_HANGING_SIGN.get(),
                ModBlocks.VIVICUS_SAPLING.get());
    }

    @SubscribeEvent
    public static void onRegisterItemColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, pTintIndex) -> {
            Dye dye = Dye.getDyeFromDyespria(stack);
            if(pTintIndex != 0 || dye.isEmpty()) {
                return -1;
            } else {
                return Dye.colorForDye(((DyespriaItem) stack.getItem()), dye.color());
            }
        }, ModItems.DYESPRIA.get());

        event.register((stack, pTintIndex) -> pTintIndex > 0 ? -1 : stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).getColor(),
                ModItems.EXTRACTED_BOTTLE.get(), ModItems.REBREWED_POTION.get(), ModItems.REBREWED_SPLASH_POTION.get(), ModItems.REBREWED_LINGERING_POTION.get());

        event.register(((stack, tintIndex) ->{
           BlockPattern pattern = BlockPattern.fromPatternspria(stack);
           if(tintIndex != 0 || pattern == BlockPattern.EMPTY) return -1;
           return alphaFixer(stack.getOrDefault(ModDataComponents.COLOR.get(), pattern.getColor()));
        }), ModItems.PATTERNSPRIA.get());

        event.register(((stack, tintIndex) -> {
                    if (tintIndex != 0) return -1;
                    return alphaFixer(stack.getOrDefault(ModDataComponents.COLOR.get(), 0xffffffff));
                }
        ), ModBlocks.VIVICUS_LOG.get(),  ModBlocks.VIVICUS_WOOD.get(), ModBlocks.STRIPPED_VIVICUS_LOG.get(),  ModBlocks.STRIPPED_VIVICUS_WOOD.get(), ModBlocks.VIVICUS_PLANKS.get(),
                ModBlocks.VIVICUS_STAIRS.get(), ModBlocks.VIVICUS_SLAB.get(), ModBlocks.VIVICUS_FENCE.get(), ModBlocks.VIVICUS_FENCE_GATE.get(), ModBlocks.VIVICUS_DOOR.get(),
                ModBlocks.VIVICUS_TRAPDOOR.get(), ModBlocks.VIVICUS_PRESSURE_PLATE.get(), ModBlocks.VIVICUS_BUTTON.get(), ModBlocks.VIVICUS_LEAVES.get(), ModBlocks.VIVICUS_SAPLING.get(),
                ModBlocks.VIVICUS_LEAVES_SPROUT.get(), ModItems.VIVICUS_SIGN.get(), ModItems.VIVICUS_HANGING_SIGN.get(), ModItems.VIVICUS_BOAT.get(), ModItems.VIVICUS_CHEST_BOAT.get(),
                ModItems.ROOTED_SOUP.get());

    }

    public static float @NotNull [] getColorHSB(int originalColor) {
        int startRed = (originalColor >> 16) & 0xFF;
        int startGreen = (originalColor >> 8) & 0xFF;
        int startBlue = originalColor & 0xFF;
        return Color.RGBtoHSB(startRed, startGreen, startBlue, null);
    }

    public static int[] hexToRGBLarge(int hex) {
        return new int[] {(hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF};
    }

    public static float[] hexToRGB(int hex) {
        int r = (hex >> 16) & 0xFF;
        int g = (hex >> 8) & 0xFF;
        int b = hex & 0xFF;
        return new float[] {r / 255f, g/ 255f, b/ 255f};
    }

    public static int[] argbToArray(int argb) {
        int r = FastColor.ARGB32.red(argb);
        int g = FastColor.ARGB32.green(argb);
        int b = FastColor.ARGB32.blue(argb);
        int a = FastColor.ARGB32.alpha(argb);
        return new int[] {r,g,b,a};
    }

    public static int alphaFixer(int color) {
        if (FastColor.ARGB32.alpha(color) < 1){
           int[] rgb = hexToRGBLarge(color);
           return FastColor.ARGB32.color(rgb[0], rgb[1], rgb[2]);
        }
        return color;
    }


    public static int RGBtoInt(Vec3 color) {
        int r = (int) color.x;
        int g = (int) color.y;
        int b = (int) color.z;

        int rgb = r;
        rgb = (rgb << 8) + g;
        rgb = (rgb << 8) + b;

        return rgb;
    }

    public static int barColorHelper(int input, int maxInput){
        int lowColor = 0x8c1111;
        int highColor = 0x179529;

        int lowRed = (lowColor >> 16) & 0xFF;
        int lowGreen = (lowColor >> 8) & 0xFF;
        int lowBlue = lowColor & 0xFF;

        int highRed = (highColor >> 16) & 0xFF;
        int highGreen = (highColor >> 8) & 0xFF;
        int highBlue = highColor & 0xFF;

        float[] lowHSB =  Color.RGBtoHSB(lowRed, lowGreen, lowBlue, null);
        float[] highHSB =  Color.RGBtoHSB(highRed, highGreen, highBlue, null);


        float finalHue = ((lowHSB[0] * (Math.abs(input - maxInput))) + (highHSB[0] * input)) / maxInput;

        return Mth.hsvToRgb(finalHue, 1.0F, 1.0F);
    }
}
