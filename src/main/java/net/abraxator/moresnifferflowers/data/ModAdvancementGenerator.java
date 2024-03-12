package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedBonmeelTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

public class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
        var root = Advancement.Builder.advancement()
                .display(
                        Items.SNIFFER_EGG.getDefaultInstance(),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed"),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed.desc"),
                        MoreSnifferFlowers.loc("textures/misc/grass_block_bg.png"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("has_advancement", ModAdvancementCritters.getSnifferAdvancement())
                .save(saver, MoreSnifferFlowers.loc("root"), existingFileHelper);

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.DYESPRIA.get(),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria"),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria.desc"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("used_dyespria", ModAdvancementCritters.usedDyespria())
                .save(saver, MoreSnifferFlowers.loc("dyespria"), existingFileHelper);

        var bonmeel = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.JAR_OF_BONMEEL,
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.bonmeel", "Let It Grow!"),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.bonmeel.desc", "Enlarge your crops using the magic of Bonmeel"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_bonmeel", ModAdvancementCritters.usedBonmeel())
                .save(saver, MoreSnifferFlowers.loc("bonmeel"), existingFileHelper);
    }

    private String id(String name) {
        return "%s:%s".formatted(MoreSnifferFlowers.MOD_ID, name);
    }
}
