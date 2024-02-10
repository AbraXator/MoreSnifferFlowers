package net.abraxator.moresnifferflowers.data;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.advancements.EarnSnifferAdvancementTrigger;
import net.abraxator.moresnifferflowers.advancements.UsedDyespriaTrigger;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.UsedEnderEyeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.antlr.runtime.UnwantedTokenException;

import java.util.Optional;
import java.util.function.Consumer;

public class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        AdvancementHolder root = Advancement.Builder.advancement().display(
                        Items.SNIFFER_EGG.getDefaultInstance(),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed"),
                        Component.translatable("advancements.more_sniffer_flowers.any_seed.desc"),
                        new ResourceLocation("textures/block/farmland.png"), AdvancementType.TASK, true, true, false)
                .addCriterion("hasAdvancement", new EarnSnifferAdvancementTrigger().createCriterion(new EarnSnifferAdvancementTrigger.Instance(Optional.empty())))
                .save(saver, MoreSnifferFlowers.MOD_ID + ":root");

        Advancement.Builder.advancement().parent(root).display(
                        ModItems.DYESPRIA.get(),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria"),
                        Component.translatable("advancements.more_sniffer_flowers.dyespria.desc", ModBlocks.CAULORFLOWER.get().getDescriptionId(), ModItems.DYESPRIA.get().getDescriptionId()),
                        null, AdvancementType.TASK, true, true, false)
                .addCriterion("used_dyespria", new UsedDyespriaTrigger().createCriterion(new UsedDyespriaTrigger.Instance(Optional.empty())))
                .save(saver, MoreSnifferFlowers.MOD_ID + ":dyespria");
    }
}
