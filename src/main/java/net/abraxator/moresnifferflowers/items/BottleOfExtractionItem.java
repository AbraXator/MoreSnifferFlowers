package net.abraxator.moresnifferflowers.items;

import com.ibm.icu.impl.units.MeasureUnitImpl;
import mezz.jei.api.runtime.IEditModeConfig;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModMobEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;

public class BottleOfExtractionItem extends Item {
    public BottleOfExtractionItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        var ret = pStack;
        
        if (!(pLivingEntity instanceof Player player) && pLevel.isClientSide) {
            return pStack;
        }
        
        if (pLivingEntity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, pStack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }
        
        ret = initPotion(((Player) pLivingEntity));
        pLivingEntity.removeAllEffects();
        return ret;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (!canExtract(pLevel, pPlayer)) {
            return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));
        } else {
            pPlayer.startUsingItem(pUsedHand);
            return InteractionResultHolder.consume(pPlayer.getItemInHand(pUsedHand));
        }
    }

    private ItemStack initPotion(Player player) {
        ItemStack itemStack1 = ModItems.EXTRACTED_BOTTLE.get().getDefaultInstance();
        PotionUtils.setCustomEffects(itemStack1, player.getActiveEffects());
        itemStack1.addTagElement("extractedBottle", EndTag.INSTANCE);
        return itemStack1;
    }
    
    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    private boolean canExtract(Level level, Player player) {
        return !level.isClientSide && player.getActiveEffects() != null && !player.getActiveEffects().isEmpty() && !player.hasEffect(ModMobEffects.EXTRACTED.get());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatableWithFallback("bottle_of_extraction.tooltip.usage", "Drink to extract all effects into single potion"));
    }
}
