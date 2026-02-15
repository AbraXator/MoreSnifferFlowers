package net.abraxator.moresnifferflowers.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.abraxator.moresnifferflowers.networking.toClient.SyncBlockPatternsPacket;
import net.abraxator.moresnifferflowers.networking.toClient.SyncGluedPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class GluedCapability {
    public static final Codec<GluedCapability> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("is_glued").forGetter(data -> data.isGlued)
            ).apply(instance, (glued) -> {
                GluedCapability data = new GluedCapability();
                data.isGlued = glued;
                return data;
            }));

    public boolean isGlued;

    public static void setAndSync(LivingEntity entity, boolean isGlued, boolean playSound) {
        Level level = entity.level();
        if (level.isClientSide()) return;

        if (playSound) playSound(level, entity);

        GluedCapability cap = entity.getData(ModDataAttachments.GLUED.get());

        cap.isGlued = isGlued;
        cap.sync(entity);
    }

    public void sync(LivingEntity entity){
        PacketDistributor.sendToAllPlayers(new SyncGluedPacket(isGlued, entity.getId()));
    }


    public static void playSound(Level level, Entity entity){
        level.playSound(null, entity.getOnPos(), SoundEvents.BUBBLE_COLUMN_BUBBLE_POP, SoundSource.PLAYERS, 5.0F, 0.02F + level.random.nextFloat() * 0.01F);
    }

}
