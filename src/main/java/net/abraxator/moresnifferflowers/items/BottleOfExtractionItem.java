package net.abraxator.moresnifferflowers.items;

import mezz.jei.api.runtime.IEditModeConfig;
import net.abraxator.moresnifferflowers.init.ModMobEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottleOfExtractionItem extends Item {
    public BottleOfExtractionItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        git add .
        if (!(pLivingEntity instanceof Player player)) return pStack;

        player.addItem(initPotion(player));
        player.curePotionEffects(pStack);

        return pStack;
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
        ItemStack potion = new ItemStack(Items.POTION);
        CompoundTag tag = potion.getOrCreateTag();
        PotionUtils.setCustomEffects(potion, player.getActiveEffects());
        tag.putBoolean("isMoreSnifferFlowers", true);
        tag.putInt("CustomPotionColor", 4723885);
        potion.setTag(tag);

        return potion;
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
