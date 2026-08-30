package net.abraxator.moresnifferflowers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;

public class MSFClientUtils {

    public static void rebuildChunkSection(BlockPos pos) {
        rebuildChunkSection(SectionPos.of(pos));
    }
    public static void rebuildChunkSection(SectionPos sectionPos) {
        Minecraft.getInstance().levelRenderer.setSectionDirty(sectionPos.x(), sectionPos.y(), sectionPos.z());
    }

}
