package net.abraxator.moresnifferflowers.events;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.capability.BlockPatternCapability;
import net.abraxator.moresnifferflowers.capability.CorruptionCapability;
import net.abraxator.moresnifferflowers.capability.GluedCapability;
import net.abraxator.moresnifferflowers.capability.UntouchableCapability;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.abraxator.moresnifferflowers.nutrition.NutritionLoader;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.item.ItemEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;

import java.util.Objects;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID)
public class ForgeEvents {

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new NutritionLoader());
    }

    @SubscribeEvent
    public static void onChunkWatch(ChunkWatchEvent.Watch.Sent event) {
        ServerLevel level = event.getLevel();

        LevelChunk chunk = event.getChunk();
        chunk.getData(ModDataAttachments.BLOCK_PATTERNS).sync(chunk.getPos().getMiddleBlockPosition(0), level);
    }

    @SubscribeEvent
    public static void onEffectAdded(MobEffectEvent.Added event){
        Holder<MobEffect> effect = event.getEffectInstance().getEffect();
        LivingEntity entity = event.getEntity();

        if (effect.equals(ModEffects.GLUED)){
            GluedCapability.setAndSync(entity, true, true);
        }
    }

    @SubscribeEvent
    public static void onEffectRemove(MobEffectEvent.Remove event){
        MobEffectInstance effect = event.getEffectInstance();
        LivingEntity entity = event.getEntity();

        if (effect == null) return;
        onEffectEnd(effect.getEffect(), entity);
    }

    @SubscribeEvent
    public static void onEffectExpire(MobEffectEvent.Expired event){
        MobEffectInstance effect = event.getEffectInstance();
        LivingEntity entity = event.getEntity();

        if (effect == null) return;
        onEffectEnd(effect.getEffect(), entity);
    }

    public static void onEffectEnd(Holder<MobEffect> effect, LivingEntity entity) {

        if (entity instanceof Player player) {
            if (effect.equals(ModEffects.HARDENED_MOUTH))
                player.getData(ModDataAttachments.HARDENED_MOUTH).onEffectEnd(player);

            if (effect.equals(ModEffects.SLIPPERY))
                player.getData(ModDataAttachments.SLIPPERY).onEffectEnd(player);

            if (effect.equals(ModEffects.COMBO_MEAL))
                player.getData(ModDataAttachments.COMBO_MEAL).onEffectEnd(player);

            if (effect.equals(ModEffects.UNTOUCHABLE))
                player.getData(ModDataAttachments.UNTOUCHABLE).onEffectEnd(player);

        }

        if (effect.equals(ModEffects.GLUED))
            GluedCapability.setAndSync(entity, false, true);


    }

    @SubscribeEvent
    public static void itemEntity(ItemTossEvent event){
        ItemEntity itemEntity = event.getEntity();
        ItemStack item = itemEntity.getItem();

       if (item.is(ModItems.BURNED_SLOT)){
           event.setCanceled(true);
       }

    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        boolean isCharged = player.getAttackStrengthScale(0.5f) > 0.9f;
        Entity entity = event.getTarget();
        Level level = player.level();

        if (player.hasEffect(ModEffects.COMBO_MEAL) && stack.is(Tags.Items.MELEE_WEAPON_TOOLS))
            player.getData(ModDataAttachments.COMBO_MEAL).onAttack(player, isCharged);


        if(player.hasEffect(ModEffects.GLUING_TOUCH) && isCharged && entity instanceof LivingEntity livingEntity && !level.isClientSide) {
            int amplifier = Objects.requireNonNull(player.getEffect(ModEffects.GLUING_TOUCH)).getAmplifier();

            if (level.random.nextFloat() < ((amplifier + 2) / 12f)) {
                livingEntity.addEffect(new MobEffectInstance(ModEffects.GLUED, (5 + amplifier*2) * 20, 0));
            }

        }
    }

    @SubscribeEvent
    public static void onItemPickup(ItemEntityPickupEvent.Pre event) {
        Player player = event.getPlayer();
        ItemEntity itemEntity = event.getItemEntity();

        if (player.hasEffect(ModEffects.STICKY)) {
           if (!player.isCrouching()) {
               event.setCanPickup(TriState.FALSE);
           } else {
               int amplifier = player.getEffect(ModEffects.STICKY).getAmplifier();
               int slowdown = 5 + amplifier*2;

               if (player.level().getGameTime() % slowdown != 0) {
                   event.setCanPickup(TriState.FALSE);
                   return;
               }
                ItemStack stack = itemEntity.getItem();
                ItemStack retStack = stack.split(1);

                player.addItem(retStack);

                itemEntity.setItem(stack);
               event.setCanPickup(TriState.FALSE);
           }

        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Level level = livingEntity.level();
        Vec3 loc = livingEntity.position();
        BlockPos blockPos = BlockPos.containing(loc);
        
        if(level.getBlockState(blockPos).is(ModBlocks.CORRUPTED_SLIME_LAYER) || level.getBlockState(blockPos.below()).is(ModBlocks.CORRUPTED_SLIME_LAYER)) {
            livingEntity.setDeltaMovement(livingEntity.getDeltaMovement().multiply(1, 0.3, 1));
        }

    }

    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent event) {
       BlockState state = event.getPlacedBlock();
       LevelAccessor badLevel = event.getLevel();

       if (state.is(ModTags.ModBlockTags.CORRUPTION_SHIELDING) && badLevel instanceof Level level){
           LevelChunk chunk = level.getChunkAt(event.getPos());
           CorruptionCapability cap = chunk.getData(ModDataAttachments.CHUNK_CORRUPTION);

           cap.resistance++;
           cap.isSource = false;
           cap.flowers.add(event.getPos());
       }
    }

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack output = event.getCrafting();
        Container input = event.getInventory();

        if (output.is(ModTags.ModItemTags.COLORABLE)){
            for (int i = 0; i < input.getContainerSize(); i++) {
                ItemStack stack = input.getItem(i);

                int colorId = stack.getOrDefault(ModDataComponents.COLOR_ID.get(), -1);
                int color = stack.getOrDefault(ModDataComponents.COLOR.get(), -1);

                if (colorId != -1 && color != -1) {
                    output.set(ModDataComponents.COLOR_ID, colorId);
                    output.set(ModDataComponents.COLOR, color);
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerInteractRightClickItem(UseItemOnBlockEvent event) {
        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        ItemStack itemStack = event.getItemStack();
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        ItemStack item = player.getItemInHand(hand).getItem().getDefaultInstance();

        if (event.isCanceled()) return;

        if(item.getItem() instanceof JarOfBonmeelItem jar && state.is(ModTags.ModBlockTags.BONMEELABLE)) {
            event.setCanceled(true);
            if (jar.useOn(event.getUseOnContext()).equals(InteractionResult.SUCCESS))
                event.setCancellationResult(ItemInteractionResult.SUCCESS);

        }

        if((item.is(ModItems.REBREWED_POTION.get()) || item.is(ModItems.EXTRACTED_BOTTLE.get())) && state.is(Blocks.DIRT)) {
            event.setCancellationResult(ItemInteractionResult.FAIL);
            event.setCanceled(true);

        }

        if(item.is(ItemTags.AXES) && (state.is(ModBlocks.VIVICUS_LOG.get()) || state.is(ModBlocks.VIVICUS_WOOD.get()))) {
            var strippedBlock = AxeItem.STRIPPABLES.get(state.getBlock());
            var state1 = strippedBlock.defaultBlockState()
                    .setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS))
                    .setValue(ModStateProperties.COLOR, state.getValue(ModStateProperties.COLOR));

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, item);
            }
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.setBlock(pos, state1, 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, state1));
            itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

            event.setCancellationResult(ItemInteractionResult.SUCCESS);
            event.setCanceled(true);

        }

        if ((item.is(ModItems.JAR_OF_BONMEEL.get()) || item.is(ModItems.JAR_OF_ACID.get())) && state.is(Blocks.CAULDRON)){
            var cauldronType = item.is(ModItems.JAR_OF_BONMEEL.get()) ? ModBlocks.BONMEEL_FILLED_CAULDRON.get() :  ModBlocks.ACID_FILLED_CAULDRON.get();
            var state1 = cauldronType.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3);
            level.setBlock(pos, state1, 3);
            level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);

            if (!player.isCreative()) player.setItemInHand(hand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));

            event.setCancellationResult(ItemInteractionResult.SUCCESS);
            event.setCanceled(true);

        }

        if (BlockPatternCapability.hasPattern(pos, level) && itemStack.is(Items.GLOW_INK_SAC)){
            BlockPatternCapability.PatternData data = BlockPatternCapability.getPattern(pos, level);
            if (!data.isGlowing()){
                BlockPatternCapability.enableGlowing(level, pos);
                if (!player.isCreative()) itemStack.shrink(1);
                event.setCancellationResult(ItemInteractionResult.SUCCESS);
                event.setCanceled(true);
            }

        }

        if (itemStack.is(Items.FLINT_AND_STEEL) && state.is(Blocks.TORCHFLOWER)){
            itemStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            player.setItemInHand(hand, itemStack);
            level.setBlock(pos, ModBlocks.TORCHFLOWER_AFLAME.get().defaultBlockState().setValue(ModStateProperties.AGE_2, 1), 3);
            event.setCancellationResult(ItemInteractionResult.SUCCESS);
            event.setCanceled(true);

        }


    }



}
