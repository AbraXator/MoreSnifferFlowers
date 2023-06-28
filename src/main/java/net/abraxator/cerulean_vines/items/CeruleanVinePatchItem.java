package net.abraxator.cerulean_vines.items;

import net.abraxator.cerulean_vines.entities.CeruleanVinePatch;
import net.abraxator.cerulean_vines.init.ModBlocks;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class CeruleanVinePatchItem extends Item {
    public CeruleanVinePatchItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(pContext.getLevel().isClientSide()) return InteractionResult.FAIL;
        else {
            if(ModBlocks.CERULEAN_VINE.isPresent()) pContext.getLevel().setBlock(pContext.getClickedPos(), ModBlocks.CERULEAN_VINE.get().defaultBlockState(), 3);
            return InteractionResult.sidedSuccess(pContext.getLevel().isClientSide());
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
        if(!pLevel.isClientSide){
            CeruleanVinePatch ceruleanVinePatch = new CeruleanVinePatch(pPlayer, pLevel);
            ceruleanVinePatch.setItem(itemStack);
            ceruleanVinePatch.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 1.5F, 1.0F);
            pLevel.addFreshEntity(ceruleanVinePatch);
        }

        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        if(!pPlayer.getAbilities().instabuild) itemStack.shrink(1);

        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide);
    }
}
