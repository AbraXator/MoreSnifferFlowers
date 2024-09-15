package net.abraxator.moresnifferflowers.items;

import net.abraxator.moresnifferflowers.blocks.Bonmeelable;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModStateProperties;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.apache.http.impl.cookie.PublicSuffixDomainFilter;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;

import java.util.*;
import java.util.stream.StreamSupport;

public class JarOfBonmeelItem extends Item {
    public JarOfBonmeelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos blockPos = pContext.getClickedPos();
        BlockState blockState = pContext.getLevel().getBlockState(blockPos);
        Player player = pContext.getPlayer();
        
        if (pContext.getHand() != InteractionHand.MAIN_HAND) {
            return InteractionResult.PASS;
        }
    
        if(blockState.is(ModTags.ModBlockTags.BONMEELABLE)) {
            Bonmeelable bonmeelable = ((Bonmeelable) Bonmeelable.MAP.get(blockState.getBlock()));
            bonmeelable.performBonmeel(blockPos, blockState, level, player);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
        pTooltipComponents.add(Component.translatableWithFallback("tooltip.jar_of_bonmeel.usage", "Can be applied to a 3x3 grid of the following crops: carrot, potato, wheat, beetroot and nether wart").withStyle(ChatFormatting.GOLD));
    }
}
