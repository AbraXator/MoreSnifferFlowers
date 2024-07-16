package net.abraxator.moresnifferflowers.events;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.init.*;
import net.abraxator.moresnifferflowers.items.JarOfBonmeelItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ForgeEvents {
    @SubscribeEvent
    public static void onUseItemOnBlock(UseItemOnBlockEvent event) {
        var itemStack = event.getPlayer().getItemInHand(event.getHand()).getItem().getDefaultInstance();
        var block = event.getLevel().getBlockState(event.getPos());
        
        if(itemStack.getItem() instanceof JarOfBonmeelItem && block.is(ModTags.ModBlockTags.BONMEELABLE)) {
            event.setCanceled(true);
            ((JarOfBonmeelItem) itemStack.getItem()).useOn(event.getUseOnContext());
        } else if((itemStack.is(ModItems.REBREWED_POTION) || itemStack.is(ModItems.EXTRACTED_BOTTLE)) && block.is(Blocks.DIRT) && event.getUsePhase() == UseItemOnBlockEvent.UsePhase.ITEM_AFTER_BLOCK) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getState().is(ModBlocks.AMBER.get()) && event.getLevel() instanceof ServerLevel serverLevel) {
            fireFlyLogic(event.getState(), serverLevel, event.getPos(), event.getPlayer(), event);
        }

        if(event.getLevel().getBlockEntity(event.getPos()) instanceof GiantCropBlockEntity entity) {
            BlockPos.betweenClosed(entity.pos1, entity.pos2).forEach(blockPos -> {
                event.getLevel().destroyBlock(blockPos, true);
            });
        }
    }

    @SubscribeEvent
    public static void onGetAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        if(event.getAdvancement().id().equals(ResourceLocation.parse("husbandry/obtain_sniffer_egg")) && event.getEntity() instanceof ServerPlayer serverPlayer) {
            ModAdvancementCritters.EARN_SNIFFER_ADVANCEMENT.trigger(serverPlayer);
        }
    }

    private static void fireFlyLogic(BlockState state, ServerLevel serverLevel, BlockPos pos, Player player, ICancellableEvent event) {
        List<ItemStack> list = Block.getDrops(state, serverLevel, pos, serverLevel.getBlockEntity(pos), player, player.getMainHandItem());
        RandomSource randomSource = serverLevel.getRandom();
        if ((randomSource.nextInt(list.size())) == list.size()) {
            event.setCanceled(true);
            serverLevel.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            serverLevel.sendParticles(
                    new BlockParticleOption(ParticleTypes.BLOCK, state),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                   10, 0, 0, 0, 0);
            Vec3 vecPos = pos.getCenter();
            serverLevel.sendParticles(
                    ModParticles.FLY.get(),
                    vecPos.x() + (randomSource.nextInt(10) - 5) / 10,
                    vecPos.y() + (randomSource.nextInt(10) - 5) / 10,
                    vecPos.z() + (randomSource.nextInt(10) - 5) / 10,
                    0, 0, 0, 0, 0);
        }
    }
}
