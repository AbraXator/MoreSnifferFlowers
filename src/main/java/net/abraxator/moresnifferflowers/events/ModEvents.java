package net.abraxator.moresnifferflowers.events;


import net.abraxator.moresnifferflowers.MoreSnifferFlowers;
import net.abraxator.moresnifferflowers.entities.Bobling;
import net.abraxator.moresnifferflowers.init.ModEntityTypes;
import net.abraxator.moresnifferflowers.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDestroyBlockEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = MoreSnifferFlowers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityTypes.BOBLING.get(), Bobling.createAttributes().build());
    }

    @SubscribeEvent
    public static void onLivingDestroyBlock(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().level();
        BlockState state = event.getState();
        BlockPos pos = event.getPos();
        if(level instanceof ServerLevel serverLevel) {
            List<ItemStack> list = Block.getDrops(state, serverLevel, pos, serverLevel.getBlockEntity(pos), event.getPlayer(), event.getPlayer().getMainHandItem());

            if((level.random.nextInt(list.size()) + 1) == list.size()) {
                event.setCanceled(true);
                level.setBlock(pos, state, 3);
                serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), pos.getX(), pos.getY(), pos.getZ(), 10, 0, 0, 0, 0);
                serverLevel.sendParticles(ModParticles.FLY, pos.getX(), pos.getY(), pos.getZ(), 5)
            }
        }
    }
}
