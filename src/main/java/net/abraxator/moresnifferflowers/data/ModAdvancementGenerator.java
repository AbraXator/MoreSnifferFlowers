package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.advancements.packs.VanillaHusbandryAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper helper) {
        Advancement root = Advancement.Builder.advancement().display(
                        Items.SNIFFER_EGG.getDefaultInstance(),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed"),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed.desc"),
                        new ResourceLocation("textures/block/farmland.png"), FrameType.TASK, true, true, false)
                .addCriterion("hasAdvancement", new EarnSnifferAdvancementTrigger.Instance(ContextAwarePredicate.ANY))
                .save(consumer, MoreSnifferFlowers.MOD_ID + ":root");

        Advancement.Builder.advancement().parent(root).display(
                        ModItems.DYESPRIA.get(),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria"),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria.desc", ModBlocks.CAULORFLOWER.get().getDescriptionId(), ModItems.DYESPRIA.get().getDescriptionId()),
                        null, FrameType.TASK, true, true, false)
                .addCriterion("used_dyespria", new UsedDyespriaTrigger.Instance(ContextAwarePredicate.ANY))
                .save(consumer, MoreSnifferFlowers.MOD_ID + ":dyespria");
    }
}
