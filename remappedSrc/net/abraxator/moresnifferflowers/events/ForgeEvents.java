package net.abraxator.moresnifferflowers.events;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.blocks.GiantCropBlock;
import net.abraxator.moresnifferflowers.blocks.blockentities.GiantCropBlockEntity;
import net.abraxator.moresnifferflowers.init.ModAdvancementCritters;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModDamageTypes;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEvents {
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getSource().is(DamageTypes.IN_WALL)) {
            World level = event.getEntity().level();
            BlockPos blockPos = BlockPos.ofFloored(event.getEntity().getEyePosition());
            if(level.getBlockState(blockPos).getBlock() instanceof GiantCropBlock giantCropBlock) {
                event.setCanceled(true);
                event.getEntity().hurt(ModDamageTypes.getDamageSource(level, ModDamageTypes.VEGGIES), 1.5F);
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if(event.getState().is(ModBlocks.AMBER.get()) && event.getLevel() instanceof ServerWorld serverLevel) {
            fireFlyLogic(event.getState(), serverLevel, event.getPos(), event.getPlayer(), event);
        }

        if(event.getLevel().getBlockEntity(event.getPos()) instanceof GiantCropBlockEntity entity) {
            BlockPos.iterate(entity.pos1, entity.pos2).forEach(blockPos -> {
                event.getLevel().destroyBlock(blockPos, true);
            });
        }
    }

    @SubscribeEvent
    public static void onGetAdvancement(AdvancementEvent.AdvancementEarnEvent event) {
        if(event.getAdvancement().id().equals(new Identifier("minecraft", "husbandry/obtain_sniffer_egg")) && event.getEntity() instanceof ServerPlayerEntity serverPlayer) {
            ModAdvancementCritters.EARN_SNIFFER_ADVANCEMENT.get().trigger(serverPlayer);
        }
    }

    private static void fireFlyLogic(BlockState state, ServerWorld serverLevel, BlockPos pos, PlayerEntity player, ICancellableEvent event) {
        List<ItemStack> list = Block.getDroppedStacks(state, serverLevel, pos, serverLevel.getBlockEntity(pos), player, player.getMainHandStack());
        Random randomSource = serverLevel.getRandom();
        if ((randomSource.nextInt(list.size())) == list.size()) {
            event.setCanceled(true);
            serverLevel.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
            serverLevel.spawnParticles(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, state),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                   10, 0, 0, 0, 0);
            Vec3d vecPos = pos.toCenterPos();
            serverLevel.spawnParticles(
                    ModParticles.FLY.get(),
                    vecPos.getX() + (randomSource.nextInt(10) - 5) / 10,
                    vecPos.getY() + (randomSource.nextInt(10) - 5) / 10,
                    vecPos.getZ() + (randomSource.nextInt(10) - 5) / 10,
                    0, 0, 0, 0, 0);
        }
    }
}
