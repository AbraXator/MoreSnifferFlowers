package net.abraxator.moresnifferflowers.mixins;


import net.abraxator.moresnifferflowers.capability.BlockPatternCapability;
import net.abraxator.moresnifferflowers.capability.CorruptionCapability;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagAttachment;
import net.abraxator.moresnifferflowers.client.shaders.ShaderTagRegistry;
import net.abraxator.moresnifferflowers.init.ModDataAttachments;
import net.abraxator.moresnifferflowers.init.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(method = "onRemove", at = @At("HEAD"))
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston, CallbackInfo ci) {
        boolean isDifferent = state.getBlock() != newState.getBlock();

        if (BlockPatternCapability.hasPattern(pos, level)) {
            BlockPatternCapability.removePattern(pos, level);
        }

        if (state.is(ModTags.ModBlockTags.CORRUPTION_SHIELDING) && !level.isClientSide){
            LevelChunk chunk = level.getChunkAt(pos);
            CorruptionCapability cap = CorruptionCapability.get(chunk);
            cap.flowers.remove(pos);
            if (cap.resistance > 0 && cap.flowers.size() < cap.resistance) cap.resistance--;
        }


        if (state.is(ShaderTagRegistry.CUSTOM_RENDER)){
            HashSet<BlockPos> positions = level.getChunkAt(pos).getData(ModDataAttachments.SHADER_BLOCKS).positions();
            positions.remove(pos);
            level.getChunkAt(pos).setData(ModDataAttachments.SHADER_BLOCKS, new ShaderTagAttachment(positions, true));
        }
    }


}
