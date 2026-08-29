package net.abraxator.moresnifferflowers.capability;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.abraxator.moresnifferflowers.init.MSFDataAttachments;
import net.abraxator.moresnifferflowers.networking.toClient.CorruptionParticlePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashSet;

public class CorruptionCapability {
    public static final int MAX_RESISTANCE = 5;
    public static final int MAX_CORRUPTION = 150;

    public int count = 0;
    public boolean isSource = false;
    public boolean isNeighbor = false;
    public int resistance = 0;
    public HashSet<BlockPos> flowers = new HashSet<>();

    public static final Codec<CorruptionCapability> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("count").forGetter(data -> data.count),
            Codec.BOOL.fieldOf("isSource").forGetter(data -> data.isSource),
            Codec.BOOL.fieldOf("isNeighbor").forGetter(data -> data.isNeighbor),
            Codec.INT.fieldOf("resistance").forGetter(data -> data.resistance),
            BlockPos.CODEC.listOf().fieldOf("flowers").xmap(HashSet::new, ArrayList::new).forGetter(data -> data.flowers)
    ).apply(instance, (count, isSource, isNeighbor, resistance, flowers) -> {
        CorruptionCapability data = new CorruptionCapability();
        data.count = count;
        data.isSource = isSource;
        data.isNeighbor = isNeighbor;
        data.resistance = resistance;
        data.flowers = flowers;
        return data;
    }));

    public static void sendFlowerParticles(LevelChunk chunk){
        CorruptionCapability cap = chunk.getData(MSFDataAttachments.CHUNK_CORRUPTION.get());
        int count = 0;

        for (BlockPos pos : cap.flowers){
            PacketDistributor.sendToAllPlayers(new CorruptionParticlePacket(pos, true, true));

            count++;
            if (count >= MAX_RESISTANCE) return;
        }
    }

    public static void onCorruptionSource(Level level, BlockPos pos){
        LevelChunk chunk = level.getChunkAt(pos);
        CorruptionCapability cap = chunk.getData(MSFDataAttachments.CHUNK_CORRUPTION.get());

        boolean hasResistance = cap.resistance > 0;
        cap.isSource = !hasResistance;
        cap.isNeighbor = true;
        cap.count++;
        if (hasResistance) {
            CorruptionCapability.sendFlowerParticles(chunk);
        }
    }

    public static void cure(LevelChunk chunk){
        CorruptionCapability cap = chunk.getData(MSFDataAttachments.CHUNK_CORRUPTION.get());

        cap.count = 0;
        cap.isSource = false;
        cap.isNeighbor = false;
    }

    public static boolean areDifferentChunks(Level level, BlockPos pos1, BlockPos pos2) {
        return !level.getChunkAt(pos1).getPos().equals(level.getChunkAt(pos2).getPos());
    }

    public static CorruptionCapability get(LevelChunk chunk) {
        return chunk.getData(MSFDataAttachments.CHUNK_CORRUPTION.get());
    }

    public static void printDebug(LevelChunk chunk){
        if (chunk.getLevel().isClientSide()) return;
        CorruptionCapability cap = chunk.getData(MSFDataAttachments.CHUNK_CORRUPTION.get());
        System.out.println(("Count: " + cap.count + " Resistance: " + cap.resistance + " isSource: " + cap.isSource + " isNeighbor: " + cap.isNeighbor + " Flowers size: " + cap.flowers.size()));
    }
}
