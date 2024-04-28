package net.abraxator.moresnifferflowers.data.advancement;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.UsingItemTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.Locale;
import java.util.function.Consumer;

public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> consumer, ExistingFileHelper helper) {
        Advancement root = Advancement.Builder.advancement()
                .display(
                        Items.SNIFFER_EGG.getDefaultInstance(),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed"),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed.desc"),
                        MoreSnifferFlowers.loc("textures/gui/grass_block_bg.png"),
                        FrameType.TASK,
                        true,
                        false,
                        false)
                .addCriterion("has_advancement", ModAdvancementCritters.getSnifferAdvancement())
                .save(consumer, MoreSnifferFlowers.loc("root").toString());

        var dyespria_plant = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.DYESPRIA.get(),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria_plant"),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria_plant.desc"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("planted_dyespria_plant", ModAdvancementCritters.placedDyespriaPlant())
                .save(consumer, MoreSnifferFlowers.loc("dyespria_plant").toString());

        Advancement.Builder.advancement()
                .parent(dyespria_plant)
                .display(
                        ModBlocks.CAULORFLOWER.get(),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria"),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria.desc"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .addCriterion("used_dyespria", ModAdvancementCritters.usedDyespria())
                .save(consumer, MoreSnifferFlowers.loc("dyespria").toString());

        var bonmeel = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.JAR_OF_BONMEEL.get(),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.bonmeel", "Let It Grow!"),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.bonmeel.desc", "Enlarge your crops using the magic of Bonmeel"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("used_bonmeel", ModAdvancementCritters.usedBonmeel())
                .save(consumer, MoreSnifferFlowers.loc("bonmeel").toString());

        Advancement.Builder.advancement()
                .parent(bonmeel)
                .display(
                        ModItems.CROPRESSED_BEETROOT.get(),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.cropressor", "Compressing with extra steps"),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.cropressor.desc", "Cropress any crop"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_cropressed_crop", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModTags.ModItemTags.CROPRESSED_CROPS).build()))
                .save(consumer, MoreSnifferFlowers.loc("cropressor").toString());

        Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.REBREWING_STAND.get(),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.rebrew", "Local Rebrewery"),
                        Component.translatableWithFallback("advancements.more_sniffer_flowers.rebrew.desc", "Rebrew an Extracted Potion"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_rebrewed_potion", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModTags.ModItemTags.REBREWED_POTIONS).build()))
                .save(consumer, MoreSnifferFlowers.loc("rebrew").toString());
    }

    private String id(String name) {
        return "%s:%S".formatted(MoreSnifferFlowers.MOD_ID, name);
    }
}
