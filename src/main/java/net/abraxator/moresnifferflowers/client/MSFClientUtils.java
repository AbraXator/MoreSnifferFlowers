package net.abraxator.moresnifferflowers.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;

public class MSFClientUtils {

    public static void rebuildChunkSection(BlockPos pos) {
        SectionPos sectionPos = SectionPos.of(pos);
        Minecraft.getInstance().levelRenderer.setSectionDirty(sectionPos.x(), sectionPos.y(), sectionPos.z());
    }
}
