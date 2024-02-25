package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Optional;
import java.util.function.Consumer;

public class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(RegistryWrapper.WrapperLookup registries, Consumer<AdvancementEntry> saver, ExistingFileHelper existingFileHelper) {
        AdvancementEntry root = Advancement.Builder.create().display(
                        Items.SNIFFER_EGG.getDefaultStack(),
                        Text.translatable("advancements.more_sniffer_flowers.any_seed"),
                        Text.translatable("advancements.more_sniffer_flowers.any_seed.desc"),
                        new Identifier("textures/block/farmland.png"), AdvancementFrame.TASK, true, true, false)
                .criterion("hasAdvancement", new EarnSnifferAdvancementTrigger().create(new EarnSnifferAdvancementTrigger.Instance(Optional.empty())))
                .build(saver, MoreSnifferFlowers.MOD_ID + ":root");

        Advancement.Builder.create().parent(root).display(
                        ModItems.DYESPRIA.get(),
                        Text.translatable("advancements.more_sniffer_flowers.dyespria"),
                        Text.translatable("advancements.more_sniffer_flowers.dyespria.desc", ModBlocks.CAULORFLOWER.get().getDescriptionId(), ModItems.DYESPRIA.get().getDescriptionId()),
                        null, AdvancementFrame.TASK, true, true, false)
                .addCriterion("used_dyespria", new UsedDyespriaTrigger().create(new UsedDyespriaTrigger.Instance(Optional.empty())))
                .save(saver, MoreSnifferFlowers.MOD_ID + ":dyespria");
    }
}
