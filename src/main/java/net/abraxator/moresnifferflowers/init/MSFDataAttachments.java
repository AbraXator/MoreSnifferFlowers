package net.abraxator.moresnifferflowers.init;

import com.mojang.serialization.Codec;
import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.*;
import net.abraxator.moresnifferflowers.components.BetterNonNullList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Supplier;

public interface MSFDataAttachments {
    DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, MoreSnifferFlowers.MOD_ID);

    Supplier<AttachmentType<BlockPatternCapability>> BLOCK_PATTERNS = ATTACHMENT_TYPES.register("block_patterns",
            () -> AttachmentType.builder(() -> new BlockPatternCapability(new HashMap<>()))
                    .serialize(BlockPatternCapability.CODEC)
                    .sync(BlockPatternCapability.STREAM_CODEC)
                    .build());

    Supplier<AttachmentType<ComboMealCapability>> COMBO_MEAL = ATTACHMENT_TYPES.register("combo_meal",
            () -> AttachmentType.builder(() -> new ComboMealCapability(1f, 0))
                    .serialize(ComboMealCapability.CODEC)
                    .sync(ByteBufCodecs.fromCodec(ComboMealCapability.CODEC)).build());

    Supplier<AttachmentType<CorruptionCapability>> CHUNK_CORRUPTION = ATTACHMENT_TYPES.register("corruption",
            () -> AttachmentType.builder(CorruptionCapability::new)
                    .serialize(CorruptionCapability.CODEC)
                    .build());

    Supplier<AttachmentType<Boolean>> IS_GLUED = ATTACHMENT_TYPES.register("glued",
            () -> AttachmentType.builder(() -> false)
                    .serialize(Codec.BOOL)
                    .build());

    Supplier<AttachmentType<BetterNonNullList<ItemStack>>> HARDENED_MOUTH_SLOTS = ATTACHMENT_TYPES.register("hardened_mouth_slots",
            () -> AttachmentType.builder(() -> BetterNonNullList.withSize(2, ItemStack.EMPTY))
                    .serialize(BetterNonNullList.codecOf(ItemStack.OPTIONAL_CODEC))
                    .sync(BetterNonNullList.streamCodecOf(ItemStack.OPTIONAL_STREAM_CODEC))
                    .build());

    Supplier<AttachmentType<Integer>> HARDENED_MOUTH_COOLDOWN = ATTACHMENT_TYPES.register("hardened_mouth_cooldown",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .sync(ByteBufCodecs.VAR_INT)
                    .build());


    Supplier<AttachmentType<SlipperyCapability>> SLIPPERY = ATTACHMENT_TYPES.register("slippery",
            () -> AttachmentType.builder(SlipperyCapability::new)
                    .serialize(SlipperyCapability.CODEC)
                    .build());

    Supplier<AttachmentType<UntouchableCapability>> UNTOUCHABLE = ATTACHMENT_TYPES.register("untouchable",
            () -> AttachmentType.builder(UntouchableCapability::new)
                    .serialize(UntouchableCapability.CODEC)
                    .build());

    Supplier<AttachmentType<NutritionCapability>> NUTRITION = ATTACHMENT_TYPES.register("nutrition",
            () -> AttachmentType.builder(() -> new NutritionCapability(new HashSet<>(), new HashSet<>()))
                    .serialize(NutritionCapability.CODEC)
                    .sync(NutritionCapability.STREAM_CODEC)
                    .copyOnDeath()
                    .build());

    Supplier<AttachmentType<Integer>> EXTRACTED_TICKS_REMAINING = ATTACHMENT_TYPES.register("extracted_ticks_remaining",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .build());
}
