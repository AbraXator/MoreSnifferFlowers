package net.abraxator.moresnifferflowers.init;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.Map;

public class ModCauldronInteractions {
    public static final CauldronInteraction.InteractionMap BONMEEL = CauldronInteraction.newInteractionMap("bonmeel");
    public static final CauldronInteraction.InteractionMap ACID = CauldronInteraction.newInteractionMap("acid");
    public static final CauldronInteraction FILL_JAR_OF_BONMEEL = (state, level, pos, player, hand, stack) ->
            emptyBottle(level, pos,  player, hand, stack, ModBlocks.BONMEEL_FILLED_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
    public static final CauldronInteraction FILL_JAR_OF_ACID = (state, level, pos, player, hand, stack) ->
            emptyBottle(level, pos,  player, hand, stack, ModBlocks.ACID_FILLED_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3));
    public static final CauldronInteraction EMPTY_JAR_OF_BONMEEL = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, ModItems.JAR_OF_BONMEEL.toStack(), blockState -> blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BOTTLE_FILL);
    public static final CauldronInteraction EMPTY_JAR_OF_ACID = (state, level, pos, player, hand, stack) ->
            CauldronInteraction.fillBucket(state, level, pos, player, hand, stack, ModItems.JAR_OF_ACID.toStack(), blockState -> blockState.getValue(LayeredCauldronBlock.LEVEL) == 3, SoundEvents.BOTTLE_FILL);


    static ItemInteractionResult emptyBottle(Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack, BlockState state) {
        if (level.getBlockState(pos).getValue(LayeredCauldronBlock.LEVEL) < 3) {
            if (!level.isClientSide) {
                Item item = filledStack.getItem();
                if (!player.isCreative()) player.setItemInHand(hand, ItemUtils.createFilledResult(filledStack, player, new ItemStack(Items.GLASS_BOTTLE)));
                player.awardStat(Stats.FILL_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item));
                level.setBlockAndUpdate(pos, state);
                level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
            }

            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }
        return ItemInteractionResult.FAIL;
    }


    public static void bootstrap() {
        Map<Item, CauldronInteraction> bonmeel = BONMEEL.map();
        bonmeel.put(Items.GLASS_BOTTLE, EMPTY_JAR_OF_BONMEEL);
        bonmeel.put(ModItems.JAR_OF_BONMEEL.asItem(), FILL_JAR_OF_BONMEEL);

        Map<Item, CauldronInteraction> acid = ACID.map();
        acid.put(Items.GLASS_BOTTLE, EMPTY_JAR_OF_ACID);
        acid.put(ModItems.JAR_OF_ACID.get(), FILL_JAR_OF_ACID);
    }
}
