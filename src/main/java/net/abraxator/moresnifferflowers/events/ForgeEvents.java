package net.abraxator.moresnifferflowers.events;

import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.init.ModBlocks;
import net.abraxator.moresnifferflowers.init.ModItems;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ForgeEvents {
    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().level();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();
        RandomSource randomSource = level.getRandom();
        if(state.is(ModBlocks.AMBER.get())) {
            if (level instanceof ServerLevel serverLevel) {
                List<ItemStack> list = Block.getDrops(state, serverLevel, pos, serverLevel.getBlockEntity(pos), event.getPlayer(), event.getPlayer().getMainHandItem());
                if ((randomSource.nextInt(list.size())) == list.size()) {
                    event.setCanceled(true);
                    level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX(), pos.getY(), pos.getZ(), 10, 0, 0, 0, 0);
                    Vec3 vecPos = pos.getCenter();
                    serverLevel.sendParticles(ModParticles.FLY.get(), vecPos.x() + (randomSource.nextInt(10) - 5) / 10, vecPos.y() + (randomSource.nextInt(10) - 5) / 10, vecPos.z() + (randomSource.nextInt(10) - 5) / 10, 0, 0, 0, 0, 0);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onItemStackedOnOther(ItemStackedOnOtherEvent event) {

    }
}
